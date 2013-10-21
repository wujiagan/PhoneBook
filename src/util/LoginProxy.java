package util;

import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JOptionPane;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import user.User;

/**
 * 
 * @author 吴偲畅
 *
 */
public class LoginProxy {
	/**
	 * 定义加密器
	 */
	private EncryptionDecryption des = null;
	
	/**
	 * 定义密钥
	 */
	private String key = "hg47d8hg46f7g6r7g9fd5d47g8s";
	
	/** 
	 * 用户登录 
	 */
	public boolean userLogin(User current_user,String name, String password){
		User root = loadRootUser();
		try {
			des = new EncryptionDecryption(key);
			if(root != null && root.getName().equals(name) && 
					des.decrypt(root.getPassword()).equals(password)){
				current_user.setName(name);
				current_user.setPassword(password);
				current_user.setLogin(true);
			}
			else{
				JOptionPane.showMessageDialog(null,
						"登录失败(密码或用户名错误)", "提醒", JOptionPane.ERROR_MESSAGE);
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/** 用户注册 */
	public boolean userRegister(String name, String password){
		try{
			des = new EncryptionDecryption(key);
			updateRootToXML(new User(name, des.encrypt(password)));
		}catch(Exception e) {
		}
		return true;
	}
	
	/** 修改密码 */
	public boolean changePassword(User user,String newPassword){
		try{
			des = new EncryptionDecryption(key);
			updateRootToXML(new User(user.getName(), des.encrypt(newPassword)));
		}catch(Exception e) {
		}
		return true;
	}
	
	/**
	 * 是否设置根用户
	 * 判断文件是否存在，如果存在则已配置跟用户信息
	 * @return 是返回true
	 */
	public static boolean isSetRoot() {
		File file = new File(Configuration.getStorePath() + File.separator + "user_conf.xml");
		return file.exists();
	}
	
	/**
	 * 修改root,并保存到文件中
	 */
	private static void updateRootToXML(User user) {
		Document doc = DocumentHelper.createDocument();;
		Element rootUser = doc.addElement("rootUser");
		
		Element name = rootUser.addElement("name");
		Element password = rootUser.addElement("password");
				
		name.setText(user.getName());
		password.setText(user.getPassword());
		
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		
		XMLWriter writer;
		try {
			writer = new XMLWriter(new FileOutputStream(new File(
					Configuration.getStorePath() + File.separator + "user_conf.xml"))
					,format);
			writer.write(doc);
			writer.close();
		}catch (Exception e) {
		}
	}
	
	/**
	 * 从文件中加载root用户信息
	 * @return
	 */
	public User loadRootUser() {
		File file = new File(Configuration.getStorePath() + File.separator + "user_conf.xml");
		if(!file.exists()){
			JOptionPane.showMessageDialog(null,
					"请先设置root信息", "提醒", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		SAXReader reader = new SAXReader();
		Document doc = null;
		
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
		}
		
		Element rootUser = doc.getRootElement();
		
		return new User(rootUser.elementText("name"), rootUser.elementText("password"));
		
	}
}
