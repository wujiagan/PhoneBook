package UI;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import user.LinkMan;

public class ProfilePanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textName, textAddress, textPhone, textEmail;
	private JComboBox groundBox = null ;
	private JButton btnNext, btnPre, btnAlter, btnBack;
	
	private Image img = null;
	
	private Object[] groundName = {"无", "家庭", "好友", "同事", "陌生人"};
	
	private Font font = new Font("宋体", 0, 20);
	
	private List<LinkMan> listMans = null;
	
	private int currenProfile ;
	
	private TablePanel parent = null;
	
	
	public ProfilePanel(TablePanel parent){
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
		
		btnPre = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/pre.png")));
		this.add(btnPre);
		btnPre.setBorderPainted(false);
		btnPre.setContentAreaFilled(false);
		btnPre.setBounds(10, 360, 150, 40);
		
		btnAlter = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/savebtn.png")));
		this.add(btnAlter);
		btnAlter.setBorderPainted(false);
		btnAlter.setContentAreaFilled(false);
		btnAlter.setBounds(160, 360, 150, 40);
		
		btnBack = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/goBack.png")));
		this.add(btnBack);
		btnBack.setBorderPainted(false);
		btnBack.setContentAreaFilled(false);
		btnBack.setBounds(310, 360, 150, 40);
		
		btnNext = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/next.png")));
		this.add(btnNext);
		btnNext.setBorderPainted(false);
		btnNext.setContentAreaFilled(false);
		btnNext.setBounds(460, 360, 150, 40);
		
		btnPre.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				preProfile();
			}
		});
		
		btnNext.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				nextProfile();
			}
		});
		
		btnAlter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(saveProfile())
					JOptionPane.showMessageDialog(null, "保存成功！", "提醒", JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		btnBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				goBack();
			}	
		});
	
		this.setOpaque(false);
	}
	
	/**
	 * 返回table界面
	 */
	public void goBack(){
		this.parent.showTable();
	}
	
	/**
	 * 保存当前修改
	 */
	public boolean saveProfile(){
		LinkMan linkMan = listMans.get(currenProfile);
		linkMan.setName(this.textName.getText());
		linkMan.setMobilePhone(this.textPhone.getText());
		linkMan.setEmail(this.textEmail.getText());
		linkMan.setAddress(this.textAddress.getText());
		linkMan.setGround(this.groundBox.getSelectedIndex());
		this.parent.updateTableRow(currenProfile);
		return true ;
	}
	
	/**
	 * 设置当前数据列表
	 * @param listMans
	 */
	public void setListMans(List<LinkMan> listMans){
		this.listMans = listMans;
	}
	
	/**
	 * 设置当前显示的数据位listMans中第select个数据
	 * @param select
	 */
	public void setCurrenProfile(int select){
		LinkMan linkMan = listMans.get(select);
		currenProfile = select;
		updateProfile(linkMan);	
	}
	
	/**
	 * 根据输入的linkMan更新当前显示的数据
	 * @param linkMan
	 */
	public void updateProfile(LinkMan linkMan){
		this.textName.setText(linkMan.getName());
		this.textPhone.setText(linkMan.getMobilePhone());
		this.textEmail.setText(linkMan.getEmail());
		this.textAddress.setText(linkMan.getAddress());
		this.groundBox.setSelectedIndex(linkMan.getGround());
	}
	
	/**
	 * 显示list中第index个数据
	 * @param list
	 * @param index
	 */
	public void showByBufferMap(List<LinkMan>list, int index){
		updateProfile(list.get(index));
	}
	
	
	/**
	 * 更新profilePanel中的数据列表，和当前选择
	 * @param listMans
	 * @param select
	 */
	public void updateProfilePanel(List<LinkMan> listMans, int select){
		setListMans(listMans);
		setCurrenProfile(select);
	}
	
	/**
	 * 显示上一条数据
	 */
	public void preProfile(){
		if(listMans == null)
			return ;
		int len = listMans.size();
		currenProfile = (currenProfile + len -1) % len;
		if(currenProfile>=0 && currenProfile<listMans.size())
			updateProfile(listMans.get(currenProfile));	
	}
	
	
	/**
	 * 显示下一条数据
	 */
	public void nextProfile(){
		if(listMans == null)
			return ;
		int index = (++currenProfile)%listMans.size();
		if(index>=0 && index<listMans.size())
			updateProfile(listMans.get(index));	
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
			img = ImageIO.read(getClass().getResource("/UI/image/profile.jpg"));
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


