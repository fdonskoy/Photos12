package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**Admin is a special kind of account used for user administration. It can add and delete users
 * @author Tim
 * */
public class Admin extends Account {
	private static final long serialVersionUID = 6693017721411817348L;
	
	/**@author Tim
	 * file that stores the only admin
	 * */
	public static final String storeFile = "Admin.dat"; 
	
	/**@author Tim
	 * @param Username of the new account admin
	 * */
	public Admin(String username, String password) {
		super(username, password);
	}

	/**@author Tim
	 * @param admin: the admin that needs to be saved. There is only 1 so it could be done without the parameter
	 * */
	public static void writeAdmin(Admin admin) throws IOException {
		File curDir = new File(storeDir + ".");
        getAllFiles(curDir);
		ObjectOutputStream oos = new ObjectOutputStream(
									new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(admin);
	} 
	
	/**@author Tim
	 * Reads in Admin from file
	 * @throws IOException when user fails to write to dat file
	 * @throws ClassNotFoundException when reading the incorrect file
	 * */
	public static Admin readAdmin() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		Admin admin = (Admin)ois.readObject();
		return admin;
	} 
	
	/**@author Tim
	 * For debugging
	 * @param curDir: directory to print out all containing files.
	 * */
	private static void getAllFiles(File curDir) {
        File[] filesList = curDir.listFiles();
        for(File f : filesList){
            if(f.isDirectory())
                System.out.println(f.getName());
            if(f.isFile()){
                System.out.println(f.getName());
            }
        }
	}
}
