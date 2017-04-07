package application.view;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SceneLoader {
	private static SceneLoader single;
	private Stage primaryStage;
	
	private SceneLoader(){};
	
	public void changeScene(String newScene) throws IOException{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(getClass().getResource("/application/view/" + newScene));
		//GridPane root = (GridPane)loader.load();
		Pane root = (Pane)loader.load();
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Photo Library");
		primaryStage.setResizable(false);  
		primaryStage.show();
	}
	
	public static SceneLoader getInstance(){
		if(single == null){
			single = new SceneLoader();
		}
		
		return single;
	}
	
	public void setStage(Stage stage){
		primaryStage = stage;
	}
}
