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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import user.User;
import util.LoginProxy;

public class RegisterPanel extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name, password;
	private JTextField textName;
	private JPasswordField textPassWordFirst, textPassWordSecond;
	private JLabel labPassWordFirst, labPassWordSecond;
	private User currenUser = null ;
	
	private boolean isRegister = true;	//标志当前对话时修改密码，还是用户注册
	
	public RegisterPanel(){
		this.setTitle("用户注册");
		ImageIcon img = new ImageIcon(
				getClass().getResource("/UI/image/register.jpg"));
		img.setImage(img.getImage().getScaledInstance(600, 500, Image.SCALE_DEFAULT));
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
		
		labPassWordFirst = new JLabel("密码: "); 
		cont.add(labPassWordFirst);
		labPassWordFirst.setFont(new java.awt.Font("宋体", 0, 20));
		labPassWordFirst.setBounds(30, 130, 100, 40);
		
		textPassWordFirst = new JPasswordField(20);
		cont.add(textPassWordFirst);
		textPassWordFirst.setBounds(120, 130, 300, 40);
		
		labPassWordSecond = new JLabel("密码确认: "); 
		cont.add(labPassWordSecond);
		labPassWordSecond.setFont(new java.awt.Font("宋体", 0, 20));
		labPassWordSecond.setBounds(30, 190, 100, 40);
		
		textPassWordSecond = new JPasswordField(20);
		cont.add(textPassWordSecond);
		textPassWordSecond.setBounds(120, 190, 300, 40);
		
		JButton btnSure = new JButton("确认");
		cont.add(btnSure);
		btnSure.setBounds(140, 250, 90, 40);
		
		JButton btnCancel = new JButton("取消");
		cont.add(btnCancel);
		btnCancel.setBounds(270, 250, 90, 40);
		
		btnSure.addActionListener(new ActionListener(){
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(textPassWordFirst.getText().equals(textPassWordSecond.getText()))
					if(isRegister)
						userRegister(textName.getText(), textPassWordFirst.getText());
					else
						changePassword(textName.getText(), textPassWordFirst.getText());
				else
					JOptionPane.showMessageDialog(null, "两次输入的密码不一样", "提醒", JOptionPane.ERROR_MESSAGE);
				
			}
		});
		
		btnCancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				closeRegisterDiglog();
			}	
		});
		
		((JPanel)cont).setOpaque(false);
		
		this.setSize(600,500);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screen.width-this.getSize().width)/2,
				(screen.height-this.getSize().height)/2);
		this.setResizable(false);
		this.setVisible(false);
	}
	
	/** 用户注册 */
	public void userRegister(String name,String password){
		if(LoginProxy.userRegister(name, password)){
			JOptionPane.showMessageDialog(null, "注册成功", "提醒", JOptionPane.PLAIN_MESSAGE);
			initRegisterDiglog();
		}
		else
			JOptionPane.showMessageDialog(null, "注册失败", "提醒", JOptionPane.ERROR_MESSAGE);
	}
	
	public void changePassword(String name,String password){
		if(LoginProxy.changePassword(currenUser, password)){
			JOptionPane.showMessageDialog(null, "修改成功", "提醒", JOptionPane.PLAIN_MESSAGE);
			initRegisterDiglog();
		}
		else
			JOptionPane.showMessageDialog(null, "修改失败", "提醒", JOptionPane.ERROR_MESSAGE);
	}
	
	/** 显示登录对话框 */
	public void showRegisterDiglog(){
		this.setVisible(true);
	}
	
	/** 显示修改密码对话框 */
	public void showChangePasswordDiglog(User user){
		this.textName.setText(user.getName());
		currenUser = user;
		this.textPassWordFirst.setText("");
		this.textPassWordSecond.setText("");
		this.setVisible(true);
	}
	
	/** 关闭登录对话框 */
	public void closeRegisterDiglog(){
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
	
	/** 初始化对话框 */
	public void initRegisterDiglog(){
		this.textName.setText("");
		this.textPassWordFirst.setText("");
		this.textPassWordSecond.setText("");
		this.setVisible(false);
	}
}

