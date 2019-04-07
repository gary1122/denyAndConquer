
    
 // Java implementation of  Server side 
 // It contains two classes : Server and ClientHandler 
 // Save file as Server.java 
 package denyAndConquer.server;  
 import java.io.*; 
 import java.text.*; 
 import java.util.*;
import java.net.*; 
   
 // Server class 
 public class Server extends Thread   
 { 
	private List<ObjectOutputStream> clients = new ArrayList<ObjectOutputStream>();
	private ServerSocket server_s = null;
	private Socket client_s = null;
	private DataInputStream in = null;
	private int port;
		
	synchronized List<ObjectOutputStream> getClients() { return clients; }
	    
	synchronized void removeFromClients(ObjectOutputStream remoteOut) {
		clients.remove(remoteOut);
	}
		
	public Server(int port) {
		this.port = port;
	}
	 
	 
	public void run() {
		try {
			server_s = new ServerSocket(port);
			System.out.println("Server started"); 
			while(true) {
				System.out.println("Waiting for a client ..."); 
				client_s = server_s.accept(); 
			
				System.out.println("Client accepted"); 
				System.out.println("Assigning new thread for this client"); 
				
				clients.add(new ObjectOutputStream(client_s.getOutputStream()));
				
				Thread t = new ClientHandler(client_s,this);
				t.start();
           }
		}catch(Exception e){
			e.printStackTrace(); 	
		}
	}
	
	 /*
     public static void main(String[] args) throws IOException  
     {     	
           Server server = new Server(8889);   
     } 
     
     */
/*
     public void begin() throws IOException {
         // running infinite loop for getting 
         // client request 
    	 
         while (true)  
         { 
             Socket client = null; 
               
             try 
             { 
                 // socket object to receive incoming client requests 
            	 client = serSock.accept(); 
                   
                 System.out.println("A new client is connected : " + client); 
                   
                 // obtaining input and out streams 
                 DataInputStream dis = new DataInputStream(client.getInputStream()); 
                 DataOutputStream dos = new DataOutputStream(client.getOutputStream()); 
                   
                 System.out.println("Assigning new thread for this client"); 
   
                 // create a new thread object 
                 clients.add(dos);
                 Thread t = new ClientHandler(client, dis, dos, this); 
   
                 // Invoking the start() method 
                 t.start(); 
                   
             } 
             catch (Exception e){ 
            	 client.close(); 
                 e.printStackTrace(); 
             } 
         } 
     }
    */

 } 



