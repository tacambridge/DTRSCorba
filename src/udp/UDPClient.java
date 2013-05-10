package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient
{
	private int port; //= 9876;
	private IUpdatable object;
	
	public UDPClient(int port)
	{
		this.port = port;
	}
	
	public void sendData(IUpdatable object) throws Exception {
		flush();
		start(object);
	}
	
	public IUpdatable receiveData() {
		return object;
	}
	
	private void flush() {
		this.object = null;
	}
	
	private void start(IUpdatable object)
	{
		try {
		byte[] sendData = Mashaller.marshall(object);
		byte[] receiveData = new byte[sendData.length];
		
		DatagramSocket clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("localhost");
			
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		clientSocket.send(sendPacket);
		
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);
		
		this.object = Mashaller.unmarshall(receivePacket.getData());
		
		clientSocket.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}
	
}