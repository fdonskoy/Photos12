package application.view;

import java.io.IOException;
import java.util.ArrayList;

import application.Album;
import application.Photo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**Controller responsible for reading in the name of a new album and creating it. It then redirects to the album administration stage
 * @author Tim
 * */
public class newAlbumController {
	/**photos to be added to the new album. To be set by search*/
	public static ArrayList<Photo> photos;
	/**the new album to be grabbed by album view once create is clicked*/
	public static Album album;
	
	@FXML TextField newAlbumName;
	@FXML Label label;
	
	/**@author Tim
	 * Redirects to pictures scene showing the specific album view
	 * @throws IOException when user fails to write
	 * */
	public void create() throws IOException{
		album = new Album(newAlbumName.getText(), LoginController.currentUser.getUsername());
		
		if(newAlbumName.getText().trim().length() == 0){
			label.setText("Please enter atleast 1 character");
			label.setTextFill(Color.web("#ff0000"));
			return;
		}
		
		if(LoginController.currentUser.addAlbum(album) == null){
			label.setText("Album name already in use");
			label.setTextFill(Color.web("#ff0000"));
			return;
		}
		
		if(photos != null){
			for(Photo photo : photos){
				album.addPhoto(photo);
			}
			photos = null;
		}
			
		LoginController.currentUser.writeUser();
		PicturesController.album = album;
		SceneLoader.getInstance().changeScene("pictures.fxml");
	}
	
	/**@author Tim
	 * returns back to the AlbumListView
	 * @throws IOException when user fails to write
	 * */
	public void cancel() throws IOException{
		photos = null;
		album = null;
		SceneLoader.getInstance().changeScene("Albums.fxml");
		System.out.println("New album clicked");
	}
	
	/**@author Tim
	 * Calls the central utility class for the drop down menu which saves everything needed and exits
	 * @throws IOException when user fails to write
	 * */
	public void exit() throws IOException{
		FileDropDown_Util.exit();
	}
	
	/**@author Tim
	 * Calls the central utility class for the drop down menu which saves everything needed and logs out
	 * @throws IOException when user fails to write
	 * */
	public void logout() throws IOException{
		FileDropDown_Util.logout();
	}
	
	/**@author Tim
	 * Calls the central utility class for the drop down menu which calls redirects to the search stage
	 * @throws IOException when user fails to write
	 * */
	public void search() throws IOException{
		FileDropDown_Util.search();
	}
}
