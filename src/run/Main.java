package run;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import util.Configuration;
import UI.MainUI;

public class Main {
	public static void main(String args[]){
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new MainUI();
	}
}
