package util.test;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import util.PinYin;

public class PinYinTest {

	@Test
	public void testGetPinyin() {
		Assert.assertEquals(PinYin.getPinyin("吴"), "wu");
	}

}
