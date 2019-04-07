package denyAndConquer.game;



import java.util.ArrayList;
import java.util.List;

import denyAndConquer.client.Client;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {


    private Client client;

    @Override
    public void start(Stage primaryStage) {
   
    	this.client = new Client("111");
    	client.startSession(8888);
    	client.connectTo("127.0.0.1", 8888);
    	
    	
    
    	//System.out.println(Tiles.size());
    	//System.out.println(Tiles.get(0).size());
    	
        GridPane board = new GridPane();
        game game = new game(client, board);

        Scene scene = new Scene(board);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    
    public static void main(String[] args) {
        launch(args);
    }
}