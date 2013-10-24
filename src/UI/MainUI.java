package UI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
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
import javax.swing.KeyStroke;

import user.User;
import util.Configuration;
import util.LoginProxy;


public class MainUI extends JFrame implements Observer{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7489067892021806972L;
	
	/**
	 * 菜单栏
	 */
	private JMenuBar menu;
	/**
	 * 菜单组
	 */
	private JMenu fileMenu, userMenu, helpMenu;

	/**
	 * 定义卡片布局管理器
	 */
	private CardLayout cardLayout = new CardLayout();
	private JPanel cardPanel = new JPanel(cardLayout);
	
	private HomePanel[] homePanel = {new HomePanel(getClass().getResource("/UI/image/shitou.jpg")),
			new HomePanel(getClass().getResource("/UI/image/taijie.jpg")),
			new HomePanel(getClass().getResource("/UI/image/jitai.jpg"))
	};
	
	/**
	 * 当前用户
	 */
	private User currentUser = new User();
	
	/**
	 *登录面板
	 */
	private LoginPanel loginPanel = null;
	
	/**
	 * 注册面板
	 */
	private RegisterPanel registerPanel = null;
	
	/**
	 * 文件储存路径选择面板
	 */
	private PathInitPanel pathPanel = null ;

	
	private int numHome = 0;
	private String nowLocation = "home" ;
	
	private Stack<String> backStack = new Stack<String>();
	private Stack<String> aheadStack = new Stack<String>();
	
	/**
	 * 用户设置按钮组
	 */
	private JMenuItem loginItem, registerItem, changePassWordItem, logoutItem;
	
	/**
	 * 数据操作面板
	 */
	private TablePanel tablePanel = null;

	/** 
	 * 创建用户界面
	 */
	public MainUI(){
		pathPanel = new PathInitPanel(this);
		boolean firstStart = false;
		if(!Configuration.isConfig()){
			Configuration.createDefaultStorePath();
			firstStart = true;
		}
			
		
		
		registerPanel = new RegisterPanel(this);
		loginPanel = new LoginPanel(currentUser);
		//观察者设计模式
		currentUser.addObserver(this);
		tablePanel = new TablePanel(currentUser);
		
		this.setLayout(new BorderLayout());
		add(cardPanel,BorderLayout.CENTER);
		createMenu(); 
		initTheme();
		
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				tablePanel.saveFile();
				System.exit(1);
			}
		});
		
		Toolkit tool= this.getToolkit();
		Image myimage=tool.getImage(getClass().getResource("/UI/image/address.png"));
		this.setIconImage(myimage);
		
		this.setSize(860,600);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((screen.width-this.getSize().width)/2,
				(screen.height-this.getSize().height)/2);
		this.setResizable(false);
		this.setVisible(true);
		cardLayout.first(cardPanel);
		
		if(firstStart)
			pathPanel.showDialog();
	}
	
	/** 
	 * 初始化主题
	 */
	public void initTheme(){
		for(int i = 0; i < 3; i++)
			cardPanel.add(homePanel[i],"home" + i);
		cardPanel.add(tablePanel, "table");
		cardPanel.add(registerPanel, "registerPanel");
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
			showTable();
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
	
	/** 
	 * 显示用户注册面板
	 */
	public void showRegisterPanel() {
		cardLayout.show(cardPanel, "registerPanel"); 
		if(nowLocation != "registerPanel"){
			backStack.push(nowLocation);
			nowLocation = "registerPanel";
		}
	}
	
	/**
	 * 回退
	 */
	public void showBack() {
		if(!backStack.empty()){
			aheadStack.push(backStack.peek());
			cardLayout.show(cardPanel,backStack.pop());
		}
	}
	
	/**
	 * 显示前一页
	 */
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
		helpMenu = new JMenu("帮助");
		
		JMenuItem newItem = new JMenuItem("新建");
		newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));
		JMenuItem chooseItem = new JMenuItem("更改储存路径");
		chooseItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,InputEvent.CTRL_MASK));
		
		loginItem = new JMenuItem("用户登陆");
		loginItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,InputEvent.CTRL_MASK));
		registerItem = new JMenuItem("账户注册");
		registerItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,InputEvent.CTRL_MASK));
		changePassWordItem = new JMenuItem("修改密码");
		changePassWordItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,InputEvent.CTRL_MASK));
		logoutItem = new JMenuItem("安全退出");
		logoutItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK));
		
		JMenuItem declareItem = new JMenuItem("版本：1.0");
		
		fileMenu.add(newItem);
		fileMenu.add(chooseItem);
		
		userMenu.add(loginItem);
		
		if(!LoginProxy.isSetRoot())
			userMenu.add(registerItem);
		
		userMenu.add(changePassWordItem);
		userMenu.add(logoutItem);
		
		helpMenu.add(declareItem);
		
		newItem.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if(tablePanel.newFileDir())
					showTable() ;
			}
		});
		
		loginItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				loginPanel.showLoginDiglog();
			}
		});
		
		chooseItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				pathPanel.showDialog();
			}
		});
		
		changePassWordItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				if(currentUser.isLogin()){
					registerPanel.showChangePasswordDiglog(currentUser);
					showRegisterPanel();
				}
				else
					JOptionPane.showMessageDialog(null, "请先登录", "提醒", JOptionPane.ERROR_MESSAGE);
			}
			
		});
		
		registerItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				showRegisterPanel();
			}
		});
		
		logoutItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				currentUser.initUser();
				loginItem.setText("登录");
				loginItem.setEnabled(true);
			}
		});

		menu.add(fileMenu);
		menu.add(userMenu);
		menu.add(helpMenu);
		setJMenuBar(menu);
		
		JToolBar tool = new JToolBar(JToolBar.HORIZONTAL);
		
		JButton jbtBack = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/arrow_left.png")));
		setButton(jbtBack);
		jbtBack.setMnemonic(KeyEvent.VK_B);
		
		JButton jbtAhead = new JButton(new ImageIcon(
				getClass().getResource("/UI/image/arrow_right.png")));
		setButton(jbtAhead);
		jbtAhead.setMnemonic(KeyEvent.VK_P);
		
		JButton jbtHome = new JButton("首页", new ImageIcon(
				getClass().getResource("/UI/image/home.png")));
		setButton(jbtHome);
		jbtHome.setMnemonic(KeyEvent.VK_H);
		
		JButton jbtFile = new JButton("新建", new ImageIcon(
				getClass().getResource("/UI/image/add.png")));
		setButton(jbtFile);
		jbtFile.setMnemonic(KeyEvent.VK_N);

		JButton jbtTable = new JButton("电话本",new ImageIcon(
				getClass().getResource("/UI/image/address.png")));
		setButton(jbtTable);
		jbtTable.setMnemonic(KeyEvent.VK_T);
		
		JButton jbtUser = new JButton("登录",new ImageIcon(
				getClass().getResource("/UI/image/user.png")));
		setButton(jbtUser);
		jbtUser.setMnemonic(KeyEvent.VK_L);
		
		jbtFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tablePanel.newFileDir())
					showTable() ;
			}	
		});
		
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
			public void actionPerformed(ActionEvent arg0) {		
				showHome();
			}
		});

		jbtTable.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {		
				showTable();
			}
		});
		
		jbtUser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				loginPanel.showLoginDiglog();
			}
		});

		tool.setFloatable(true);
		tool.add(jbtBack);
		tool.add(jbtAhead);
		tool.add(jbtHome);
		tool.add(jbtFile);
		tool.add(jbtTable);
		tool.add(jbtUser);
		
		add(tool,BorderLayout.NORTH);
		
	}
	
	/**
	 * 设置注册功能是否可用
	 * @param enible
	 */
	public void setRigirter(boolean enible) {
		registerItem.setEnabled(enible);
	}
	
	/**
	 * 按钮默认设置
	 * @param e
	 */
	public void setButton(JButton e){
		e.setBorderPainted(false);
		e.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	
	

	/**
	 * 当用户改变时该函数被触发
	 */
	public void update(Observable o, Object arg) {
		if(currentUser.isLogin()){
			this.loginItem.setText("用户:" + currentUser.getName());
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



