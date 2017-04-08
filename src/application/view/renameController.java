package application.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class renameController {
	@FXML TextField albumNameEditable;
	@FXML Label albumName;
	
	public void renameSave(ActionEvent e) throws IOException{
		renameSave();
	}
	
	public void renameSave() throws IOException{
		String name = albumNameEditable.getText();
		if(name.length() > 0){
			AlbumsController.currentAlbum.setName(name);
			albumName.setText(name);
			albumNameEditable.setVisible(false);
			albumName.setVisible(true);
			AlbumsController.currentUser.writeUser();
		}
	}
}
