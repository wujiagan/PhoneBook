package util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import user.LinkMan;

public class XMLFileProxy {
	/**
	 * 将新的newlinkMan添加到对应的文件中
	 * @param newlinkMan
	 * @param fileName
	 */
	public static void writeXML(LinkMan newlinkMan,String fileName) {
		File file = new File(fileName);
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
		}
		Element linkManList = null;
		if(doc != null)		
			linkManList = doc.getRootElement();
		else
		{
			doc =  DocumentHelper.createDocument();
			linkManList = doc.addElement("linkManList");
		}
		
		Element linkMan = linkManList.addElement("linkMan");
		Element name = linkMan.addElement("name");
		Element mobilePhone = linkMan.addElement("mobilePhone");
		Element address = linkMan.addElement("address");
		Element email = linkMan.addElement("email");
		Element ground = linkMan.addElement("ground");
		
		name.setText(newlinkMan.getName());
		mobilePhone.setText(newlinkMan.getMobilePhone());
		address.setText(newlinkMan.getAddress());
		email.setText(newlinkMan.getEmail());
		ground.setText("" + newlinkMan.getGround());	
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		
		XMLWriter writer;
		try {
			writer = new XMLWriter(new FileOutputStream(new File(
					fileName)),format);
			writer.write(doc);
			writer.close();
		}catch (Exception e) {
		}
	}
	
	/**
	 * 读文件，并保存在链表linkMans中
	 * @param linkMans
	 * @param fileName
	 */
	@SuppressWarnings("unchecked")
	public static void load(List<LinkMan> linkMans, String fileName) {
		File file = new File(fileName);
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
		}
		
		Element linkManList = doc.getRootElement();
		
		Iterator<Element> iter = linkManList.elementIterator();
		
		linkMans.clear();
		while(iter.hasNext()){
			Element linkMan = iter.next();
			LinkMan newLinkMan = new LinkMan(linkMan.elementText("name"),
					linkMan.elementText("mobilePhone"), linkMan.elementText("address"),
					linkMan.elementText("email"), Integer.parseInt(linkMan.elementText("ground"))) ;
			linkMans.add(newLinkMan);
		}
	}
	
	/**
	 * 将linkMan写入文件中
	 * @param linkMans
	 * @param fileName
	 */
	public static void saveToFile(List<LinkMan> linkMans, String fileName) {
		Document doc = DocumentHelper.createDocument();;
		Element linkManList = doc.addElement("linkManList");
		
		Iterator<LinkMan> iterator = linkMans.iterator();
		
		while(iterator.hasNext()){
			Element linkMan = linkManList.addElement("linkMan");
			Element name = linkMan.addElement("name");
			Element mobilePhone = linkMan.addElement("mobilePhone");
			Element address = linkMan.addElement("address");
			Element email = linkMan.addElement("email");
			Element ground = linkMan.addElement("ground");
			
			LinkMan newlinkMan = iterator.next();
			name.setText(newlinkMan.getName());
			mobilePhone.setText(newlinkMan.getMobilePhone());
			address.setText(newlinkMan.getAddress());
			email.setText(newlinkMan.getEmail());
			ground.setText("" + newlinkMan.getGround());
		}
		
		
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		
		XMLWriter writer;
		try {
			writer = new XMLWriter(new FileOutputStream(new File(
					fileName)),format);
			writer.write(doc);
			writer.close();
		}catch (Exception e) {
		}
	}

}
