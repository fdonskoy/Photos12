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
	
	/**The account username*/
	private String password;
	
	/**@author Tim
	 * @param Username of the new account holder
	 * */
	public Account(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	/**@author Tim
	 * @return the account's username
	 * */
	public String getUsername(){
		return username;
	}
	
	/**@author Tim
	 * @param pass is the attempted password in login
	 * @return True if password is correct. False, otherwise. 
	 * */
	public boolean checkPassword(String pass){
		return password.equals(pass);
	}
	
	/**@author Tim
	 * @param pass is the attempted new password
	 * @return True if password is set correctly. False if password was already set before. You can only set password once from admin 
	 * */	
	public boolean trySetPassword(String pass){
		if(password == null){
			password = pass;
			return true;
		}
		
		return false;
	}
}
