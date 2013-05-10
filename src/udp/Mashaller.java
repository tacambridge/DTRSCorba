package udp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 
 * @author Terri-Anne
 *
 */
public class Mashaller implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3248576832359417555L;

	/**
	 * Serialize to a byte array. Get the bytes of the serialized object
	 * @param object
	 * @return
	 * @throws IOException
	 */
	public static byte[] marshall(IUpdatable object) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
		ObjectOutput out = new ObjectOutputStream(bos) ;
		out.writeObject(object);
		out.close();
		byte[] sendData = bos.toByteArray();
		return sendData;
	}

	/**
	 * Deserialize from a byte array
	 * @param data
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static IUpdatable unmarshall(byte[] data) throws IOException, ClassNotFoundException { 
		ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data));
		IUpdatable object = (IUpdatable) in.readObject();
		in.close();
		return object;
	}

}
