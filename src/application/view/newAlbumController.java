package application.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class newAlbumController {
	@FXML TextField newAlbumName;
	
	public void create(ActionEvent e) throws IOException{
		SceneLoader.getInstance().changeScene("pictures.fxml");
		System.out.println("New album clicked");
	}
	
	public void cancel(ActionEvent e) throws IOException{
		SceneLoader.getInstance().changeScene("Albums.fxml");
		System.out.println("New album clicked");
	}
}
