package application.view;

import java.io.IOException;

import application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**Helper class to change the stage to a different scene.
 * @author Tim
 * */
public class SceneLoader {
	private static SceneLoader single;
	private Stage primaryStage;
	
	private SceneLoader(){};
	
	/**@author Tim
	 * @param newScene the name of the fxml file to be loaded into the stage
	 * @throws IOException if passed an incorrect file name*/
	public void changeScene(String newScene) throws IOException{
		FXMLLoader loader = new FXMLLoader(); 
		loader.setLocation(getClass().getResource("/application/view/" + newScene));

		Pane root = (Pane)loader.load();
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Photo Library");
		primaryStage.setResizable(false);  
		primaryStage.show();
	}
	
	/**@author Tim
	 * @returns an instance of Sceneloader. SceneLoader is a singleton*/
	public static SceneLoader getInstance(){
		if(single == null){
			single = new SceneLoader();
		}
		
		return single;
	}
	
	/**@author Tim
	 * @param stage the stage to change scenes on. Set when the application is launched
	 * */
	public void setStage(Stage stage){
		primaryStage = stage;
	}
}
