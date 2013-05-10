package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPServer extends Thread
{
	private static int port = 9876;
	private int myPort; //TODO CHANGE NAME
	private boolean stopServer = true;

	public UDPServer() {
		myPort = ++port;
	}

	public int getPort() {
		return this.myPort;
	}

	public void run()
	{
		try {
			stopServer = false;

			System.out.println("Started UDP Server...");

			DatagramSocket serverSocket = new DatagramSocket(port);
			byte[] receiveData = new byte[1024];

			while(!stopServer)
			{
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				InetAddress IPAddress = receivePacket.getAddress();
				int port = receivePacket.getPort();

				IUpdatable object = Mashaller.unmarshall(receivePacket.getData());

				object.update();

				byte[] sendData = Mashaller.marshall(object);

				DatagramPacket sendPacket =	new DatagramPacket(sendData, sendData.length, IPAddress, port);
				serverSocket.send(sendPacket);

			}
		} catch (Exception e) {
			//TODO 
			System.out.println("udp exception");
		}
	}

	public void stope() {
		this.stopServer = true;
	}
}