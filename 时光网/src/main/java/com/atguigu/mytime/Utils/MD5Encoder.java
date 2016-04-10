package com.atguigu.mytime.Utils;

import java.security.MessageDigest;

public class MD5Encoder {
	public static String encode(String string) throws Exception {
		//先创建加密对象 在将字符串转换为一个16进制的byte数组
		byte[] hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
		StringBuilder hex = new StringBuilder(hash.length * 2);
		//遍历字byte数组对每个元素进行加密
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10) {
				hex.append("0");
			}
			//连接密文
			hex.append(Integer.toHexString(b & 0xFF));
		}
		//将密文转换为string类型
		return hex.toString();
	}
}
