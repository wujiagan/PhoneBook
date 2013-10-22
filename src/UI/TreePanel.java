package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import util.Configuration;
import util.FileProxy;

/**
 * 显示文件的树形结构
 * @author Administrator
 *
 */
public final class TreePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 定义树状图
	 */
	private JTree jtree = null;
	
	private JPopupMenu popup = new JPopupMenu();
	
	private Font font = new Font("宋体", 0, 15);
	
	private TablePanel parentPanel = null;
	private TreePath selPath;
	
	/**
	 * 定义根节点，为方便操作所以定义为全局变量
	 */
	private DefaultMutableTreeNode root;
	
	/**
	 * 方便使用定义成全局变量
	 */
	private JMenuItem addLinkMan = null;
	
	public TreePanel(TablePanel parentPanel){
		this.parentPanel = parentPanel;
		root = new DefaultMutableTreeNode("root");
		loadTree();
		jtree = new JTree(new DefaultTreeModel(root));
		jtree.setRowHeight(40);
		jtree.setRootVisible(false);
		jtree.setShowsRootHandles(true);
		jtree.setFont(font);
		
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer)jtree.getCellRenderer();
		
		renderer.setLeafIcon(new ImageIcon(
				getClass().getResource("/UI/image/phoneBook.png")));
		
		renderer.setBackgroundSelectionColor(Color.yellow);
		
		this.setLayout(new GridLayout(1,1));
		this.add(new JScrollPane(jtree));
		
		createPopupMenu();

		MouseListener ml = new MouseAdapter() {
			@SuppressWarnings("static-access")
			public void mousePressed(MouseEvent e) {
				int selRow = jtree.getRowForLocation(e.getX(), e.getY());
				selPath = jtree.getPathForLocation(e.getX(), e.getY());
				if(selRow != -1) {
				    if(e.getClickCount() == 1) {
				        
				    }
				    else if(e.getClickCount() == 2) {

				    }
				    if(e.getButton() == e.BUTTON3) {
				    	TreePath path = jtree.getSelectionPath();
				    	if(path != null && ((DefaultMutableTreeNode)path.getLastPathComponent()).isLeaf())
				    		isLeafOperate();
				    	else
				    		isNotLeafOperate();
				    	popup.show(e.getComponent(),e.getX(),e.getY());
				    }
				}
			}
		};
		
		jtree.addMouseListener(ml);

	}
	
	/**
	 * 创建鼠标右击弹出菜单
	 */
	private void createPopupMenu() {
		popup = new JPopupMenu();
		
		JMenuItem openFile = new JMenuItem("打开");
		JMenu addMenu = new JMenu("添加");
		JMenuItem addFile = new JMenuItem("文件");
		addLinkMan = new JMenuItem("联系人");
		addMenu.add(addFile);
		addMenu.add(addLinkMan);
		JMenuItem renameMenu = new JMenuItem("重命名");
		JMenuItem deleteMenu = new JMenuItem("删除");
		JMenuItem properties = new JMenuItem("属性");
		
		popup.add(openFile);
		popup.add(addMenu);
		popup.add(renameMenu);
		popup.add(deleteMenu);
		popup.add(properties);
		
		openFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String[] file = getFile();
				openFile(file[0], file[1]);
			}
		});
		
		addFile.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				addTreeNode(e);
			}
			
		});
		
		addLinkMan.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String[] file = getFile();
				addLinkMans(file[0], file[1]);
			}
			
		});
		
		deleteMenu.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				deleteTreeNode(e);
			}
		});

	}
	
	/**
	 * 当前选择为叶子节点，某些按钮可用
	 */
	public void isLeafOperate() {
		addLinkMan.setEnabled(true);
	}
	
	/**
	 * 当前选择为叶子节点，某些按钮不可用
	 */
	public void isNotLeafOperate() {
		addLinkMan.setEnabled(false);
	}

	
	/**
	 * 返回长度为2的字符串数组，地各位叶子节点的所在的目录路径，第二个为叶子节点所代表的文件路径
	 */
	private String[] getFile(){
		Object[] path = selPath.getPath();
		String[] result = {getDir(), path[path.length - 1] + ".xml"};
		return result;
	}
	
	/**
	 * 返回当前节点对应的目录
	 * @return
	 */
	private String getDir() {
		StringBuffer filePath = new StringBuffer();
		Object[] path = selPath.getPath();
		for(int i = 1; i < path.length; i++)
			filePath.append(File.separator + path[i].toString());
		return filePath.toString();
	}
	
	/**
	 * 添加新节点
	 * @param e
	 */
	public void addTreeNode(ActionEvent e) {
		DefaultMutableTreeNode parent = (DefaultMutableTreeNode)
			jtree.getLastSelectedPathComponent();
		if(parent == null){
			JOptionPane.showMessageDialog(null, "no node is selected!");
			return;
		}
		
		String nodeName = JOptionPane.showInputDialog(null,
				"添加文件" ,
				"添加文件",JOptionPane.QUESTION_MESSAGE
				);
		
		if(nodeName == null || "".equals(nodeName))
			return ;
		
		FileProxy.createDir(Configuration.getStorePath() + getDir() + File.separator + nodeName);
		
		parent.add(new DefaultMutableTreeNode(nodeName));
		
		((DefaultTreeModel)(jtree.getModel())).reload();
	}
	
	/**
	 * 直接向根节点添加节点
	 * @return 创建成功返回true，失败返回false
	 */
	public boolean addToRoot() {
		String nodeName = JOptionPane.showInputDialog(null,
				"新建文件夹" ,
				"新建文件夹",JOptionPane.QUESTION_MESSAGE
				);
		
		if(nodeName == null || "".equals(nodeName)){
			JOptionPane.showMessageDialog(null, "新建文件夹失败", "提醒", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		
		FileProxy.createDir(Configuration.getStorePath() + File.separator + nodeName);
		
		root.add(new DefaultMutableTreeNode(nodeName));
		
		((DefaultTreeModel)(jtree.getModel())).reload();	
		return true;
	}
	
	/**
	 * 删除当前节点
	 * @param e
	 */
	public void deleteTreeNode(ActionEvent e) {
		
		TreePath path = jtree.getSelectionPath();
		
		System.out.println(path);
		
		if(path == null){
			JOptionPane.showMessageDialog(null, "你没有选择任何文件");
			return ;
		}
		
		DefaultMutableTreeNode node = (DefaultMutableTreeNode)
			(path.getLastPathComponent());
		
		Object[] temp = path.getPath();
		StringBuffer filePath = new StringBuffer();
		for(int i=1; i<temp.length; i++)
			filePath.append(File.separator + temp[i].toString());
		if(node.isLeaf()){
			filePath.append(".xml");
		}
		
		if(node.isRoot())
			JOptionPane.showMessageDialog(null, "非法操作，cannot remoe the root");
		else{
			/** 删除关联文件*/
			FileProxy.DeleteFolder(Configuration.getStorePath() + filePath.toString());
			node.removeFromParent();
		}
		((DefaultTreeModel)(jtree.getModel())).reload();
	}
	
	
	/**
	 * 添加新联系人
	 */
	public void addLinkMans(String dirPath, String fileName) {
		parentPanel.addLinkMan(dirPath, fileName);	
	}
	
	/**
	 * 打开该叶子节点所指定的文件
	 * @param dirPath
	 * @param fileName
	 */
	public void openFile(String dirPath, String fileName) {
		parentPanel.openXMLFile(dirPath, fileName);	
	}
	
	/**
	 * 根据文件夹显示树结构
	 */
	public void loadTree() {
		loadChildNode(root, new File(Configuration.getStorePath()));
	}
	
	/**
	 * 递归添加子节点
	 */
	public void loadChildNode(DefaultMutableTreeNode node, File file) {
		File[] childFiles = file.listFiles();
		for(int i=0; i<childFiles.length; i++){
			if(childFiles[i].isDirectory()){
				DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(childFiles[i].getName());
				node.add(childNode);
				loadChildNode(childNode, childFiles[i]);
			}
		}
	}
	
}
