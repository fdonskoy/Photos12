package application.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SlideShowController {
	
	
	@FXML ImageView image;
	
	public void initialize() throws ClassNotFoundException, IOException {
		Image img = new Image(PicturesController.photos.get(PicturesController.selectedPhotoIndex).getPhotoAddress()); 
		
		image.setImage(img);
	}
	
	public void next(ActionEvent e){
		if(PicturesController.selectedPhotoIndex == PicturesController.photos.size() - 1){
			PicturesController.selectedPhotoIndex = 0;
		}
		else{
			PicturesController.selectedPhotoIndex++;
		}
		
		Image img = new Image(PicturesController.photos.get(PicturesController.selectedPhotoIndex).getPhotoAddress()); 
		
		image.setImage(img);
	}
	
	public void exit(ActionEvent e) throws IOException{
		SceneLoader.getInstance().changeScene("pictures.fxml");
	}
	
	public void prev(ActionEvent e){
		if(PicturesController.selectedPhotoIndex == 0){
			PicturesController.selectedPhotoIndex = PicturesController.photos.size() - 1;
		}
		else{
			PicturesController.selectedPhotoIndex--;
		}
		
		Image img = new Image(PicturesController.photos.get(PicturesController.selectedPhotoIndex).getPhotoAddress()); 
		
		image.setImage(img);
	}
}
