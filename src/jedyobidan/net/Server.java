package jedyobidan.net;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server{
	private volatile boolean accept;
	private List<ClientAgent> clients;
	public List<Message> messages;
	private HashSet<MessageObserver> observers;
	public Server() throws IOException{
		clients = new CopyOnWriteArrayList<ClientAgent>();
		messages = new CopyOnWriteArrayList<Message>();
		observers = new HashSet<MessageObserver>();
		Runtime.getRuntime().addShutdownHook(new Thread(){
			public void run(){
				try {
					close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void acceptConnections(int port){
		new Thread(new ConnectionAccept(port), "Connection_Accept").start();
	}
	
	public void stopAccepting(){
		accept = false;
	}
	
	public void clientJoined(ClientAgent client){
		
	}
	
	public void clientQuit(ClientAgent client){
		
	}
	
	public synchronized void messageRecieved(Message m){
		messages.add(m);
		for(MessageObserver o: observers){
			o.messageRecieved(m);
		}
	}
	
	public void broadcastMessage(Message m){
		for(ClientAgent client: clients){
			if(client!=null) client.sendMessage(m);
		}
	}
	
	public void sendMessage(int clientID, Message m){
		ClientAgent c = clients.get(clientID-1);
		if(c!= null) c.sendMessage(m);
	}
	
	public void addObserver(MessageObserver o){
		observers.add(o);
	}
	
	public void removeObserver(MessageObserver o){
		observers.remove(o);
	}
	
	public void close() throws IOException{
		System.out.println("SERVER: Closed");
		for(ClientAgent client: clients){
			if(client!=null){
				client.close();
			}
		}
	}
	
	private class ConnectionAccept implements Runnable{
		private int port;
		public ConnectionAccept(int port){
			this.port = port;
		}
		@Override
		public void run() {
			try {
				System.out.println("SERVER: Accepting connections...");
				ServerSocket s = new ServerSocket(port);
				accept = true;
				while(accept){
					ClientAgent client = new ClientAgent(s.accept(), clients.size()+1);
					clients.add(client);
					clientJoined(client);
					new Thread(client, "Client_Agent-" + clients.size()).start();
				}
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected class ClientAgent implements Runnable{
		private int clientID;
		private ObjectOutputStream out;
		private ObjectInputStream in;
		private Socket sock;
		private volatile boolean clientQuit;
		public ClientAgent(Socket clientSocket, int clientID) throws IOException{
			this.clientID = clientID;
			this.sock = clientSocket;
			this.out = new ObjectOutputStream(clientSocket.getOutputStream());
			out.flush();
			this.in = new ObjectInputStream(clientSocket.getInputStream());
			System.out.println("SERVER: Initialized ClientAgent_" + clientID);
			sendMessage(new ClientInit(clientID));
		}
		@Override
		public void run(){
			try{
				while(!clientQuit){
					Message m;
					if(clientQuit) break;
					m = (Message) in.readObject();
					messageRecieved(m);
				}
			}catch(EOFException e){
				
			}catch(SocketException e){
				if(!clientQuit){
					e.printStackTrace();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			clientQuit = true;
			System.out.println("SERVER: ClientAgent_" + clientID + " quit");
			clients.set(clientID-1, null);
			clientQuit(this);
			try {
				close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public void sendMessage(Message m){
			try{
				out.writeObject(m);
				out.flush();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		
		public synchronized void close() throws IOException{
			clientQuit = true;
			out.close();
			in.close();
			sock.close();
		}
		
	}


	
}
