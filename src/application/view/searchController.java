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
import java.util.Collections;
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

public class searchController {
	public static int selectedPhotoIndex;
	
	/**The user currently logged in*/
	public static User currentUser = LoginController.currentUser;
	/**The current selected album*/

	/**The current selected album's Pane, used for rename only, as the class used there is different from this one*/
	public static Pane currentPane;
	
	private ArrayList<Photo> photosList = new ArrayList<Photo>();
	private List<String> albums = null;
	private String captionsTag = null;
	private List<String> locationsTag = null;
	private List<String> peoplesTag = null;
	private List<String> eventsTag = null;
	private LocalDate start = null;
	private LocalDate end = null;
	
	@FXML Button createAlbum;
	
	
	@FXML Label albumTitle;
	
	@FXML TextArea caption;
	@FXML ImageView preview;
	
	@FXML TextField events;
	@FXML TextField locations;
	@FXML TextField peoples;
	
	@FXML TextField ALBUMStag;
	@FXML TextField CAPTIONStag;
	@FXML TextField LOCATIONStag;
	@FXML TextField PEOPLEStag;
	@FXML TextField EVENTStag;
	@FXML DatePicker startDate;
	@FXML DatePicker endDate;
	
	@FXML TilePane albumList;
	
	@FXML DatePicker date;
	
	public void initialize() throws ClassNotFoundException, IOException {
		date.setDisable(true);
		//Image img = new Image(currentUser.getAlbums().get(0).getPhotos().get(0).getPhotoAddress());
		//preview.setImage(img);
		/*
		try {
			Photo first = album.getPhotos().get(0);
			Image img = new Image(first.getPhotoAddress());
			setLocations(first);
			setPeople(first);
			setEvent(first);
			caption.setText(first.getDescription());
			preview.setImage(img);
			
			for (Photo p: album.getPhotos()) {
				System.out.println(p.getPhotoAddress());
				albumList.getChildren().add(constructAlbumView(p));
			}
			
		}catch (Exception e)
	    {
	        System.out.println("No first photo foundd");
	    }
		*/
	}
	
	public void searchCall(){
		photosList.clear();
		set(0);
		albumList.getChildren().clear();
		
		if (!ALBUMStag.getText().trim().equals("")) {
			albums = Arrays.asList(ALBUMStag.getText().split("\\s*,\\s*"));
		}
		if (!CAPTIONStag.getText().trim().equals("")) {
			captionsTag = CAPTIONStag.getText();
		}
		if (!LOCATIONStag.getText().trim().equals("")) {
			locationsTag = Arrays.asList(LOCATIONStag.getText().split("\\s*,\\s*"));
		}
		if (!PEOPLEStag.getText().trim().equals("")) {
			peoplesTag = Arrays.asList(PEOPLEStag.getText().split("\\s*,\\s*"));
		}
		if (!EVENTStag.getText().trim().equals("")) {
			eventsTag = Arrays.asList(EVENTStag.getText().split("\\s*,\\s*"));
		}

		start = startDate.getValue();
		end = endDate.getValue();
		
		boolean albumFound = false;
		boolean captionsFound = false;
		boolean locationsFound = false;
		boolean peoplesFound = false;
		boolean eventsFound = false;
		boolean dateFound = false;
		
		//Photo p = currentUser.getAlbums().get(0).getPhotos().get(0);
		for (Album a: currentUser.getAlbums()) {
			for (Photo p: a.getPhotos()) {
				if (albums == null || albums.contains(p.getDescription())) { //fix this, how to get album name from p
					albumFound = true;
				}
				if (captionsTag == null || captionsTag.equals(p.getDescription())) {
					captionsFound = true;
				}
				if (locationsTag == null || !Collections.disjoint(locationsTag, p.getLocations())) {
					locationsFound = true;
				}
				if (peoplesTag == null || !Collections.disjoint(peoplesTag, p.getPeople())) {
					peoplesFound = true;
				}
				if (eventsTag == null || !Collections.disjoint(eventsTag, p.getEvents())) {
					eventsFound = true;
				}
				dateFound = true;
				
				if (albumFound && captionsFound && locationsFound && peoplesFound && eventsFound && dateFound && !photosList.contains(p)) {
					photosList.add(p);
					System.out.println("All true");
					
				}
				albumFound = false;
				captionsFound = false;
				locationsFound = false;
				peoplesFound = false;
				eventsFound = false;
				dateFound = false;
			}
		}
		
		if (photosList.isEmpty()) {
			albumList.getChildren().clear();
		}
		else {
			for (Photo photo: photosList) {
				System.out.println(photo.getPhotoAddress());
				try {
					albumList.getChildren().add(constructAlbumView(photo));
					System.out.println("Displayed");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Failed to display");
				}
			}
		}
		set(0);
		
		
		albums = null;
		captionsTag = null;
		locationsTag = null;
		peoplesTag = null;
		eventsTag = null;
		
		
	}
	
	public void set(int index) {
		try {
			Photo first = photosList.get(index);
			Image img = new Image(first.getPhotoAddress());
			setLocations(first);
			setPeople(first);
			setEvent(first);
			caption.setText(first.getDescription());
			preview.setImage(img);
			setDate(first);
			//currentUser.writeUser();
		}
		catch (Exception e)
	    {
			locations.setText("");
			peoples.setText("");
			events.setText("");
			caption.setText("");
			preview.setImage(null);
			date.setValue(null);
			albumList.getChildren().clear();
	        System.out.println("Set all to null");
	    }
		

	}
	
	public void update() {
		try {
			Photo p = photosList.get(selectedPhotoIndex);
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
					for (Photo curPhoto: photosList) {
		    			if (curPhoto.getPhotoAddress().equals(pane.getAccessibleText())) {
		    				thisPhoto = curPhoto;
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
				    date.setValue(localDate);
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
	
	public void back() throws IOException{
			LoginController.currentUser.writeUser();
			SceneLoader.getInstance().changeScene("Albums.fxml");
	}
	
	/*public void add() throws IOException {
		FileChooser chooser = new FileChooser();
	    chooser.setTitle("Open File");
	    File file = new File("");
	    file = chooser.showOpenDialog(new Stage());
		
	    String s = null;
	    if (file != null) {
	    	s = file.getAbsolutePath();
	    	s = s.replace("\\", "/");
		    System.out.println("file:/" + s);
		    
		    File imageFile = new File(s);
			String fileLocation = imageFile.toURI().toString();
			
			Photo p = new Photo(fileLocation);
			

			for (Photo photo: album.getPhotos()) {
				if (photo.getPhotoAddress().equals(p.getPhotoAddress())) {
					System.out.println("Can't add duplicate photo");
					p = null;return;
				}
				
			}
			
			
			album.addPhoto(p);
			//album.addPhoto(s);
			int size = album.getPhotos().size();

			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
			
		    Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MILLISECOND,0);
			cal.set(Calendar.YEAR, Integer.parseInt(sdf.format(file.lastModified()).substring(6, 10)));
			cal.set(Calendar.MONTH, Integer.parseInt(sdf.format(file.lastModified()).substring(0, 2)));
			cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(sdf.format(file.lastModified()).substring(3, 5)));
			album.getPhotos().get(size-1).setDate(cal);
			album.getPhotos().get(size-1).setLastModifiedLong(file.lastModified());;
			
			albumList.getChildren().add(constructAlbumView(album.getPhotos().get(size-1)));
		    set(size-1);
		    
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
		    LocalDate localDate = LocalDate.parse(sdf.format(file.lastModified()).substring(0, 10), formatter);
		    date.setValue(localDate);
		    currentUser.writeUser();
		    
			System.out.println("added");
	    }
	    
	}*/
	
	private void setDate(Photo p) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
	    LocalDate localDate = LocalDate.parse(sdf.format(p.getLastModifiedLong()).substring(0, 10), formatter);
	    date.setValue(localDate);
	}
	
	/*private void copyPhoto(String albumName) {
		try {
			Photo photo = album.getPhotos().get(selectedPhotoIndex);
			Album copy = null;
			for (Album a: currentUser.getAlbums()) {
				if (a.getName().equals(albumName)) {
					System.out.println("This is album name " + a.getName());
					copy = a;
					break;
				}
				
			}
			copy.addPhoto(photo);
			//System.out.println("Copied");
			
			//File imageFile = new File(photo.getPhotoAddress());
			//String fileLocation = imageFile.toURI().toString();
			//copy.addPhoto(fileLocation);
			int size = copy.getPhotos().size();
			Photo p = copy.getPhotos().get(size-1);
			
			p.setDate(photo.getDate());
			p.setDescription(photo.getDescription());
			p.setEvents(photo.getEvents());
			p.setLocations(photo.getLocations());
			p.setPeople(photo.getPeople());
			
			currentUser.writeUser();
			System.out.println("Copied");
			System.out.println(copy.getPhotos().get(0).getDescription());
			System.out.println("Current photo " + copy.getPhotos().get(selectedPhotoIndex).getDescription());
		}
		catch (Exception e)
	    {
			//e.printStackTrace();
	        System.out.println("No copy");
	    }	
	}*/

	
	
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
