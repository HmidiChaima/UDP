package tp1Udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;

import tp1Udp.models.groupe;
import tp1Udp.models.message;
import tp1Udp.models.student;

public class Server extends Thread {

	int port = 7331;
	DatagramSocket socket;

	ArrayList<message> messagesList;
	ArrayList<student> studentsList;
	ArrayList<groupe> groupsList;

	public Server() throws Exception {
		socket = new DatagramSocket(port);
		studentsList = new ArrayList<student>();
		messagesList = new ArrayList<message>();
		groupsList = new ArrayList<groupe>();
		student a = new student("hammadi azaiez", "hammadi", 3);
		student b = new student("ali gamoun", "ali", 3);
		student c = new student("achref bouzayeni", "achref", 3);
		student d = new student("khalil bouzayen", "khalil", 3);
		studentsList.add(a);
		studentsList.add(b);
		studentsList.add(c);
		studentsList.add(d);

		groupe g1 = new groupe("groupe A");
		groupe g2 = new groupe("groupe B");
		g1.add(d);
		groupsList.add(g1);
		groupsList.add(g2);
	}

	public void run() {
		byte[] buf = new byte[1024];
		while (true) {
			try {
				// System.out.println("server waiting");
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				String content = new String(buf, 0, packet.getLength());
				// System.out.print ("server received content from : "+content + " from :");

				SendResponse(content, packet);

			} catch (Exception e) {

			}
		}
	}

	public void SendResponse(String content, DatagramPacket packet) throws Exception {
		try {
			InetAddress addr = packet.getAddress();
			int port = packet.getPort();
			String ip = addr.toString();
			// System.out.println(ip + " : " + port);

			if (content.startsWith("##")) {
				String data = content.substring(2);
				for (student st : studentsList) {
					if (st.getLogin().equals(data.strip())) {
						handleSend("Welcome " + st.name + " you are Connected", packet, addr, port);
						st.setIp(ip);
						st.setPort(port);
						st.setConnected(true);
						return;
					}
				}
				handleSend("sorry " + data + " you are not registered", packet, addr, port);

			} else if (content.startsWith("#LISTE_EDTS")) {
				String data = content.substring(11);
				if (isConnected(ip, port)) {
					String payload = "";
					for (student st : studentsList) {
						if (st.isConnected()) {
							payload += st.toString() + "\n";
						}
					}
					handleSend("List of students : \n " + payload, packet, addr, port);
				} else {
					handleSend("sorry you are not connected", packet, addr, port);
				}
			} else if (content.startsWith("#HISTO")) {
				if (isConnected(ip, port)) {
					String payload = "";
					for (message ms : messagesList) {
						if (ms.sender.getLogin().equals(getStudentByIpAndPort(ip, port).getLogin())) {
							payload += ms.toString() + "\n";
						}
					}
					handleSend("List of messages : \n " + payload, packet, addr, port);
				} else {
					handleSend("sorry you are not connected", packet, addr, port);
				}
			} else if (content.startsWith("@#")) {
				String[] data = content.split("@#");
				if (isConnected(ip, port)) {
					String login = data[1];
					String messageStr = data[2];

					for (student st : studentsList) {
						if (st.getLogin().equals(login)) {
							student sender = getStudentByIpAndPort(ip, port);
							message m = new message(sender, st, messageStr);
							messagesList.add(m);
							if (st.isConnected()) {
								handleSend(m.toString(), packet, InetAddress.getByName(st.getIp().substring(1)),
										st.getPort());
								handleSend("message transfered :  " + m.toString(), packet, addr, port);
							} else {
								handleSend("client " + m.receiver.getLogin() + " is offline, message has been saved",
										packet, addr, port);
							}
							return;
						}
					}
					handleSend("Error, Login : " + login + " not found", packet, addr, port);
				} else {
					handleSend("sorry you are not connected", packet, addr, port);
				}
			} else if (content.equals("#GROUPS")) {
				if (isConnected(ip, port)) {
					String payload = "";
					for (groupe gp : groupsList) {
						payload += gp.toString() + "\n";
					}
					handleSend("List of groups : \n " + payload, packet, addr, port);
				} else {
					handleSend("sorry you are not connected", packet, addr, port);
				}
			} else if (content.startsWith("#GROUP#")) {
				if (isConnected(ip, port)) {
					String groupeName = content.substring(7);
					if (groupeName.isEmpty() || groupeName.isBlank()) {
						handleSend("you must provied a valid groupe name", packet, addr, port);
						return;
					}
					groupe x = new groupe(groupeName);
					groupsList.add(x);
					handleSend("List new groupe with name : " + groupeName, packet, addr, port);
				} else {
					handleSend("sorry you are not connected", packet, addr, port);
				}
			} else if (content.startsWith("#>")) {
				if (isConnected(ip, port)) {
					String groupeTitre = content.substring(2);
					for (groupe gp : groupsList) {
						if (gp.getTitre().equals(groupeTitre)) {
							student sender = getStudentByIpAndPort(ip, port);
							handleSend("Welcome " + sender.name + " to your new groupe : " + groupeTitre, packet, addr,
									port);
							gp.list.add(sender);
							return;
						}
					}
					handleSend("sorry, there is no group named " + groupeTitre, packet, addr, port);
				} else {
					handleSend("sorry you are not connected", packet, addr, port);
				}
			} else if (content.startsWith("#ETDS#")) {
				if (isConnected(ip, port)) {
					String groupeTitre = content.substring(6);
					for (groupe gp : groupsList) {
						if (gp.getTitre().equals(groupeTitre)) {
							String payload = "";
							for (student st : gp.getList()) {
								payload += st.toString() + "\n";
							}
							handleSend("List of students in groupe" + groupeTitre +" : \n " + payload, packet, addr, port);
							return;
						}
					}
					handleSend("sorry, there is no group named " + groupeTitre, packet, addr, port);
				} else {
					handleSend("sorry you are not connected", packet, addr, port);
				}
			} else {
				handleSend("Unknown message type", packet, addr, port);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public boolean isConnected(String ip, int port) {
		for (student st : studentsList) {
			if (st.getIp().equals(ip) && st.getPort() == port) {
				return true;
			}
		}
		return false;
	}

	public student getStudentByIpAndPort(String ip, int port) {
		for (student st : studentsList) {
			if (st.getIp().equals(ip) && st.getPort() == port) {
				return st;
			}
		}
		return null;
	}

	public void handleSend(String data, DatagramPacket packet, InetAddress addressInet, int port) throws Exception {
		byte buf[] = data.getBytes();
		packet = new DatagramPacket(buf, buf.length, addressInet, port);
		socket.send(packet);
	}

	public static void main(String args[]) throws Exception {
		Server s = new Server();
		s.start();
	}

}