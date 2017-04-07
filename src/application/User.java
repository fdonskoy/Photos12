package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class User extends Account {
	private static final long serialVersionUID = 5641427077701344979L;
	public String storeFile; 
	
	public User(String username) {
		super(username);
		storeFile = username + ".dat";
	}

	public void writeUser() throws IOException {
		File f = new File(storeDir + File.separator + storeFile);
		if(!f.exists())
		    f.createNewFile();
		ObjectOutputStream oos = new ObjectOutputStream(
									new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(this);
	} 
	
	public static User readUser(String username) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + username + ".dat"));
		User user = (User)ois.readObject();
		return user;
	} 
}
