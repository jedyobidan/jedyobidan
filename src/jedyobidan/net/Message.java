package jedyobidan.net;

import java.io.Serializable;

public abstract class Message implements Serializable{
	private static final long serialVersionUID = 1L;
	public final int origin;
	public Message(int origin){
		this.origin = origin;
	}
}
