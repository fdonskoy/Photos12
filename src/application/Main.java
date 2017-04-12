package application;
	
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import application.view.SceneLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


public class Main extends Application{	
	private static Admin admin;
	private static User user;
	private static User user2; 
	private static Album album;
	
	public static Main self;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		SceneLoader sl = SceneLoader.getInstance();
		sl.setStage(primaryStage);
		sl.changeScene("Picture-Library-login.fxml");
	}
	
	public static void main(String[] args) throws IOException {
		launch(args);
	}
}
