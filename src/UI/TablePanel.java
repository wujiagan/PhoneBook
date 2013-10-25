package UI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import table.WDefaultTableModel;
import user.LinkMan;
import user.User;
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
	
	/**
	 * 文件树状图
	 */
	private TreePanel treePanel = null ;
	
	private Font font = new Font("宋体", 0, 18);
	
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
	 * 数据缓冲
	 */
	private Map<LinkMan, Integer> bufferMap = new HashMap<LinkMan, Integer>();
	
	/** 
	 * 保存当前table数据所对应的文件
	 */
	private String currenFilePath = "";
	/**
	 * 标志当前文件是否为加锁文件
	 */
	private boolean currentFileClock = false;
	
	private JPanel findPanel = null;
	
	/**
	 * 保存当前用户
	 */
	private User currentUser = null;
	
	public TablePanel(User currentUser){
		this.currentUser = currentUser;
		createToolBar();
		this.setLayout(null);
		profilePanel = new ProfilePanel(this);
		linkManAddPanel = new LinkManAddPanel(this);
		JPanel tablePanel = new JPanel(new BorderLayout());
		
		
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
			public void valueChanged(ListSelectionEvent e)
			{
				selectRow = table.getSelectedRow();
			}
		});
		
		table.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(2 == e.getClickCount()){
					if(!bufferMap.isEmpty()){
						Set<LinkMan> set = bufferMap.keySet();
						List<LinkMan> list = new LinkedList<LinkMan>(set);
						profilePanel.showByBufferMap(linkMans, bufferMap.get(list.get(selectRow)));
						bufferMap.clear();
					}
					else
						profilePanel.updateProfilePanel(linkMans, selectRow);
					cardLayout.show(tableView, "profile");
				}
			}
		});
		
		createFindPanel();
		tablePanel.add(findPanel, BorderLayout.NORTH);
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
	 * 显示分析面板
	 */
	public void showAnalyzePanel(){
		cardLayout.show(tableView, "analyzePanel");
	}
	
	
	/**
	 * 创建查找面板
	 */
	public void createFindPanel() {
		findPanel = new JPanel();
		final JTextField keyText = new JTextField(30);
		keyText.setFont(font);
		
		JButton btnSure = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/find.png")));
		btnSure.setBorderPainted(false);
		
		btnSure.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String key = keyText.getText();
				if(!key.equals("")){
					DataOperate.find(linkMans, key, bufferMap);
					if(bufferMap.size() == 0)
						JOptionPane.showMessageDialog(null, "没有相关记录", "提示", JOptionPane.PLAIN_MESSAGE);	
					else
						updateTable(bufferMap);
					closeFindPanel();
				}
				else
					JOptionPane.showMessageDialog(null, "关键字不能为空", "提示", JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		
		
		findPanel.setLayout(new FlowLayout());
		findPanel.add(keyText);
		findPanel.add(btnSure);
		findPanel.setVisible(false);
	}
	
	/**
	 * 显示查找面板
	 */
	public void showFindPanel() {
		findPanel.setVisible(true);
	}
	
	/**
	 * 隐藏查找面板
	 */
	public void closeFindPanel() {
		findPanel.setVisible(false);
	}
	
	
	/**
	 * 创建工具栏
	 */
	public void createToolBar() {
		tool = new JToolBar(JToolBar.VERTICAL);
		
		JButton btnAddLinkMan = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/add.png")));
		tool.add(btnAddLinkMan);
		btnAddLinkMan.setMnemonic(KeyEvent.VK_A);
		
		btnAddLinkMan.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(!currenFilePath.equals(""))
					showLinkManAddPanel();
				else
					JOptionPane.showMessageDialog(null, "你还没有打开任何文件", "提示", JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		JButton btnDelete = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/delete.png")));
		tool.add(btnDelete);
		btnDelete.setMnemonic(KeyEvent.VK_D);
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteTableData();
			}
			
		});
		
		JButton btnFind = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/find.png")));
		tool.add(btnFind);
		btnFind.setMnemonic(KeyEvent.VK_F);
		
		btnFind.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				showFindPanel();
			}
		});
		
		JButton btnSave = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/save_as.png")));
		btnSave.setMnemonic(KeyEvent.VK_S);
		tool.add(btnSave);
		
		btnSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				saveFile();
			}
		});
		
		
		JButton btnLock = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/lock.png")));
		tool.add(btnLock);
		btnLock.setMnemonic(KeyEvent.VK_L);
		
		btnLock.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				lockFile();
			}
		});
		
		
		JButton btnImport = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/import.png")));
		tool.add(btnImport);
		btnImport.setMnemonic(KeyEvent.VK_I);
		
		
		btnImport.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {	
				if(!currenFilePath.equals(""))
					importFile();
				else
					JOptionPane.showMessageDialog(null, "你还没有打开任何文件", "提示", JOptionPane.PLAIN_MESSAGE);
				
			}
		});
		
		JButton btnExport = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/export.png")));
		tool.add(btnExport);
		btnExport.setMnemonic(KeyEvent.VK_E);
		
		btnExport.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(!currenFilePath.equals(""))
					exportExcel();
				else
					JOptionPane.showMessageDialog(null, "你还没有打开任何文件", "提示", JOptionPane.PLAIN_MESSAGE);
				
				
			}
		});
		
		
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
	public void showLinkManAddPanel() {		
		cardLayout.show(tableView, "linkManAddPanel");
	}
	
	/**
	 * 添加新联系人到指定文件下
	 */
	public void addLinkMan(String dirName, String fileName) {
		
		openXMLFile(dirName, fileName);	//更新后台数据
		showLinkManAddPanel();
	}
	
	/** 
	 * 保存table数据
	 */
	public void saveFile() {
		XMLFileProxy.saveToFile(linkMans, currenFilePath, currentFileClock, currentUser.getPassword());
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
	 * 删除表中数据
	 */
	public void deleteTableData() {
		int len = tableModel.getRowCount();
		int num = 0;
		for(int i=len-1; i>=0; i--){
			if((Boolean)tableModel.getValueAt(i, 5) == true){
				num++;
				tableModel.removeRow(i);
				linkMans.remove(i);
			}
		}
		if(0 == num)
			JOptionPane.showMessageDialog(null, "没有选择任何联系人", "删除操作", JOptionPane.WARNING_MESSAGE);
		else
			JOptionPane.showMessageDialog(null, "删除" + num + "记录！", "删除操作", JOptionPane.PLAIN_MESSAGE);
			
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
				updateTable();
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
	public void updateTable() {
		clearTable();
		DataOperate.sort(linkMans);
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
	 *  根据list刷新表中数据 
	 */
	public void updateTable(Map<LinkMan, Integer> map) {
		clearTable();
		Set<LinkMan> list = map.keySet() ;
		Iterator<LinkMan> iter = list.iterator();
		while(iter.hasNext()){
			Vector<Object> newRow = new Vector<Object>();
			LinkMan linkMan = iter.next();
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
		firsetColumn.setPreferredWidth(50);
		firsetColumn.setMaxWidth(50);
		firsetColumn.setMinWidth(50);
		
		TableColumn SecondColumn = table.getColumnModel().getColumn(1);
		SecondColumn.setPreferredWidth(90);
		SecondColumn.setMaxWidth(90);
		SecondColumn.setMinWidth(90);
		
		TableColumn FiveColumn = table.getColumnModel().getColumn(4);
		FiveColumn.setPreferredWidth(90);
		FiveColumn.setMaxWidth(90);
		FiveColumn.setMinWidth(90);
		
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
		
		if(currenFilePath.equals(Configuration.getStorePath() + dirPath + File.separator + fileName)){
			updateTable();
			return ;
		}
		saveFile();
		currenFilePath = Configuration.getStorePath() + dirPath + File.separator + fileName;
		XMLFileProxy.load(linkMans, currenFilePath, currentFileClock);
		updateTable();
		showTable();
	}
	
	
	/**
	 * 添加新文件
	 * @return
	 */
	public boolean newFileDir() {
		return treePanel.addToRoot();
	}
	
	/**
	 * 插入到
	 * @param linkMan
	 */
	public void insert(LinkMan linkMan){
		linkMans.add(linkMan);
		updateTable();
	}
	
	/**
	 * 清空后台数据
	 */
	public void clearList() {
		linkMans.clear();
	}
	
	/**
	 * 设置选中行
	 * @param row
	 */
	public void setSelect(int row) {
		table.setRowSelectionInterval(row, row);
	}
	
	/**
	 * 返回后台数据
	 * @return
	 */
	public List<LinkMan> getLinkMans() {
		return linkMans;
	}
	
	/**
	 * 文件上锁
	 */
	public void lockFile() {
		if(currentUser.isLogin()){
			currentFileClock = true;
			XMLFileProxy.saveToFile(linkMans, currenFilePath, currentFileClock, currentUser.getPassword());
		}
		else
			JOptionPane.showMessageDialog(null, "你还没有登录", "提示", JOptionPane.WARNING_MESSAGE);
	}
	
	/**
	 * 
	 * @return
	 */
	public Map<String, Integer> analyzeByAddress() {
		Map<String, Integer> temp = new HashMap<String, Integer>();
		int[] result = new int[linkMans.size()];
		Iterator<LinkMan> iter = linkMans.iterator();
		while(iter.hasNext()){
			LinkMan e = iter.next();
			if(temp.containsKey(e.getAddress()))
				result[temp.get(e.getAddress())]++;
			else
			{
				int postion = temp.size();
				result[postion] = 0;
				temp.put(e.getName(), postion);
			}
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		Set<String> set = temp.keySet();
		Iterator<String> iterator = set.iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			map.put(key, result[temp.get(key)]);
		}
			
		return map;
	}
	
}
