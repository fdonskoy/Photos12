package application.view;

import java.io.IOException;

public class FileDropDown_Util {
	public static void exit() throws IOException{
		LoginController.save();
		
		System.exit(0);
	}
}
