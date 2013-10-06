package util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XMLFileProxy {
	
	public static void writeXML() {
		Document doc = DocumentHelper.createDocument();
		Element addressList = doc.addElement("address");
		Element listMan = addressList.addElement("listMan");
		Element name = listMan.addElement("name");
		Element email = listMan.addElement("email");
		name.setText("吴家淦");
		email.setText("1847758670@qq.com");
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("utf-8");
		
		XMLWriter writer;
		try {
			writer = new XMLWriter(new FileOutputStream(new File(
					"D:" + File.separator + "test.xml")),format);
			writer.write(doc);
			writer.close();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void readXML(){
		File file = new File("D:" + File.separator + "test.xml");
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(file);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Element root = doc.getRootElement();
		Iterator iter = root.elementIterator();
		while(iter.hasNext()){
			Element linkMan = (Element)iter.next();
			System.out.println("姓名：" + linkMan.elementText("name"));
			System.out.println("邮箱：" + linkMan.elementText("email"));
		}
	}
	
	public static void main(String args[]){
		//XMLFileProxy.writeXML();
		XMLFileProxy.readXML();
	}
}
