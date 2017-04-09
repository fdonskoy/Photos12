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

public class adminController {
	private static Admin admin;
	public static User currentUser;
	public ArrayList<String> usernames;
	
	@FXML Button newUserButton;
	@FXML Button deleteUserButton;

	@FXML TextField newUserField;
	@FXML TextField deleteUserField;
	
	@FXML ListView<String> users;
	@FXML Label header;
	
	@FXML MenuItem exit;
	@FXML MenuItem logout;
	
	/*public void initialize() throws IOException {
		admin = Admin.readAdmin();
		usernames = new ArrayList<String>();
		getAllFiles(new File("src/application/savedObjects/Users"));
		
		//SceneLoader sl = SceneLoader.getInstance();
		//sl.setStage(primaryStage);
		//sl.changeScene("Picture-Library-admin.fxml");
	}*/
	
	/*public void login(ActionEvent e) throws IOException{
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
	}*/
	
	
	private void getAllFiles(File curDir) {
        File[] filesList = curDir.listFiles();
        for(File f : filesList){
            if(f.isFile()){
            	usernames.add(f.getName().substring(0, f.getName().lastIndexOf(".")));
            }
        }
	}
	
	public void newUser(ActionEvent e) throws IOException {

	}
	
	public void deleteUser(ActionEvent e) throws IOException {
		deleteUserDAT();
	}
	
	private void save() {
  
	}
	
	private void deleteUserDAT() {
   
	}
	
	
	public void exit() throws IOException{
		//save();
		FileDropDown_Util.exit();
	}
	
	public void logout() throws IOException{
		//save();
		FileDropDown_Util.logout();
	}
	
	
}
