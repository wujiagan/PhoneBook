package util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYin {
 
 
	 /**
	  * 获取拼音
	  * @param src
	  * @return Set<String>
	  */
	public static String getPinyin(String src){
		if(src!=null && !src.trim().equalsIgnoreCase("")){
		char[] srcChar ;
		srcChar = src.toCharArray();
		//汉语拼音格式输出类
		HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();
	
		//输出设置，大小写，音标方式等
		hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE); 
		hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
	   
		StringBuffer result = new StringBuffer();
		
		for(int i=0;i<srcChar.length;i++){
			char c = srcChar[i];
			//是中文或者a-z或者A-Z转换拼音(我的需求，是保留中文或者a-z或者A-Z)
			//[\\u4E00-\\u9FA5]+正则匹配中文
			if(String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")){
				try{
					String[] temp = PinyinHelper.toHanyuPinyinStringArray(srcChar[i], hanYuPinOutputFormat);
					result.append(temp[0]);
				}catch(BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			}else 
				if(((int)c>=65 && (int)c<=90) || ((int)c>=97 && (int)c<=122)){
					result.append(String.valueOf(srcChar[i]));
				}
		}
		return new String(result);
		}
		return null;
	}
 
	public static void main(String[] args) {
		String str = "无";
		System.out.println(getPinyin(str));
	
	}

}
