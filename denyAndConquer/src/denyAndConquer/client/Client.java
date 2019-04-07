package denyAndConquer.client;

//Java implementation for a client 
//Save file as Client.java 

import java.io.*; 
import java.net.*;
import java.util.List;
import java.util.Scanner;


import denyAndConquer.client.clientMessages.exitRequest;
import denyAndConquer.client.clientMessages.initRequest;
import denyAndConquer.client.clientMessages.mousePressedRequest;
import denyAndConquer.client.clientMessages.mouseReleasedRequest;
import denyAndConquer.game.Tile;
import denyAndConquer.client.clientMessages.gameUpdateRequest;
import denyAndConquer.server.Server; 

//Client class 
public class Client  
{ 
	private String clientId = null;
	private int port;
	private Socket clientSocket;
	private String address; 
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	private List<List<Tile>> Tiles;
	
	public Client(String clientId) {
		this.clientId = clientId;
		
		

	}
	
	public ObjectInputStream getClientInputStream() {
		return this.in;
	}
	
	public ObjectOutputStream getClientOutputStream() {
		return this.out;
	}
	
	public String getClientID() {
		return this.clientId;
	}
	
	public int getClientPort() {
		return this.port;
	}
	
	public String getClientIp() {
		return this.address;
	}
	
	public Socket getClientSocket() {
		return this.clientSocket;
	}
	
	public void init() {
		
	}
	
	public void exit() {
		try {
			exitRequest exit = new exitRequest();
			out.writeObject(exit);
			clientSocket.close();
			System.out.println("exit");
		}catch(Exception e) {
	         e.printStackTrace(); 
		}
	}
	
	
	//initialization request
	public void sendInitRequest() {
		try {
			
			initRequest conn = new initRequest();
			out.writeObject(conn);
			out.reset();
			System.out.println("send initilization request");
		}catch(Exception e){
	         e.printStackTrace(); 
		}
		
	}
	
	
	public void sendUpdate(gameUpdateRequest update) {
		try {
			
			out.writeObject(update);
			System.out.println("send update request");
		}catch(Exception e){
	         e.printStackTrace(); 
		}
		
	}
	
	
	public void startSession(int port) {
			Thread t = new Server(port);
			t.start();
		 
	}
	
	public void connectTo(String address,int port) {
		try {
			//getting local host ip 
			this.address = address;
			this.clientSocket = new Socket(this.address, port);
			this.out    = new ObjectOutputStream(clientSocket.getOutputStream()); 
			this.in    = new ObjectInputStream(clientSocket.getInputStream()); 
			
			Thread t = new serverResponseHandler(this);
			t.start();
			
			System.out.println("Connected");
		}catch(Exception e){ 
	         e.printStackTrace(); 
	     } 
		
	}
	
	
	public void sendMouseRelease() {
		try {
			
			mouseReleasedRequest release = new mouseReleasedRequest();
			out.writeObject(release);
			out.reset();
			System.out.println("send release request");
		}catch(Exception e){
	         e.printStackTrace(); 
		}
	}
	
	public void sendMousePressed() {
		try {
			
			mousePressedRequest pressed = new mousePressedRequest();
			out.writeObject(pressed);
			out.reset();
			System.out.println("send release request");
		}catch(Exception e){
	         e.printStackTrace(); 
		}
	}
	

	public void startGame() {
		System.out.println("game start");
		//this.game = new Game(this);
		System.out.println("game end");
	}
	
	
	public void setTiles(List<List<Tile>> Tiles) {
		this.Tiles = Tiles;
	}
	
	public Tile getTile(int xCord,int yCord) {
		return Tiles.get(xCord).get(yCord);
	}
 	
} 