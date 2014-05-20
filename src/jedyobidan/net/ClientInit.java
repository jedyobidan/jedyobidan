package jedyobidan.net;

public class ClientInit extends Message {
	public final int clientID;
	public ClientInit(int clientID) {
		super(0);
		this.clientID = clientID;
	}

}
