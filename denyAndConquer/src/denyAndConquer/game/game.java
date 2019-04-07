package denyAndConquer.game;

import java.util.ArrayList;
import java.util.List;

import denyAndConquer.client.Client;
import javafx.scene.layout.GridPane;

public class game {
	
    private final int tileSize = 50 ;
    int boardRow =10;
    int boardCol =10;
    private Client player;
    private List<List<Tile>> Tiles = new ArrayList<List<Tile>>();
    private GridPane board; 
	
	public game(Client player, GridPane board) {
		this.player = player;
		this.board = board;
		initTiles();
		initBoard();
		player.setTiles(Tiles);
	}
	
	public void initBoard() {
		
		board.setHgap(1);
		board.setVgap(1);
		board.setStyle("-fx-background-color: black;");

        for (int y = 0 ; y < boardRow; y++) {
            for (int x = 0 ; x < boardCol ; x++) {
            	board.add(Tiles.get(y).get(x).getImageView(), x, y);
            }
        }
	}

    public void initTiles() {
    	for(int y = 0; y < boardRow ;y++) {
    		Tiles.add(new ArrayList<Tile>());
    		for(int x = 0; x<boardCol; x++) {
    			Tiles.get(y).add(new Tile(tileSize,tileSize,x,y,player));
    			
    		}
    	}
    }
	
}

