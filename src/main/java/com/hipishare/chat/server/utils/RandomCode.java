package com.hipishare.chat.server.utils;

import java.util.Random;

/**
 * 
 * @description: 随机邀请码工具类
 * @author sunlei
 * @date: 2016年7月20日
 *
 */
public class RandomCode {

	/**
	 * 
	 * @Description: 生成指定长度的邀请码
	 * @param length 邀请码长度
	 * @return
	 * String 邀请码
	 */
	public static String getCharAndNumr(int length, boolean numOnly) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			if (numOnly) {
				val += String.valueOf(random.nextInt(10));
			} else {
				// 输出字母还是数字
				String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
				// 字符串
				if ("char".equalsIgnoreCase(charOrNum)) {
					// 取得大写字母还是小写字母
					int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
					val += (char) (choice + random.nextInt(26));
				} else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
					val += String.valueOf(random.nextInt(10));
				}
			}
		}
		return val.toUpperCase();
	}

	public static void main(String[] args) {
		for(int i = 0;i<100;i++){
			System.out.println(getCharAndNumr(6, false));
		}
	}
}
