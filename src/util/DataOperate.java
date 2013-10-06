package util;

import java.util.Vector;

public class DataOperate {
	public static Vector<Vector<Object>> sort(Vector<Vector<Object>> source,int col){
		System.out.println("col = " + source.elementAt(0).size());
		for(int i = 0; i < source.size(); i++){
			for(int j = 0; j < source.elementAt(i).size(); j++)
				System.out.print(source.elementAt(i).elementAt(j) + " ");
			System.out.println();
		}
		//修改
		for(int i = source.size()-1; i>0; i--)
			for(int j = 0; j<i; j++)
				if(compareChinese((String)source.elementAt(j).elementAt(1),(String)source.elementAt(j+1).elementAt(1))>0)
				{
					Vector<Object> temp = source.get(j);
					source.set(j, source.get(j+1));
					source.set(j+1, temp);
				}
			
		for(int i = 0; i < source.size(); i++){
			for(int j = 0; j < source.elementAt(i).size(); j++)
				System.out.print(source.elementAt(i).elementAt(j) + " ");
			System.out.println();
		}
		return source;
	}
	
	public static int[] find(Vector<Vector<Object>> source,String info){
		int[] result = {0,3};
		return result;
	}
	
	public static int compareChinese(String str1,String str2){
		if(str1 == null || str2 == null)
			return 1;
		str1 = PinYin.getPinyin(str1);
		str2 = PinYin.getPinyin(str2);
		return str1.compareTo(str2);
	}
	
	public static void main(String args[]){
		System.out.println(PinYin.getPinyin("晨晨"));
		System.out.println(PinYin.getPinyin("李磊"));
		System.out.println(compareChinese("晨晨","李磊"));
	}
}
