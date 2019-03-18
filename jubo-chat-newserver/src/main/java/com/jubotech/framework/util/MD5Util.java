package com.jubotech.framework.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5Util {

	public static String getMD5(String str) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] digest = md5.digest(str.getBytes());
			String hashString = new BigInteger(1, digest).toString(16);
			return hashString.toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
