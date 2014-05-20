package jedyobidan.net.test;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

import jedyobidan.net.Client;
import jedyobidan.net.Message;

public class ChatClient extends Client {
	public ChatClient(String serverIp, int port) throws UnknownHostException,
			IOException {
		super(serverIp, port);
	}
	
	public void messageRecieved(Message m){
		super.messageRecieved(m);
		if(m instanceof ChatMessage){
			System.out.println(m);
		}
	}
	
	public static void main(String[] args) throws Exception{
		ChatClient client = new ChatClient("localhost", 9000);
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNextLine()){
			String line = scanner.nextLine();
			if(line.equals("exit")){
				client.close(0);
				break;
			}
			client.sendMessage(new ChatMessage(client.getClientID(), line));
		}
		scanner.close();
	}
}
