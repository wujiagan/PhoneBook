package UI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;


import user.User;
import util.Configuration;


public class MainUI extends JFrame implements Observer{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7489067892021806972L;
	private JMenuBar menu;
	private JMenu fileMenu, userMenu;

	private CardLayout cardLayout = new CardLayout();
	private JPanel cardPanel = new JPanel(cardLayout);
	
	private HomePanel[] homePanel = {new HomePanel(getClass().getResource("/UI/image/shitou.jpg")),
			new HomePanel(getClass().getResource("/UI/image/taijie.jpg")),
			new HomePanel(getClass().getResource("/UI/image/jitai.jpg"))
	};
	
	private User user = new User();
	private LoginPanel loginPanel = new LoginPanel(user);
	private RegisterPanel registerPanel = new RegisterPanel() ;
	
	/**
	 * 文件储存路径选择面板
	 */
	private PathInitPanel pathPanel = null ;
	
	/**
	 * 查找面板
	 */
	private FindPanel findPanel = null;
	
	private int numHome = 0;
	private String nowLocation = "home" ;
	
	private Stack<String> backStack = new Stack<String>();
	private Stack<String> aheadStack = new Stack<String>();
	
	private JMenuItem loginItem;
	
	private TablePanel tablePanel = new TablePanel();
	
	private boolean isOpen = false;
	

	
	/** 
	 * 创建用户界面
	 */
	public MainUI(){
		
		pathPanel = new PathInitPanel(this);
		findPanel = new FindPanel(this);
		
		user.addObserver(this);
		this.setLayout(new BorderLayout());
		add(cardPanel,BorderLayout.CENTER);
		createMenu(); 
		initTheme();
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(1);
			}
		});
		this.setSize(860,600);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screen.width-this.getSize().width)/2,
				(screen.height-this.getSize().height)/2);
		this.setResizable(false);
		this.setVisible(true);
		cardLayout.first(cardPanel);
		if(!Configuration.isConfig())
			pathPanel.showDialog();
			
	}
	
	/** 
	 * 初始化主题
	 */
	public void initTheme(){
		for(int i = 0; i < 3; i++)
			cardPanel.add(homePanel[i],"home" + i);
		cardPanel.add(tablePanel, "table");
		MouseListener mouseListener =  new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				enter(e);
			}
		};
		
		MouseWheelListener mouseWheellistener =  new MouseWheelListener(){
			public void mouseWheelMoved(MouseWheelEvent e) {
				nextTheme(e);
			}
		};
		
		for(int i = 0; i < 3; i++){
			homePanel[i].addMouseListener(mouseListener);
			homePanel[i].addMouseWheelListener(mouseWheellistener);
		}
	}
	
	/** 
	 * 双击用户进入table界面
	 * @param e
	 */
	public void enter(MouseEvent e){
		if(e.getClickCount() == 2)
			cardLayout.show(cardPanel, "table");
	}
	
	/** 
	 * 转动鼠标滑轮,显示不同界面、
	 * @param e
	 */
	public void nextTheme(MouseWheelEvent e){
		numHome = (numHome + e.getWheelRotation() + 3) % 3;
		cardLayout.show(cardPanel, "home"+numHome);
	}
	
	
	
	/**
	 *  打开文件
	 */
	public void openFile() {
		if(tablePanel.importFile()){
			showTable();
			isOpen = true;
		}
		
	}

	/** 
	 * 显示主题
	 */
	public void showHome() {
		numHome = (numHome+1)%3;
		cardLayout.show(cardPanel, "home"+numHome);
		if(nowLocation != "home"){
			backStack.push(nowLocation);
			nowLocation = "home";
		}
	}
	
	/** 
	 * 显示table数据
	 */
	public void showTable() {
		cardLayout.show(cardPanel, "table"); 
		if(nowLocation != "table"){
			backStack.push(nowLocation);
			nowLocation = "table";
		}
	}
		
	public void showBack() {
		if(!backStack.empty()){
			aheadStack.push(backStack.peek());
			cardLayout.show(cardPanel,backStack.pop());
		}
	}
	
	public void showAhead() {
		if(!aheadStack.empty()){
			backStack.push(aheadStack.peek());
			cardLayout.show(cardPanel,aheadStack.pop());
		}
	}
	
	/** 
	 * 创建菜单
	 */
	private void createMenu() {
		
		menu = new JMenuBar();
		fileMenu = new JMenu("菜单");
		userMenu = new JMenu("用户");
		
		JMenuItem saveItem = new JMenuItem("保存");
		JMenuItem openItem = new JMenuItem("导入");
		JMenuItem exportItem = new JMenuItem("导出excel");
		JMenuItem sortItem = new JMenuItem("排序");
		JMenuItem chooseItem = new JMenuItem("更改储存路径");
		
		loginItem = new JMenuItem("登陆");
		JMenuItem registerItem = new JMenuItem("注册");
		JMenuItem changePassWordItem = new JMenuItem("修改密码");
		JMenuItem logoutItem = new JMenuItem("安全退出");
		
		fileMenu.add(saveItem);
		fileMenu.add(openItem);
		fileMenu.add(exportItem);
		fileMenu.add(sortItem);
		fileMenu.add(chooseItem);
		
		userMenu.add(loginItem);
		userMenu.add(registerItem);
		userMenu.add(changePassWordItem);
		userMenu.add(logoutItem);
		
		loginItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				loginPanel.showLoginDiglog();
			}
		});
		
		sortItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(isOpen)
					tablePanel.sort();
			}
		});
		
		chooseItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pathPanel.showDialog();
			}
		});
		
		changePassWordItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(user.isLogin())
					registerPanel.showChangePasswordDiglog(user);
				else
					JOptionPane.showMessageDialog(null, "请先登录", "提醒", JOptionPane.ERROR_MESSAGE);
			}
			
		});
		
		registerItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				registerPanel.showRegisterDiglog();
			}
		});
		
		logoutItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				user.initUser();
				loginItem.setText("登录");
				loginItem.setEnabled(true);
			}
		});
		
		saveItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tablePanel.saveFile();
			}
		});
		
		openItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				openFile();
			}	
		});
		
		exportItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				tablePanel.exportExcel();
			}
				
		});
		
		menu.add(fileMenu);
		menu.add(userMenu);
		setJMenuBar(menu);
		
		JToolBar tool = new JToolBar(JToolBar.HORIZONTAL);
		
		JButton jbtBack = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/arrow_left.png")));
		setButton(jbtBack);
		
		JButton jbtAhead = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/arrow_right.png")));
		setButton(jbtAhead);
		
		JButton jbtHome = new JButton("首页", new ImageIcon(
				getClass().getResource("/UI/image/home.png")));
		setButton(jbtHome);
		
		JButton jbtFile = new JButton("新建", new ImageIcon(
				getClass().getResource("/UI/image/add.png")));
		setButton(jbtFile);
		
		JButton jbtSave = new JButton("保存", new ImageIcon(
				getClass().getResource("/UI/image/save_as.png")));
		setButton(jbtSave);
		
		JButton jbtAddress = new JButton("电话本",new ImageIcon(
				getClass().getResource("/UI/image/address.png")));
		setButton(jbtAddress);
		
		JButton jbtDelete = new JButton("删除",new ImageIcon(
				getClass().getResource("/UI/image/delete.png")));
		setButton(jbtDelete);
		
		JButton jbtGround = new JButton("用户",new ImageIcon(
				getClass().getResource("/UI/image/user.png")));
		setButton(jbtGround);
		
		JButton jbtSafe = new JButton("安全",new ImageIcon(
				getClass().getResource("/UI/image/safe.png")));
		setButton(jbtSafe);
		
		JButton jbtSetUp = new JButton("设置",new ImageIcon(
				getClass().getResource("/UI/image/setup.png")));
		setButton(jbtSetUp);
		
		JButton jbtSearch = new JButton("查找",new ImageIcon(
				getClass().getResource("/UI/image/find.png")));
		setButton(jbtSearch);
		
		jbtBack.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				showBack();
			}
		});
		
		jbtAhead.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				showAhead();
			}
		});
		
		jbtHome.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {		
				showHome();
			}
		});

		jbtSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				tablePanel.saveFile();
			}
		});
		
		jbtDelete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				tablePanel.deleteTableData();
			}	
		});
		
		jbtSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findPanel.showDialog();
			}
		});

		tool.setFloatable(true);
		tool.add(jbtBack);
		tool.add(jbtAhead);
		tool.add(jbtHome);
		tool.add(jbtFile);
		tool.add(jbtSave);
		tool.add(jbtAddress);
		tool.add(jbtDelete);
		tool.add(jbtGround);
		tool.add(jbtSafe);
		tool.add(jbtSetUp);
		tool.add(jbtSearch);
		
		add(tool,BorderLayout.NORTH);
		
	}
	
	/**
	 * 按钮默认设置
	 * @param e
	 */
	public void setButton(JButton e){
		e.setBorderPainted(false);
		//e.setContentAreaFilled(false);
		e.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	

	/**
	 * 当用户改变时该函数被触发
	 */
	public void update(Observable o, Object arg) {
		if(user.isLogin()){
			this.loginItem.setText("用户:" + user.getName());
			this.loginItem.setEnabled(false);
		}
	}
	
	/**
	 * 如果配置文件不存在时，显示配置对话框
	 */
	public void showMainUI(){
		if(!Configuration.isConfig())
			this.pathPanel.setVisible(true);
	}
	
	public void showInitDialog(){
		pathPanel.showDialog();
	}

}



