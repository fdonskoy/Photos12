package application.view;

import java.awt.Component;
import java.awt.ScrollPane;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
	public static String remainingAddress;
	public static Photo deletedPhoto;
	private static FileChooser chooser = new FileChooser();
	
	/**The user currently logged in*/
	public static User currentUser = LoginController.currentUser;
	/**The current selected album*/

	/**The current selected album's Pane, used for rename only, as the class used there is different from this one*/
	public static Pane currentPane;
	
	@FXML Button add;
	
	
	@FXML Label albumTitle;
	@FXML Label dateLabel;
	
	@FXML TextArea caption;
	@FXML ImageView preview;
	
	@FXML TextField events;
	@FXML TextField locations;
	@FXML TextField peoples;
	
	@FXML TilePane albumList;
	
	@FXML Menu manage;
	
	
	@FXML Button update;
	
	public void initialize() throws ClassNotFoundException, IOException {
		albumTitle.setText(album.getName());
		
		try {
			Photo first = album.getPhotos().get(0);
			Image img = new Image(first.getPhotoAddress());
			setLocations(first);
			setPeople(first);
			setEvent(first);
			caption.setText(first.getDescription());
			preview.setImage(img);
			dateLabel.setText(first.getLocalDate() + "");
			
			for (Photo p: album.getPhotos()) {
				System.out.println(p.getPhotoAddress());
				albumList.getChildren().add(constructPhotoView(p));
			}
			
		}catch (Exception e)
	    {
			manage.setDisable(true);
			update.setDisable(true);
	        System.out.println("No first photo foundd");
	    }
		
		
		for (Album a: currentUser.getAlbums()) {
			CheckMenuItem e = new CheckMenuItem(a.getName());
			if (a.getName().equals(album.getName())) {
				e.setSelected(true);
			}
			e.setOnAction(MouseClick -> {
				if (e.isSelected()) {
			    //execute this code if checked
					try {
						copyPhoto(e.getText());
					} catch (Exception e1) {
						System.out.println("copy failed");
						e1.printStackTrace();
					}
					System.out.println(e.getText() + " now Selected");
				}
				else {
			    //execute this code if unchecked
					try {
						removePhoto(e.getText());
					} catch (Exception e1) {
						System.out.println("remove failed");
						e1.printStackTrace();
					}
					System.out.println(e.getText() + " now unSelected");
				}
			});
			manage.getItems().add(e);	
		}
		if (album.getPhotos().size() == 0) {
			handleMenuItems(null);
		}
		else {
			handleMenuItems(album.getPhotos().get(0));
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
			setDate(first);
			dateLabel.setText(first.getLocalDate() + "");
			currentUser.writeUser();
		}
		catch (Exception e)
	    {
			locations.setText("");
			peoples.setText("");
			events.setText("");
			caption.setText("");
			preview.setImage(null);
			dateLabel.setText("");
	        System.out.println("Set all to null");
	    }
		
		if(album.getPhotos().size() > 0){
			handleMenuItems(album.getPhotos().get(index));
		}	
	}
	
	public void update() {
		try {
			Photo p = null;
			if (selectedPhotoIndex == -1) {
				p = deletedPhoto;
			}
			else {
				p = album.getPhotos().get(selectedPhotoIndex);
			}
			List<String> eventList = Arrays.asList(events.getText().split("\\s*,\\s*"));
			List<String> locationList = Arrays.asList(locations.getText().split("\\s*,\\s*"));
			List<String> peopleList = Arrays.asList(peoples.getText().split("\\s*,\\s*"));
			
			p.setDescription(caption.getText());
			
			p.setEvents(eventList);
			p.setLocations(locationList);
			p.setPeople(peopleList);
			
			currentUser.writeUser();
			System.out.println("Updated");
		}
		catch (Exception e)
	    {
	        System.out.println("No photo selected");
	    }
		
	}
	
	private Pane constructPhotoView(Photo p) throws IOException{
		
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
		    				System.out.println("Current photo address " + thisPhoto.getPhotoAddress());
		    				break;
		    			}
		    			c++;
		    		}
					if (thisPhoto == null) {
						set(0); return;
					}
					selectedPhotoIndex = c;
					setLocations(thisPhoto);
					setPeople(thisPhoto);
					setEvent(thisPhoto);
					caption.setText(thisPhoto.getDescription());
					preview.setImage(img);

					SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
					
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
				    LocalDate localDate = LocalDate.parse(sdf.format(thisPhoto.getLastModifiedLong()).substring(0, 10), formatter);
				    dateLabel.setText(thisPhoto.getLocalDate() + ""); 
				    
					
					handleMenuItems(thisPhoto);
					
		    	}  
		    	else {
		    		preview.setImage(null);
		    		set(0); return;
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
			System.out.println("No Photos: disabling manage dropdown");
			manage.setDisable(true);
			
			return;
		}
		
		for (Album a: currentUser.getAlbums()) {
			boolean found = false;
			for (Photo photo: a.getPhotos()) {
				if (photo.equals(thisPhoto)) {
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
			selectedPhotoIndex = 0;
			LoginController.currentUser.writeUser();
			SceneLoader.getInstance().changeScene("Albums.fxml");
	}
	
	public void add() throws IOException {
		if (selectedPhotoIndex == -1 && !add.isArmed()) {
			album.addPhoto(deletedPhoto);
			selectedPhotoIndex = album.getPhotos().size() - 1;
			albumList.getChildren().add(constructPhotoView(album.getPhotos().get(selectedPhotoIndex)));
		    set(selectedPhotoIndex);
			currentUser.writeUser();
			return;
		}
		//String currentDir = System.getProperty("user.home");

		
		File file = new File("");
		//chooser = new FileChooser();
		chooser.setTitle("Open File");
		System.out.println("Every time initial " + chooser.getInitialDirectory());
		file = chooser.showOpenDialog(new Stage());
		
	    String s = null;
	    if (file != null) {
	    	//System.out.println("Every time" + chooser.getInitialDirectory());
	    	if (chooser.getInitialDirectory() == null) {
	    		System.out.println("Every time");
	    		chooser.setInitialDirectory(file.getAbsoluteFile());
	    	}
	    	
	    	s = file.getAbsolutePath();
	    	s = "file:/" + s.replace("\\", "/");
	    	
			System.out.println("Adding " + s);
	    	
			Photo p = new Photo(s);
			
			//duplicate photos allowed as different objects for potentially different descriptions
			/*for (Photo photo: album.getPhotos()) {
				if (photo.getPhotoAddress().equals(p.getPhotoAddress())) {
					System.out.println("Can't add duplicate photo");
					p = null;
					return;
				}	
			}*/
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		    LocalDate localDate = LocalDate.parse(sdf.format(file.lastModified()).substring(0, 10), formatter);
		    dateLabel.setText(localDate + "");
		    p.setLocalDate(localDate);
		    
			album.addPhoto(p);
			selectedPhotoIndex = album.getPhotos().size() - 1;
			
			
			
		    Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MILLISECOND,0);
			cal.set(Calendar.YEAR, Integer.parseInt(sdf.format(file.lastModified()).substring(6, 10)));
			cal.set(Calendar.MONTH, Integer.parseInt(sdf.format(file.lastModified()).substring(0, 2)));
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sdf.format(file.lastModified()).substring(3, 5)));
			
			album.getPhotos().get(selectedPhotoIndex).setLastModifiedLong(cal);
			album.getPhotos().get(selectedPhotoIndex).setLastModifiedLong(file.lastModified());;
			
			
			System.out.println(album.getFirstPhotoDateString() + " + " + album.getLastPhotoDateString());
			
			albumList.getChildren().add(constructPhotoView(album.getPhotos().get(selectedPhotoIndex)));
		    set(selectedPhotoIndex);
		    
		    
		    System.out.println("This is the local date added " + localDate);
		    
		    currentUser.writeUser();
		    
		    manage.setDisable(false);
		    update.setDisable(false);
			System.out.println("added");
			
	    }

	}
	
	private void setDate(Photo p) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
	    LocalDate localDate = LocalDate.parse(sdf.format(p.getLastModifiedLong()).substring(0, 10), formatter);
	    dateLabel.setText(p.getLocalDate() + "");
	}
	
	private void copyPhoto(String albumName) throws IOException {
		if (selectedPhotoIndex == -1 && album.getName().equals(albumName)) {
			add();
			return;
		}
		Photo photo = null;
		if (selectedPhotoIndex == -1) {
			photo = deletedPhoto;
		}
		else {
			photo = album.getPhotos().get(selectedPhotoIndex);
		}
		Album copy = null;
		for (Album a: currentUser.getAlbums()) {
			if (a.getName().equals(albumName)) {
				System.out.println("Copying to album " + a.getName());
				copy = a;
				break;
			}
			
		}
		
		copy.addPhoto(photo);
		//copy.testLocalDate(photo);
		
		currentUser.writeUser();
		System.out.println("Copied");
		System.out.println(copy.getPhotos().get(0).getDescription());
		System.out.println("Current photo " + copy.getPhotos().get(copy.getPhotos().size() - 1).getDescription());	
	}
	
	private void removePhoto(String albumName) throws IOException {
		Photo p = null;
		if (selectedPhotoIndex == -1) {
			p = deletedPhoto;
		}
		else {
			p = album.getPhotos().get(selectedPhotoIndex);
		}
		Album other = null;
		for (Album a: currentUser.getAlbums()) {
			if (a.getName().equals(albumName)) {
				System.out.println("This is album name " + a.getName());
				other = a;
				break;
			}
		}
		
		other.removePhoto(p);
		//other.testLocalDate(p);
		currentUser.writeUser();
		
		if(albumName.equals(album.getName())){
			albumList.getChildren().remove(selectedPhotoIndex);
			selectedPhotoIndex = -1;
			deletedPhoto = p;
		}
		
		/*if(album.getPhotos().size() <= 0){
			update.setDisable(true);
			manage.setDisable(true);
		}*/

		System.out.println("Removed from " + other.getName());
		
		//set(0);
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
