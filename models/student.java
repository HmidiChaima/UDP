package tp1Udp.models;

public class student {

	public String name;
	public String login;
	public int level;
	public boolean isConnected;
	public String ip;
	public int port;
	
	public student() {
		// TODO Auto-generated constructor stub
	}

	public student(String name, String login, int level, boolean isConnected, String ip, int port) {
		super();
		this.name = name;
		this.login = login;
		this.level = level;
		this.isConnected = isConnected;
		this.ip = ip;
		this.port = port;
	}
	public student(String name, String login, int level) {
		super();
		this.name = name;
		this.login = login;
		this.level = level;
		this.isConnected = false;
		this.ip = "x.x.x.x";
		this.port = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public String toString() {
		return "student [name=" + name + ", login=" + login + ", level=" + level + ", isConnected=" + isConnected
				+ ", ip=" + ip + ", port=" + port + "]";
	}
	

}
