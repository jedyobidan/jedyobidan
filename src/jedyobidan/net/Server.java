package jedyobidan.net;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server{
	private List<ClientAgent> clients;
	private Set<MessageObserver> observers;
	private List<String> log;
	private ConnectionAccept acceptor;
	private boolean closed;
	protected boolean writeToLog;
	public Server() throws IOException{
		clients = new CopyOnWriteArrayList<ClientAgent>();
		observers = Collections.newSetFromMap(new ConcurrentHashMap<MessageObserver, Boolean>());
		log = new ArrayList<String>();
		Runtime.getRuntime().addShutdownHook(new Thread(){
			public void run(){
				if(writeToLog) 
					writeLog("_serverlogs/" + System.currentTimeMillis() + ".log");
			}
		});
	}
	
	public void acceptConnections(int port){
		new Thread(acceptor = new ConnectionAccept(port), "Connection_Accept").start();
	}
	
	public void stopAccepting(){
		acceptor.close();
	}
	
	public void clientJoined(ClientAgent client){
		
	}
	
	public void clientQuit(ClientAgent client){
		
	}
	
	public synchronized void messageRecieved(Message m){
		log.add("[RCVE] " + m);
		for(MessageObserver o: observers){
			o.messageRecieved(m);
		}
	}
	
	public void broadcastMessage(Message m){
		log.add("[BCST] " + m);
		for(ClientAgent client: clients){
			if(client!=null) client.sendMessage(m);
		}
	}
	
	public void sendMessage(int clientID, Message m){
		log.add("[SEND] " + m + " TO <" + clientID + ">");
		ClientAgent c = clients.get(clientID-1);
		if(c!= null) c.sendMessage(m);
	}
	
	public void addObserver(MessageObserver o){
		observers.add(o);
	}
	
	public void removeObserver(MessageObserver o){
		observers.remove(o);
	}
	
	public void close(int exitStatus) throws IOException{
		stopAccepting();
		for(ClientAgent client: clients){
			if(client!=null && !client.closed){
				client.sendMessage(new ServerQuit(0, exitStatus));
				client.close();
			}
		}
		System.out.println("SERVER: Closed");
		closed = true;
	}
	
	public void close() throws IOException{
		close(0);
	}
	
	public void writeLog(String file){
		try {
			File f = new File(file);
			if(!f.getParentFile().exists()){
				f.getParentFile().mkdirs();
			}
			PrintWriter writer = new PrintWriter(file);
			for(String s: log){
				writer.println(s);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			System.err.println("ERROR: Failed to write logs");
		}
	}
	
	private class ConnectionAccept implements Runnable{
		private int port;
		private boolean accept;
		private ServerSocket serverSock;
		public ConnectionAccept(int port){
			this.port = port;
		}
		@Override
		public void run() {
			try {
				System.out.println("SERVER: Accepting connections on port " + port + "...");
				serverSock = new ServerSocket(port);
				accept = true;
				while(accept){
					ClientAgent client = new ClientAgent(serverSock.accept(), clients.size()+1);
					clients.add(client);
					clientJoined(client);
					new Thread(client, "Client_Agent-" + clients.size()).start();
				}
				serverSock.close();
			} catch (SocketException e){
				if(accept){
					e.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("SERVER: Not accepting connections");
		}
		
		public void close(){
			try {
				accept = false;
				serverSock.close();
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
		private boolean closed;
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
					Message m = (Message) in.readObject();
					if(m.origin != clientID){
						System.out.println("SERVER_WARN: Message origin= "+ m.origin + " does not match clientID=" + clientID);
						continue;
					}
					if(m instanceof ClientQuit){
						ClientQuit q = (ClientQuit) m;
						clientQuit = true;
						if(q.exitStatus!=0){
							System.out.println("SERVER: ClientAgent_" + clientID + " closed unexpectedly (" + q.exitStatus + ")");
						}
						
					}
					try{
						messageRecieved(m);
					} catch (Exception e){
						e.printStackTrace();
					}
				}
			} catch(SocketException e){
				if(!e.toString().toLowerCase().matches(".*connection.*reset.*")){
					e.printStackTrace();
				}
			} catch(Exception e){
				e.printStackTrace();
			}
			try {
				close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("SERVER: ClientAgent_" + clientID + " quit");
			clients.set(clientID-1, null);
			clientQuit(this);
		}
		
		public void sendMessage(Message m){
			try{
				out.writeObject(m);
				out.flush();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		
		public void close() throws IOException{
			closed = true;
			out.close();
			in.close();
			sock.close();
		}
		
		public int getClientID(){
			return clientID;
		}
		
	}


	
}
