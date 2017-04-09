package application.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class newAlbumController {
	@FXML TextField newAlbumName;
	
	/**@author Tim
	 * redirects to pictures scene showing the specific album view
	 * */
	public void create(ActionEvent e) throws IOException{
		SceneLoader.getInstance().changeScene("pictures.fxml");
		System.out.println("New album clicked");
	}
	
	/**@author Tim
	 * returns back to the AlbumListView
	 * */
	public void cancel(ActionEvent e) throws IOException{
		SceneLoader.getInstance().changeScene("Albums.fxml");
		System.out.println("New album clicked");
	}
}
