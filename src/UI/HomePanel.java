package UI;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomePanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HomePanel(URL theme) {
		ImageIcon image = new ImageIcon(theme);
		image.setImage(image.getImage().getScaledInstance(854, 505, Image.SCALE_DEFAULT));
		add(new JLabel(image));
	}
	
}
