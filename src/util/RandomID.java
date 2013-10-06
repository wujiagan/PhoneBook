package util;

import java.util.Random;

public class RandomID {
	private static char ch[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
        '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
        'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
        'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
        'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
        'z' };
	
	/** 随机生成32位字符串 */
	public static String get32ID(){
		Random random = new Random();
		StringBuffer ID = new StringBuffer();
		for(int i = 0; i < 32; i++)
			ID.append(ch[random.nextInt(62)]);
		return new String(ID);
	}
	
}
