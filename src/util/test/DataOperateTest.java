package util.test;

import junit.framework.Assert;

import org.junit.Test;

import util.DataOperate;

public class DataOperateTest {

	@Test
	public void testisChinese() {
		
		for(int i =0;i<10;i++){
		Assert.assertTrue(DataOperate.isChinese("吴家淦"));
		Assert.assertTrue(DataOperate.isChinese("吴偲畅"));
		Assert.assertTrue(DataOperate.isChinese("余福"));
		Assert.assertTrue(DataOperate.isChinese("叶威锋"));		
		Assert.assertFalse(DataOperate.isChinese("wujiagan"));
		Assert.assertFalse(DataOperate.isChinese("wusichang"));
		Assert.assertFalse(DataOperate.isChinese("yufu"));
		Assert.assertFalse(DataOperate.isChinese("yeweifeng"));
		}
		
	}
	
	@Test
	public void testcompareChinese(){
		
		for(int i = 0;i<10;i++){
		Assert.assertTrue(DataOperate.compareChinese("吴","我")>0);
		Assert.assertTrue(DataOperate.compareChinese("余","吴")>0);
		Assert.assertFalse(DataOperate.compareChinese("吴","我")<0);
		Assert.assertFalse(DataOperate.compareChinese("余","吴")<0);
		}
	}
	
	
}
