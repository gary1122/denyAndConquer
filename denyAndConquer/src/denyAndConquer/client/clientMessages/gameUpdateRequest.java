package denyAndConquer.client.clientMessages;

import java.io.Serializable;

public class gameUpdateRequest implements Serializable{
	public int xCord, yCord, pixelX, pixelY;
	public gameUpdateRequest(int xCord,int yCord, int pixelX, int pixelY){
		this.xCord = xCord;
		this.yCord = yCord;
		this.pixelX = pixelX;
		this.pixelY = pixelY;
	}


}
