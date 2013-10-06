package UI;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import user.User;
import util.LoginProxy;

public class LoginPanel extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name, password;
	private JTextField textName;
	private JPasswordField textPassword;
	
	private User user = null;
	
	public LoginPanel(){
		this.setTitle("用户登录");
		ImageIcon img = new ImageIcon(
				getClass().getResource("/UI/image/login.jpg"));
		img.setImage(img.getImage().getScaledInstance(500, 350, Image.SCALE_DEFAULT));
		JLabel imgLabel = new JLabel(img);
		this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));
		imgLabel.setBounds(0,0,img.getIconWidth(), img.getIconHeight());
		Container cont = this.getContentPane();
		cont.setLayout(null);
		
		JLabel labName = new JLabel("用户名: "); 
		cont.add(labName);
		labName.setFont(new java.awt.Font("宋体", 0, 20));
		labName.setBounds(30, 60, 100, 40);
		
		textName = new JTextField(20);
		cont.add(textName);
		textName.setBounds(120, 60, 300, 40);
		
		JLabel labPassWord = new JLabel("密码: "); 
		cont.add(labPassWord);
		labPassWord.setFont(new java.awt.Font("宋体", 0, 20));
		labPassWord.setBounds(30, 130, 100, 40);
		
		textPassword = new JPasswordField(20);
		cont.add(textPassword);
		textPassword.setBounds(120, 130, 300, 40);
		
		JButton btnLogin = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/btnLogin.png")));
		cont.add(btnLogin);
		btnLogin.setBorderPainted(false);
		btnLogin.setContentAreaFilled(false);
		btnLogin.setBounds(120, 200, 120, 60);
		
		JButton btnRegister = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/btnRegister.png")));
		btnRegister.setBorderPainted(false);
		btnRegister.setContentAreaFilled(false);
		cont.add(btnRegister);
		btnRegister.setBounds(270, 200, 120, 60);
		
		btnLogin.addActionListener(new ActionListener(){

			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				user.setName(textName.getText());
				user.setPassword(textPassword.getText());
				LoginProxy.userLogin(user);
				closeLoginDiglog();
			}
			
		});
		
		btnRegister.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				closeLoginDiglog();
			}
			
		});
		
		((JPanel)cont).setOpaque(false);
		
		this.setSize(500,350);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screen.width-this.getSize().width)/2,
				(screen.height-this.getSize().height)/2);
		this.setResizable(false);
		this.setVisible(false);
	}
	
	public LoginPanel(User user){
		this();
		this.user = user;
	}
	
	/** 
	 * 显示登录对话框 
	 */
	public void showLoginDiglog(){
		this.setVisible(true);
	}
	
	/** 关闭登录对话框 */
	public void closeLoginDiglog(){
		this.setVisible(false);
	}
	
	/** 获取用户名 */
	public String getName(){
		return this.name;
	}
	
	/** 获取用户密码 */
	public String getPassword(){
		return this.password;
	}
}
