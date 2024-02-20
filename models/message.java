package tp1Udp.models;

public class message {

	public student sender;
	public student receiver;
	public String payload;
	public message(student sender, student receiver, String payload) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.payload = payload;
	}
	@Override
	public String toString() {
		return "message [sender=" + sender.getName() + ", receiver=" + receiver.getName() + ", payload=" + payload + "]";
	}
	
	
}
