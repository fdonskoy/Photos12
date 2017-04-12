/**
 * 
 */
package application;

import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author Tim
 *
 */
public class Album implements Serializable {
	private static final long serialVersionUID = -5448129927370652090L;

	private ArrayList<Photo> photos;
	private String name;
	private int numPhotos;
	private Calendar firstPhotoDate;
	private Calendar lastPhotoDate;
	private LocalDate firstDate;
	private LocalDate lastDate;
	
	/**@author Tim
	 * @param name name of the album
	 * @param username of owning user*/
	public Album(String name, String username){
		this.setName(name);
		this.setNumPhotos(0);
		this.setPhotos(new ArrayList<Photo>());
	}

	/**@author Tim
	 * @param photoAddress the address of the photo to be added to the album*/
	public void addPhoto(String photoAddress){
		Photo photo = new Photo(photoAddress);
		
		if (firstDate == null) {
			firstDate = photo.getLocalDate();
		}
		if (lastDate == null) {
			lastDate = photo.getLocalDate();
		}
		if(photo.getLocalDate() != null && photo.getLocalDate().compareTo(firstDate) > 0){
			lastDate = photo.getLocalDate();
		} 
		if(photo.getLocalDate() != null && photo.getLocalDate().compareTo(firstDate) < 0){
			firstDate = photo.getLocalDate();
		} 
		
		
		if(photo.getLastModified() != null && photo.getLastModified().compareTo(firstPhotoDate) < 0){
			lastPhotoDate = photo.getLastModified();
		} 
		else if(photo.getLastModified() != null && photo.getLastModified().compareTo(lastPhotoDate) > 0){
			lastPhotoDate = photo.getLastModified();
		}
		
		numPhotos++;
		photos.add(photo);
	}
	
	/**@author Tim
	 * @param photoAddress the address of the photo to be added to the album*/
	public void addPhoto(Photo photo){
		if (firstDate == null) {
			firstDate = photo.getLocalDate();
		}
		if (lastDate == null) {
			lastDate = photo.getLocalDate();
		}
		if(photo.getLocalDate() != null && photo.getLocalDate().compareTo(firstDate) > 0){
			lastDate = photo.getLocalDate();
		} 
		if(photo.getLocalDate() != null && photo.getLocalDate().compareTo(firstDate) < 0){
			firstDate = photo.getLocalDate();
		} 
		
		numPhotos++;
		photos.add(photo);
	}
	
	/**@author Tim
	 * @param photoAddress the address of the photo to be removed from the album
	 * @return the photo that was removed*/
	public Photo removePhoto(Photo photo){
		photos.remove(photo);
		numPhotos--;
		if (this.getNumPhotos() == 0) {
			firstDate = null;
			lastDate = null;
		}
		else if(firstDate != null && firstDate.equals(photo.getLocalDate())){
			LocalDate min = lastDate;
			for(Photo p : photos){
				if(p.getLocalDate().compareTo(min) <= 0){
					min = p.getLocalDate();
				}
			}
			firstDate = min;
		} else if(lastDate != null && lastDate.equals(photo.getLocalDate())){
			LocalDate max = firstDate;
			for(Photo p : photos){
				if(p.getLocalDate().compareTo(max) >= 0){
					max = p.getLocalDate();
				}
			}
			lastDate = max;
		}
		
		return photo;
	}
	
	/**@author Tim
	 * @return the address of the first photo in the album or null if the album is empty*/
	public String getFirstPhotoThumbnail(){
		return (photos.size() > 0) ? photos.get(0).getPhotoAddress() : null;
	}
	
	/**@author Tim
	 * @return the name of the album*/
	public String getName() {
		return name;
	}

	/**@author Tim
	 * @param name the new name of the album*/
	public void setName(String name) {
		this.name = name;
	}

	/**@author Tim
	 * @return the number of photos in the album*/
	public int getNumPhotos() {
		return photos.size();
	}

	/**@author Tim
	 * @param numPhotos the new number of photos in the album*/
	public void setNumPhotos(int numPhotos) {
		this.numPhotos = numPhotos;
	}

	/**@author Tim
	 * @return the earliest date of a photo in the album*/
	public Calendar getFirstPhotoDate() {
		return firstPhotoDate;
	}

	/**@author Tim
	 * @return the earliest date of a photo in the album*/
	public String getFirstPhotoDateString(){
		if(firstPhotoDate == null) return "";
		
		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
		return format1.format(firstPhotoDate.getTime());
	}
	
	/**@author Tim
	 * @return the earliest date of a photo in the album*/
	public String getFirstLocalDateString(){
		if(firstDate == null) return "";
		
		return firstDate + "";
	}
	
	/**@author Tim
	 * @return the latest date of a photo in the album*/
	public String getLastLocalDateString(){
		if(lastDate == null) return "";
		
		return lastDate + "";
	}
	
	/**@author Tim
	 * @param firstPhotoDate the new earliest date of a photo in the album*/
	public void setFirstPhotoDate(Calendar firstPhotoDate) {
		if (this.getNumPhotos() > 0) {
			this.firstDate = this.getPhotos().get(0).getLocalDate();
		}
		this.firstPhotoDate = firstPhotoDate;
	}
	

	/**@author Tim
	 * @return latest photo date in the album*/
	public Calendar getLastPhotoDate() {
		return lastPhotoDate;
	}

	/**@author Tim
	 * @return latest photo date in the album*/
	public String getLastPhotoDateString(){
		if(lastPhotoDate == null) return "";
		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
		return format1.format(lastPhotoDate.getTime());
	}
	

	/**@author Tim
	 * @param lastPhotoDate the new latest date of a photo in the album*/
	public void setLastPhotoDate(Calendar lastPhotoDate) {
		this.lastPhotoDate = lastPhotoDate;
	}

	/**@author Tim
	 * @return all the photos of this album*/
	public ArrayList<Photo> getPhotos() {
		return photos;
	}

	/**@author Tim
	 * @param photos all the new photos of this album to replace all the old*/
	public void setPhotos(ArrayList<Photo> photos) {
		this.photos = photos;
	}	
}
