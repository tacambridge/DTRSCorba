/*package common.remote;

import java.util.Properties;
import java.util.concurrent.ConcurrentMap;

import junit.framework.TestCase;

import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import common.local.Show;

public class TestBoxOffice extends TestCase {

	MyServer.MyService boxOffice;
	private final String TEST_SHOW_ID = "MTL111";
	private final int TEST_CUSTOMER_ID = 111111;

	public void setUp() {
		try {
			String boxOfficeName = "MTL";
			Properties props = System.getProperties();
			props.setProperty("org.omg.CORBA.ORBClass", "com.sun.corba.se.internal.POA.POAORB");
			props.setProperty("org.omg.CORBA.ORBSingletonClass", "com.sun.corba.se.internal.corba.ORBSingleton");
			org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(new String[1], props);

			org.omg.CORBA.Object ncobj;
			ncobj = orb.resolve_initial_references("NameService");
			NamingContextExt nc = NamingContextExtHelper.narrow(ncobj);
			org.omg.CORBA.Object obj = nc.resolve_str("BO_" + boxOfficeName);
			boxOffice = MyServer.MyServiceHelper.narrow(obj);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void tearDown () {
		boxOffice = null;
	}

	
	 * Reserve ticket
	 * Expected Result: the ticket is reserved.
	 
	public void testReserve() {
		try {
			int testNumberOfTickets = 5;
			boxOffice.reserve(TEST_CUSTOMER_ID, TEST_SHOW_ID, testNumberOfTickets);
			int actualNumberOfTickets = ((ConcurrentMap<String, Show>) boxOffice.getShowList()).get(TEST_SHOW_ID).getNumberOfTickets();
			int actualNumberOfRemainingTickets = ((ConcurrentMap<String, Show>) boxOffice.getShowList()).get(TEST_SHOW_ID).getNumberOfRemainingTickets();
			assertEquals(testNumberOfTickets, actualNumberOfTickets - actualNumberOfRemainingTickets);
			boxOffice.cancel(TEST_CUSTOMER_ID, TEST_SHOW_ID, testNumberOfTickets);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	
	 * Reserving more tickets than available
	 * Expected result : no tickets are reserved
	 
	public void testReserveAboveQuantity() {
		boolean thrown = false;
		try {
			int actualNumberOfRemainingTicketsBefore = ((ConcurrentMap<String, Show>) boxOffice.getShowList()).get(TEST_SHOW_ID).getNumberOfRemainingTickets();
			int testNumberOfTickets = actualNumberOfRemainingTicketsBefore + 1;
			boxOffice.reserve(TEST_CUSTOMER_ID, TEST_SHOW_ID, testNumberOfTickets);
			int actualNumberOfRemainingTicketsAfter = ((ConcurrentMap<String, Show>) boxOffice.getShowList()).get(TEST_SHOW_ID).getNumberOfRemainingTickets();
			assertEquals(actualNumberOfRemainingTicketsBefore, actualNumberOfRemainingTicketsAfter);
			boxOffice.cancel(TEST_CUSTOMER_ID, TEST_SHOW_ID, testNumberOfTickets);
		} catch (Exception e) {
			e.printStackTrace();
			thrown = true;
		}
		assertTrue(thrown);
	}

	
	 * Cancel ticket
	 * Expected Result: the ticket is canceled.
	 
	public void testCancel() {
		try {
			int testNumberOfTickets = 5;
			int numberOfUncanceledTickets = 1;
			boxOffice.reserve(TEST_CUSTOMER_ID, TEST_SHOW_ID, testNumberOfTickets);
			boxOffice.cancel(TEST_CUSTOMER_ID, TEST_SHOW_ID, testNumberOfTickets - numberOfUncanceledTickets);
			int actualNumberOfTickets = ((ConcurrentMap<String, Show>) boxOffice.getShowList()).get(TEST_SHOW_ID).getNumberOfTickets();
			int actualNumberOfRemainingTickets = ((ConcurrentMap<String, Show>) boxOffice.getShowList()).get(TEST_SHOW_ID).getNumberOfRemainingTickets();
			assertEquals(numberOfUncanceledTickets, actualNumberOfTickets - actualNumberOfRemainingTickets);
			boxOffice.cancel(TEST_CUSTOMER_ID, TEST_SHOW_ID, testNumberOfTickets - numberOfUncanceledTickets);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	
	 * Canceling more tickets than user bought
	 * Expected Result: Only the tickets reserved are reimbursed, not more.
	 
	public void testCancelAboveQuantity() {
		try {
			int testNumberOfTickets = 5;
			boxOffice.reserve(TEST_CUSTOMER_ID, TEST_SHOW_ID, testNumberOfTickets);
			boxOffice.cancel(TEST_CUSTOMER_ID, TEST_SHOW_ID, testNumberOfTickets + 1);
			int actualNumberOfTickets = ((ConcurrentMap<String, Show>) boxOffice.getShowList()).get(TEST_SHOW_ID).getNumberOfTickets();
			int actualNumberOfRemainingTickets = ((ConcurrentMap<String, Show>) boxOffice.getShowList()).get(TEST_SHOW_ID).getNumberOfRemainingTickets();
			assertEquals(actualNumberOfTickets, actualNumberOfRemainingTickets);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	
	 * Check number of available tickets after reservation
	 * Expected result: the correct number of available tickets is returned.
	 
	public void testCheck() {
		try {
			int testNumberOfTickets = 5;
			boxOffice.reserve(TEST_CUSTOMER_ID, TEST_SHOW_ID, testNumberOfTickets);
			int actualNumberOfTickets = ((ConcurrentMap<String, Show>) boxOffice.getShowList()).get(TEST_SHOW_ID).getNumberOfTickets();
			int actualNumberOfRemainingTickets = ((ConcurrentMap<String, Show>) boxOffice.getShowList()).get(TEST_SHOW_ID).getNumberOfRemainingTickets();
			assertEquals(actualNumberOfTickets - 5, actualNumberOfRemainingTickets);
			boxOffice.cancel(TEST_CUSTOMER_ID, TEST_SHOW_ID, testNumberOfTickets);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}*/