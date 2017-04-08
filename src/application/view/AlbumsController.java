package application.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import application.Admin;
import application.Album;
import application.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;

public class AlbumsController {
	public User currentUser;
	private ArrayList<Album> albums;
	
	@FXML Menu newAlbum;
	
	public void initialize() throws ClassNotFoundException, IOException {
		currentUser = LoginController.currentUser;
		albums = new ArrayList<Album>();
	}
	
	public void newAlbum(ActionEvent e){
		System.out.println("New album clicked");
	}
	
	public void exit() throws IOException{
		FileDropDown_Util.exit();
	}
	
	public void logout() throws IOException{
		FileDropDown_Util.logout();
	}
}
