package util.test;

import junit.framework.Assert;

import org.junit.Test;

import util.PinYin;

public class PinYinTest {

	@Test
	public void testGetPinyin() {
		for(int i = 0;i<10;i++)
		{
		Assert.assertEquals(PinYin.getPinyin("吴家淦"),"wujiagan");
		Assert.assertEquals(PinYin.getPinyin("吴偲畅"),"wusichang");
		Assert.assertEquals(PinYin.getPinyin("余福"),"yufu");
		Assert.assertEquals(PinYin.getPinyin("叶威锋"),"yeweifeng");
		}
	}

}
