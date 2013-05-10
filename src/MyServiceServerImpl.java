import java.util.Iterator;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import udp.IUpdatable;
import udp.UDPClient;
import udp.UDPServer;
import MyServer.MyServicePackage.DTRSException;

import common.local.Exchange;
import common.local.Show;
import common.local.ShowFactory;

/**
 * This class is the implementation object for your IDL interface.
 *
 * Let the Eclipse complete operations code by choosing 'Add unimplemented methods'.
 */
public class MyServiceServerImpl extends MyServer.MyServicePOA {

	private UDPServer udpServer = new UDPServer();
	private ConcurrentMap<String, Show> showList = new ConcurrentHashMap<String, Show>();
	private static final String PREFIX = "BO_";
	private String boxOffice;

	/**
	 * Constructor for MyServiceServerImpl 
	 */
	public MyServiceServerImpl() {
	}

	@Override
	public String getBoxOffice() {
		return boxOffice;
	}

	@Override
	public int getUDPPort() throws DTRSException {
		if(udpServer != null) {
			udpServer.start();
			return udpServer.getPort();
		}
		else
			throw new DTRSException("UDPServer is null");
	}


	@Override
	public void initializeBoxOffice(String boxOffice) throws DTRSException {
		if(this.boxOffice == null) {
			if(boxOffice.length() == 3)
				this.boxOffice = boxOffice.toUpperCase();
			else 
				throw new DTRSException("Invalid box office.");

			this.boxOffice = PREFIX + boxOffice;

			try {
				if(this.showList.isEmpty()) {
					this.showList.putAll(ShowFactory.getInstance().getListOfShows(boxOffice));
				}
			} catch (Exception e) {
				throw new DTRSException("initialize fail " + e.getMessage());
			}
		}
	}

	@Override
	public synchronized String printBoxOfficeRecords(int customerID) {
		String record = "\n=======================";
		record += "\nCustomer ID: " + customerID;
		record += "\nBox Office Shows";
		record += "\n=======================\n";	
		Iterator<Entry<String, Show>> it = showList.entrySet().iterator();
		while (it.hasNext()) {
			ConcurrentMap.Entry<String, Show> pairs = (ConcurrentMap.Entry<String, Show>)it.next();
			Show show = pairs.getValue();
			record += "\n" + show.printShowRecords();
		}	
		return record;
	}

	@Override
	public void reserve(int customerID, String showID, int numberOfTickets) throws DTRSException {
		System.out.println("reserve " + showID + " " + numberOfTickets);
		Show show = showList.get(showID);
		if(show != null)
			try {
				show.sellTickets(customerID, numberOfTickets);
			} catch (Exception e) {
				throw new DTRSException(getClass() + " reserve fail");
			}
	}

	@Override
	public void cancel(int customerID, String showID, int numberOfTickets) throws DTRSException {
		Show show = showList.get(showID);
		if(show != null)
			try {
				show.reimburseTickets(customerID, numberOfTickets);
			} catch (Exception e) {
				throw new DTRSException(getClass() + "cancel fail");
			}
	}

	@Override
	public int check(String showID) {
		Show show = showList.get(showID);
		if(show != null)
			return show.getNumberOfRemainingTickets();
		else
			return -1; //negative value means show not there 
	}

	@Override
	public synchronized void exchange(int customerID, String reservedShowID,
			int reservedTickets, String desiredShowID, int desiredTickets) throws DTRSException {

		try {
			int port = getBoxOfficeByShowID(desiredShowID).getUDPPort();
			IUpdatable exchangeMessage = new Exchange(customerID, desiredShowID, desiredTickets);
			UDPClient client = new UDPClient(port);
			client.sendData(exchangeMessage);
			exchangeMessage = client.receiveData();
			udpServer.join();//TODO necessary?

			boolean otherBoxOfficeCanExchange = ((Exchange) exchangeMessage).isCanExchange();

			if(otherBoxOfficeCanExchange) {
				this.cancel(customerID, reservedShowID, reservedTickets);
			}
		} catch (Exception e) {
			System.out.println(getClass() + " e.getMessage() " + e.getMessage());
			throw new DTRSException(e.getMessage());
		}

	}

	public synchronized boolean canExchange(int customerID, String showID, int numberOfTickets) throws DTRSException {
		//if exception is caught, canExchange will not become true
		boolean canExchange = false;
		try {
			reserve(customerID, showID, numberOfTickets);
			canExchange = true;
		} catch (Exception e) {
		}
		return canExchange;
	}

	private MyServer.MyService getBoxOfficeByShowID(String desiredShowID) throws InvalidName, NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName, DTRSException {

		String boxOffice = desiredShowID.substring(0, 3);

		Properties props = System.getProperties();
		props.setProperty("org.omg.CORBA.ORBClass", "com.sun.corba.se.internal.POA.POAORB");
		props.setProperty("org.omg.CORBA.ORBSingletonClass", "com.sun.corba.se.internal.corba.ORBSingleton");
		org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(new String[1], props);

		org.omg.CORBA.Object ncobj = orb.resolve_initial_references("NameService");
		NamingContextExt nc = NamingContextExtHelper.narrow(ncobj);

		org.omg.CORBA.Object obj = nc.resolve_str("BO_" + boxOffice);

		MyServer.MyService target = MyServer.MyServiceHelper.narrow(obj);
		target.initializeBoxOffice(boxOffice);
		return target;
	}
}
