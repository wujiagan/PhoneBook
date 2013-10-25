package util;

import java.io.File;
import java.io.FileOutputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Configuration {
	
	private static File configFile = new File("config.xml");
	
	/**
	 * 创建配置文件
	 * @param storePath
	 */
	public static void createConfigFile(String storePath) {
		Document doc = DocumentHelper.createDocument();
		Element configuration = doc.addElement("configuration");
		Element storePlace = configuration.addElement("storePath");
		storePlace.setText(storePath);
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		XMLWriter writer;
		try {
			writer = new XMLWriter(new FileOutputStream(configFile),format);
			writer.write(doc);
			writer.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 修改储存文件路径
	 */
	public static void changeConfigFile(String configPath) {
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(configFile);
		} catch (DocumentException e) {
		}
		Element root = doc.getRootElement();
		Element configuration = root.element("configuration");
		configuration.setText(configPath);
		
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		XMLWriter writer;
		try {
			writer = new XMLWriter(new FileOutputStream(configFile),format);
			writer.write(doc);
			writer.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 是否创建初始化文件
	 * @return true代表已创建
	 */
	public static boolean isConfig() {
		return configFile.exists();
	}
	
	/**
	 * 返回工作目录路径
	 * @return
	 */
	public static String getStorePath() {
		if(configFile.exists()){
			SAXReader reader = new SAXReader();
			Document doc = null;
			try {
				doc = reader.read(configFile);
			} catch (DocumentException e) {
			}
			Element root = doc.getRootElement();
			return root.elementText("storePath");	
		}
		return null;
	}
	
	/**
	 * 创建默认储存路径
	 */
	public static boolean createDefaultStorePath() {
		File[] rootFile = File.listRoots();
		String path = rootFile[0] + "phoneBook";
		createConfigFile(path);
		FileProxy.createDir(path);
		return true;
	}
	
}
