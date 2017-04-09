package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Account implements Serializable {
	/**The directory to save the account holder object*/
	public static final String storeDir = "src/savedObjects/Users";

	/**The account username*/
	private String username;
	
	/**@author Tim
	 * @param Username of the new account holder
	 * */
	public Account(String username){
		this.username = username;
	}
	
	/**@author Tim
	 * @return the account's username
	 * */
	public String getUsername(){
		return username;
	}
}
