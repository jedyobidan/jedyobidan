package jedyobidan.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

public class Client {
	private Set<MessageObserver> observers;
	private int clientID;
	private ServerAgent serverAgent;
	
	public Client(String serverIp, int port) throws UnknownHostException, IOException{
		observers = Collections.newSetFromMap(new ConcurrentHashMap<MessageObserver, Boolean>());
		System.out.println("CLIENT: Connecting to server at " + serverIp + ":" + port + "...");
		serverAgent = new ServerAgent(new Socket(serverIp, port));
		Runtime.getRuntime().addShutdownHook(new Thread(){
			public void run(){
				try {
					if(!serverAgent.closed) close(1);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		new Thread(serverAgent, "Server_Agent").start();
	}
	
	public void messageRecieved(Message m){	
		if(m instanceof ClientInit){
			clientID = ((ClientInit)m).clientID;
			System.out.println("CLIENT: clientID=" + clientID);
		}
		for(MessageObserver o: observers){
			o.messageRecieved(m);
		}
	}
	
	public void sendMessage(Message m) {
		serverAgent.sendMessage(m);
	}
	
	public void addObserver(MessageObserver m){
		observers.add(m);
	}
	
	public void removeObserver(MessageObserver m){
		observers.remove(m);
	}
	
	public void close() throws IOException{
		close(0);
	}
	
	public void close(int exitStatus) throws IOException{
		if(!serverAgent.isClosed()){
			try{
				sendMessage(new ClientQuit(clientID, exitStatus));
			} catch (Exception e){
				e.printStackTrace();
			}
			serverAgent.close();
		}
		System.out.println("CLIENT: Closed");
	}
	
	public int getClientID(){
		return clientID;
	}
	
	private class ServerAgent implements Runnable{
		private boolean serverQuit;
		private boolean closed;
		private ObjectInputStream in;
		private ObjectOutputStream out;
		private Socket socket;
		public ServerAgent(Socket serverSocket) throws IOException{
			this.socket = serverSocket;
			this.out = new ObjectOutputStream(serverSocket.getOutputStream());
			out.flush();
			this.in = new ObjectInputStream(serverSocket.getInputStream());
			System.out.println("CLIENT: Initialized ServerAgent");
		}
		@Override
		public void run() {
			try {
				while(!serverQuit){
					Message m = (Message) in.readObject();
					if(m instanceof ServerQuit){
						ServerQuit q = (ServerQuit) m;
						serverQuit = true;
						if(q.exitStatus!=0){
							System.out.println("CLIENT: ServerAgent closed unexpectedly (" + q.exitStatus + ")");
						}
						close();
					}
					messageRecieved(m);
				}
			} catch(SocketException e){
				if(!closed){
					e.printStackTrace();
				}
			}catch (Exception e){
				e.printStackTrace();
			}
			closed = true;
			System.out.println("CLIENT: ServerAgent quit");
		}
		
		public void sendMessage(Message m){
			try{
				if(m.origin != clientID || clientID == 0){
					throw new SecurityException("Message origin(" + m.origin + ") does not match clientID(" + clientID + ")");
				}
				out.writeObject(m);
				out.flush();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		
		public void close() throws IOException{
			closed = true;
			in.close();
			out.close();
			socket.close();
		}
		
		public boolean isClosed(){
			return closed;
		}
	}

	
}
