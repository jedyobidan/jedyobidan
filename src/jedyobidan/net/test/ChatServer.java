package jedyobidan.net.test;

import java.io.IOException;

import jedyobidan.net.Message;
import jedyobidan.net.Server;

public class ChatServer extends Server{
	public ChatServer() throws IOException {
		super();
	}
	
	public void clientJoined(ClientAgent client){
		client.sendMessage(new ChatMessage(0, "Welcome to the Server!"));
	}
	
	public void clientQuit(ClientAgent client){
		System.out.println("Goodbye.");
	}
	
	public void messageRecieved(Message m){
		super.messageRecieved(m);
		if(m instanceof ChatMessage){
			System.out.println(m);
			broadcastMessage(m);
		}
			
	}
	
	public static void main(String[] args) throws Exception{
		ChatServer server = new ChatServer();
		server.acceptConnections(9000);
	}

}
