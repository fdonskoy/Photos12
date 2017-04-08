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
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;

public class AlbumsController {
	public User currentUser;
	
	@FXML MenuItem newAlbum;
	@FXML TilePane albumList;
	
	public void initialize() throws ClassNotFoundException, IOException {
		currentUser = LoginController.currentUser;
		System.out.println("constructing");
		
		System.out.println(currentUser.getUsername() + " has " + currentUser.getAlbums().size() + " albums");
		
		populateListView();
	}
	
	private void populateListView() throws IOException{
		for(Album album : currentUser.getAlbums()){
			albumList.getChildren().add(constructAlbumView(album));
		}
	}
	
	
	
	public void newAlbum(ActionEvent e){
		System.out.println("New album clicked");
	}
	
	public void open(ActionEvent e){
		System.out.println("open clicked");
	}
	
	public void rename(ActionEvent e){
		System.out.println("rename clicked");
	}
	
	public void delete(ActionEvent e){
		System.out.println("delete clicked");
	}
	
	
	
	
	private Pane constructAlbumView(Album album) throws IOException{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(getClass().getResource("/application/view/AlbumView.fxml"));
		//GridPane root = (GridPane)loader.load();
		Pane albumView = (Pane)loader.load();
		
		Image img;
		String dateString = "";
		
		ImageView photo = (ImageView)albumView.lookup("#imageAddress");
		Label albumName = (Label)albumView.lookup("#albumName");
		Label dateRange = (Label)albumView.lookup("#dateRange");
		Label numPhotots = (Label)albumView.lookup("#numberOfPhotos");
		
		if(album.getFirstPhotoThumbnail() != null){
			img = new Image(album.getFirstPhotoThumbnail());
			dateString = album.getFirstPhotoDateString() + " - " + album.getLastPhotoDateString(); 
		}
		else{
			File imageFile = new File("src/utility/placeholder.png");
			String fileLocation = imageFile.toURI().toString();
			img = new Image(fileLocation);
		}
		
		albumName.setText(album.getName());
		photo.setImage(img);
		numPhotots.setText(album.getNumPhotos() + " photos");
		dateRange.setText(dateString);
		
		albumView.setId(album.getName() + "_id");
		
		return albumView;
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
