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
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author Tim
 *
 */
public class Album implements Serializable {
	private static final long serialVersionUID = -5448129927370652090L;
	public static final String storeDir = "src/application/savedObjects/Albums";
	public String storeFile; 
	
	private ArrayList<Photo> photos;
	private String name;
	private int numPhotos;
	private Calendar firstPhotoDate;
	private Calendar lastPhotoDate;
	
	public Album(String name, String username){
		this.setName(name);
		this.setNumPhotos(0);
		this.setPhotos(new ArrayList<Photo>());
		
		storeFile = name + "_" + username + ".dat";
	}

	/**@author Tim
	 * @param photoAddress the address of the photo to be added to the album*/
	public void addPhoto(String photoAddress){
		Photo photo = new Photo(photoAddress);
		if(photo.getDate().compareTo(firstPhotoDate) < 0){
			lastPhotoDate = photo.getDate();
		} 
		else if(photo.getDate().compareTo(lastPhotoDate) > 0){
			lastPhotoDate = photo.getDate();
		}
		
		photos.add(photo);
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

	public String getFirstPhotoDateString(){
		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
		return format1.format(firstPhotoDate.getTime());
	}
	
	
	public void setFirstPhotoDate(Calendar firstPhotoDate) {
		this.firstPhotoDate = firstPhotoDate;
	}
	
	public Calendar getLastPhotoDate() {
		return lastPhotoDate;
	}
	
	public String getLastPhotoDateString(){
		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
		return format1.format(lastPhotoDate.getTime());
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
	
	
	public void writeAlbum() throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(
									new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(this);
	} 
	
	/**@author Tim
	 * Reads in Album from file
	 * @param name the name of the Album to be read in
	 * @param username of the user who owns the album
	 * @return the album read from the file
	 * */
	public static Album readAlbum(String name, String username) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + name + "_" + username + ".dat"));
		Album album = (Album)ois.readObject();
		return album;
	}
	
	/**@author Tim
	 * Reads in Album from file
	 * @param fileName: the file name of the Album to be read in
	 * @return the album read from the file
	 * */
	public static Album readAlbum(String fileName) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + fileName));
		Album album = (Album)ois.readObject();
		return album;
	} 
}
