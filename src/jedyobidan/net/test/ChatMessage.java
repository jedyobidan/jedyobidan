package jedyobidan.net.test;

import jedyobidan.net.Message;

public class ChatMessage extends Message{
	private static final long serialVersionUID = -1817079290698957172L;
	public final String chat;
	public ChatMessage(int origin, String chat) {
		super(origin);
		this.chat = chat;
	}
	
	public String toString(){
		return origin + ": " + chat;
	}
}
