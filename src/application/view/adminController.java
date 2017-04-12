package application.view;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import application.Account;
import application.Admin;
import application.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**The controller responsible for the admin stage, adding and deleting users
 * @author Tim
 * */
public class adminController {
	@FXML Button newUserButton; 
	@FXML Button deleteUserButton;

	@FXML Label error;
	@FXML TextField newUserField;
	@FXML PasswordField newUserPasswordField;
	@FXML TextField deleteUserField;
	
	@FXML ListView<String> users;
	@FXML Label header;
	
	@FXML MenuItem exit;
	@FXML MenuItem logout;
	
	/**@author Tim
	 * populates user list
	 * */
	public void initialize(){
		Collections.sort(LoginController.usernames.subList(0, LoginController.usernames.size()));
		ObservableList<String> obsList = FXCollections.observableArrayList(LoginController.usernames);
		error.setVisible(false);
		users.setItems(obsList);
	}
	

	/**@author Fil
	 * @param arg0 detects a click on the list of usernames and copies the user to the delete field
	 */
	@FXML public void handleMouseClick(MouseEvent arg0) {
		deleteUserField.setText(users.getSelectionModel().getSelectedItem());
	}
	
	/**@author Tim
	 * @throws IOException if user fails to write to file*/
	public void newUser() throws IOException {
		if (newUserField.getText().trim().length() <= 0 || newUserField.getText().contains("\\") || newUserField.getText().contains("/")) {
			error.setVisible(true);
			error.setText("Invalid username");
			return;
		}
		
		String s = newUserField.getText();
		String p = newUserPasswordField.getText();
		if (s.toLowerCase().equals("admin")) {
			error.setVisible(true);
			error.setText("Can't create a user with name 'admin'");
			System.out.println("Can't create a user with name 'admin'");
			return;
		}
		
		if (LoginController.usernames.contains(s)) {
			error.setVisible(true);
			error.setText("This user already exists");
			System.out.println("Duplicate username " + s);
			return;
		}
		
		User u = new User(s, p);
		u.writeUser();
		LoginController.usernames.add(s);
		newUserField.setText(null);
		newUserPasswordField.setText(null);
		initialize();
		System.out.println("Adding user " + s);
	}
	
	/**@author Tim
	 * @throws IOException if user fails to delete file, for example, if file actively currently in use*/
	public void deleteUser() throws IOException {
		if (deleteUserField.getLength() == 0) {
			return;
		}
		
		String s = deleteUserField.getText();
		if (!LoginController.usernames.contains(s)) {
			error.setVisible(true);
			error.setText("Username '"  + s + "' not found ");
			System.out.println("Username not found " + s);
			return;
		}
		
		System.out.println("Deleting user " + s);

		
		User.deleteUser(s);
		LoginController.usernames.remove(s);
		
		deleteUserField.setText(null);
		initialize();
	}
	
	/**@author Tim
	 * @throws IOException if user fails to write to file*/
	public void exit() throws IOException{
		FileDropDown_Util.exit();
	}
	
	/**@author Tim
	 * @throws IOException if user fails to write to file*/
	public void logout() throws IOException{
		FileDropDown_Util.logout();
	}
	
	
}
