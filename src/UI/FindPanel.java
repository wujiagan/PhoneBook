package UI;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.DataOperate;


public class FindPanel extends JFrame{
	
	private JTextField keyText ;
	private Font font = new Font("宋体", 0, 20);
	private TablePanel parent = null;
	private List<Integer> result = null;
	private JButton btnPre, btnNext;
	private int index = 0;
	
	public FindPanel(TablePanel parent){
		
		this.setTitle("查找");
		ImageIcon img = new ImageIcon(
				getClass().getResource("/UI/image/profile.jpg"));
		img.setImage(img.getImage().getScaledInstance(500, 350, Image.SCALE_DEFAULT));
		JLabel imgLabel = new JLabel(img);
		this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));
		imgLabel.setBounds(0,0,img.getIconWidth(), img.getIconHeight());
		Container cont = this.getContentPane();
		cont.setLayout(null);
		
		JLabel wordPath = new JLabel("输入查找关键字:"); 
		this.add(wordPath);
		wordPath.setFont(font);
		wordPath.setBounds(30, 50, 300, 40);
				
		keyText = new JTextField(30);
		cont.add(keyText);
		keyText.setFont(font);
		keyText.setBounds(30, 100, 350, 40);
		
		JButton btnSure = new JButton("查找");
		cont.add(btnSure);
		btnSure.setFont(font);
		btnSure.setBorderPainted(false);
		btnSure.setContentAreaFilled(false);
		btnSure.setBounds(30, 160, 100, 50);
		
		btnSure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String key = keyText.getText();
				if(!key.equals(""))
					search(key);
				else
					JOptionPane.showMessageDialog(null, "关键字不能为空", "提示", JOptionPane.ERROR_MESSAGE);
					
			}
		});
		
		btnPre = new JButton("上一个");
		cont.add(btnPre);
		btnPre.setFont(font);
		btnPre.setBorderPainted(false);
		btnPre.setContentAreaFilled(false);
		btnPre.setBounds(130, 160, 100, 50);
		
		btnPre.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				index = (index + result.size() - 1)% result.size();
				setSelect(index);
			}
		});
		
		btnNext = new JButton("下一个");
		btnNext.setFont(font);
		btnNext.setBorderPainted(false);
		btnNext.setContentAreaFilled(false);
		cont.add(btnNext);
		btnNext.setBounds(230, 160, 100, 50);
		
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				setSelect(index);
				index = ++index % result.size();
			}
		});
		
		JButton btnCanel = new JButton("退出");
		btnCanel.setFont(font);
		btnCanel.setBorderPainted(false);
		btnCanel.setContentAreaFilled(false);
		cont.add(btnCanel);
		btnCanel.setBounds(330, 160, 100, 50);
		
		btnCanel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeDialog();
			}	
		});
		
		
		((JPanel)cont).setOpaque(false);
		
		//this.setUndecorated(true); 
		this.setSize(500,350);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screen.width-this.getSize().width)/2,
				(screen.height-this.getSize().height)/2);
		this.setResizable(false);
		this.setVisible(false);
	}
	
	/**
	 * 查找
	 * @param key
	 */
	public void search(String key) {
		result = DataOperate.find(parent.getLinkMans(), key);
		if(result.size() == 0)
			JOptionPane.showMessageDialog(null, "没有找到相关记录", "提示", JOptionPane.PLAIN_MESSAGE);
		setTraverse(true);
		setSelect(0);
	}
	
	/**
	 *  关闭对话框
	 */
	public void closeDialog() {
		
		this.setVisible(false);
	}
	
	public void setTraverse(boolean flag){
		btnNext.setEnabled(flag);
		btnPre.setEnabled(flag);
	}
	
	/**
	 *  打开对话框
	 */
	public void showDialog() {
		setTraverse(false);
		this.setVisible(true);
	}
	
	/**
	 * 设置选中行
	 * @param row
	 */
	public void setSelect(int row) {
		parent.setSelect(row);
	}

}