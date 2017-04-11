package application.view;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import application.Account;
import application.Admin;
import application.Photo;
import application.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
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
	public static ArrayList<String> usernames;
	
	@FXML Pane container;
	
	@FXML Button loginBtn;
	@FXML TextField loginInput;
	@FXML PasswordField passwordInput;
	@FXML Label loginText;
	
	@FXML MenuItem exit;
	
	/**@author Tim
	 * gets the admin and reads all the usernames in from the user dat files
	 * */
	public void initialize() throws ClassNotFoundException, IOException {
		admin = Admin.readAdmin();
		usernames = new ArrayList<String>();
		getAllFiles(new File("src/savedObjects/Users"));
		
		
	}
	
	/**@author Tim
	 * Listener to the login button that either logs the user in or informs the username is wrong
	 * */
	public void login(ActionEvent e) throws IOException, ClassNotFoundException{
		String username = loginInput.getText();
		
		if(username.equals(admin.getUsername())){
			if(admin.checkPassword(passwordInput.getText()))
				SceneLoader.getInstance().changeScene("Picture-Library-admin.fxml");
			else
				wrongInput();
		}
		else if(usernames.contains(username)){
			currentUser = User.readUser(username);
			System.out.println("Current User: " + username);
			if(currentUser.checkPassword(passwordInput.getText())) {
				if (username.equals("stock") && currentUser.getAlbums().size() == 0){
					File[] filesList = new File("src/utility").listFiles();
			        currentUser.addAlbum("Colors");
			        for(File f : filesList){
			        	Photo p = new Photo("file:/" + f.getAbsolutePath());
			        	SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
					    LocalDate localDate = LocalDate.parse(sdf.format(f.lastModified()).substring(0, 10), formatter);
					    System.out.println("Stock localdate " + localDate);
					    p.setLocalDate(localDate);
					    currentUser.getAlbums().get(0).addPhoto(p);
			        }
				}
				
				SceneLoader.getInstance().changeScene("Albums.fxml");
			}	
			else{
				wrongInput();
			}		
		}
		else{
			wrongInput();
		}
	}
	
	/**@author Tim
	 * Gets all the file names as strings from the directory and stores it as usernames
	 * @param Directory to process*/
	private void getAllFiles(File curDir) {
        File[] filesList = curDir.listFiles();
        for(File f : filesList){
            if(f.isFile() && (!f.getName().substring(0, f.getName().lastIndexOf(".")).toLowerCase().equals("admin"))){
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
	
	private void wrongInput(){
		currentUser = null;
		loginText.setTextFill(Color.web("#ff0000"));
		loginText.setText("Invalid username or password.\nPlease try again");
	}
}
