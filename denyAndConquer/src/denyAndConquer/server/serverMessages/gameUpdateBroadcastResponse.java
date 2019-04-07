package denyAndConquer.server.serverMessages;

import java.io.Serializable;

public class gameUpdateBroadcastResponse implements Serializable{
	public int xCord, yCord, pixelX, pixelY;
	public gameUpdateBroadcastResponse(int xCord,int yCord, int pixelX, int pixelY){
		this.xCord = xCord;
		this.yCord = yCord;
		this.pixelX = pixelX;
		this.pixelY = pixelY;
	}

}
