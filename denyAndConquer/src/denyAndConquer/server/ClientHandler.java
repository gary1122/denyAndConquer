package denyAndConquer.server;


import java.io.*; 
import java.text.*; 
import java.util.*;


import denyAndConquer.client.clientMessages.exitRequest;
import denyAndConquer.client.clientMessages.gameUpdateRequest;
import denyAndConquer.client.clientMessages.initRequest;
import denyAndConquer.client.clientMessages.mousePressedRequest;
import denyAndConquer.client.clientMessages.mouseReleasedRequest;
import denyAndConquer.game.Tile;
import denyAndConquer.server.serverMessages.gameUpdateBroadcastResponse;
import denyAndConquer.server.serverMessages.initResponse;
import denyAndConquer.server.serverMessages.mousePressedResponse;
import denyAndConquer.server.serverMessages.mouseReleaseResponse;

import java.net.*; 
//ClientHandler class 
class ClientHandler extends Thread  
{ 
 
private Socket client_s;
private ObjectOutputStream dos;
private ObjectInputStream dis;
private Server server;
private Tile tile;
   

 // Constructor 
 public ClientHandler(Socket socket, Server server)  
 { 
	 try{
		 this.client_s = socket; 
		 this.server = server;
		 //this.dos = new ObjectOutputStream(client_s.getOutputStream());
		 this.dis = new ObjectInputStream(client_s.getInputStream());
		 System.out.println("client Handldler initialization completed");
	 }catch(Exception e){
         e.printStackTrace(); 
	 }

 } 

 @Override
 public void run()  
 { 
     Object received;
     
     
     try {
         while (true) {
        	 try {
        	
        	 received = dis.readObject();
        	 
        	 if(received instanceof initRequest) {
        		 //dos.writeObject(new initResponse());
        		 broadcast(new initResponse());
        		 System.out.println("received is instance of initialization Request"); 
        	 }else if(received instanceof gameUpdateRequest){
        		 broadcast(new gameUpdateBroadcastResponse(((gameUpdateRequest)received).xCord,
        				 ((gameUpdateRequest)received).yCord,
        				 ((gameUpdateRequest)received).pixelX,
        				 ((gameUpdateRequest)received).pixelY));
        		 System.out.println("col: "+ ((gameUpdateRequest)received).xCord);
        		 System.out.println("row: "+ ((gameUpdateRequest)received).yCord);
        		 System.out.println("pixelX: "+ ((gameUpdateRequest)received).pixelX);
        		 System.out.println("pixelY: "+ ((gameUpdateRequest)received).pixelY);
        	 }else if(received instanceof exitRequest) {
        		 dos.close();
        		 dis.close();
        		 client_s.close();
        		 System.out.println("received is instance of exit request");
        		 break;
        	 }else if(received instanceof mouseReleasedRequest) {
        		 broadcast(new mouseReleaseResponse());
        		 System.out.println("received is instance of exit request");

        	 }else if(received instanceof mousePressedRequest) {
        		 broadcast(new mousePressedResponse());
        		 System.out.println("received is instance of exit request");

        	 }
        	 
        	 }catch(ClassNotFoundException e) {
        		 e.printStackTrace();
        	 }
         }
     } 
	catch (IOException e) {
		System.out.println("2");
	    //broadcast("User " + user + " disconnected");
	    //server.removeFromClients(dos);
		e.printStackTrace();
     } 
	finally {
		 System.out.println("1");
         try { cleanUp(); } 
	    catch (IOException x) { }
     }
 }
 
 
 
 
 // Send the message to all the sockets connected to the server.
 private void broadcast(Object obj) {
    List<ObjectOutputStream> clients = server.getClients();
    ObjectOutputStream dataOut = null;
    System.out.println(clients);
    
    Iterator<ObjectOutputStream> i = clients.iterator();
    while (i.hasNext() ) {
	   dataOut = (ObjectOutputStream)(i.next());
	   try { dataOut.writeObject(obj);} 
	   catch (IOException x) {
	       System.out.println(x.getMessage() +
				  ": Failed to broadcast to client.");
	       server.removeFromClients(dataOut);
	   }
    }
 }
 
 private void cleanUp() throws IOException {
     if (dos != null) {
       // server.removeFromClients(dos);
        dos.close();
     }

     if (dis != null) {
        dis.close();
     }

     if (client_s != null) {
    	 client_s.close();
     }
  }
 
 
} 