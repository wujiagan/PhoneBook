package user;

import java.util.Observable;

public class User extends Observable{

	private String name;
	private String password;
	private boolean isLogin;
	
	public User(){
		initUser();
	}
	
	public User(String name, String password) {
		this.name = name;
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public boolean isLogin() {
		return isLogin;
	}
	
	public void setLogin(boolean isLogin) {
		super.setChanged();
		this.isLogin = isLogin;
		super.notifyObservers();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	
	public void initUser(){
		this.name = "root";
		this.password = "";
		this.isLogin = false;
	}
	
	public String toString(){
		return "name = " + this.name + ", " + 
			"password = " + this.password + ", " +
			"isLogin = " + this.isLogin;
	}
	
}
