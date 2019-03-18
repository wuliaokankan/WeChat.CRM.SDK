package com.jubotech.framework.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DateUtil {

	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	/** yyyy-MM-dd */
	public static String DATE_FORMAT_1 = "yyyy-MM-dd";
	/** yyyy-MM-dd HH:mm:ss */
	public static String DATE_FORMAT_2 = "yyyy-MM-dd HH:mm:ss";
	/** yyyyMMddHHmm */
	public static String DATE_FORMAT_3 = "yyyyMMddHHmm";
	/** yyyy-MM-dd HH:mm */
	public static String DATE_FORMAT_4 = "yyyy-MM-dd HH:mm";
	/** yyyyMMdd */
	public static String DATE_FORMAT_5 = "yyyyMMdd";
	/** yyyyMMddHHmmss */
	public static String DATE_FORMAT_6 = "yyyyMMddHHmmss";

	/** 取得当前日期 */
	public static Date currentDate() {
		return new Date(System.currentTimeMillis());
	}

	/** 获取秒级间隔 */
	public static int dateInterval4Sec(Date dateBegin, Date dateEnd) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT_2);
			long begin = df.parse(df.format(dateBegin)).getTime();
			long end = df.parse(df.format(dateEnd)).getTime();
			return (int) ((end - begin) / 1000);
		} catch (Exception e) {
			logger.error("dateInterval4Sec error : " + e);
			return 0;
		}
	}

	public static Date convertString2Date(String dateStr) {
		return convertString2Date(dateStr, DATE_FORMAT_2);
	}

	/** 将字符串转换成日期 */
	public static Date convertString2Date(String dateStr, String dateFormat) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			return sdf.parse(dateStr);
		} catch (Exception e) {
			logger.error("newDate error : " + e);
			return null;
		}
	}

	public static String convertDate2String(Date date) {
		return convertDate2String(date, DATE_FORMAT_2);
	}

	/** 将日期转换成字符串 */
	public static String convertDate2String(Date date, String dateFormat) {
		if (date == null) {
			return "";
		}
		if (StringUtils.isBlank(dateFormat)) {
			dateFormat = DATE_FORMAT_2;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	}

	/**
	 * 获取指定日期偏移指定时间后的时间
	 * @param date 基准日期
	 * @param calendarField 偏移的粒度大小（小时、天、月等）使用Calendar中的常数
	 * @param offset 偏移量，正数为向后偏移，负数为向前偏移
	 * @return 偏移后的日期
	 */
	public static Date offsetDate(Date date, int calendarField, int offset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(calendarField, offset);
		return cal.getTime();
	}

	/**
	 * 偏移天
	 * @param date 日期
	 * @param offsite 偏移天数，正数向未来偏移，负数向历史偏移
	 * @return 偏移后的日期
	 */
	public static Date offsetDay(Date date, int offsite) {
		return offsetDate(date, Calendar.DAY_OF_YEAR, offsite);
	}

	/**
	 * 偏移周
	 * @param date 日期
	 * @param offsite 偏移周数，正数向未来偏移，负数向历史偏移
	 * @return 偏移后的日期
	 */
	public static Date offsetWeek(Date date, int offsite) {
		return offsetDate(date, Calendar.WEEK_OF_YEAR, offsite);
	}

	/**
	 * 偏移月
	 * @param date 日期
	 * @param offsite 偏移月数，正数向未来偏移，负数向历史偏移
	 * @return 偏移后的日期
	 */
	public static Date offsetMonth(Date date, int offsite) {
		return offsetDate(date, Calendar.MONTH, offsite);
	}

	public static int getDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 一周第一天是否为星期天
		boolean isFirstSunday = (calendar.getFirstDayOfWeek() == Calendar.SUNDAY);
		// 获取周几
		int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
		// 若一周第一天为星期天，则-1
		if (isFirstSunday) {
			weekDay = weekDay - 1;
			if (weekDay == 0) {
				weekDay = 7;
			}
		}
		return weekDay;
	}

	public static void main(String[] args) {
		Date date = offsetDay(new Date(), 1);
		System.out.println(date);
	}

}
