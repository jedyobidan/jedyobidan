package jedyobidan.net.test;

import jedyobidan.net.Message;

public class ChatMessage extends Message{
	public final String chat;
	public ChatMessage(int origin, String chat) {
		super(origin);
		this.chat = chat;
	}
	
	public String toString(){
		return origin + ": " + chat;
	}
}
