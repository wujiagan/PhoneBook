package UI;


import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import user.LinkMan;
import util.Configuration;
import util.FileProxy;
import util.XMLFileProxy;

public final class LinkManAddPanel extends JPanel{
	
	private JTextField textName, textAddress, textPhone, textEmail;
	private JComboBox groundBox = null ;
	private JButton btnSave, btnBack;
	
	private Image img = null;
	
	private Object[] groundName = {"无", "家庭", "好友", "同事", "陌生人"};
	
	private Font font = new Font("宋体", 0, 20);
	
	private TablePanel parent = null;
	
	private String fileName ;
	
	
	public LinkManAddPanel(TablePanel parent){
		this.parent = parent;
		this.setLayout(null);
		
		JLabel labName = new JLabel("姓名: "); 
		this.add(labName);
		labName.setFont(font);
		labName.setBounds(30, 60, 100, 40);
		
		textName = new JTextField(20);
		this.add(textName);
		textName.setFont(font);
		textName.setBounds(150, 60, 300, 40);
		
		JLabel labAddress = new JLabel("地址: "); 
		this.add(labAddress);
		labAddress.setFont(font);
		labAddress.setBounds(30, 120, 100, 40);
		
		textAddress = new JTextField(20);
		this.add(textAddress);
		textAddress.setFont(font);
		textAddress.setBounds(150, 120, 300, 40);
		
		JLabel labPhone = new JLabel("电话: "); 
		this.add(labPhone);
		labPhone.setFont(font);
		labPhone.setBounds(30, 180, 100, 40);
		
		textPhone = new JTextField(20);
		this.add(textPhone);
		textPhone.setFont(font);
		textPhone.setBounds(150, 180, 300, 40);
		
		JLabel labEmail = new JLabel("邮箱: "); 
		this.add(labEmail);
		labEmail.setFont(font);
		labEmail.setBounds(30, 240, 100, 40);
		
		textEmail = new JTextField(20);
		this.add(textEmail);
		textEmail.setFont(font);
		textEmail.setBounds(150, 240, 300, 40);
		
		JLabel labGround = new JLabel("群组: "); 
		this.add(labGround);
		labGround.setFont(font);
		labGround.setBounds(30, 300, 100, 40);
		
		groundBox = new JComboBox(groundName);
		this.add(groundBox);
		groundBox.setFont(font);
		groundBox.setBounds(150, 300, 300, 40);
		

		
		btnSave = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/profile_save.png")));
		this.add(btnSave);
		btnSave.setBorderPainted(false);
		btnSave.setContentAreaFilled(false);
		btnSave.setBounds(130, 360, 100, 40);
		
		btnBack = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/profile_comeback.png")));
		this.add(btnBack);
		btnBack.setBorderPainted(false);
		btnBack.setContentAreaFilled(false);
		btnBack.setBounds(230, 360, 100, 40);
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(saveProfile(fileName))
					JOptionPane.showMessageDialog(null, "保存成功！", "提醒", JOptionPane.PLAIN_MESSAGE);
			}
		});
	
		this.setOpaque(false);
	}
	

	
	/**
	 * 保存当前修改
	 */
	public boolean saveProfile(String fileName){
		LinkMan linkMan = new LinkMan();
		linkMan.setName(this.textName.getText());
		linkMan.setMobilePhone(this.textPhone.getText());
		linkMan.setEmail(this.textEmail.getText());
		linkMan.setAddress(this.textAddress.getText());
		linkMan.setGround(this.groundBox.getSelectedIndex());
		XMLFileProxy.writeXML(linkMan, fileName);
		return true ;
	}

	
	/**
	 * 设置操作文件对象
	 * @param dirName
	 * @param fileName
	 */
	public void setOperateFile(String dirPath, String fileName){
		File file = new File(Configuration.getStorePath() + dirPath) ;
		if(!file.exists())
			FileProxy.createDir(Configuration.getStorePath() + dirPath);
		this.fileName = fileName ;
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
		try {
			img = ImageIO.read(new File(getClass().getResource("/UI/image/profile.jpg").toURI()));
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


