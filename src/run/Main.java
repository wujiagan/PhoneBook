package run;

import javax.swing.UIManager;
import UI.MainUI;

public class Main {
	public static void main(String args[]){
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		new MainUI();
	}
}
