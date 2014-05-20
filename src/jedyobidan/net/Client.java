package jedyobidan.net;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Client {
	public volatile List<Message> messages;
	private HashSet<MessageObserver> observers;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Socket socket;
	private int clientID;
	private ServerAgent serverAgent;
	
	public Client(String serverIp, int port) throws UnknownHostException, IOException{
		messages = new CopyOnWriteArrayList<Message>();
		observers = new HashSet<MessageObserver>();
		socket = new Socket(serverIp, port);
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		out.flush();
		Runtime.getRuntime().addShutdownHook(new Thread(){
			public void run(){
				try {
					close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		new Thread(serverAgent = new ServerAgent()).start();
	}
	
	public void messageRecieved(Message m){
		
		if(m instanceof ClientInit){
			clientID = ((ClientInit)m).clientID;
			System.out.println("CLIENT: clientID=" + clientID);
		}
		messages.add(m);
		for(MessageObserver o: observers){
			o.messageRecieved(m);
		}
	}
	
	public void sendMessage(Message m) {
		try {
			if(m.origin != clientID || clientID == 0){
				throw new SecurityException("Message origin does not match clientID");
			}
			out.writeObject(m);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addObserver(MessageObserver m){
		observers.add(m);
	}
	
	public void removeObserver(MessageObserver m){
		observers.remove(m);
	}
	
	public void close() throws IOException{
		in.close();
		out.close();
		socket.close();
		serverAgent.open = false;
		System.out.println("CLIENT: Closed");
	}
	
	public int getClientID(){
		return clientID;
	}
	
	private class ServerAgent implements Runnable{
		boolean open = true;
		@Override
		public void run() {
			try {
				while(open){
					Message m = (Message) in.readObject();
					messageRecieved(m);
				}
			}catch(EOFException e){
				
			}catch(SocketException e){
				if(open){
					e.printStackTrace();
				}
			} catch (Exception e){
				e.printStackTrace();
			}
			System.out.println("CLIENT: ServerAgent quit");
		}
	}

	
}
