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
	
	public static void main(String[] args) {
		/*admin = new Admin("admin");
		user = new User("user");
		user2 = new User("user2");
		
		try {
			if(user.addAlbum("testAlbum")) System.out.println("testAlbum added to user"); 
			else System.out.println("testAlbum not added to user");
			
			if(user.addAlbum("testAlbum")) System.out.println("testAlbum added to user"); 
			else System.out.println("testAlbum not added to user");
			
			if(user.addAlbum("testAlbum2")) System.out.println("testAlbum2 added to user"); 
			else System.out.println("testAlbum not added to user");
			
			if(user2.addAlbum("testAlbum")) System.out.println("testAlbum added to user2"); 
			else System.out.println("testAlbum not added to user2");
			
			System.out.println("user has " + user.getAlbums().size() + " albums");
			System.out.println("user2 has " + user2.getAlbums().size() + " albums");
			
			Admin.writeAdmin(admin);
			user.writeUser();
			user2.writeUser();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		System.out.println("Original admin: " + admin.getUsername());
		System.out.println("Original user: " + user.getUsername());
		System.out.println("Original user2: " + user2.getUsername());
		try {
			admin = Admin.readAdmin();
			user = User.readUser("user2");
			user2 = User.readUser("user");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		
		System.out.println("Read admin: " + admin.getUsername());
		System.out.println("Read user: " + user.getUsername());
		System.out.println("Read user2: " + user2.getUsername());
		
		System.out.println("user has " + user.getAlbums().size() + " albums");
		System.out.println("user2 has " + user2.getAlbums().size() + " albums");
		*/
		
		
		
		launch(args);
	}
}
