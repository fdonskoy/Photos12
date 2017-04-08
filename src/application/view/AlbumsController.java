package application.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import application.Admin;
import application.Album;
import application.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

public class AlbumsController {
	public User currentUser;
	
	@FXML Menu newAlbum;
	@FXML TilePane albumList;
	
	public void initialize() throws ClassNotFoundException, IOException {
		currentUser = LoginController.currentUser;
		System.out.println("constructing");
		
		System.out.println(currentUser.getUsername() + " has " + currentUser.getAlbums().size() + " albums");
		
		constructAlbumView(currentUser.getAlbums().get(0));
	}
	
	public void newAlbum(ActionEvent e){
		System.out.println("New album clicked");
	}
	
	
	
	
	private void constructAlbumView(Album album) throws IOException{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(getClass().getResource("/application/view/AlbumView.fxml"));
		//GridPane root = (GridPane)loader.load();
		Pane albumView = (Pane)loader.load();
		
		ImageView photo = (ImageView)albumView.lookup("#imageAddress");
		Image img = (album.getFirstPhotoThumbnail() != null)	? new Image(album.getFirstPhotoThumbnail()) 
																: new Image("src/utility/placeholder.png");
		photo.setImage(img);
		
		Label albumName = (Label)albumView.lookup("#albumName");
		albumName.setText(album.getName());
		
		Label dateRange = (Label)albumView.lookup("#dateRange");
		albumName.setText(album.getName());
		
		Label numPhotots = (Label)albumView.lookup("#numberOfPhotos");
		albumName.setText(album.getNumPhotos() + " photos");
	}
	
	
	
	public void exit() throws IOException{
		FileDropDown_Util.exit();
	}
	
	public void logout() throws IOException{
		FileDropDown_Util.logout();
	}
	
	public void search() throws IOException{
		FileDropDown_Util.search();
	}
}
