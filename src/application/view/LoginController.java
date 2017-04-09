package application.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import application.Account;
import application.Admin;
import application.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginController {
	/**@author Tim
	 * stores the single admin
	 * */
	private static Admin admin;
	/**@author Tim
	 * Once the login is successful, holds the logged in user
	 * */
	public static User currentUser;
	
	/**@author Tim
	 * holds all usernames
	 * */
	public ArrayList<String> usernames;
	
	@FXML Pane container;
	
	@FXML Button loginBtn;
	@FXML TextField loginInput;
	@FXML Label loginText;
	
	@FXML MenuItem exit;
	
	/**@author Tim
	 * gets the admin and reads all the usernames in from the user dat files
	 * */
	public void initialize() throws ClassNotFoundException, IOException {
		admin = Admin.readAdmin();
		usernames = new ArrayList<String>();
		getAllFiles(new File("src/application/savedObjects/Users"));
	}
	
	/**@author Tim
	 * Listener to the login button that either logs the user in or informs the username is wrong
	 * */
	public void login(ActionEvent e) throws IOException, ClassNotFoundException{
		String username = loginInput.getText();
		
		if(username.equals(admin.getUsername())){
			SceneLoader.getInstance().changeScene("Picture-Library-admin.fxml");
		}
		else if(usernames.contains(username)){
			currentUser = User.readUser(username);
			System.out.println("Current User: " + username);
			SceneLoader.getInstance().changeScene("Albums.fxml");
		}
		else{
			loginText.setTextFill(Color.web("#ff0000"));
			loginText.setText("Invalid username.\nPlease try again");
		}
	}
	
	/**@author Tim
	 * Gets all the file names as strings from the directory and stores it as usernames
	 * @param Directory to process*/
	private void getAllFiles(File curDir) {
        File[] filesList = curDir.listFiles();
        for(File f : filesList){
            if(f.isFile()){
            	usernames.add(f.getName().substring(0, f.getName().lastIndexOf(".")));
            }
        }
	}
	
	/**@author Tim
	 * Calls the utility dropdown exit function*/
	public void exit() throws IOException{
		FileDropDown_Util.exit();
	}
	

	/**@author Tim
	 * Saves current state, ie currentUser*/
	public static void save() throws IOException{
		if(currentUser != null)
			currentUser.writeUser();
	}
}
