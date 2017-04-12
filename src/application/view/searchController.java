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
import java.util.ListIterator;

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
	private List<String> captionsTag = null;
	private List<String> locationsTag = null;
	private List<String> peoplesTag = null;
	private List<String> eventsTag = null;
	private LocalDate start = null;
	private LocalDate end = null;
	
	private List<String> photoDescription = null;
	
	@FXML Button createAlbum;
	
	
	@FXML Label albumTitle;
	@FXML Label description1;
	@FXML Label description2;
	
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
	}
	
	public void searchCall(){
		photosList.clear();
		set(0);
		albumList.getChildren().clear();
		
		if (!ALBUMStag.getText().trim().equals("")) {
			albums = Arrays.asList(ALBUMStag.getText().split("\\s*,\\s*"));
		}
		if (!CAPTIONStag.getText().trim().equals("")) {
			captionsTag = Arrays.asList(CAPTIONStag.getText().split("\\s*,\\s*"));
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
		
		if ( (startDate.getValue() != null && endDate.getValue() == null) || (startDate.getValue() == null && endDate.getValue() != null)) {
			description1.setTextFill(Color.web("#ff0000"));
			description2.setTextFill(Color.web("#ff0000"));
			description1.setText("Please enter both start and end dates");
			description2.setText("or none for both");
			return;
			//put a error statement on label and say to input values or no values
		}
		description1.setTextFill(Color.web("#111010"));
		description2.setTextFill(Color.web("#111010"));
		description1.setText("Tags are separated by commas");
		description2.setText("Captions search does text matching, not tags");
		
		for (Album a: currentUser.getAlbums()) {
			for (Photo p: a.getPhotos()) {
				if (p == null) {continue;}
				if (albums == null || albumContainedInKeyWords(a.getName(), albums)) {
					albumFound = true;
				}
				System.out.println(captionsTag);
				if (captionsTag == null ||  stringContainsItemFromList(p.getDescription(), captionsTag)) {
					captionsFound = true;
				}
				if (locationsTag == null || (p.getLocations() != null && !Collections.disjoint(replace(locationsTag), replace(p.getLocations())))) {
					locationsFound = true;
				}
				if (peoplesTag == null || (p.getPeople() != null && !Collections.disjoint(replace(peoplesTag), replace(p.getPeople())))) {
					peoplesFound = true;
				}
				if (eventsTag == null || (p.getEvents() != null && !Collections.disjoint(replace(eventsTag), replace(p.getEvents())))) {
					eventsFound = true;
				}
				if (startDate.getValue() == null || (convertDate(p).compareTo(startDate.getValue()) >= 0 && (convertDate(p).compareTo(endDate.getValue()) <= 0))) {
					dateFound = true;
				}
				
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
		start = null;
		end = null;
		
	}
	public static boolean albumContainedInKeyWords(String inputStr, List<String> keyWords) {
		for (String s: keyWords) {
	        if(inputStr.equals(s.trim()))
	        {
	            return true;
	        }
	    }
	    return false;
	}
	public static boolean stringContainsItemFromList(String inputStr, List<String> captionsTag2) {
	    if (inputStr == null) {
	    	return false;
	    }
		inputStr = inputStr.toLowerCase();
		for (String s: captionsTag2) {
	        if(inputStr.contains(s.toLowerCase().trim()))
	        {
	            return true;
	        }
	    }
	    return false;
	}
	public static List<String> replace(List<String> strings)
	{
		if (strings == null) {
			return null;
		}
		List<String> lower = new ArrayList<String>();
	    ListIterator<String> iterator = strings.listIterator();
	    while (iterator.hasNext())
	    {
	        lower.add(iterator.next().toLowerCase().trim());
	    }
	    return lower;
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
	
	public void createAlbum() throws IOException {
		currentUser.writeUser();
		newAlbumController.photos = photosList;
		System.out.println("added");
		SceneLoader.getInstance().changeScene("NewAlbum.fxml");  
	}
	
	private void setDate(Photo p) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
	    LocalDate localDate = LocalDate.parse(sdf.format(p.getLastModifiedLong()).substring(0, 10), formatter);
	    date.setValue(localDate);
	}
	private LocalDate convertDate(Photo p) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
	    LocalDate localDate = LocalDate.parse(sdf.format(p.getLastModifiedLong()).substring(0, 10), formatter);

	    return localDate;
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
