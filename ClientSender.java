package tp1Udp;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

public class ClientSender extends Thread {

	int PORT = 7331;
    DatagramSocket sock;
    String hostname;
   
   public ClientSender(DatagramSocket s, String h) {
       sock = s;
       hostname = h;
   }
   
	void sendMessage(String s) throws Exception {
        byte buf[] = s.getBytes();
        InetAddress address = InetAddress.getByName(hostname);
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, PORT);
        sock.send(packet);
    }
	
	public void run() {
        do {
            try {
            	Scanner scanner = new Scanner(System.in);
            	System.out.println(" ");
            	System.out.println("Enter your message ");
                String name = scanner.nextLine();
                sendMessage(name);
        
            } catch (Exception e) { }
            
         
        } while (true);
	}
}
