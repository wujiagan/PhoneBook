package util.test;

import junit.framework.Assert;

import org.junit.Test;

import util.DataOperate;
import util.PinYin;


public class DataOperateTest {
	
	@Test
	public void testCompareChinese(){
		
	}
	@Test
	public void testIsChinese(){
		Assert.assertTrue(DataOperate.isChinese("吴"));
		Assert.assertTrue(DataOperate.isChinese("wu"));
	}
}
