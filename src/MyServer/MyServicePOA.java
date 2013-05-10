package MyServer;


/**
* MyServer/MyServicePOA.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Users/Terri-Anne/Desktop/workspace-classic/DTRSCorbaAssignment/simple.idl
* Friday, November 4, 2011 4:25:21 PM PDT
*/

public abstract class MyServicePOA extends org.omg.PortableServer.Servant
 implements MyServer.MyServiceOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("getUDPPort", new java.lang.Integer (0));
    _methods.put ("initializeBoxOffice", new java.lang.Integer (1));
    _methods.put ("getBoxOffice", new java.lang.Integer (2));
    _methods.put ("printBoxOfficeRecords", new java.lang.Integer (3));
    _methods.put ("reserve", new java.lang.Integer (4));
    _methods.put ("cancel", new java.lang.Integer (5));
    _methods.put ("check", new java.lang.Integer (6));
    _methods.put ("exchange", new java.lang.Integer (7));
    _methods.put ("canExchange", new java.lang.Integer (8));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // MyServer/MyService/getUDPPort
       {
         try {
           int $result = (int)0;
           $result = this.getUDPPort ();
           out = $rh.createReply();
           out.write_ulong ($result);
         } catch (MyServer.MyServicePackage.DTRSException $ex) {
           out = $rh.createExceptionReply ();
           MyServer.MyServicePackage.DTRSExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 1:  // MyServer/MyService/initializeBoxOffice
       {
         try {
           String boxOffice = in.read_string ();
           this.initializeBoxOffice (boxOffice);
           out = $rh.createReply();
         } catch (MyServer.MyServicePackage.DTRSException $ex) {
           out = $rh.createExceptionReply ();
           MyServer.MyServicePackage.DTRSExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 2:  // MyServer/MyService/getBoxOffice
       {
         String $result = null;
         $result = this.getBoxOffice ();
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 3:  // MyServer/MyService/printBoxOfficeRecords
       {
         int customerID = in.read_ulong ();
         String $result = null;
         $result = this.printBoxOfficeRecords (customerID);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 4:  // MyServer/MyService/reserve
       {
         try {
           int customerID = in.read_ulong ();
           String showID = in.read_string ();
           int numberOfTickets = in.read_ulong ();
           this.reserve (customerID, showID, numberOfTickets);
           out = $rh.createReply();
         } catch (MyServer.MyServicePackage.DTRSException $ex) {
           out = $rh.createExceptionReply ();
           MyServer.MyServicePackage.DTRSExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 5:  // MyServer/MyService/cancel
       {
         try {
           int customerID = in.read_ulong ();
           String showID = in.read_string ();
           int numberOfTickets = in.read_ulong ();
           this.cancel (customerID, showID, numberOfTickets);
           out = $rh.createReply();
         } catch (MyServer.MyServicePackage.DTRSException $ex) {
           out = $rh.createExceptionReply ();
           MyServer.MyServicePackage.DTRSExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 6:  // MyServer/MyService/check
       {
         String showID = in.read_string ();
         int $result = (int)0;
         $result = this.check (showID);
         out = $rh.createReply();
         out.write_ulong ($result);
         break;
       }

       case 7:  // MyServer/MyService/exchange
       {
         try {
           int customerID = in.read_ulong ();
           String reservedShowID = in.read_string ();
           int reservedTickets = in.read_ulong ();
           String desiredShowID = in.read_string ();
           int desiredTickets = in.read_ulong ();
           this.exchange (customerID, reservedShowID, reservedTickets, desiredShowID, desiredTickets);
           out = $rh.createReply();
         } catch (MyServer.MyServicePackage.DTRSException $ex) {
           out = $rh.createExceptionReply ();
           MyServer.MyServicePackage.DTRSExceptionHelper.write (out, $ex);
         }
         break;
       }

       case 8:  // MyServer/MyService/canExchange
       {
         try {
           int customerID = in.read_ulong ();
           String showID = in.read_string ();
           int numberOfTickets = in.read_ulong ();
           boolean $result = false;
           $result = this.canExchange (customerID, showID, numberOfTickets);
           out = $rh.createReply();
           out.write_boolean ($result);
         } catch (MyServer.MyServicePackage.DTRSException $ex) {
           out = $rh.createExceptionReply ();
           MyServer.MyServicePackage.DTRSExceptionHelper.write (out, $ex);
         }
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:MyServer/MyService:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public MyService _this() 
  {
    return MyServiceHelper.narrow(
    super._this_object());
  }

  public MyService _this(org.omg.CORBA.ORB orb) 
  {
    return MyServiceHelper.narrow(
    super._this_object(orb));
  }


} // class MyServicePOA