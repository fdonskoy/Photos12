package application.view;

import java.util.List;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import application.Admin;
import application.Album;
import application.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;

/**Controller responsible for the stage where albums are selected for editing, renamed, or deleted
 * @author Tim
 * */
public class AlbumsController {
	/**The user currently logged in*/
	private static User currentUser;
	/**The current selected album*/
	public static Album currentAlbum;
	/**The current selected album's Pane, used for rename only, as the class used there is different from this one*/
	public static Pane currentPane;
	
	@FXML MenuItem newAlbum;
	@FXML TilePane albumList;
	@FXML MenuItem rename;
	@FXML MenuItem open;
	@FXML MenuItem delete;
	
	/**@author Tim
	 * populates view with album displays
	 * @throws IOException if user fail to update
	 * */
	public void initialize() throws IOException {
		currentUser = LoginController.currentUser;
		currentAlbum = null;
		populateListView();
		if(currentAlbum == null){
			open.setDisable(true);
			rename.setDisable(true);
			delete.setDisable(true);
		}
	}
	
	/**@author Tim
	 * looks at the current user's albums and populates the album list with ui representations
	 * @throws IOException if user fail to update
	 * */
	private void populateListView() throws IOException{
		for(Album album : currentUser.getAlbums()){
			albumList.getChildren().add(constructAlbumView(album));
		}
	}
	
	
	/**@author Tim
	 * redirects to the scene asking for the new album name
	 * @throws IOException if user fail to update
	 * */
	public void newAlbum() throws IOException{
		currentUser.writeUser();
		SceneLoader.getInstance().changeScene("NewAlbum.fxml");
	}
	
	/**@author Tim
	 * redirects to a view of pictures within the selected album
	 * @throws IOException if user fail to update
	 * */
	public void open() throws IOException{
		if(currentAlbum == null){
			return;
		}
		currentUser.writeUser();
		PicturesController.album = currentAlbum;
		SceneLoader.getInstance().changeScene("pictures.fxml");
	}
	
	/**@author Tim
	 * replaces album title with a text field. The save part of the rename is covered in renameController because the album ui is covered in a different fxml file, which does not like to share controllers with others
	 * */
	public void rename(){
		if(currentAlbum == null){
			return;
		}
		if(currentAlbum != null){
			currentPane.lookup("#albumName").setVisible(false);
			currentPane.lookup("#albumNameEditable").setVisible(true);
		}
	}
	
	/**@author Tim
	 * Deletes and album, saves user, and refreshes the scene
	 * @throws IOException if user fail to update
	 * */
	public void delete() throws IOException{
		if(currentAlbum == null){
			return;
		}
		albumList.getChildren().remove(albumList.lookup("#" + currentAlbum.getName() + "_" + currentUser.getUsername()));
		currentUser.removeAlbum(currentAlbum);
		currentUser.writeUser();
		currentAlbum = null;
		SceneLoader.getInstance().changeScene("Albums.fxml");
	}
	
	
	
	/**@author Tim
	 * @param album: the album which is used to create the album representation from the AlbumView.fxml file
	 * @return This returns a pane based on AlbumView.fxml file and customized to represent the album
	 * @throws IOException if user fail to update
	 * */
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
			//dateString = album.getFirstPhotoDateString() + " - " + album.getLastPhotoDateString();
			
			dateString = (album.getFirstLocalDateString().replace('-', '/') + " - " + album.getLastLocalDateString().replace('-', '/'));
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
		albumView.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent me) {
		    	rename.setDisable(false);
		    	open.setDisable(false);
		    	delete.setDisable(false);
		    	if(currentPane != null){
		    		((TextField) currentPane.lookup("#albumNameEditable")).setText(null);
		    		currentPane.lookup("#albumNameEditable").setVisible(false);
			    	currentPane.lookup("#albumName").setVisible(true);
		    	}
		    	
		    	Pane pane = (Pane)me.getSource();
		        currentPane = pane;
		        String id = pane.getId();
		        for(Album album : currentUser.getAlbums()){
		        	if(album.getName().equals(id.substring(0, id.lastIndexOf("_")))){
		        		currentAlbum = album;
		        	}
		        }
		        	
		        for(Node otherPane : albumList.getChildren()){
		        	((Pane)otherPane).setBorder(null);
		        }
		        
		        pane.setBorder(new Border(new BorderStroke(Color.RED, 
		                									BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		        
		        System.out.println(currentAlbum.getName() + " selected");
		    }
		});
		
		return albumView;
	}
	
	
	/**@author Tim
	 * Calls the central utility class for the drop down menu which saves everything needed and exits
	 * @throws IOException if user fail to update
	 * */
	public void exit() throws IOException{
		FileDropDown_Util.exit();
	}
	
	/**@author Tim
	 * Calls the central utility class for the drop down menu which saves everything needed and logs out
	 * @throws IOException if user fail to update
	 * */
	public void logout() throws IOException{
		FileDropDown_Util.logout();
	}
	
	/**@author Tim
	 * Calls the central utility class for the drop down menu which calls redirects to the search stage
	 * @throws IOException if user fail to update
	 * */
	public void search() throws IOException{
		FileDropDown_Util.search();
	}
}
