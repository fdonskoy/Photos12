package application.view;

import java.io.IOException;

public class FileDropDown_Util {
	public static void exit() throws IOException{
		System.out.println("exiting...");
		LoginController.save();
		
		System.exit(0);
	}
	
	public static void logout() throws IOException{
		System.out.println("logging out...");
		
		LoginController.save();
		LoginController.currentUser = null;
		
		SceneLoader.getInstance().changeScene("Picture-Library-login.fxml");
	}
}
