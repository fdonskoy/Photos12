package application;

import java.io.*;
import java.util.Date;
import java.util.List;

public class Photo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String description;
	private Date date;
	private String photoAddress;
	private List<String> people;
	
	public static final String storeDir = "savedObjects";
	public static final String storeFile = "Photos.dat"; 
	
	
	public Photo(String photoAddress){
		this.photoAddress = photoAddress;
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
