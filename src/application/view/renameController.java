package application.view;

import java.io.IOException;

import application.Album;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**Helper class to receive 'enter' keystroke to save the rename of an album
 * @author Tim
 * */
public class renameController {
	@FXML TextField albumNameEditable;
	@FXML Label albumName;

	/**@author Tim
	 * switches textfield to label and saves the new title, or gives warning message
	 * @throws IOException when user fails to write to dat file
	 * */
	public void renameSave() throws IOException{
		String name = albumNameEditable.getText();
		for(Album album : LoginController.currentUser.getAlbums()){
			if(album.getName().equals(name)){
				albumNameEditable.setText(null);
				albumNameEditable.setPromptText("Album name already in use");
				return;
			}
		}
		
		if(name.length() > 0){
			albumNameEditable.setPromptText("New Title");
			AlbumsController.currentAlbum.setName(name);
			albumName.setText(name);
			albumNameEditable.setVisible(false);
			albumName.setVisible(true);
			LoginController.currentUser.writeUser();
		}
	}
}
