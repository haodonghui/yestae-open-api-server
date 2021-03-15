/**
 * 
 *
 * https://www.yestae.com/home
 *
 * copyright yyh
 */

package com.yestae.common.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期处理
 *
 * @author daniel
 */
public class DateUtils {
	private static Logger logger = LoggerFactory.getLogger(DateUtils.class);
	/** 时间格式(yyyy-MM-dd) */
	public final static String YYYY_MM_DD = "yyyy-MM-dd";
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public static final String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss SSS";
	public static final String YYYY_MM = "yyyy-MM";
	public static final String YYYYMM = "yyyyMM";
	public static final String YYYYMMDD = "yyyyMMdd";

	public static final String DAY_FIRST_TIME = " 00:00:00";
	public static final String DAY_LAST_TIME = " 23:59:59";

	public final static String DATE_PATTERN_ONE = "yyyy.MM.dd";

	public final static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	/**
	 * 得到当月的最大日期
	 *
	 * @return
	 */
	public static final String DATE_PATTERN_DAY_CHINNESS = "【yyyy】年【MM】月【dd】";
	public static final String DATE_PATTERN_DAY_CHINNESS_DEFAULT = "yyyy年MM月dd日";
	public static final String DATE_PATTERN_JUEST_DAY = "dd";

	public static String formatEnd(Date date) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD);
			return df.format(date) + " 23:59:59";
		}
		return null;
	}

	public static String formatStart(Date date) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD);
			return df.format(date) + " 00:00:00";
		}
		return null;
	}


	
	/**
	 * 字符串转换成日期
	 * @param strDate 日期字符串
	 * @param pattern 日期的格式，如：DateUtils.DATE_TIME_PATTERN
	 */
	public static Date stringToDate(String strDate, String pattern) {
		if (StringUtils.isBlank(strDate)) {
			return null;
		}

		DateTimeFormatter fmt = DateTimeFormat.forPattern(pattern);
		return fmt.parseLocalDateTime(strDate).toDate();
	}

	/***
	 * 将日期转化为字符串
	 */
	public static String dateToString(Date date, String pattern) {
		String str = "";
		try {
			SimpleDateFormat formater = new SimpleDateFormat(pattern);
			str = formater.format(date);
		} catch (Throwable e) {
			logger.error(e.getLocalizedMessage());
		}
		return str;
	}

	/**
	 * 返回不大于date2的日期 如果 date1 >= date2 返回date2 如果 date1 < date2 返回date1
	 */
	public static Date ceil(Date date1, Date date2) {
		if (date1 == null && date2 == null) {
			return null;
		}
		if (date1 == null) {
			return date2;
		}
		if (date2 == null) {
			return date1;
		}
		if (date1.after(date2)) {
			return date2;
		} else {
			return date1;
		}
	}


	/**
	 * 返回不小于date2的日期 如果 date1 >= date2 返回date1 如果 date1 < date2 返回date2
	 */
	public static Date floor(Date date1, Date date2) {
		if (date1 == null && date2 == null) {
			return null;
		}
		if (date1 == null) {
			return date2;
		}
		if (date2 == null) {
			return date1;
		}
		if (date1.after(date2)) {
			return date1;
		} else {
			return date2;
		}
	}

	
	/**
	 * 获取2个时间相隔几天
	 */
	public static int getBetweenDayNumber(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return -1;
		}

		if (startDate.after(endDate)) {
			Date tmp = endDate;
			endDate = startDate;
			startDate = tmp;
		}

		long dayNumber = -1L;
		long DAY = 24L * 60L * 60L * 1000L;
		try {
			// "2010-08-01 00:00:00 --- 2010-08-03 23:59:59"算三天
			dayNumber = (endDate.getTime() + 1000 - startDate.getTime()) / DAY;
			// System.out.println(endDate.getTime()+" "+startDate.getTime());
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		return (int) dayNumber;
	}

	/**
	 * 获取2个时间相隔几天，非绝对值
	 */
	public static int getBetweenDayNumberNotAbsolute(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return -1;
			//
			// if (startDate.after(endDate)) {
			// Date tmp = endDate;
			// endDate = startDate;
			// startDate = tmp;
			// }
		}

		long dayNumber = -1L;
		long DAY = 24L * 60L * 60L * 1000L;
		try {
			// System.out.println((endDate.getTime() + 1000 -
			// startDate.getTime()));
			dayNumber = (endDate.getTime() - startDate.getTime()) / DAY;
			// System.out.println(endDate.getTime()+" "+startDate.getTime());
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		return (int) dayNumber;
	}

	/**
	 * 获取2个时间相隔几小时
	 */
	public static int getBetweenHourNumber(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return -1;
		}

		if (startDate.after(endDate)) {
			Date tmp = endDate;
			endDate = startDate;
			startDate = tmp;
		}

		long timeNumber = -1l;
		long TIME = 60L * 60L * 1000L;
		try {
			timeNumber = (endDate.getTime() - startDate.getTime()) / TIME;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return (int) timeNumber;
	}

	/**
	 * 获取2个时间相隔几小时，非绝对值
	 */
	public static int getBetweenHourNumberNotAbsolute(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return -1;
		}

		// if (startDate.after(endDate)) {
		// Date tmp = endDate;
		// endDate = startDate;
		// startDate = tmp;
		// }

		long timeNumber = -1l;
		long TIME = 60L * 60L * 1000L;
		try {
			timeNumber = (endDate.getTime() - startDate.getTime()) / TIME;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return (int) timeNumber;
	}

	/**
	 * 获取2个时间相隔几分钟
	 */
	public static int getBetweenMinuteNumber(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return -1;
		}

		if (startDate.after(endDate)) {
			Date tmp = endDate;
			endDate = startDate;
			startDate = tmp;
		}

		long timeNumber = -1l;
		long TIME = 60L * 1000L;
		try {
			timeNumber = (endDate.getTime() - startDate.getTime()) / TIME;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return (int) timeNumber;
	}

	/**
	 * 获取2个时间相隔几分钟,非绝对值
	 */
	public static int getBetweenMinuteNumberNotAbsolute(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return -1;
		}

		// if (startDate.after(endDate)) {
		// Date tmp = endDate;
		// endDate = startDate;
		// startDate = tmp;
		// }

		long timeNumber = -1l;
		long TIME = 60L * 1000L;
		try {
			timeNumber = (endDate.getTime() - startDate.getTime()) / TIME;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return (int) timeNumber;
	}

	/**
	 * 获取2个时间相隔几月
	 */
	public static int getBetweenMonthNumber(Date startDate, Date endDate) {
		int result = 0;
		try {
			if (startDate == null || endDate == null) {
				return -1;
			}

			// swap start and end date
			if (startDate.after(endDate)) {
				Date tmp = endDate;
				endDate = startDate;
				startDate = tmp;
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);

			int monthS = calendar.get(Calendar.MONTH);
			int yearS = calendar.get(Calendar.YEAR);

			calendar.setTime(endDate);
			int monthE = calendar.get(Calendar.MONTH);
			int yearE = calendar.get(Calendar.YEAR);

			if (yearE - yearS == 0) {
				result = monthE - monthS;
			} else {
				result = (yearE - yearS - 1) * 12 + (12 - monthS) + monthE;
			}

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		return result;
	}

	/**
	 * 获取2个时间相隔几月，非绝对值
	 */
	public static int getBetweenMonthNumberNotAbsolute(Date startDate, Date endDate) {
		int result = 0;
		try {
			if (startDate == null || endDate == null) {
				return -1;
			}

			// swap start and end date
			// if (startDate.after(endDate)) {
			// Date tmp = endDate;
			// endDate = startDate;
			// startDate = tmp;
			// }

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);

			int monthS = calendar.get(Calendar.MONTH);
			int yearS = calendar.get(Calendar.YEAR);

			calendar.setTime(endDate);
			int monthE = calendar.get(Calendar.MONTH);
			int yearE = calendar.get(Calendar.YEAR);

			if (yearE - yearS == 0) {
				result = monthE - monthS;
			} else {
				result = (yearE - yearS - 1) * 12 + (12 - monthS) + monthE;
			}

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		return result;
	}

	/**
	 * 获取2个时间相隔几秒
	 */
	public static int getBetweenSecondNumber(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return -1;
		}

		if (startDate.after(endDate)) {
			Date tmp = endDate;
			endDate = startDate;
			startDate = tmp;
		}

		long timeNumber = -1L;
		long TIME = 1000L;
		try {
			timeNumber = (endDate.getTime() - startDate.getTime()) / TIME;

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		return (int) timeNumber;
	}

	/**
	 * 获取2个时间相隔几秒,非绝对值
	 */
	public static int getBetweenSecondNumberNotAbsolute(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return -1;
			//
			// if (startDate.after(endDate)) {
			// Date tmp = endDate;
			// endDate = startDate;
			// startDate = tmp;
			// }
		}

		long timeNumber = -1L;
		long TIME = 1000L;
		try {
			timeNumber = (endDate.getTime() - startDate.getTime()) / TIME;

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		return (int) timeNumber;
	}

	/**
	 * 获取2个时间相隔几年
	 */
	public static int getBetweenYearNumber(Date startDate, Date endDate) {
		int result = 0;
		try {
			if (startDate == null || endDate == null) {
				return -1;
			}

			// swap start and end date
			if (startDate.after(endDate)) {
				Date tmp = endDate;
				endDate = startDate;
				startDate = tmp;
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			int yearS = calendar.get(Calendar.YEAR);

			calendar.setTime(endDate);
			int yearE = calendar.get(Calendar.YEAR);

			result = yearE - yearS;

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		return result;
	}

	/**
	 * 获取2个时间相隔几年，非绝对值
	 */
	public static int getBetweenYearNumberNotAbsolute(Date startDate, Date endDate) {
		int result = 0;
		try {
			if (startDate == null || endDate == null) {
				return -1;
			}

			// swap start and end date
			// if (startDate.after(endDate)) {
			// Date tmp = endDate;
			// endDate = startDate;
			// startDate = tmp;
			// }

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			int yearS = calendar.get(Calendar.YEAR);

			calendar.setTime(endDate);
			int yearE = calendar.get(Calendar.YEAR);

			result = yearE - yearS;

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		return result;
	}

	/**
	 * 根据字符串时间,返回Calendar
	 */
	public static Calendar getCalendar(String datetimeStr) {
		Calendar cal = Calendar.getInstance();
		if (StringUtils.isNotBlank(datetimeStr)) {
			Date date = stringToDate(datetimeStr, YYYY_MM_DD_HH_MM_SS);
			cal.setTime(date);
		}
		return cal;
	}

	/**
	 * 本季度
	 */
	public static List<Date> getCurrentQuarter() {
		List<Date> dateList = new ArrayList<Date>();
		Date date = new Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);// 一月为0

		dateList.add(1, calendar.getTime());// 结束时间设置为当前时间

		if (month >= 0 && month <= 2) {// 第一季度
			calendar.set(Calendar.MONTH, 0);
		} else if (month >= 3 && month <= 5) {// 第二季度
			calendar.set(Calendar.MONTH, 3);
		} else if (month >= 6 && month <= 8) {// 第三季度
			calendar.set(Calendar.MONTH, 6);
		} else {// 第四季度
			calendar.set(Calendar.MONTH, 9);
		}

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		dateList.add(0, calendar.getTime());

		return dateList;
	}

	/**
	 * 得到当前的日
	 *
	 * @return
	 */
	public static int getCurrentStatDay() {
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	// 获取当前月份，cal.get(Calendar.MONTH)是从零开始。
	public static int getCurrentStatMonth() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * 得到当前的年份
	 *
	 * @return
	 */
	public static int getCurrentStatYear() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return year;
	}


	/**
	 * 得到给定时间的日
	 *
	 * @return
	 */
	public static int getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	/**
	 * startStr 或者 startStr-endStr
	 */
	public static String getDifferentStr(String startStr, String endStr) {
		String dateRangeStr = "";
		if (startStr.equals(endStr)) {
			dateRangeStr = startStr;
		} else {
			dateRangeStr = startStr + "-" + endStr;
		}
		return dateRangeStr;
	}

	/**
	 * 获取给定日期的上个月的最早
	 *
	 * @param startDate
	 * @return
	 */
	public static Date getFirstMonthFirstDay(Date startDate) {

		return getMinDateOfMonth(getFristMonthDay(startDate));
	}

	/**
	 * 获取给定日期的上一个月的日期的最早时间
	 *
	 * @param startDate
	 * @return
	 */
	public static Date getFristMonthDay(Date startDate) {
		// 是不是
		// int month = startDate.getMonth();
		Date monthEndDate = getMaxDateOfMonth(startDate);
		Date nextMonth = org.apache.commons.lang.time.DateUtils.addMonths(startDate, -1);
		nextMonth = stringToDate(dateToString(nextMonth, YYYY_MM_DD) + DAY_FIRST_TIME, YYYY_MM_DD_HH_MM_SS);
		if (isTheSameDay(startDate, monthEndDate)) {
			nextMonth = getMaxDateOfMonth(nextMonth);
		}
		return nextMonth;
	}

	/**
	 * 功能：获取昨天最大时间。 输入: 2010-01-31 00:00:00 返回：2010-01-30 23:59:59
	 */
	public static Date getLastMaxDay(Date startDate) {
		if (startDate == null) {
			return null;
		}
		startDate = org.apache.commons.lang.time.DateUtils.addDays(startDate, -1);
		return getMaxDateOfDay(startDate);
	}

	/**
	 * 获取给定日期的上一个月的最晚时间
	 *
	 * @param startDate
	 * @return
	 */
	public static Date getLastMonthDay(Date startDate) {
		// 是不是
		// int month = startDate.getMonth();
		Date monthEndDate = getMaxDateOfMonth(startDate);
		Date nextMonth = org.apache.commons.lang.time.DateUtils.addMonths(startDate, -1);
		nextMonth = stringToDate(dateToString(nextMonth, YYYY_MM_DD) + DAY_LAST_TIME, YYYY_MM_DD_HH_MM_SS);
		if (isTheSameDay(startDate, monthEndDate)) {
			nextMonth = getMaxDateOfMonth(nextMonth);
		}
		return nextMonth;
	}

	/**
	 * 获取给定日期的上个月的日期的最后一天
	 *
	 * @param startDate
	 * @return
	 */
	public static Date getLastMonthLastDay(Date startDate) {

		return getMaxDateOfMonth(getLastMonthDay(startDate));
	}

	/**
	 * 上季度
	 */
	public static List<Date> getLastQuarter() {
		List<Date> dateList = new ArrayList<Date>();
		Date date = new Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);// 一月为0

		// 如果是第一季度则返回去年的第四季度
		if (month >= 0 && month <= 2) {// 当前第一季度
			calendar.add(Calendar.YEAR, -1);// 退到去年
			calendar.set(Calendar.MONTH, 9);// 去年十月
		} else if (month >= 3 && month <= 5) {// 当前第二季度
			calendar.set(Calendar.MONTH, 0);
		} else if (month >= 6 && month <= 8) {// 当前第三季度
			calendar.set(Calendar.MONTH, 3);
		} else {// 当前第四季度
			calendar.set(Calendar.MONTH, 6);
		}
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		dateList.add(0, calendar.getTime());

		calendar.add(Calendar.MONTH, 3);// 加3个月，到下个季度的第一天
		calendar.add(Calendar.DAY_OF_MONTH, -1);// 退一天，得到上季度的最后一天
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);

		dateList.add(1, calendar.getTime());

		return dateList;
	}

	/**
	 * 取得一个date对象对应的日期的23点59分59秒时刻的Date对象。
	 */
	public static Date getMaxDateOfDay(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

		return calendar.getTime();
	}

	/**
	 * 取得一个date对象对应的小时的59分59秒时刻的Date对象。
	 */
	public static Date getMaxDateOfHour(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

		return calendar.getTime();
	}

	/**
	 * 取得一月中的最后一天
	 */
	public static Date getMaxDateOfMonth(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

		return calendar.getTime();
	}

	/**
	 * 取得一周中的最后一天
	 */
	public static Date getMaxDateOfWeek(Date date, Locale locale) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);

		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMaximum(Calendar.DAY_OF_WEEK));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

		if (locale == null) {
			locale = Locale.CHINESE;
		}
		Date tmpDate = calendar.getTime();
		if (Locale.CHINESE.getLanguage().equals(locale.getLanguage())) {
			if (day_of_week == 1) {// 星期天
				tmpDate = org.apache.commons.lang.time.DateUtils.addDays(tmpDate, -6);
			} else {
				tmpDate = org.apache.commons.lang.time.DateUtils.addDays(tmpDate, 1);
			}
		}

		return tmpDate;
	}

	/**
	 * 取得一年中的最后一天
	 */
	public static Date getMaxDateOfYear(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));

		return calendar.getTime();
	}

	public static int getMaxDayOfLastMonth() {
		Date now = new Date();
		Date lastMonth = org.apache.commons.lang.time.DateUtils.addMonths(now, -1);
		lastMonth = getMaxDateOfMonth(lastMonth);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(lastMonth);
		int maxDay = calendar.get(Calendar.DAY_OF_MONTH);
		return maxDay;
	}

	/**
	 * 取得一个date对象对应的日期的0点0分0秒时刻的Date对象。
	 */
	public static Date getMinDateOfDay(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getMinDateOfHour(date));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		return calendar.getTime();
	}

	/**
	 * 取得一个date对象对应的日期的0分0秒时刻的Date对象。
	 */
	public static Date getMinDateOfHour(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
		return calendar.getTime();
	}

	/**
	 * 取得一月中的最早一天。
	 */
	public static Date getMinDateOfMonth(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));

		return calendar.getTime();
	}

	/**
	 * 取得一周中的最早一天。
	 */
	public static Date getMinDateOfWeek(Date date, Locale locale) {
		if (date == null) {
			return null;
		}

		Calendar calendar = Calendar.getInstance();
		int day_of_week = calendar.get(Calendar.DAY_OF_WEEK);

		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getActualMinimum(Calendar.DAY_OF_WEEK));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));

		if (locale == null) {
			locale = Locale.CHINESE;
		}
		Date tmpDate = calendar.getTime();
		if (Locale.CHINESE.getLanguage().equals(locale.getLanguage())) {
			if (day_of_week == 1) {// 星期天
				tmpDate = org.apache.commons.lang.time.DateUtils.addDays(tmpDate, -6);
			} else {
				tmpDate = org.apache.commons.lang.time.DateUtils.addDays(tmpDate, 1);
			}
		}

		return tmpDate;
	}

	/**
	 * 取得一年中的最早一天。
	 */
	public static Date getMinDateOfYear(Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMinimum(Calendar.DAY_OF_YEAR));
		calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));

		return calendar.getTime();
	}

	// 获取给定时间月份，cal.get(Calendar.MONTH)是从零开始。
	public static int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * 得到下一个月的月份
	 *
	 * @return
	 */
	public static int getMonthOfLastMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		int lastMonth = calendar.get(Calendar.MONTH) + 1;
		return lastMonth;
	}

	/**
	 * 给定一个日期和天数，得到这个日期+天数的日期
	 *
	 * @author daniel,2012-11-28
	 * @param date
	 *            指定日期 天数
	 * @return
	 */
	public static String getNextDay(String date, int num,String pattern) {
		Date d = stringToDate(date, YYYY_MM_DD);
		Calendar ca = Calendar.getInstance();
		ca.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		ca.setTime(d);

		int day = ca.get(Calendar.DATE);
		day = day + num;
		ca.set(Calendar.DATE, day);
		return dateToString(ca.getTime(), pattern);

	}

	/**
	 * 获取给定日期的下一个月的日期的最晚时间
	 *
	 * @param startDate
	 * @return
	 */
	public static Date getNextMonthDay(Date startDate) {
		// 是不是
		// int month = startDate.getMonth();
		Date monthEndDate = getMaxDateOfMonth(startDate);
		Date nextMonth = org.apache.commons.lang.time.DateUtils.addMonths(startDate, 1);
		nextMonth = stringToDate(dateToString(nextMonth, YYYY_MM_DD) + DAY_LAST_TIME, YYYY_MM_DD_HH_MM_SS);
		if (isTheSameDay(startDate, monthEndDate)) {
			nextMonth = getMaxDateOfMonth(nextMonth);
		}
		return nextMonth;
	}

	/**
	 * 得到给定时间的年份
	 *
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		return year;
	}

	/**
	 * 得到当年的最大月份
	 *
	 * @return
	 */
	public static int getYearOfLastMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		int yearOfLastMonth = calendar.get(Calendar.YEAR);
		return yearOfLastMonth;
	}

	/**
	 * 判断输入日期是否是一天中的最大时刻
	 */
	public static boolean isMaxDayOfDay(Date date1) {
		if (date1 == null) {
			return false;
		}
		Date date2 = getMaxDateOfDay(date1);
		int secondNum = getBetweenSecondNumber(date1, date2);
		return secondNum == 0;
	}

	/**
	 * 判断输入日期是否是一天中的最大时刻
	 */
	public static boolean isMaxDayOfDay(Date date1, String precision) {
		if (date1 == null) {
			return false;
		}
		Date date2 = getMaxDateOfDay(date1);
		int diffNum = 0;
		if ("HH".equals(precision)) {
			diffNum = getBetweenHourNumber(date1, date2);
		} else if ("mm".equals(precision)) {
			diffNum = getBetweenMinuteNumber(date1, date2);
		} else {
			diffNum = getBetweenSecondNumber(date1, date2);
		}
		return diffNum == 0;
	}

	/**
	 * 判断输入日期是否是一月中的最大时刻
	 */
	public static boolean isMaxDayOfMonth(Date date1) {
		if (date1 == null) {
			return false;
		}
		Date date2 = getMaxDateOfMonth(date1);
		int secondNum = getBetweenSecondNumber(date1, date2);
		return secondNum == 0;
	}

	/**
	 * 判断输入日期是否是一天中的最小时刻
	 */
	public static boolean isMinDayOfDay(Date date1) {
		if (date1 == null) {
			return false;
		}
		Date date2 = getMinDateOfDay(date1);
		int secondNum = getBetweenSecondNumber(date1, date2);
		return secondNum == 0;
	}

	/**
	 * 判断输入日期是否是一天中的最小时刻
	 */
	public static boolean isMinDayOfDay(Date date1, String precision) {
		if (date1 == null) {
			return false;
		}
		Date date2 = getMinDateOfDay(date1);
		int diffNum = 0;
		if ("HH".equals(precision)) {
			diffNum = getBetweenHourNumber(date1, date2);
		} else if ("mm".equals(precision)) {
			diffNum = getBetweenMinuteNumber(date1, date2);
		} else {
			diffNum = getBetweenSecondNumber(date1, date2);
		}
		return diffNum == 0;
	}

	/**
	 * 判断输入日期是否是一月中的最小时刻
	 */
	public static boolean isMinDayOfMonth(Date date1) {
		if (date1 == null) {
			return false;
		}
		Date date2 = getMinDateOfMonth(date1);
		int secondNum = getBetweenSecondNumber(date1, date2);
		return secondNum == 0;
	}

	/**
	 * 输入日期是否为同一天.
	 */
	public static boolean isTheSameDay(Date startDate, Date endDate) {
		String startDateStr = dateToString(startDate,YYYY_MM_DD);
		String endDateStr = dateToString(endDate,YYYY_MM_DD);
		return startDateStr.equals(endDateStr);
	}


	/**
	 * 返回2个日期中的大者
	 */
	public static Date max(Date date1, Date date2) {
		if (date1 == null && date2 == null) {
			return null;
		}
		if (date1 == null) {
			return date2;
		}
		if (date2 == null) {
			return date1;
		}
		if (date1.after(date2)) {
			return date1;
		} else {
			return date2;
		}
	}

	/**
	 * 返回2个日期中的小者
	 */
	public static Date min(Date date1, Date date2) {
		if (date1 == null && date2 == null) {
			return null;
		}
		if (date1 == null) {
			return date2;
		}
		if (date2 == null) {
			return date1;
		}
		if (date1.after(date2)) {
			return date2;
		} else {
			return date1;
		}
	}

	/**
	 * 时间校验: 不能晚于上一个月(如果晚于上一个月，则替换为上一个月)
	 */
	public static Date notAfterLastMonth(Date myDate) {
		Date today = new Date();
		Date lastMonth = org.apache.commons.lang.time.DateUtils.addMonths(today, -1);
		lastMonth = getMaxDateOfMonth(lastMonth);
		// 3. 结束时间不能大于上一个月.
		if (myDate.after(lastMonth)) {
			myDate = lastMonth;
		}
		return myDate;
	}

	/**
	 * 时间校验: 不能晚于上一年(如果晚于上一年，则替换为上一年)
	 */
	public static Date notAfterLastYear(Date myDate) {
		Date today = new Date();
		Date lastYear = org.apache.commons.lang.time.DateUtils.addYears(today, -1);
		lastYear = getMaxDateOfYear(lastYear);
		// 3. 结束时间不能大于上一年.
		if (myDate.after(lastYear)) {
			myDate = lastYear;
		}
		return myDate;
	}

	/**
	 * 时间校验: 不能晚于当前时间(如果晚于当前时间，则替换为当前时间)
	 */
	public static Date notAfterNow(Date myDate) {
		Date today = new Date();
		if (myDate.after(today)) {
			myDate = today;
		}
		return myDate;
	}

	/**
	 * 时间校验: 不能晚于昨天(如果晚于昨天，则替换为昨天)
	 */
	public static Date notAfterYesterday(Date myDate) {
		Date today = new Date();
		Date yesterday = org.apache.commons.lang.time.DateUtils.addDays(today, -1);
		;
		// 3. 结束时间不能大于昨天.
		if (myDate.after(yesterday)) {
			myDate = yesterday;
		}
		return myDate;
	}

	/**
	 * 时间校验: myDate不能早于basicDate(如果早于basicDate，则替换为basicDate)
	 *
	 * @throws Exception
	 */
	public static Date notBefore(Date myDate, String basicStr) throws Exception {
		Date basicDate = stringToDate(basicStr,YYYY_MM_DD_HH_MM_SS_SSS);
		// Date today = new Date();
		// Date yesterday =
		// org.apache.commons.lang.time.DateUtils.addDays(today, -1);;
		// 3. 结束时间不能大于昨天.
		if (myDate.before(basicDate)) {
			myDate = basicDate;
		}
		return myDate;
	}

	/**
	 * 按天拆分时间
	 */
	public static List<Date> splitDateByDay(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) {
			return null;
		}

		List<Date> dateList = new ArrayList<Date>();
		dateList.add(startDate);

		int num = getBetweenDayNumber(startDate, endDate);
		for (int i = 1; i <= num; i++) {
			dateList.add(org.apache.commons.lang.time.DateUtils.addDays(startDate, i));
		}

		return dateList;
	}

	/**
	 * 按月拆分时间
	 */
	public static List<Date> splitDateByMonth(Date startDate, Date endDate) {
		List<Date> dateList = new ArrayList<Date>();

		if (startDate == null || endDate == null) {
			return dateList;
		}

		dateList.add(startDate);
		int num = getBetweenMonthNumber(startDate, endDate);
		for (int i = 1; i <= num; i++) {
			dateList.add(org.apache.commons.lang.time.DateUtils.addMonths(startDate, i));
		}

		return dateList;
	}

	/**
	 * 按年拆分时间
	 */
	public static List<Date> splitDateByYear(Date startDate, Date endDate) {
		List<Date> dateList = new ArrayList<Date>();

		if (startDate == null || endDate == null) {
			return dateList;
		}

		dateList.add(startDate);
		int num = getBetweenYearNumber(startDate, endDate);
		for (int i = 1; i <= num; i++) {
			dateList.add(org.apache.commons.lang.time.DateUtils.addYears(startDate, i));
		}

		return dateList;
	}

	/**
	 * 将字符串转化为日期(从一种格式到另一种格式)。
	 */
	public static String StringPatternToPattern(String str, String pattern1, String pattern2) {
		Date dateTime = null;
		String productStr = "";
		try {
			if (!(str == null || str.equals(""))) {
				SimpleDateFormat formater = new SimpleDateFormat(pattern1);
				dateTime = formater.parse(str);

				SimpleDateFormat formater1 = new SimpleDateFormat(pattern2);
				productStr = formater1.format(dateTime);
			}
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
		}
		return productStr;
	}

	/**
	 * 日期时间带时分秒的Timestamp表示
	 */
	public static Timestamp stringToDateHMS(String str) {
		Timestamp time = null;
		try {
			time = Timestamp.valueOf(str);
		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
		}
		return time;

	}


	/**
	 * 时间校验: 开始时间不能大于当前时间.
	 */
	public static Date validateStartDate(Date startDate) {
		Date today = new Date();
		// 开始时间不能大于当前时间.
		if (startDate.compareTo(today) == 1) {
			startDate = today;
		}
		return startDate;
	}

	/**
	 * 将传入的年月日转化为Date类型
	 */
	public static Date YmdToDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month, day);
		return calendar.getTime();
	}


	/**
	 * 前时间前n月
	 *
	 * @return
	 */
	public static String getForwardMonth(int month,String pattern) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -month);
		return dateToString(calendar.getTime(),pattern);
	}

	/**
	 * 当前时间前n天
	 *
	 * @return
	 */
	public static Date getForwardDay(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -day);
		return calendar.getTime();
	}

	/**
	 *
	 * <p>
	 * 判断是否是闰年<br>
	 * (这里描述这个方法适用条件 – 可选)<br>
	 * (这里描述这个方法的执行流程 – 可选)<br>
	 * (这里描述这个方法的使用方法 – 可选)<br>
	 * (这里描述这个方法的注意事项 – 可选)<br>
	 * </p>
	 */

	public static boolean chkIsLeapYear(Date date) {
		@SuppressWarnings("deprecation")
		int y = date.getYear();
		// 必须满足其中一个条件即为闰年：能被4整除且不能被100整除；能被400整除。
		boolean bFlag = (y % 4 == 0 && y % 100 != 0 || y % 400 == 0);
		return bFlag;
	}


	/**
	 * 根据周数，获取开始日期、结束日期
	 * @param week 周期 0本周，-1上周，-2上上周，1下周，2下下周
	 * @return 返回date[0]开始日期、date[1]结束日期
	 */
	public static Date[] getWeekStartAndEnd(int week) {
		DateTime dateTime = new DateTime();
		LocalDate date = new LocalDate(dateTime.plusWeeks(week));

		date = date.dayOfWeek().withMinimumValue();
		Date beginDate = date.toDate();
		Date endDate = date.plusDays(6).toDate();
		return new Date[] { beginDate, endDate };
	}

	/**
	 * 对日期的【秒】进行加/减
	 * @param date 日期
	 * @param seconds 秒数，负数为减
	 * @return 加/减几秒后的日期
	 */
	public static Date addSeconds(Date date, int seconds) {
		DateTime dateTime = new DateTime(date);
		return dateTime.plusSeconds(seconds).toDate();
	}

	/**
	 * 对日期的【分钟】进行加/减
	 *
	 * @param date  日期
	 * @param minutes 分钟数，负数为减
	 * @return 加/减几分钟后的日期
	 */
	public static Date addMinutes(Date date, int minutes) {
		DateTime dateTime = new DateTime(date);
		return dateTime.plusMinutes(minutes).toDate();
	}

	/**
	 * 对日期的【小时】进行加/减
	 * @param date 日期
	 * @param hours 小时数，负数为减
	 * @return 加/减几小时后的日期
	 */
	public static Date addHours(Date date, int hours) {
		DateTime dateTime = new DateTime(date);
		return dateTime.plusHours(hours).toDate();
	}

	/**
	 * 对日期的【天】进行加/减
	 * @param date  日期
	 * @param days 天数，负数为减
	 * @return 加/减几天后的日期
	 */
	public static Date addDays(Date date, int days) {
		DateTime dateTime = new DateTime(date);
		return dateTime.plusDays(days).toDate();
	}

	/**
	 * 对日期的【周】进行加/减
	 * @param date 日期
	 * @param weeks 周数，负数为减
	 * @return 加/减几周后的日期
	 */
	public static Date addWeeks(Date date, int weeks) {
		DateTime dateTime = new DateTime(date);
		return dateTime.plusWeeks(weeks).toDate();
	}

	/**
	 * 对日期的【月】进行加/减
	 * @param date 日期
	 * @param months 月数，负数为减
	 * @return 加/减几月后的日期
	 */
	public static Date addMonths(Date date, int months) {
		DateTime dateTime = new DateTime(date);
		return dateTime.plusMonths(months).toDate();
	}

	/**
	 * 对日期的【年】进行加/减
	 *
	 * @param date
	 *            日期
	 * @param years
	 *            年数，负数为减
	 * @return 加/减几年后的日期
	 */
	public static Date addYears(Date date, int years) {
		DateTime dateTime = new DateTime(date);
		return dateTime.plusYears(years).toDate();
	}

	/***
	 * 获取两个日期之间的日期  不包括start和end
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return 日期集合
	 */
	public static List<Date> getBetweenDates(Date start, Date end) {
	    List<Date> result = new ArrayList<Date>();
	    Calendar tempStart = Calendar.getInstance();
	    tempStart.setTime(start);
	    tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    
	    Calendar tempEnd = Calendar.getInstance();
	    tempEnd.setTime(end);
	    while (tempStart.before(tempEnd)) {
	        result.add(tempStart.getTime());
	        tempStart.add(Calendar.DAY_OF_YEAR, 1);
	    }
	    return result;
	}
	
	public static String formatDateByLong(Long millisecond, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date(millisecond);
		return sdf.format(date);

	}

}
