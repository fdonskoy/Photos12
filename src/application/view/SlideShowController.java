package application.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**Controller responsible for showing the slideshow starting with the photo last selected in the album administration stage
 * @author Tim
 * */
public class SlideShowController {
	
	
	@FXML ImageView image;
	
	/**@author Tim
	 sets up the first image in the slide show*/
	public void initialize() {
		Image img = new Image(PicturesController.album.getPhotos().get(PicturesController.selectedPhotoIndex).getPhotoAddress()); 
		
		image.setImage(img);
	}

	/**@author Tim
	 moves the slideshow to the next picture in the album*/
	public void next(){
		if(PicturesController.selectedPhotoIndex == PicturesController.album.getPhotos().size() - 1){
			PicturesController.selectedPhotoIndex = 0;
		}
		else{
			PicturesController.selectedPhotoIndex++;
		}
		
		Image img = new Image(PicturesController.album.getPhotos().get(PicturesController.selectedPhotoIndex).getPhotoAddress()); 
		
		image.setImage(img);
	}
	

	/**@author Tim
	 * returns back to the view of the album
	 * @throws IOException if user fail to update*/
	public void exit(ActionEvent e) throws IOException{
		SceneLoader.getInstance().changeScene("pictures.fxml");
	}
	
	/**@author Tim
	 moves the slideshow to the previous picture in the album*/
	public void prev(){
		if(PicturesController.selectedPhotoIndex == 0){
			PicturesController.selectedPhotoIndex = PicturesController.album.getPhotos().size() - 1;
		}
		else{
			PicturesController.selectedPhotoIndex--;
		}
		
		Image img = new Image(PicturesController.album.getPhotos().get(PicturesController.selectedPhotoIndex).getPhotoAddress()); 
		
		image.setImage(img);
	}
}
