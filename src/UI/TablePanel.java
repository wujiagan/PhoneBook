package UI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import table.WDefaultTableModel;
import user.LinkMan;
import util.Configuration;
import util.DataOperate;
import util.FileProxy;
import util.XMLFileProxy;


public class TablePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DefaultTableModel tableModel;
	private JTable table;
	
	private CardLayout cardLayout = new CardLayout();
	private JPanel tableView = new JPanel(cardLayout);
	
	private Vector<Object> columnNames = new Vector<Object>();

	private int selectRow;
	
	private ProfilePanel profilePanel = null;
	private LinkManAddPanel linkManAddPanel = null;
	private TreePanel treePanel = null ;
	
	private Font font = new Font("宋体", 0, 15);
	
	/**
	 * 创建工具栏
	 */
	private JToolBar tool = null;
	
	private ImageIcon[] photo = {
			new ImageIcon(getClass().getResource("/UI/image/user.png")),
			new ImageIcon(getClass().getResource("/UI/image/family.png")),
			new ImageIcon(getClass().getResource("/UI/image/friend.png")),
			new ImageIcon(getClass().getResource("/UI/image/colleague.png")),
			new ImageIcon(getClass().getResource("/UI/image/stranger.png")),
	};
	
	/**
	 * 储存table后台数据
	 */
	private List<LinkMan> linkMans = new LinkedList<LinkMan>();
	
	/** 
	 * 保存当前table数据所对应的文件
	 */
	private String currenFilePath = null;
	
	
	public TablePanel(){
		createToolBar();
		this.setLayout(null);
		profilePanel = new ProfilePanel(this);
		linkManAddPanel = new LinkManAddPanel(this);
		JPanel tablePanel = new JPanel(new BorderLayout());
		//JPanel findPanel = new JPanel();
		Object[] columnDatas = { "群组", "姓名", "手机", "邮箱", "地址", "选择"};
		
		for(Object eachData: columnDatas){
			columnNames.add(eachData);
		}
		
		tableModel = new WDefaultTableModel(null,columnNames);
		table = new JTable(tableModel);
		table.setRowHeight(35);
		table.setFont(font);
		table.setShowGrid(false);
		table.setForeground(Color.blue);
		table.setSelectionBackground(Color.yellow);
		setColumnWith();
		
		
		
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e)
			{
				selectRow = table.getSelectedRow();
			}
		});
		
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(2 == e.getClickCount()){
					profilePanel.updateProfilePanel(linkMans, selectRow);
					cardLayout.show(tableView, "profile");
				}
			}
		});
		
		
		tablePanel.add(tool, BorderLayout.EAST);
		tablePanel.add(new JScrollPane(table),BorderLayout.CENTER);
		
		tableView.add(tablePanel,"tablePanel");
		tableView.add(profilePanel,"profile");
		tableView.add(linkManAddPanel,"linkManAddPanel");
		
		tableView.setBounds(200, 0,654, 505);
		this.add(tableView);
		
		treePanel = new TreePanel(this);
		treePanel.setBounds(0, 0, 200, 505);
		this.add(treePanel);
	}
	
	/**
	 * 创建工具栏
	 */
	public void createToolBar() {
		tool = new JToolBar(JToolBar.VERTICAL);
		
		JButton btnAddLinkMan = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/add.png")));
		tool.add(btnAddLinkMan);
		
		JButton btnDelete = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/delete.png")));
		tool.add(btnDelete);
		
		JButton btnFind = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/find.png")));
		tool.add(btnFind);
		
		tool.setBorderPainted(true);
		
	}
	/**
	 * 
	 * @return选中的行
	 */
	public int getSelectRow(){
		return selectRow;
	}
	
	/**
	 * 显示添加新联系人对话框
	 */
	public void showLinkManAddPanel(String dirName, String fileName) {
		cardLayout.show(tableView, "linkManAddPanel");
		linkManAddPanel.setOperateFile(dirName, fileName);
	}
	
	/** 
	 * 保存table数据
	 */
	public void saveFile() {
		XMLFileProxy.saveToFile(linkMans, currenFilePath);
	}
	
	/** 
	 * 清空table数据
	 */
	public void clearTable() {
		while(tableModel.getRowCount()>0)
		      tableModel.removeRow(tableModel.getRowCount()-1);
	}
	
	/**
	 * 显示table表
	 */
	public void showTable(){
		cardLayout.show(tableView, "tablePanel");
	}
	
	/**
	 * 将table的数据排序
	 */
	@SuppressWarnings("unchecked")
	public void sort(){
		tableModel.setDataVector(DataOperate.sort(tableModel.getDataVector(),1), columnNames);
	}
	
	/**
	 * 删除表中数据
	 */
	public void deleteTableData() {
		int len = tableModel.getRowCount();
		for(int i=len-1; i>=0; i--){
			if((Boolean)tableModel.getValueAt(i, 5) == true){
				tableModel.removeRow(i);
				linkMans.remove(i);
			}
		}
	}
	
	/**
	 * 以table中的数据生成Excel文件
	 */
	public void exportExcel(){
		FileProxy.exportExcel(linkMans);
	}
	
	/**
	 *  从文件导入数据
	 */
	public boolean importFile() {
		if(FileProxy.getData(linkMans)){
			try{
				updateTable(linkMans);
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "选择的文件不合法", "提醒", JOptionPane.WARNING_MESSAGE);
			}
			return true;
		}
		return false;
		
	}
	
	/**
	 *  根据linkMans刷新表中数据 
	 */
	public void updateTable(List<LinkMan> linkMans) {
		clearTable();
		for(int i = 0; i < linkMans.size(); i++){
			Vector<Object> newRow = new Vector<Object>();
			LinkMan linkMan = linkMans.get(i);
			newRow.add(photo[linkMan.getGround()]);
			newRow.add(linkMan.getName());
			newRow.add(linkMan.getMobilePhone());
			newRow.add(linkMan.getEmail());
			newRow.add(linkMan.getAddress());
			newRow.add(false);
			tableModel.addRow(newRow);
		}
		
	}
	
	/**
	 * 更新第select行的数据
	 * @param select
	 */
	public void updateTableRow(int select){
		LinkMan linkMan = linkMans.get(select);
		tableModel.setValueAt(photo[linkMan.getGround()], select, 0);
		tableModel.setValueAt(linkMan.getName(), select, 1);
		tableModel.setValueAt(linkMan.getMobilePhone(), select, 2);
		tableModel.setValueAt(linkMan.getEmail(), select, 3);
		tableModel.setValueAt(linkMan.getAddress(), select, 4);
	}
	
	/**
	 * 设置列宽
	 */
	public void setColumnWith() {
		TableColumn firsetColumn = table.getColumnModel().getColumn(0);
		firsetColumn.setPreferredWidth(60);
		firsetColumn.setMaxWidth(60);
		firsetColumn.setMinWidth(60);
		
		TableColumn lastColumn = table.getColumnModel().getColumn(5);
		lastColumn.setPreferredWidth(40);
		lastColumn.setMaxWidth(40);
		lastColumn.setMinWidth(40);
	}
	
	/**
	 * 打开路径为fileName的XML文件
	 * @param dirPath
	 * @param fileName
	 */
	public void openXMLFile(String dirPath, String fileName) {
		currenFilePath = Configuration.getStorePath() + dirPath + File.separator + fileName;
		XMLFileProxy.load(linkMans, currenFilePath);
		updateTable(linkMans);
		showTable();
	}
	
	public boolean newFileDir() {
		return treePanel.addToRoot();
	}
}
