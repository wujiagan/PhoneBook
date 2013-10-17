package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import user.LinkMan;

public class XMLFileProxy {
	
	public static void writeXML(LinkMan linkMan,String fileName) {
		Document doc = readXML(fileName);
		Element listManList = null;
		if(doc == null){
			doc = DocumentHelper.createDocument();
			listManList = doc.addElement("listManList");
		}
		else
			listManList = doc.getRootElement();	
		Element listMan = listManList.addElement("listMan");
		Element name = listMan.addElement("name");
		Element mobilePhone = listMan.addElement("mobilePhone");
		Element address = listMan.addElement("address");
		Element email = listMan.addElement("email");
		Element ground = listMan.addElement("ground");
		
		name.setText(linkMan.getName());
		mobilePhone.setText(linkMan.getMobilePhone());
		address.setText(linkMan.getAddress());
		email.setText(linkMan.getEmail());
		ground.setText("" + linkMan.getGround());
		
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		
		XMLWriter writer;
		try {
			writer = new XMLWriter(new FileOutputStream(new File(
					fileName)),format);
			writer.write(doc);
			writer.close();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Document readXML(String fileName){
		File file = new File("fileName");
		if(!file.exists())
			return null;
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
		}
		
		//Element root = doc.getRootElement();
		/*Iterator iter = root.elementIterator();
		while(iter.hasNext()){
			Element linkMan = (Element)iter.next();
			System.out.println("姓名：" + linkMan.elementText("name"));
			System.out.println("邮箱：" + linkMan.elementText("email"));
		}*/
		return doc;
	}
	
	
}
