package UI;


import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.Configuration;
import util.FileProxy;


public class PathInitPanel extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static File[] roots = File.listRoots();
	private JTextField pathText ;
	private static String path = roots[0] + "phoneBook";
	private Font font = new Font("宋体", 0, 20);
	
	public PathInitPanel(JFrame parent){
		
		this.setTitle("通讯录管理系统");
		ImageIcon img = new ImageIcon(
				getClass().getResource("/UI/image/storePath.jpg"));
		img.setImage(img.getImage().getScaledInstance(500, 350, Image.SCALE_DEFAULT));
		JLabel imgLabel = new JLabel(img);
		this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));
		imgLabel.setBounds(0,0,img.getIconWidth(), img.getIconHeight());
		Container cont = this.getContentPane();
		cont.setLayout(null);
		
		JLabel labPath = new JLabel("请选择文件储存路径:"); 
		this.add(labPath);
		labPath.setFont(font);
		labPath.setBounds(30, 120, 300, 40);
				
		pathText = new JTextField(30);
		cont.add(pathText);
		pathText.setText(path);
		pathText.setFont(font);
		pathText.setBounds(30, 180, 350, 40);
		
		JButton btnChoose = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/category.png")));
		cont.add(btnChoose);
		btnChoose.setBorderPainted(false);
		btnChoose.setContentAreaFilled(false);
		btnChoose.setBounds(360, 180, 120, 30);
		
		btnChoose.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				selectStorePlace();
			}
			
		});
		
		JButton btnSure = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/submit.png")));
		cont.add(btnSure);
		btnSure.setBorderPainted(false);
		btnSure.setContentAreaFilled(false);
		btnSure.setBounds(30, 250, 160, 60);
		
		btnSure.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				path = pathText.getText();
				FileProxy.createDir(path);
				Configuration.createConfigFile(path);
				closeDialog();
			}
		});
		
		JButton btnCancel = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/cancel.png")));
		btnCancel.setBorderPainted(false);
		btnCancel.setContentAreaFilled(false);
		cont.add(btnCancel);
		btnCancel.setBounds(230, 250, 160, 60);
		
		btnCancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				closeDialog();
			}
		});
		
		((JPanel)cont).setOpaque(false);
		
		this.setUndecorated(true); 
		this.setSize(500,350);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screen.width-this.getSize().width)/2,
				(screen.height-this.getSize().height)/2);
		this.setResizable(false);
		this.setVisible(false);
	}
	
	public void selectStorePlace(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if(fileChooser.showSaveDialog(null) == 0){
			File storeFile = fileChooser.getSelectedFile();
			pathText.setText(storeFile.getAbsolutePath());
		}
	}
	
	/**
	 *  关闭对话框
	 */
	public void closeDialog() {
		// TODO Auto-generated method stub
		this.setVisible(false);
	}
	
	/**
	 *  打开对话框
	 */
	public void showDialog(){
		if(Configuration.isConfig())
			pathText.setText(Configuration.getStorePath());
		this.setVisible(true);
	}

}

