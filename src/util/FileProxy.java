package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.List;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import user.LinkMan;


public class FileProxy {
	
	/** 
	 * 文件读取 
	 */
	public static boolean getData(List<LinkMan> linkMans){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("选择文件");
		File selectFile;
		if(fileChooser.showOpenDialog(null) == 0){
			selectFile = fileChooser.getSelectedFile();
			try {
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(selectFile), "utf-8");
				BufferedReader reader = new BufferedReader(read);
				String buffer;
				
				while((buffer = reader.readLine()) != null){
					String[] info = buffer.split(" ");
					LinkMan newLinkMan = new LinkMan(info);
					linkMans.add(newLinkMan);
				}
				read.close();
			} catch (Exception e) {
			}
			return true;
		}
		return false;
	}
	
	/** 
	 * 将linkMans写到指定文件 
	 */
	public static void saveFile(Vector<Vector<Object>> linkMans) {
		JFileChooser fileChooser = new JFileChooser();
		Calendar cal = Calendar.getInstance();
		File file = new File( "通讯录" +  cal.get(Calendar.DATE) + "-" + cal.get(Calendar.HOUR) + ".dat");
		fileChooser.setSelectedFile(file);
		if(fileChooser.showSaveDialog(null) == 0){	
			try {
				file = fileChooser.getSelectedFile();
				if(!file.exists())
					file.createNewFile();
			    FileWriter writer = new FileWriter(file);
			    for(int i=0; i<linkMans.size(); i++){
			    	String content = "";
			    	for(int j = 0; j < 5; j++)
			    		content += linkMans.elementAt(i).elementAt(j) + " ";
			    	content += "\r\n";
			    	writer.write(content);
			    }
			    writer.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "写入文件错误", "提醒", JOptionPane.ERROR_MESSAGE);
			}	
		}
	}
	
	/** 
	 * 将linkMans写到指定文件 
	 * 
	 */
	public static void exportExcel(List<LinkMan> linkMans){
		JFileChooser fileChooser = new JFileChooser();
		File file = new File( "通讯录.xls");
		fileChooser.setSelectedFile(file);
		FileWriter writer = null;
		if(fileChooser.showSaveDialog(null) == 0){	
			try {
				file = fileChooser.getSelectedFile();
				if(!file.exists())
					file.createNewFile();
			    writer = new FileWriter(file);
			    for(int i=0; i<linkMans.size(); i++){
			    	for(Object eachItem: linkMans.get(i).toArray()){
			    		System.out.println((String)eachItem);
			    		writer.write((String)eachItem + '\t');
			    	}
			    	writer.write('\n');
			    }
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "写入文件错误", "提醒", JOptionPane.ERROR_MESSAGE);
			}finally{
			    try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
		}
	}
	
	public static void createDir(String path){
		File file = new File(path);
		createParentfile(file);
	}
	
	public static void createParentfile(File file){
		File parent = null ;
		if(!file.exists()){
			parent = file.getParentFile();
			if(parent != null)
				createParentfile(parent);
			file.mkdir();
		}
			
	}
}
