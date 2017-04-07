package application.view;

import java.io.IOException;

import application.Account;
import application.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController {
	private static Admin admin;
	public static Account currentUser;
	
	@FXML Pane container;
	
	@FXML Button loginBtn;
	@FXML TextField loginInput;
	
	@FXML MenuItem exit;
	
	public void initialize() throws ClassNotFoundException, IOException {
		admin = Admin.readAdmin();
	}
	
	public void login(ActionEvent e) throws IOException{
		String username = loginInput.getText();
		
		if(username.equals(admin.getUsername())){
			SceneLoader.getInstance().changeScene("Picture-Library-admin.fxml");
		}
	}
}
