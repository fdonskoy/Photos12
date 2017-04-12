package application;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class Photo implements Serializable{
	private static final long serialVersionUID = -3739580358789280590L;

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
	
	/**@author Tim
	 * @param photoAddress
	 * */
	public Photo(String photoAddress){
		this.photoAddress = photoAddress;
	}
	
	/**@author Tim
	 * @return the caption of the photo
	 * */
	public String getDescription(){
		return description;
	}
	
	/**@author Tim
	 * @return date of lastModification
	 * */
	public Calendar getLastModified(){
		return lastModified;
	}
	
	/**@author Tim
	 * @return the address of the photo
	 * */
	public String getPhotoAddress(){
		return photoAddress;
	}
	
	/**@author Tim
	 * @return list of people in the photo
	 * */
	public List<String> getPeople(){
		return people;
	}
	
	/**@author Tim
	 * @return list of events the photo is associated with
	 * */
	public List<String> getEvents(){
		return events;
	}
	
	/**@author Tim
	 * @return list of locations associated with the photo
	 * */
	public List<String> getLocations(){
		return locations;
	}
	
	/**@author Fil
	 * @return other tags the user deems relevant
	 * */
	public List<String> getOther(){
		return other;
	}
	
	/**@author Fil
	 * @return the date of last modification as a long
	 * */
	public long getLastModifiedLong(){
		return lastModifiedLong;
	}
	
	/**@author Fil
	 * @return local date of photo file
	 * */
	public LocalDate getLocalDate(){
		return date;
	}
	
	/**@author Fil
	 * @param dateIn the new date to be stored in dateIn
	 * */
	public void setLocalDate(LocalDate dateIn){
		this.date = dateIn;
	}
	
	/**@author Tim
	 * @param description the new caption
	 * */
	public void setDescription(String description){
		this.description = description;
	}
	
	/**@author Tim
	 * @param people the new list of people associated with the photo
	 * */
	public void setPeople(List<String> people){
		this.people = people;
	}
	
	/**@author Tim
	 * @param events the new list of events associated with the photo
	 * */
	public void setEvents(List<String> events){
		this.events = events;
	}
	
	/**@author Tim
	 * @param locations the new list of locations associated with the photo
	 * */
	public void setLocations(List<String> locations){
		this.locations = locations;
	}
	
	/**@author Fil
	 * @param other the new list of other tags associated with the photo
	 * */
	public void setOther(List<String> other){
		this.other = other;
	}
	
	/**@author Fil
	 * @param lm the new last modified as a long
	 * */
	public void setLastModifiedLong(Calendar lm){
		this.lastModified = lm;
	}
	
	/**@author Fil
	 * @param lm the new last modified as a long
	 * */
	public void setLastModifiedLong(long mod){
		this.lastModifiedLong = mod;
	}

}
