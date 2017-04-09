package application.view;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import application.Photo;

public class PicturesController {
	public static ArrayList<Photo> photos;
	public static int selectedPhotoIndex;
	
	@FXML Button add;
	
	public void startSlideShow() throws IOException{
		if(photos.size() > 0){
			LoginController.currentUser.writeUser();
			SceneLoader.getInstance().changeScene("SlideShow.fxml");
		}
	}
	
}
