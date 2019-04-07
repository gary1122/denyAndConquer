package denyAndConquer.game;



import denyAndConquer.client.Client;
import denyAndConquer.client.clientMessages.gameUpdateRequest;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Tile {
	enum State {
		IDLE,
		OCCUPIED,
		LOCKED,
	}
	
	
	private State state; 
	private WritableImage image;
	private ImageView imageView;
	private PixelWriter pixelWriter;
	private PixelReader pixelReader;
	private int xCord;
	private int yCord;
	private int imageViewHeight;
	private int imageViewWidth;
	private int imageResolutionX;
	private int imageResolutionY;
	private int brushSize=5;
	private int x;
	private int y;
	private int numPixelOccupied =0;
	private Client player;
	public String owener;
	
	
	public Tile(int width, int height, int xCord, int yCord,Client client){
		this.player = client;
		this.xCord = xCord;
		this.yCord = yCord;
		this.imageViewHeight = height;
		this.imageViewWidth = width;
		this.imageResolutionX = (int)(width/brushSize);
		this.imageResolutionY = (int)(height/brushSize);
		this.image = new WritableImage(imageResolutionX, imageResolutionY);
		this.pixelWriter = image.getPixelWriter();
		this.pixelReader = image.getPixelReader();
		state = State.IDLE;
		
		fill(Color.WHITE);
		
		this.imageView = new ImageView(image);
		imageView.setFitWidth(imageViewWidth);
		imageView.setFitHeight(imageViewHeight);
		

		initMouseEvent();
	}
	
	
	public ImageView getImageView() {
		return this.imageView;
	}
	
	public void fill(Color color) {
		for(int y = 0; y<imageResolutionY;y++) {
			for(int x = 0; x < imageResolutionX; x++) {
				pixelWriter.setColor(x, y, color);
			}
		}
	}
	
	public void checkPercentageCovered() {
		int area = imageResolutionX*imageResolutionY;
		double percentage = (double)numPixelOccupied/area;
		System.out.println("area: " + area);
		System.out.println("percentage: " + percentage);
		System.out.println("numPixelOccupied: " + numPixelOccupied);
		if(percentage > 0.5) {
			fill(Color.BLACK);
			numPixelOccupied = area;
			state = State.OCCUPIED;
		}else {
			numPixelOccupied = 0;
			fill(Color.WHITE);
			state = State.IDLE;
			this.owener = null;
		}
	}
	
	public void updateColor(int x,int y,Color color) {
		pixelWriter.setColor(x, y, color);
		
	}

	public void initMouseEvent() {

		imageView.setOnMouseDragEntered(e->{
			System.out.println("entered");
	
		});
		
		
		imageView.setOnMousePressed(e->{
			if(this.isIdle()) {
				state = State.LOCKED;
				this.owener = player.getClientID(); 
				System.out.println("press");
				System.out.println("image resolution y: " + imageResolutionY);
				System.out.println("image resolution x: " + imageResolutionX);
			}

		});
		
		imageView.setOnMouseReleased(e->{
			
			player.sendMouseRelease();
			
		});
		
		imageView.setOnMouseDragged(e->{
			//System.out.println("moved");
			
			x = (int)(e.getX()/brushSize);
			y = (int)(e.getY()/brushSize);
	
			if(x<0) {
				x = 0;
			}else if(x > imageResolutionX-1) {
				x = imageResolutionX-1;
			}
			
			if(y<0) {
				y = 0;
			}else if(y > imageResolutionY-1) {
				y = imageResolutionY-1;
			}
			
			
			if(pixelReader.getColor(x, y).equals(Color.WHITE)) {
				player.sendUpdate(new gameUpdateRequest(xCord, yCord, x, y));

			};
			

			
		});
		
		imageView.setOnMouseDragExited(e->{
			System.out.println("Exit");

		});
		
		
	}
	
	public void setOwener(String owener) {
		if(!this.isIdle()) {
			this.owener = owener;
		}
	}
	
	public String getOwner() {
		return owener;
	}
	
	public boolean isIdle() {
		return (state == State.IDLE);
	}
	
	public void incPixelNum() {
		numPixelOccupied++;
	}
	
	public void setColor(int x,int y ,Color color) {
		pixelWriter.setColor(x,y, color);
	}
	
}
