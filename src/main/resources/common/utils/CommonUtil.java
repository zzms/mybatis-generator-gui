package /packageName/.common.utils;

import java.util.List;

/**
 * 公共工具类
 */

public class CommonUtil {

	/**
	 * List不为空，且至少有一条记录
	 * @param list
	 * @return
	 */
	public static boolean isNotEmptyList(List<?> list) {
		return !isEmptyList(list);
	}

	/**
	 * List为空，或list里面没有任何记录
	 * @param list
	 * @return
	 */
	public static boolean isEmptyList(List<?> list) {
		boolean flag = false;
		if (list == null || list.size() <= 0) {
			flag = true;
		}
		return flag;
	}
}
