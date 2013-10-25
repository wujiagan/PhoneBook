package UI;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import user.User;
import util.LoginProxy;

public class RegisterPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name, password;
	private JTextField textName;
	private JPasswordField textPassWordFirst, textPassWordSecond;
	private JLabel labPassWordFirst, labPassWordSecond;
	
	/**
	 * 当前用户
	 */
	private User currenUser = null ;
	
	private boolean isRegister = true;	//标志当前对话时修改密码，还是用户注册
	
	private Font font = new java.awt.Font("宋体", 0, 20);
	
	private MainUI parent = null;
	
	public RegisterPanel(MainUI parent){
		
		this.parent = parent;
		
		this.setLayout(null);
		
		
		JLabel labName = new JLabel("用户名: "); 
		this.add(labName);
		labName.setFont(font);
		labName.setBounds(40, 160, 100, 40);
		
		textName = new JTextField(20);
		this.add(textName);
		textName.setFont(font);
		textName.setBounds(140, 160, 300, 40);
		
		labPassWordFirst = new JLabel("密码: "); 
		this.add(labPassWordFirst);
		labPassWordFirst.setFont(font);
		labPassWordFirst.setBounds(40, 230, 100, 40);
		
		textPassWordFirst = new JPasswordField(20);
		this.add(textPassWordFirst);
		textPassWordFirst.setBounds(140, 230, 300, 40);
		
		labPassWordSecond = new JLabel("密码确认: "); 
		this.add(labPassWordSecond);
		labPassWordSecond.setFont(new java.awt.Font("宋体", 0, 20));
		labPassWordSecond.setBounds(40, 300, 100, 40);
		
		textPassWordSecond = new JPasswordField(20);
		this.add(textPassWordSecond);
		textPassWordSecond.setBounds(140, 300, 300, 40);
		
		JButton btnSure = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/sure.png")));
		this.add(btnSure);
		btnSure.setBorderPainted(false);
		btnSure.setContentAreaFilled(false);
		btnSure.setBounds(100, 370, 150, 40);
		
		JButton btnCancel = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/goBack.png")));
		btnCancel.setBorderPainted(false);
		btnCancel.setContentAreaFilled(false);
		this.add(btnCancel);
		btnCancel.setBounds(270, 370, 150, 40);
		
		btnSure.addActionListener(new ActionListener(){
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if("".equals(textPassWordFirst.getText())){
					JOptionPane.showMessageDialog(null, "密码不能为空", "提醒", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(textPassWordFirst.getText().equals(textPassWordSecond.getText())){
					if(isRegister){
						userRegister(textName.getText(), textPassWordFirst.getText());
						closeResgisterFuction();
					}
					else
						changePassword(textName.getText(), textPassWordFirst.getText());
					goBack();
				}
				else
					JOptionPane.showMessageDialog(null, "两次输入的密码不一样", "提醒", JOptionPane.ERROR_MESSAGE);
				
			}
		});
		
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				goBack();
			}	
		});
		
	}
	
	/**
	 *用户注册完成，关闭注册功能
	 */
	public void closeResgisterFuction() {
		parent.setRigirter(false);
	}
	
	/**
	 * 返回首页
	 */
	public void goBack() {
		parent.showHome();
	}
	
	/** 用户注册 */
	public void userRegister(String name,String password){
		LoginProxy loginProxy = new LoginProxy();
		if(loginProxy.userRegister(name, password)){
			JOptionPane.showMessageDialog(null, "注册成功", "提醒", JOptionPane.PLAIN_MESSAGE);
			initRegisterDiglog();
		}
		else
			JOptionPane.showMessageDialog(null, "注册失败", "提醒", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * 修改密码
	 * @param name
	 * @param password
	 */
	public void changePassword(String name,String password){
		LoginProxy loginProxy = new LoginProxy();
		if(loginProxy.changePassword(currenUser, password)){
			JOptionPane.showMessageDialog(null, "修改成功", "提醒", JOptionPane.PLAIN_MESSAGE);
			initRegisterDiglog();
		}
		else
			JOptionPane.showMessageDialog(null, "修改失败", "提醒", JOptionPane.ERROR_MESSAGE);
	}
	
	
	/** 显示修改密码对话框 */
	public void showChangePasswordDiglog(User user){
		this.textName.setText(user.getName());
		this.textName.setEditable(false);
		currenUser = user;
		this.textPassWordFirst.setText("");
		this.textPassWordSecond.setText("");
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
	}
	
	/**
	 * 设置背景图
	 * @param g
	 */
	private void paintBackgroundImage(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		int width = getWidth();
		int height = getHeight();
		Image img = null;
		try {
			img = ImageIO.read(getClass().getResource("/UI/image/register.jpg"));
		} catch (Exception e) {
			
		}    
		if(img != null)
			g2.drawImage(img, 0, 0, width, height, this);
	}


	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		paintBackgroundImage(g);   
	}
}

