package /packageName/.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormart {

	public static String separator_horizontal = "-";
	public static String separator_oblique = "/";
	private static String YYYY = "yyyy";
	private static String YYYYMM = "yyyyMM";
	private static String YYYYMMDD = "yyyyMMdd";
	private static String YYYY_MM_DD = "yyyy-MM-dd";
	private static String YYYYXMMXDD = "yyyy/MM/dd";
	private static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	private static String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
	private static String YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
	private static String YYYYXMMXDDXHHMMSS = "yyyy/MM/dd HH:mm:ss";
	private static String HH_MM_SS = "HH:mm:ss";

	public static String convertToString(Date date, String str) {
		if (date == null || StringUtil.isEmpty(str)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(str);
		return sdf.format(date);
	}

	public static Date pareStrToDate(String strDate, String formatStr) {
		if (StringUtil.isEmpty(strDate) || StringUtil.isEmpty(formatStr)) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String convertToHH_MM_SS(Date date) {
		return convertToString(date, HH_MM_SS);
	}

	public static String convertToYYYYMMDD(Date date) {
		return convertToString(date, YYYYMMDD);
	}

	public static String convertToYYYY_MM_DD(Date date) {
		return convertToString(date, YYYY_MM_DD);
	}

	public static String convertToYYYYXMMXDD(Date date) {
		return convertToString(date, YYYYXMMXDD);
	}

	public static String convertToYYYYMMDDHHMMSS(Date date) {
		return convertToString(date, YYYYMMDDHHMMSS);
	}

	public static String convertToYYYYMMDDHHMMSSSSS(Date date) {
		return convertToString(date, YYYYMMDDHHMMSSSSS);
	}

	public static String convertToYYYY_MM_DD_HHMMSS(Date date) {
		return convertToString(date, YYYY_MM_DD_HHMMSS);
	}

	public static String convertToYYYYXMMXDDXHHMMSS(Date date) {
		return convertToString(date, YYYYXMMXDDXHHMMSS);
	}

	public static Date paserYYYY_MM_DDToDate(String strDate) {
		return pareStrToDate(strDate, YYYY_MM_DD);
	}

	public static Date paserYYYY_MM_DD_HHMMSSToDate(String strDate) {
		return pareStrToDate(strDate, YYYY_MM_DD_HHMMSS);
	}

	public static Date getNow() {
		return new Date();
	}

	public static String getNowHH_MM_SS() {
		return convertToHH_MM_SS(getNow());
	}

	public static String getNowYYYY_MM_DD() {
		return convertToYYYY_MM_DD(getNow());
	}

	public static String getNowYYYYMMDD() {
		return convertToYYYYMMDD(getNow());
	}

	public static String getNowYYYY() {
		return convertToString(getNow(), YYYY);
	}

	public static String getNowYYYYMM() {
		return convertToString(getNow(), YYYYMM);
	}

	public static String getNowYYYY_MM_DD_HHMMSS() {
		return convertToYYYY_MM_DD_HHMMSS(getNow());
	}

	public static String getNowYYYYMMDDHHMMSSSSS() {
		return convertToYYYYMMDDHHMMSSSSS(getNow());
	}

	public static int getAgeByYYYY_MM_DDHHMMSS(String birthDay) {
		return getAge(paserYYYY_MM_DD_HHMMSSToDate(birthDay));
	}

	public static int getAgeByYYYY_MM_DD(String birthDay) {
		return getAge(paserYYYY_MM_DDToDate(birthDay));
	}

	/**
	 * 计算两个时间的时间间隔（秒）
	 */
	public static long getSsInterval(Date pre,Date next){
		return (next.getTime()-pre.getTime())/1000;
	}

	/**
	 * 是否是某年的第一天
	 * 1月1日
	 */
	public static boolean isFirstDayOfYear() {
		boolean bo = false;
		String nowArr[] = getNowYYYY_MM_DD().split(separator_horizontal);
		if (Integer.valueOf(nowArr[2]) == 1 && Integer.valueOf(nowArr[3]) == 1) {
			bo = true;
		}
		return bo;
	}

	/**
	 * 是否是某月的第一天
	 * 1日
	 */
	public static boolean isFirstDayOfMonth() {
		boolean bo = false;
		String nowArr[] = getNowYYYY_MM_DD().split(separator_horizontal);
		if (Integer.valueOf(nowArr[2]) == 1) {
			bo = true;
		}
		return bo;
	}

	/**
	 * 根据生日计算年龄
	 *
	 * @param birthDay
	 * @return
	 */
	public static int getAge(Date birthDay) {

		if (birthDay == null) {
			return 0;
		}

		Calendar cal = Calendar.getInstance();
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

		cal.setTime(birthDay);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;
		if (monthNow >= monthBirth) {
			if (dayOfMonthNow >= dayOfMonthBirth) {
				age++;
			}
		}
		return age;
	}

	/**
	 * 加减日期，为负数则为减
	 *
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date plusDate(Date date, int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, year);
		cal.add(Calendar.MONTH, month);
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}

	/**
	 * 加减日期，为负数则为减
	 *
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date plusDate(int year, int month, int day) {
		return plusDate(new Date(), year, month, day);
	}

	/**
	 * 1小时
	 */
	public static Date DATE_HOUR1 = plusDate(0, 0, 1);

	/**
	 * 1天
	 */
	public static Date DATE_DAY1 = plusDate(0, 0, 24);

	/**
	 * 1月
	 */
	public static Date DATE_MONTH1 = plusDate(0, 1, 0);

	/**
	 * 1年
	 */
	public static Date DATE_YEAR1 = plusDate(1, 0, 0);
}
