package org.pillow.common.security;

import java.util.Random;

public class RandomUtil {

	public static String randStr(int length) {
		if(length<=0) {
			return "";
		}
		Random rand = new Random();
		StringBuffer sb=new StringBuffer();
		for (int i=0;i<length;++i){
			//ascii id:33~126
			int ascii = rand.nextInt(93)+33;
			sb.append((char)ascii);
		}
		return sb.toString();
	}
}
