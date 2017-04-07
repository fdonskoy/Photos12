package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Admin extends Account {
	private static final long serialVersionUID = 6693017721411817348L;
	public static final String storeFile = "Admin.dat"; 
	
	public Admin(String username) {
		super(username);
	}

	public static void writeAdmin(Admin admin) throws IOException {
		File curDir = new File(storeDir + ".");
        getAllFiles(curDir);
		ObjectOutputStream oos = new ObjectOutputStream(
									new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(admin);
	} 
	
	public static Admin readAdmin() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		Admin admin = (Admin)ois.readObject();
		return admin;
	} 
	
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
