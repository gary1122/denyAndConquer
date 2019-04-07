import java.io.IOException;

import denyAndConquer.client.Client;

public class test_server {
	 public static void main(String[] args) throws IOException  
	 { 
	     try
	     { 
	    	 Client client = new Client("555");
	    	 //client.startSession(8889);
	    	 client.connectTo("127.0.0.1", 8889);
	    	 client.sendInitRequest();
	    	 client.sendInitRequest();
	    	// client.exit();
	     }catch(Exception e){ 
	         e.printStackTrace(); 
	     } 
	 } 
}
