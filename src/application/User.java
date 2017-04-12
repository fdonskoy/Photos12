package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class User extends Account {
	private static final long serialVersionUID = 5641427077701344979L;
	/**the name of the file in which this object is to be saved*/
	public String storeFile; 
	
	/**list of all albums that belong to this user. This allows the albums to be saved when the user is saved*/
	private ArrayList<Album> albums;
	
	/**@author Tim
	 * @param username: the user's username used to log in. This is also what is used to get the name of the file used to save the user
	 * */
	public User(String username, String password) {
		super(username, password);
		storeFile = username + ".dat";
		albums = new ArrayList<Album>();
	}

	public ArrayList<Album> getAlbums(){
		return albums;
	}
	
	/**@author Tim
	 * @param name the name of the album to be returned
	 * @return null if album already exists, else album by name
	 * */
	public Album addAlbum(String name){
		for(Album a : albums){
			if(a.getName().equals(name)){
				return null;
			}
		}
		
		Album album = new Album(name, this.getUsername());
		albums.add(album);
		
		return album;
	}
	
	/**@author Tim
	 * @param album the album to be added to this user's collection
	 * @return null if album already exists, else album
	 * */
	public Album addAlbum(Album album){
		for(Album a : albums){
			if(a.getName().equals(album.getName())){
				return null;
			}
		}

		albums.add(album);
		
		return album; 
	}
	
	/**@author Tim
	 * @param album the album to be removed from this user's collection
	 * @return true if album removed, false if album not in user's collection
	 * */
	public boolean removeAlbum(Album album){
		return albums.remove(album);
	}
	
	/**@author Tim
	 * @param albumName the name of the album to be removed from this user's collection
	 * @return true if album removed, false if album not in user's collection
	 * */
	public boolean removeAlbum(String albumName){
		for(Album album : albums){
			if(album.getName().equals(albumName)){
				return albums.remove(album);
			}
		}
		
		return false;
	}
	
	/**@author Tim
	 * @throws IOException if fails to write because of factors like file currently in use
	 * */
	public void writeUser() throws IOException {
		File f = new File(storeDir + File.separator + storeFile);
		if(!f.exists())
		    f.createNewFile();
		ObjectOutputStream oos = new ObjectOutputStream(
									new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(this);
		oos.close();
	} 
	
	/**@author Tim
	 * @param username the username of the user to be read in from a .dat file
	 * @throws IOException if fails to write because of factors like file currently in use
	 * @throws ClassNotFoundException if trying to read a non-user file
	 * */
	public static User readUser(String username) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + username + ".dat"));
		User user = (User)ois.readObject();
		ois.close();
		return user;
	} 
	
	/**@author Tim
	 * @param username of the user to be deleted
	 * @throws IOException if fails to write because of factors like file currently in use
	 * */
	public static void deleteUser(String username) throws IOException {
		int c = 0;
		File init = new File("src/savedObjects/Users");
		File[] filesList = init.listFiles();
        for(File f : filesList){
        	System.out.println(f.getName());
            if(f.isFile() && (f.getName().substring(0, f.getName().lastIndexOf(".")).toLowerCase().equals(username))){
            	if (f.delete()) {
            		return;
            	}
            	System.out.println("Failed to delete");
            }
            c++;
            System.out.println(f.getName().substring(0, f.getName().lastIndexOf(".")).toLowerCase() + " and username " + username);
        }
        
	}
}
