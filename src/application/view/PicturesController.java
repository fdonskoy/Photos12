package application.view;

import java.awt.Component;
import java.awt.ScrollPane;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.print.DocFlavor.URL;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
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
	
	@FXML Menu manage;
	
	@FXML DatePicker date;
	
	public void initialize() throws ClassNotFoundException, IOException {
		albumTitle.setText(album.getName());
		date.setDisable(true);
		
		try {
			Photo first = album.getPhotos().get(0);
			Image img = new Image(first.getPhotoAddress());
			setLocations(first);
			setPeople(first);
			setEvent(first);
			caption.setText(first.getDescription());
			preview.setImage(img);
			
			for (Photo p: album.getPhotos()) {
				albumList.getChildren().add(constructAlbumView(p));
			}
			
		}catch (Exception e)
	    {
	        System.out.println("No first photo found");
	    }
		
		
		for (Album a: currentUser.getAlbums()) {
			CheckMenuItem e = new CheckMenuItem(a.getName());
			if (a.getName().equals(album.getName())) {
				e.setSelected(true);
			}
			e.setOnAction(event -> {
				if (e.isSelected()) {
			    //execute this code if checked
					copyPhoto(e.getText());System.out.println("Selected");
				}
				else {
			    //execute this code if unchecked
					removePhoto(e.getText());System.out.println("unSelected");
				}
			});
			manage.getItems().add(e);	
		}
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
		}
		catch (Exception e)
	    {
			locations.setText("");
			peoples.setText("");
			events.setText("");
			caption.setText("");
			preview.setImage(null);
	        System.out.println("No first photo found");
	    }
		
		handleMenuItems(album.getPhotos().get(index));
	}
	
	public void update() {
		try {
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
		catch (Exception e)
	    {
	        System.out.println("No photo selected");
	    }
		
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
					
					handleMenuItems(thisPhoto);
					
		    	}   
		        	
		        for(Node otherPane : albumList.getChildren()){
		        	((Pane)otherPane).setBorder(null);
		        }

		        pane.setBorder(new Border(new BorderStroke(Color.RED, 
		                									BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL)));
		    }
		});
		
		return albumView;
	}
	
	private void handleMenuItems(Photo thisPhoto) {
		//set checkmenuitems to unchecked or checked
		if (thisPhoto == null) {
			for (MenuItem e: manage.getItems()) {
				((CheckMenuItem) e).setSelected(false);
			}
			return;
		}
		for (Album a: currentUser.getAlbums()) {
			boolean found = false;
			for (Photo photo: a.getPhotos()) {
				if (photo.getPhotoAddress().equals(thisPhoto.getPhotoAddress())) {
					for (MenuItem e: manage.getItems()) {
						if (e.getText().equals(a.getName())) {
							((CheckMenuItem) e).setSelected(true);
							found = true;
						}
					}
				}
			}
			if (!found) {
				for (MenuItem e: manage.getItems()) {
					if (e.getText().equals(a.getName())) {
						((CheckMenuItem) e).setSelected(false);
					}
				}
			}
			
		}
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
	    	s = file.getAbsolutePath();
	    	s = s.replace("\\", "/");
		    System.out.println("file:/" + s);

		    
			
		    File imageFile = new File(s);
			String fileLocation = imageFile.toURI().toString();
			album.addPhoto(fileLocation);
			int size = album.getPhotos().size();
			
			
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
			
		    Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MILLISECOND,0);
			cal.set(Calendar.YEAR, Integer.parseInt(sdf.format(file.lastModified()).substring(6, 10)));
			cal.set(Calendar.MONTH, Integer.parseInt(sdf.format(file.lastModified()).substring(0, 2)));
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sdf.format(file.lastModified()).substring(3, 5)));
			album.getPhotos().get(size-1).setDate(cal);
			
			albumList.getChildren().add(constructAlbumView(album.getPhotos().get(size-1)));
		    set(size-1);
		    
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		    LocalDate localDate = LocalDate.parse(sdf.format(file.lastModified()).substring(0, 10), formatter);
		    //date.setAccessibleText(sdf.format(file.lastModified()).substring(0, 10));
		    date.setValue(localDate);
		   
			System.out.println("added");
	    }
	    
	}
	private void setDate() {
		
	}
	
	private void copyPhoto(String albumName) {
		try {
			Photo p = album.getPhotos().get(selectedPhotoIndex);
			Album copy = null;
			for (Album a: currentUser.getAlbums()) {
				if (a.getName().equals(albumName)) {
					System.out.println("This is album name " + a.getName());
					copy = a;
					break;
				}
				
			}
			copy.addPhoto(p);
			System.out.println("Copied");
			System.out.println(copy.getPhotos().get(0).getDescription());
		}
		catch (Exception e)
	    {
			e.printStackTrace();
	        System.out.println("No copy");
	    }	
	}
	
	private void removePhoto(String albumName) {
		try {
			Photo p = album.getPhotos().get(selectedPhotoIndex);
			Album other = null;
			for (Album a: currentUser.getAlbums()) {
				if (a.getName().equals(albumName)) {
					System.out.println("This is album name " + a.getName());
					other = a;
					break;
				}
			}
			
			other.getPhotos().remove(p);
			System.out.println("Removed from " + other.getName());
			set(0);
		}
		catch (Exception e)
	    {
			e.printStackTrace();
	        System.out.println("No remove");
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
