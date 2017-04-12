package application;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class Photo implements Serializable{
	private static final long serialVersionUID = -3739580358789280590L;
	public static final String storeDir = "src/application/savedObjects";

	private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	private String description;
	private Calendar lastModified;
	private long lastModifiedLong;
	private LocalDate date;
	
	private String photoAddress;
	private List<String> people;
	private List<String> events;
	private List<String> locations;
	private List<String> other;
	
	/**directory to save photos to*/
	public static final String photoDir = "Photos"; 
	/**file  to save this photo to*/
	public String storeFile;
	
	public Photo(String photoAddress){
		this.photoAddress = photoAddress;
		this.storeFile = photoAddress.substring(photoAddress.lastIndexOf("/") + 1, photoAddress.lastIndexOf("."));
	}
	
	public String getDescription(){
		return description;
	}
	
	public Calendar getLastModified(){
		return lastModified;
	}
	
	public String getPhotoAddress(){
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
	
	public List<String> getOther(){
		return other;
	}
	
	public long getLastModifiedLong(){
		return lastModifiedLong;
	}
	
	public LocalDate getLocalDate(){
		return date;
	}
	
	public void setLocalDate(LocalDate dateIn){
		this.date = dateIn;
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
	
	public void setOther(List<String> other){
		this.other = other;
	}
	
	public void setLastModifiedLong(Calendar lm){
		this.lastModified = lm;
	}
	
	public void setLastModifiedLong(long mod){
		this.lastModifiedLong = mod;
	}
	
	/**@author Tim
	 * Saves this photo to file
	 * */
	public void writePhoto() throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(
									new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(this);
	} 
	
	/**@author Tim
	 * Reads in Photo from file
	 * */
	public static Photo readPhoto(String fileName) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + fileName));
		Photo photo = (Photo)ois.readObject();
		return photo;
	} 
}
