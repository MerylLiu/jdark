package com.iptv.core.utils;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class EncryptUtil {
	private final static String passwordKey = "elink@2017%^&*";

	/*
	 * 加密*
	 * 
	 * @param datasource byte[]
	 * 
	 * @param password String*@return byte[]
	 */

	public static byte[] encrypt(String source) {
		byte[] datasource = source.getBytes();

		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(passwordKey.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(desKey);

			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);

			return cipher.doFinal(datasource);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param src
	 * @param password
	 * @throws Exception
	 */
	public static byte[] decrypt(String source) throws Exception {
		byte[] src = source.getBytes();

		SecureRandom random = new SecureRandom();
		DESKeySpec desKey = new DESKeySpec(passwordKey.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey securekey = keyFactory.generateSecret(desKey);

		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);

		return cipher.doFinal(src);
	}
}
