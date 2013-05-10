package common.local;

import java.io.Serializable;
import java.util.Properties;

import org.omg.CORBA.ORBPackage.InvalidName;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.NamingContextPackage.CannotProceed;
import org.omg.CosNaming.NamingContextPackage.NotFound;

import udp.IUpdatable;
import MyServer.MyServicePackage.DTRSException;

public class Exchange implements Serializable, IUpdatable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7072761329002344797L;
	private boolean canExchange = false;
	private int customerID;
	private String showID;
	private int numberOfTickets;

	public Exchange(int customerID, String showID, int numberOfTickets) {
		super();
		this.customerID = customerID;
		this.showID = showID;
		this.numberOfTickets = numberOfTickets;
	}

	public boolean isCanExchange() {
		return canExchange;
	}

	@Override
	public void update() throws DTRSException {
		try {
			MyServer.MyService boxOffice = getBoxOfficeByShowID(showID);
			canExchange = boxOffice.canExchange(customerID, showID, numberOfTickets);
		} catch (Exception e) {
			throw new DTRSException(getClass() + " update fail");
		}
	}
	
	private MyServer.MyService getBoxOfficeByShowID(String desiredShowID) throws InvalidName, NotFound, CannotProceed, org.omg.CosNaming.NamingContextPackage.InvalidName {

		String boxOffice = desiredShowID.substring(0, 3);

		Properties props = System.getProperties();
		props.setProperty("org.omg.CORBA.ORBClass", "com.sun.corba.se.internal.POA.POAORB");
		props.setProperty("org.omg.CORBA.ORBSingletonClass", "com.sun.corba.se.internal.corba.ORBSingleton");
		org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(new String[1], props);

		org.omg.CORBA.Object ncobj = orb.resolve_initial_references("NameService");
		NamingContextExt nc = NamingContextExtHelper.narrow(ncobj);

		org.omg.CORBA.Object obj = nc.resolve_str("BO_" + boxOffice);

		MyServer.MyService target = MyServer.MyServiceHelper.narrow(obj);

		return target;
	}

}
