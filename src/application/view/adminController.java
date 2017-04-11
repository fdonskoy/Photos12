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

public class adminController {
	private static Admin admin;
	public static User currentUser;
	public ArrayList<String> usernames;
	
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

	public void initialize() throws IOException, ClassNotFoundException {
		Collections.sort(LoginController.usernames.subList(0, LoginController.usernames.size()));
		ObservableList<String> obsList = FXCollections.observableArrayList(LoginController.usernames);
		error.setVisible(false);
		users.setItems(obsList);
	}
	
	@FXML public void handleMouseClick(MouseEvent arg0) {
		deleteUserField.setText(users.getSelectionModel().getSelectedItem());
	}
	
	public void newUser(ActionEvent e) throws IOException {
		if (newUserField.getText().trim().length() <= 0) {
			error.setVisible(true);
			error.setText("Invalid username");
			return;
		}
		
		String s = newUserField.getText();
		String p = newUserPasswordField.getText();
		if (s.toLowerCase().equals("admin")) {
			System.out.println("Can't create a user with name 'admin'");
			return;
		}
		
		if (LoginController.usernames.contains(s)) {
			error.setVisible(true);
			error.setText("This user already exists");
			System.out.println("Duplicate username " + s);
			return;
		}
		
		try {
			User u = new User(s, p);
			u.writeUser();
			LoginController.usernames.add(s);
			newUserField.setText(null);
			newUserPasswordField.setText(null);
			initialize();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Adding user " + s);
	}
	
	public void deleteUser(ActionEvent e) throws IOException {
		if (deleteUserField.getLength() == 0) {
			return;
		}
		String s = deleteUserField.getText();
		if (!LoginController.usernames.contains(s)) {
			System.out.println("Username not found " + s);
			return;
		}
		
		System.out.println("Deleting user " + s);

		
		try {
			LoginController.usernames.remove(s);
			User.deleteUser(s);
			deleteUserField.setText(null);
			initialize();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	
	public void exit() throws IOException{
		FileDropDown_Util.exit();
	}
	
	public void logout() throws IOException{
		FileDropDown_Util.logout();
	}
	
	
}
