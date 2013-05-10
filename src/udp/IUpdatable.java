package udp;

import java.io.Serializable;

import MyServer.MyServicePackage.DTRSException;

/**
 * Objects passed over UDP protocol must implement this interface
 * @author Terri-Anne
 *
 */
public interface IUpdatable extends Serializable {
	
	/**
	 * This method is called after the object is sent to the server and before it is sent back to the client
	 * @throws DTRSException 
	 */
	public void update() throws DTRSException;

}
