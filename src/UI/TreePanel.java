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
public class TreePanel extends JPanel {
	
	private JTree jtree = null;
	
	private JPopupMenu popup = new JPopupMenu();
	
	private Font font = new Font("宋体", 0, 15);
	
	private TablePanel parentPanel = null;
	private TreePath selPath;
	
	public TreePanel(TablePanel parentPanel){
		this.parentPanel = parentPanel;
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		DefaultMutableTreeNode major1 = new DefaultMutableTreeNode("软件学院");
		DefaultMutableTreeNode major2 = new DefaultMutableTreeNode("艺术学院");
		major1.add(new DefaultMutableTreeNode("软工R3班"));
		root.add(major1);
		root.add(major2);
		jtree = new JTree(new DefaultTreeModel(root));
		jtree.setRowHeight(40);
		jtree.setRootVisible(false);
		jtree.setShowsRootHandles(true);
		jtree.setFont(font);
		
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer)jtree.getCellRenderer();
		renderer.setLeafIcon(new ImageIcon(getClass().getResource("/UI/image/phoneBook.png")));
		renderer.setBackgroundSelectionColor(Color.yellow);
		
		this.setLayout(new GridLayout(1,1));
		this.add(new JScrollPane(jtree));
		
		createPopupMenu();

		MouseListener ml = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int selRow = jtree.getRowForLocation(e.getX(), e.getY());
				selPath = jtree.getPathForLocation(e.getX(), e.getY());
				if(selRow != -1) {
				    if(e.getClickCount() == 1) {
				        mySingleClick(selRow, selPath);
				    }
				    else if(e.getClickCount() == 2) {
				        myDoubleClick(selRow, selPath);
				    }
				    if(e.getButton() == e.BUTTON3) {
				    	popup.show(e.getComponent(),e.getX(),e.getY());
				    }
				}
			}
		};
		
		jtree.addMouseListener(ml);

	}
	
	private void mySingleClick(int selRow, TreePath selPath) {
		System.out.println(selRow);
		System.out.println();
	}
	
	private void myDoubleClick(int selRow, TreePath selPath) {
		System.out.println(selRow);
		System.out.println(selPath);
	}
	
	/**
	 * 创建鼠标右击弹出菜单
	 */
	private void createPopupMenu() {
		popup = new JPopupMenu();
		
		JMenuItem openFile = new JMenuItem("打开");
		JMenu addMenu = new JMenu("添加");
		JMenuItem addFile = new JMenuItem("文件");
		JMenuItem addLinkMan = new JMenuItem("联系人");
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
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String[] file = getFile();
				addLinkMans(file[0], file[1]);
			}
			
		});

	}
	

	
	/**
	 * 返回长度为2的字符串数组，地各位叶子节点的所在的目录路径，第二个为叶子节点所代表的文件路径
	 */
	private String[] getFile(){
		StringBuffer filePath = new StringBuffer();
		Object[] path = selPath.getPath();
		for(int i = 1; i < path.length - 1; i++)
			filePath.append(File.separator + path[i].toString());
		String[] result = {new String(filePath), path[path.length - 1] + ".xml"};
		return result;
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
		if(nodeName == null || nodeName.length() == 0)
			return ;
		
		parent.add(new DefaultMutableTreeNode(nodeName));
		((DefaultTreeModel)(jtree.getModel())).reload();
	}
	
	/**
	 * 添加新联系人
	 */
	public void addLinkMans(String dirPath, String fileName) {
		parentPanel.showLinkManAddPanel(dirPath, fileName);	
	}
	
	/**
	 * 打开该叶子节点所指定的文件
	 * @param dirPath
	 * @param fileName
	 */
	public void openFile(String dirPath, String fileName) {
		parentPanel.openXMLFile(dirPath, fileName);	
	}

}
