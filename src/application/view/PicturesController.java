package application.view;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import application.Album;
import application.Photo;

public class PicturesController {
	public static Album album;
	public static int selectedPhotoIndex;
	
	@FXML Button add;
	
	public void startSlideShow() throws IOException{
		if(album.getPhotos().size() > 0){
			LoginController.currentUser.writeUser();
			SceneLoader.getInstance().changeScene("SlideShow.fxml");
		}
	}
	
	
	
}
