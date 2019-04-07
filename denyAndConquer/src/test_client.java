import java.io.IOException;

import denyAndConquer.client.Client;

public class test_client {
	 public static void main(String[] args) throws IOException  
	 { 
	     try
	     { 
	    	 Client client = new Client("123");
	    	 client.startSession(8889);
	    	 
	    	 //client.connectTo("127.0.0.1", 8889);
	    	 //while(true);
	    	// client.sendInitRequest();
	    	// client.sendInitRequest();
	    	//client.sendInitRequest();
	    	// client.exit();
	    	 
	    	 //client.startSession(1234);

	     }catch(Exception e){ 
	         e.printStackTrace(); 
	     } 
	 } 
}
