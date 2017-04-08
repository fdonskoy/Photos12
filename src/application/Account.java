package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public abstract class Account implements Serializable {
	public static final String storeDir = "src/savedObjects/Users";

	private String username;
	
	public Account(String username){
		this.username = username;
	}
	
	public String getUsername(){
		return username;
	}
}
