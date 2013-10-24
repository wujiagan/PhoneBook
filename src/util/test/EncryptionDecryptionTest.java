package util.test;

import junit.framework.Assert;

import org.junit.Test;

import util.EncryptionDecryption;

public class EncryptionDecryptionTest {

	@Test
	public void testencrypt() throws Exception {
		for(int i = 0;i < 10;i++){
		Assert.assertEquals(new EncryptionDecryption().decrypt(new EncryptionDecryption().encrypt("123456789@fdj.com")),"123456789@fdj.com");
		Assert.assertEquals(new EncryptionDecryption().decrypt(new EncryptionDecryption().encrypt("234564897@fdj.com")),"234564897@fdj.com");
		Assert.assertEquals(new EncryptionDecryption().decrypt(new EncryptionDecryption().encrypt("548723154@fdj.com")),"548723154@fdj.com");
		Assert.assertEquals(new EncryptionDecryption().decrypt(new EncryptionDecryption().encrypt("975646412@fdj.com")),"975646412@fdj.com");
		}
		}
	
}
