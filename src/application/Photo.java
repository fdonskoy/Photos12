package application;

import java.io.*;
import java.util.Date;
import java.util.List;

public class Photo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String description;
	private Date date;
	private Date lastModified;
	private String photoAddress;
	private List<String> people;
	private List<String> events;
	private List<String> locations;
	
	public static final String photoDir = "Photos"; 
	
	public static final String storeDir = "savedObjects";
	public static final String storeFile = "Photos.dat"; 
	
	
	public Photo(String photoAddress){
		this.photoAddress = photoAddress;
	}
	
	public String getDescription(){
		return description;
	}
	
	public Date getDate(){
		return date;
	}
	
	public Date getLastModified(){
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
	
	public void setDate(Date date){
		this.date = date;
	}
	
	public static void writeApp(Photo photo) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(
									new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(photo);
	} 
	
	public static Photo readApp() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		Photo photo = (Photo)ois.readObject();
		return photo;
	} 
}
