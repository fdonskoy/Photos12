/**
 * 
 */
package application;

import java.awt.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author Tim
 *
 */
public class Album implements Serializable {
	private ArrayList<Photo> photos;
	private String name;
	private int numPhotos;
	private Calendar firstPhotoDate;
	private Calendar lastPhotoDate;
	
	public Album(String name){
		this.setName(name);
		this.setNumPhotos(0);
		this.setPhotos(new ArrayList<Photo>());
	}

	public String getFirstPhotoThumbnail(){
		return (photos.size() > 0) ? photos.get(0).getPhoto() : null;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumPhotos() {
		return numPhotos;
	}

	public void setNumPhotos(int numPhotos) {
		this.numPhotos = numPhotos;
	}

	public Calendar getFirstPhotoDate() {
		return firstPhotoDate;
	}

	public void setFirstPhotoDate(Calendar firstPhotoDate) {
		this.firstPhotoDate = firstPhotoDate;
	}

	public Calendar getLastPhotoDate() {
		return lastPhotoDate;
	}

	public void setLastPhotoDate(Calendar lastPhotoDate) {
		this.lastPhotoDate = lastPhotoDate;
	}

	public ArrayList<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(ArrayList<Photo> photos) {
		this.photos = photos;
	}
	
}
