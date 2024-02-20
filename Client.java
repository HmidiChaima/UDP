package tp1Udp;

import java.net.DatagramSocket;

public class Client {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("start client ");
		DatagramSocket sock=new DatagramSocket();
		ClientSender cs=new ClientSender(sock,"localhost");
		cs.start();
		ClientReceiver cr=new ClientReceiver(sock,"localhost");
		cr.start();
	}

}

