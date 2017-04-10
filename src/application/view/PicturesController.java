package application.view;

import java.awt.Component;
import java.awt.ScrollPane;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.print.DocFlavor.URL;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import application.Album;
import application.Photo;
import application.User;

public class PicturesController {
	public static Album album;
	public static int selectedPhotoIndex;
	
	/**The user currently logged in*/
	public static User currentUser = LoginController.currentUser;
	/**The current selected album*/

	/**The current selected album's Pane, used for rename only, as the class used there is different from this one*/
	public static Pane currentPane;
	
	@FXML Button add;
	
	
	@FXML Label albumTitle;
	
	@FXML TextArea caption;
	@FXML ImageView preview;
	
	@FXML TextField events;
	@FXML TextField locations;
	@FXML TextField peoples;
	
	@FXML TilePane albumList;
	
	public void initialize() throws ClassNotFoundException, IOException {
		set(0);
	}
	
	public void set(int index) {
		try {
			Photo first = album.getPhotos().get(index);
			Image img = new Image(first.getPhotoAddress());
			setLocations(first);
			setPeople(first);
			setEvent(first);
			caption.setText(first.getDescription());
			preview.setImage(img);
		
			for (Photo p: album.getPhotos()) {
				albumList.getChildren().add(constructAlbumView(p));
			}
			albumTitle.setText(album.getName());
		}catch (Exception e)
	    {
	        System.out.println("No first photo found");
	    }
		
		//initialize the manage drop down
		
	}
	
	public void update() {
		Photo p = album.getPhotos().get(selectedPhotoIndex);
		List<String> eventList = Arrays.asList(events.getText().split("\\s*,\\s*"));
		List<String> locationList = Arrays.asList(locations.getText().split("\\s*,\\s*"));
		List<String> peopleList = Arrays.asList(peoples.getText().split("\\s*,\\s*"));
		
		p.setDescription(caption.getText());
		
		p.setEvents(eventList);
		p.setLocations(locationList);
		p.setPeople(peopleList);
		
		System.out.println("Updated");
	}
	
	private Pane constructAlbumView(Photo p) throws IOException{
		
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(getClass().getResource("/application/view/PhotoView.fxml"));
		Pane albumView = (Pane)loader.load();
		
		
		Image img;
	
		ImageView photo = (ImageView)albumView.lookup("#imageAddress");
		
		
		if(p != null){
			img = new Image(p.getPhotoAddress());
		}
		else{
			File imageFile = new File("src/utility/placeholder.png");
			String fileLocation = imageFile.toURI().toString();
			img = new Image(fileLocation);
		}

		photo.setImage(img);
		albumView.setAccessibleText(p.getPhotoAddress());

		albumView.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent me) {
		    	Pane pane = (Pane)me.getSource();
		    	
		    	currentPane = pane;
		    	if(currentPane != null){
		    		System.out.println(currentPane.getAccessibleText());
		    		//location, event, people, caption
		    		Photo thisPhoto = null;
		    		int c = 0;
					for (Photo curPhoto: album.getPhotos()) {
		    			if (curPhoto.getPhotoAddress().equals(pane.getAccessibleText())) {
		    				thisPhoto = curPhoto;
		    				break;
		    			}
		    			c++;
		    		}
					selectedPhotoIndex = c;
					setLocations(thisPhoto);
					setPeople(thisPhoto);
					setEvent(thisPhoto);
					caption.setText(thisPhoto.getDescription());
					preview.setImage(img);
		    	}   
		        	
		        for(Node otherPane : albumList.getChildren()){
		        	((Pane)otherPane).setBorder(null);
		        }

		        pane.setBorder(new Border(new BorderStroke(Color.RED, 
		                									BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL)));

		        System.out.println(currentPane.getId() + " selected");
		    }
		});
		
		return albumView;
	}

	private void setLocations(Photo p) {
		String locationString = "";
		if (p.getLocations() != null) {
			for (String s: p.getLocations()) {
				locationString += s + ", ";
			}
			locationString = locationString.substring(0, locationString.length()-2);
		}
		locations.setText(locationString);
	}
	private void setPeople(Photo p) {
		String string = "";
		if (p.getPeople() != null) {
			for (String s: p.getPeople()) {
				string += s + ", ";
			}
			string = string.substring(0, string.length()-2);
		}
		peoples.setText(string);
	}
	private void setEvent(Photo p) {
		String string = "";
		if (p.getEvents() != null) {
			for (String s: p.getEvents()) {
				string += s + ", ";
			}
			string = string.substring(0, string.length()-2);
		}
		events.setText(string);
	}
	
	public void startSlideShow() throws IOException{
		if(album.getPhotos().size() > 0){
			LoginController.currentUser.writeUser();
			SceneLoader.getInstance().changeScene("SlideShow.fxml");
		}
	}
	
	public void back() throws IOException{
			SceneLoader.getInstance().changeScene("Albums.fxml");
	}
	
	public void add() throws IOException {
		FileChooser chooser = new FileChooser();
	    chooser.setTitle("Open File");
	    File file = chooser.showOpenDialog(new Stage());
		
	    String s = null;
	    if (file != null) {
	    	s =  file.getAbsolutePath();
	    	s = s.replace("\\", "/");
		    System.out.println("file:/" + s);
		    
		    Photo newPhoto = new Photo("file:/" + s);
		    
		    File imageFile = new File("s");
			String fileLocation = imageFile.toURI().toString();
			album.addPhoto(fileLocation);

		    albumList.getChildren().add(constructAlbumView(newPhoto));
		    
		    
		    
			System.out.println("added");
	    }
	    
	}
	
	
	/**@author Tim
	 * Calls the central utility class for the drop down menu which saves everything needed and exits
	 * */
	public void exit() throws IOException{
		FileDropDown_Util.exit();
	}
	
	/**@author Tim
	 * Calls the central utility class for the drop down menu which saves everything needed and logs out
	 * */
	public void logout() throws IOException{
		FileDropDown_Util.logout();
	}
	
	/**@author Tim
	 * Calls the central utility class for the drop down menu which calls redirects to the search stage
	 * */
	public void search() throws IOException{
		FileDropDown_Util.search();
	}
	
	
}
