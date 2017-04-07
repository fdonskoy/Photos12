package application;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Photo implements Serializable{
	private static final long serialVersionUID = -3739580358789280590L;
	public static final String storeDir = "src/application/savedObjects";
	public static final String storeFile = "Photos.dat"; 
	
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	private String description;
	private Calendar date;
	private Calendar lastModified;
	
	private String photoAddress;
	private List<String> people;
	private List<String> events;
	private List<String> locations;
	
	public static final String photoDir = "Photos"; 

	public Photo(String photoAddress){
		this.photoAddress = photoAddress;
	}
	
	public String getDescription(){
		return description;
	}
	
	public Calendar getDate(){
		return date;
	}
	
	public Calendar getLastModified(){
		return lastModified;
	}
	
	public String getPhoto(){
		return photoAddress;
	}
	
	public List<String> getPeople(){
		return people;
	}
	
	public List<String> getEvents(){
		return events;
	}
	
	public List<String> getLocations(){
		return locations;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public void setPeople(List<String> people){
		this.people = people;
	}
	
	public void setEvents(List<String> events){
		this.events = events;
	}
	
	public void setLocations(List<String> locations){
		this.locations = locations;
	}
	
	public void setDate(Calendar cal){
		this.date = cal;
	}
	
	public static void writePhoto(Photo photo) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(
									new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(photo);
	} 
	
	public static Photo readPhoto() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		Photo photo = (Photo)ois.readObject();
		return photo;
	} 
}
