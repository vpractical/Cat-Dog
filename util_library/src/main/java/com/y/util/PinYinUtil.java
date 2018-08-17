package com.y.util;

import com.github.promeg.pinyinhelper.Pinyin;

/**
 * Created by ywb on 2016/11/23.
 */

public class PinYinUtil {

	public static String toPinYin(String character) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < character.length(); i++) {
			buffer.append(Pinyin.toPinyin(character.charAt(i)));
		}
		return buffer.toString();
	}
}
