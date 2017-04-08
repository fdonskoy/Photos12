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
	private static Admin admin;
	public static User currentUser;
	private ArrayList<String> usernames;
	
	@FXML Pane container;
	
	@FXML Button loginBtn;
	@FXML TextField loginInput;
	@FXML Label loginText;
	
	@FXML MenuItem exit;
	
	public void initialize() throws ClassNotFoundException, IOException {
		admin = Admin.readAdmin();
		usernames = new ArrayList<String>();
		getAllFiles(new File("src/application/savedObjects/Users"));
	}
	
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
	
	
	private void getAllFiles(File curDir) {
        File[] filesList = curDir.listFiles();
        for(File f : filesList){
            if(f.isFile()){
            	usernames.add(f.getName().substring(0, f.getName().lastIndexOf(".")));
            }
        }
	}
	
	public void exit() throws IOException{
		System.out.println("exiting...");
		FileDropDown_Util.exit();
	}
	
	
	
	
	
	
	
	
	
	public static void save() throws IOException{
		if(currentUser != null)
			currentUser.writeUser();
	}
}
