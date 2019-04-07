package denyAndConquer.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


import denyAndConquer.client.clientMessages.exitRequest;
import denyAndConquer.client.clientMessages.gameUpdateRequest;

import denyAndConquer.game.Tile;
import denyAndConquer.server.Server;

import denyAndConquer.server.serverMessages.gameUpdateBroadcastResponse;
import denyAndConquer.server.serverMessages.initResponse;
import denyAndConquer.server.serverMessages.mousePressedResponse;
import denyAndConquer.server.serverMessages.mouseReleaseResponse;
import javafx.scene.paint.Color;

public class serverResponseHandler extends Thread  {
	
	private Socket client_s;
	private ObjectOutputStream dos;
	private ObjectInputStream dis;
	private Client client;
	private Tile tile;

	
	public serverResponseHandler(Client client) {
		 try{
			 this.client = client; 
			 this.client_s = client.getClientSocket();
			 this.dos = client.getClientOutputStream();
			 this.dis = client.getClientInputStream();
			 System.out.println("server response Handldler initialization completed");
		 }catch(Exception e){
	         e.printStackTrace(); 
		 }
	}
	
	public void run() {
	    Object received;
	     try {
	         while (true) {
	        	 try {
	        	
	        	 received = dis.readObject();
	        	 
	        	 if(received instanceof initResponse) {
	        		 System.out.println("received is instance of initialization response"); 
	        	 }else if(received instanceof gameUpdateBroadcastResponse){
	        		 
	        		 tile = client.getTile(((gameUpdateBroadcastResponse)received).yCord, ((gameUpdateBroadcastResponse)received).xCord);
	        		 tile.setColor(((gameUpdateBroadcastResponse)received).pixelX, ((gameUpdateBroadcastResponse)received).pixelY, Color.BLACK);
	        		 tile.incPixelNum();
	        		 System.out.println("received is instance of game Update ");
	        	 }else if(received instanceof mouseReleaseResponse) {
	        		 tile.checkPercentageCovered();
	
	        		 System.out.println("received is instance of mouseReleaseResponse");
	        	 }else if(received instanceof mousePressedResponse) {
	        		 tile.checkPercentageCovered();
	
	        		 System.out.println("received is instance of mouseReleaseMessage request");
	        	 }else if(received instanceof exitRequest) {
	        		 dos.close();
	        		 dis.close();
	        		 client_s.close();
	        		 System.out.println("received is instance of exit request");
	        		 break;
	        	 }
	        	 
	        	 //System.out.println("after read");
	        	 //System.out.println(received);
	             //broadcast(received);
	        	
	        	 }catch(ClassNotFoundException e) {
	        		 System.out.println("3");
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

	}
	
}
