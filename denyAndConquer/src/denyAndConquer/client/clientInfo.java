package denyAndConquer.client;
import java.io.*;


public class clientInfo implements Serializable {
	private String id;
	private String color;
	
	
	public clientInfo(String id, String color) {
		this.id = id;
		this.color = color;
	}
	
	
	public String getId() {
		return this.id;
	}
	
	public String getColor(String color) {
		return this.color;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public void setColor(String color) {
		this.color = color;
	}

}
