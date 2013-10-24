package util;

import java.util.List;
import java.util.Map;

import user.LinkMan;

public class DataOperate {
	/**
	 * 排序
	 * @param source
	 * @param col
	 * @return
	 */
	public static void sort(List<LinkMan> linkMans){
		for(int i = linkMans.size()-1; i>0; i--)
			for(int j = 0; j<i; j++)
				if(compareChinese(linkMans.get(j).getName(), linkMans.get(j+1).getName())>0)
				{
					LinkMan temp = linkMans.get(j);
					linkMans.set(j, linkMans.get(j+1));
					linkMans.set(j+1, temp);
				}
		return ;
	}
	
	/**
	 * 模糊查找
	 * @param linkMans
	 * @param key
	 * @return
	 */
	public static boolean find(List<LinkMan> linkMans, String key, Map<LinkMan, Integer> result){
		result.clear();
		if(isChinese(key)){
			for(int i=0; i<linkMans.size(); i++)
				if(linkMans.get(i).getName().indexOf(key) != -1)
					result.put(linkMans.get(i), i);
		}
		else{
			for(int i=0; i<linkMans.size(); i++)
				if(PinYin.getPinyin(linkMans.get(i).getName()).indexOf(key) != -1)
					result.put(linkMans.get(i), i);
		}
		if(result.isEmpty())
			return false;
		return true;
	}
	
	/**
	 * 连个中文字符串排序
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static int compareChinese(String str1,String str2){
		if(str1 == null || str2 == null)
			return 1;
		str1 = PinYin.getPinyin(str1);
		str2 = PinYin.getPinyin(str2);
		return str1.compareTo(str2);
	}
	
	/**
	 * 判断是否是中文
	 * @param key
	 * @return
	 */
	public static boolean isChinese(String key) {
		return key.matches("[\\u4E00-\\u9FA5]+");
	}
	
	public static void main(String args[]){
		System.out.println(PinYin.getPinyin("晨晨"));
		System.out.println(PinYin.getPinyin("李磊"));
		System.out.println(compareChinese("晨晨","李磊"));
		System.out.println(isChinese("利好"));
		System.out.println(isChinese("abc"));
		System.out.println("利好".indexOf("利"));
	}
}
