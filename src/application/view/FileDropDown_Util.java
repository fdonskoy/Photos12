package application.view;

import java.io.IOException;

/**Takes care of safe exiting via file mene, as well as redirecting to search or safely logging out.
 * @author Tim
 * */
public class FileDropDown_Util {
	/**@author Tim
	 * Executes the functionality of the exit option in the file dropdown
	 * @throws IOException when user fails to write to dat file
	 * */
	public static void exit() throws IOException{
		System.out.println("exiting...");
		LoginController.save();
		
		System.exit(0);
	}
	
	/**@author Tim
	 * Executes the functionality of the logout option in the file dropdown
	 * @throws IOException when user fails to write to dat file
	 * */
	public static void logout() throws IOException{
		System.out.println("logging out...");
		
		LoginController.save();
		LoginController.currentUser = null;
		
		SceneLoader.getInstance().changeScene("Picture-Library-login.fxml");
	}
	
	/**@author Tim
	 * redirects to search scene
	 * @throws IOException when user fails to write to dat file
	 * */
	public static void search() throws IOException{
		System.out.println("going to search");
		LoginController.save();
		SceneLoader.getInstance().changeScene("search.fxml");
	}
}
