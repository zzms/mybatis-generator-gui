package /packageName/.common.utils;

import org.apache.commons.lang3.math.NumberUtils;


public class NumberUtil extends NumberUtils{

	/**
	 * 获取数字的长度
	 */
	public static int getNumberLength(int num){
		String s = num < 0 ? num * -1 + "" : num + "";
		return s.length();
	}

	/**
	 * 数字不足长度的前面补0个数
	 * 返回 0 或00 或000 等...
	 */
	public static String getNumberPrefix0(int num,int length){
		StringBuffer numPre = new StringBuffer();
		for (int i = 0; i < length
				- /packageName/.common.utils.NumberUtil.getNumberLength(num); i++) {
			numPre.append("0");
		}
		return numPre.toString();
	}

}
