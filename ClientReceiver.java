package tp1Udp;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.*;

public class ClientReceiver extends Thread{
 
	 int PORT = 7331;
     DatagramSocket sock;
     String hostname;
    
    public ClientReceiver(DatagramSocket s, String h) {
        sock = s;
        hostname = h;
    }
    
    
	public void run() {
		while (true) {
			byte[] buf = new byte[1024];
            try {
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				sock.receive(packet);
				String content = new String(buf, 0, packet.getLength());
				System.out.println("client received : "+content);
            } catch(Exception e) {
                System.err.println(e);
            }
        }
	}

}
