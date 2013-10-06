package UI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import table.WDefaultTableModel;
import user.LinkMan;
import util.DataOperate;
import util.FileProxy;


public class TablePanel extends JPanel {
	private DefaultTableModel tableModel;
	private JTable table;
	
	private CardLayout cardLayout = new CardLayout();
	private JPanel tableView = new JPanel(cardLayout);
	
	private Vector<Object> columnNames = new Vector<Object>();
	
	private JTextField factor;
	private JButton find;
	private boolean isFound = false ;
	
	private int[] results = null;
	private int select, selectRow;
	
	private ProfilePanel profilePanel = null;
	
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
	
	
	public TablePanel(){
		this.setLayout(new BorderLayout());
		profilePanel = new ProfilePanel(this);
		JPanel tablePanel = new JPanel(new BorderLayout());
		JPanel findPanel = new JPanel();
		Object[] columnDatas = { "群组", "姓名", "手机", "邮箱", "地址", "选择"};
		
		for(Object eachData: columnDatas){
			columnNames.add(eachData);
		}
		
		tableModel = new WDefaultTableModel(null,columnNames);
		table = new JTable(tableModel);
		table.setRowHeight(35);
		table.setShowGrid(false);
		table.setForeground(Color.blue);
		table.setSelectionBackground(Color.yellow);
		setColumnWith();
		
		factor = new JTextField(30);
		find = new JButton("查找",new ImageIcon(
				getClass().getResource("/UI/image/find.png")));
		findPanel.add(factor);
		findPanel.add(find);
		
		find.addActionListener(new ActionListener(){
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(!isFound){
					results = DataOperate.find(tableModel.getDataVector(), factor.getText()) ;
					if(results != null || results.length !=0){
						isFound = true;
						select = 0;
						table.setRowSelectionInterval(results[select], results[select]);
					}
					else
						JOptionPane.showMessageDialog(null, "无相关记录", "提醒", JOptionPane.WARNING_MESSAGE);
				}
				else{
					select++;
					if(select<results.length){
						find.setText("下一个");
						table.setRowSelectionInterval(results[select], results[select]);
					}
					else{
						isFound = false ;
						find.setText("查找");
						JOptionPane.showMessageDialog(null, "无相关记录", "提醒", JOptionPane.WARNING_MESSAGE);
					}
				}
				
					
				
			}
		});
		
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
		
		
		
		factor.getDocument().addDocumentListener(new DocumentListener(){

            public void insertUpdate(DocumentEvent e) {
                 find.setText("查找");
                 isFound = false ;
            }

            public void removeUpdate(DocumentEvent e) {
            	find.setText("查找");
            	isFound = false ;
            }

            public void changedUpdate(DocumentEvent e) {
            	find.setText("查找");
            	isFound = false ;
            }
        });
		
		
		tablePanel.add(findPanel,BorderLayout.NORTH);
		tablePanel.add(new JScrollPane(table),BorderLayout.CENTER);
		
		tableView.add(tablePanel,"tablePanel");
		tableView.add(profilePanel,"profile");
		
		this.add(tableView, BorderLayout.CENTER);
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("华南农业大学         ");
		DefaultMutableTreeNode major1 = new DefaultMutableTreeNode("软件学院");
		DefaultMutableTreeNode major2 = new DefaultMutableTreeNode("艺术学院");
		major1.add(new DefaultMutableTreeNode("软工R3班"));
		major1.add(new DefaultMutableTreeNode("软工R4班"));
		root.add(major1);
		root.add(major2);
		JTree jtree = new JTree(new DefaultTreeModel(root));
		JScrollPane jtreePanel = new JScrollPane(jtree);
		this.add(jtreePanel,BorderLayout.WEST);
	}
	
	/**
	 * 
	 * @return选中的行
	 */
	public int getSelectRow(){
		return selectRow;
	}

	
	/** 
	 * 保存table数据
	 */
	@SuppressWarnings("unchecked")
	public void saveFile() {
		FileProxy.saveFile(tableModel.getDataVector());
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
}