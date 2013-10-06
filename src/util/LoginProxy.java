package util;

import user.User;

public class LoginProxy {
	/** 用户登录 */
	public static boolean userLogin(User user){
		System.out.println("name = "+user.getName()+","+"password = "+user.getPassword());
		//登录
		//文件
		//加密
		
		user.setLogin(true);
		return true;
	}
	
	/** 用户注册 */
	public static boolean userRegister(String name, String password){
		System.out.println("name = " + name + "," + "password = " + password);
		//登录
		//文件
		//加密
		return true;
	}
	
	/** 修改密码 */
	public static boolean changePassword(User user,String newPassword){
		System.out.println("name = " + user.getName() + "," + "newPassword = " + newPassword);
		//登录
		//文件
		//加密
		return true;
	}
}
