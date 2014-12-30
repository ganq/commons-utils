package com.mysoft.b2b.commons.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期帮助类
 */
public class DateHelper {
	// 默认显示日期的格式
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String TIME_FORMAT = "HH:mm:ss";

	/**
	 * 功能概述：计算指定年月的周数
	 */
	public static int getWeekOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getActualMaximum(Calendar.WEEK_OF_MONTH);
	}

	/**
	 * 获取月的天数
	 */
	public static int getDaysOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取当前时间为周几
	 */
	public static int getDayOfWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 在指定日期之后剩余的日期
	 */
	public static Date[] getDatesOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int begin = c.get(Calendar.DAY_OF_MONTH);
		int end = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		Date[] dates = new Date[end - begin + 1];
		int index = 0;
		while (begin <= end) {
			dates[index++] = c.getTime();
			c.add(Calendar.DATE, 1);
			begin++;
		}
		return dates;
	}

	/**
	 * 在指定日期之后剩余的周
	 */
	public static int[] getWeeksOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int begin = c.get(Calendar.WEEK_OF_MONTH);
		int end = c.getActualMaximum(Calendar.WEEK_OF_MONTH);
		int[] weeks = new int[end - begin + 1];
		int index = 0;
		while (begin <= end) {
			weeks[index++] = begin;
			begin++;
		}
		return weeks;
	}

	/**
	 * 在指定日期之间的周数
	 */
	public static int[] getWeeksOfMonth(Date beginDate, Date endDate) {
		Calendar c = Calendar.getInstance();
		c.setTime(beginDate);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(endDate);
		int begin = c.get(Calendar.WEEK_OF_MONTH);
		int end = c2.get(Calendar.WEEK_OF_MONTH);
		// 处理最后一天为周日
		if (c2.get(Calendar.DAY_OF_WEEK) == 1)
			end--;
		int[] weeks = new int[end - begin + 1];
		int index = 0;
		while (begin <= end) {
			weeks[index++] = begin;
			begin++;
		}
		return weeks;
	}

	/**
	 * 获取date1 、 date2日期之间不同月份的日期数组
	 */
	public static Date[] getMonthsOfDate(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int begin = cal1.get(Calendar.MONTH);
		int end = cal2.get(Calendar.MONTH);
		Date[] dates = new Date[end - begin + 1];
		int index = 0;
		while (begin <= end) {
			dates[index++] = cal1.getTime();
			cal1.add(Calendar.MONTH, 1);
			if (1 == index)
				cal1.set(Calendar.DATE, 1);
			begin++;
		}
		return dates;
	}

	/**
	 * 获取begin月到月末日期或者到end日期
	 * 
	 * @param begin
	 * @return
	 */
	public static Date[] getBeginToMonthEnd(Date begin, Date end, int count) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(begin);
		cal1.add(Calendar.MONTH, count);
		if (0 != count)
			cal1.set(Calendar.DAY_OF_MONTH, 1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(end);
		// 判定是否为同一个月
		int beginDate = cal1.get(Calendar.MONTH);
		int endDate = cal2.get(Calendar.MONTH);
		if (beginDate != endDate) {
			return getDatesOfMonth(cal1.getTime());
		} else {
			return getBetweenDatesOfMonth(cal1.getTime(), end);
		}
	}

	/**
	 * 当前月在begin和end之间的第几个月
	 */
	public static int getIndexBeginToEnd(Date begin, Date end) {
		if (begin.after(end))
			return -1;
		// 当前
		Calendar cal = Calendar.getInstance(Locale.getDefault());
		// 开始
		Calendar calBegin = Calendar.getInstance(Locale.getDefault());
		calBegin.setTime(begin);
		if (cal.before(calBegin))
			return 1;
		if (cal.get(Calendar.MONTH) == calBegin.get(Calendar.MONTH))
			return 1;
		// 结束
		Calendar calEnd = Calendar.getInstance(Locale.getDefault());
		calEnd.setTime(end);
		if (cal.after(calEnd))
			return crossMonth(begin, end);
		return cal.get(Calendar.MONTH) - calBegin.get(Calendar.MONTH) + 1;
	}

	/**
	 * date 在begin和end之间的第几个周
	 */
	public static int[] getIndexBeginToEndOfWeek(Date date, Date begin, Date end) {
		// date不在begin和end之间则返回0
		if (begin.after(end) || date.before(begin) || date.after(end))
			return new int[] { 0, 0 };
		// date日期
		Calendar cal = Calendar.getInstance(Locale.getDefault());
		// 开始日期
		Calendar calBegin = Calendar.getInstance(Locale.getDefault());
		calBegin.setTime(begin);
		// 结束日期
		Calendar calEnd = Calendar.getInstance(Locale.getDefault());
		calEnd.setTime(end);
		return new int[] {
				cal.get(Calendar.WEEK_OF_YEAR)
						- calBegin.get(Calendar.WEEK_OF_YEAR) + 1,
				calEnd.get(Calendar.WEEK_OF_YEAR)
						- calBegin.get(Calendar.WEEK_OF_YEAR) + 1 };
	}

	/**
	 * date 在begin和end之间的第几个周(跨年份时，结束日期的所在周数需加上52周数*相差年数)
	 */
	public static int[] getIndexBeginToEndOfWeek_checkYear(Date date,
			Date begin, Date end) {
		// date不在begin和end之间则返回0
		if (begin.after(end) || date.before(begin) || date.after(end))
			return new int[] { 0, 0 };
		// date日期
		Calendar cal = Calendar.getInstance(Locale.getDefault());
		// 开始日期
		Calendar calBegin = Calendar.getInstance(Locale.getDefault());
		calBegin.setTime(begin);
		// 结束日期
		Calendar calEnd = Calendar.getInstance(Locale.getDefault());
		calEnd.setTime(end);
		int yearBegin = calBegin.get(Calendar.YEAR); // 开始年份
		int yearEnd = calEnd.get(Calendar.YEAR); // 结束年份

		int initData = calEnd.get(Calendar.WEEK_OF_YEAR)
				- calBegin.get(Calendar.WEEK_OF_YEAR) + 1;
		int endData = 0;
		if (yearEnd > yearBegin) // 考虑结束大于开始年份或者同一年份中，加上52周数*相差年数
		{
			endData = 52 - calBegin.get(Calendar.WEEK_OF_YEAR) + 1
					+ (yearEnd - yearBegin) * 52;
		} else if (initData < 0)// 结果为负数，加上52周数
		{
			endData = 52 - calBegin.get(Calendar.WEEK_OF_YEAR) + 1;
		} else {
			endData = initData;
		}
		return new int[] {
				cal.get(Calendar.WEEK_OF_YEAR)
						- calBegin.get(Calendar.WEEK_OF_YEAR) + 1, endData };
	}

	/**
	 * date 在begin和end之间的第几个周
	 */
	public static int[] getIndexBeginToEndOfWeek(Date begin, Date end, int index) {
		// date不在begin和end之间则返回0
		if (begin.after(end))
			return new int[] { 0, 0 };
		Calendar calBegin = Calendar.getInstance(Locale.getDefault());
		calBegin.setTime(begin);
		// 结束日期
		Calendar calEnd = Calendar.getInstance(Locale.getDefault());
		calEnd.setTime(end);
		// date日期
		Calendar cal = Calendar.getInstance(Locale.getDefault());
		cal.setTime(calBegin.getTime());
		cal.add(Calendar.WEEK_OF_YEAR, index);
		return new int[] {
				cal.get(Calendar.WEEK_OF_YEAR)
						- calBegin.get(Calendar.WEEK_OF_YEAR) + 1,
				calEnd.get(Calendar.WEEK_OF_YEAR)
						- calBegin.get(Calendar.WEEK_OF_YEAR) + 1 };
	}

	/**
	 * 获取月底日期 但不能超过limit
	 * 
	 * @param limit
	 */
	public static Date getDateOfMonthEnd(Date date, Date limit) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
				- cal.get(Calendar.DAY_OF_MONTH);
		cal.add(Calendar.DAY_OF_MONTH, count);
		// 如果月末在limit之后则返回limit
		if (cal.getTime().after(limit))
			return limit;
		return cal.getTime();
	}

	/**
	 * 获取指定周的日期
	 */
	public static Date[] getDatesOfWeeks(Date date, int week) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.WEEK_OF_MONTH, week);
		if (1 != week)
			c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		int index = 0;
		int days = c.get(Calendar.DAY_OF_WEEK) - 1;
		Date[] dates = new Date[8 - days];
		while (days < 8) {
			dates[index++] = c.getTime();
			c.add(Calendar.DAY_OF_MONTH, 1);
			days++;
		}
		return dates;
	}

	/**
	 * 
	 * @param mouth
	 * @param day
	 * @param point
	 * @param minute
	 * @return
	 */
	public static Date getAgoTime(int mouth, int day, int point, int minute){
	    if(mouth>12 || day > 31){
	        throw new IllegalArgumentException("DateHelper.getAgoTime参数错误", null);
	    }
        Date date = new Date();
        Long mouthTime = 2592000000L*mouth;
        Long dayTime = 86400000L*day;
        Long pointTime = 3600000L*point;
        Long minuteTime = 60000L*minute;
        date.setTime(date.getTime()-mouthTime-dayTime-pointTime-minuteTime);
        return date;
    }
	
	/**
	 * 获取date月 指定周的日期
	 */
	public static Date[] getDatesOfWeeks(Date date, Date limit) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int days = c.get(Calendar.DAY_OF_WEEK) - 1;
		// 星期天则处理
		if (0 == days)
			days = 7;
		int month = c.get(Calendar.MONTH);
		boolean flag = true;
		List<Date> lstDates = new ArrayList<Date>();
		while (flag && days < 8) {
			lstDates.add(c.getTime());
			c.add(Calendar.DAY_OF_MONTH, 1);
			if (month != c.get(Calendar.MONTH) || limit.before(c.getTime()))
				flag = false;
			days++;
		}
		int index = 0;
		Date[] dates = new Date[lstDates.size()];
		for (Date d : lstDates) {
			dates[index++] = d;
		}
		return dates;
	}

	/**
	 * 获取当前第begin天到begin+count天的日期
	 * 
	 * @param count
	 * @return
	 */
	public static Timestamp[] getCurDateRange(int begin, int count) {
		Timestamp[] times = new Timestamp[count == 1 ? 2 : count];
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_YEAR, -(begin - 1) * count);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		times[0] = new Timestamp(c.getTimeInMillis());
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		if (count > 1) {
			for (int i = 1; i < count; i++) {
				c.add(Calendar.DAY_OF_YEAR, -1);
				times[i] = new Timestamp(c.getTimeInMillis());
			}
		} else {
			c.add(Calendar.DAY_OF_YEAR, -1);
			times[1] = new Timestamp(c.getTimeInMillis());
		}

		return times;
	}

	/**
	 * 获取两日期之间的日期数组(包含begin、end)
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static Date[] getBetweenDatesOfMonth(Date begin, Date end) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(begin);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(end);
		int begins = cal1.get(Calendar.DAY_OF_MONTH);
		int ends = cal2.get(Calendar.DAY_OF_MONTH);
		Date[] dates = new Date[ends - begins + 1];
		for (int i = 0; i < dates.length; i++) {
			dates[i] = cal1.getTime();
			cal1.add(Calendar.DAY_OF_MONTH, 1);
		}
		return dates;
	}

	/**
	 * 获取两日期之间的日期数组(包含begin、end)
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static Date[] getBetweenDates(Date begin, Date end) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(begin);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(end);
		int begins = cal1.get(Calendar.DAY_OF_YEAR);
		int ends = cal2.get(Calendar.DAY_OF_YEAR);
		Date[] dates = new Date[ends - begins + 1];
		for (int i = 0; i < dates.length; i++) {
			dates[i] = cal1.getTime();
			cal1.add(Calendar.DAY_OF_YEAR, 1);
		}
		return dates;
	}

	/**
	 * 将默认字符串格式转日期
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date stringToDate(String date) throws ParseException{
		return stringToDate(DATE_FORMAT, date);
	}

	/**
	 * 通过指定格式将字符串转日期格式
	 * 
	 * @param format
	 * @param date
	 * @return
	 */
	public static Date stringToDate(String format, String date) {
		return DateUtils.getDate(date, format);
	}

	/**
	 * 根据type获取开始结束日期
	 * 
	 * @param date
	 *            指定当前日期时间 end 为最后日期限制
	 * @param type
	 *            0：begin end一致 1：一周的开始结束日期 2：一个月的开始结束日期
	 * @return
	 */
	public static Date[] beginAndEnd(Date date, Date limit, String type)
			throws ParseException {
		// 是否date在end之后
		if (date.after(limit))
			return null;
		Date[] dates = new Date[2];
		dates[0] = nextWorkDate(date);
		// 是否date在end之后
		if (dates[0].after(limit))
			return null;
		if ("0".equals(type)) {// 开始结束同一天
			dates[1] = dates[0];
		} else if ("1".equals(type)) {// 开始结束一周时间
			Calendar c = Calendar.getInstance();
			c.setTime(dates[0]);
			int count = 7 - getDayOfWeek(c.getTime());
			c.add(Calendar.DAY_OF_MONTH, count);
			if (c.getTime().after(limit))
				dates[1] = limit;
			else
				dates[1] = c.getTime();
		}
		return dates;
	}

	/**
	 * 获取date下一个工作日
	 * 
	 * @param date
	 */
	public static Date nextWorkDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		// 周六
		if (7 == cal.get(Calendar.DAY_OF_WEEK)) {
			cal.add(Calendar.DAY_OF_MONTH, 2);
		}
		// 周日
		if (1 == cal.get(Calendar.DAY_OF_WEEK)) {
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		return cal.getTime();
	}

	/**
	 * 获取date下一个工作日
	 * 
	 * @param date
	 */
	public static Date nextDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 得到指定时间的前/后若干天的时间
	 * 
	 * @param days
	 *            - 间隔天数
	 * @return Date - 返回时间
	 */
	public static Date addDaysToTheDate(Date theDate, int days) {
		Calendar now = Calendar.getInstance(TimeZone.getDefault());
		now.setTime(theDate);
		now.add(Calendar.DATE, days);
		return now.getTime();
	}

	/**
	 * 跨越月数
	 * 
	 * @param begin
	 * @param end
	 */
	public static int crossMonth(Date begin, Date end) {
		// 开始时间大于结束时间返回0
		if (begin.after(end))
			return 0;
		Calendar cal1 = Calendar.getInstance(TimeZone.getDefault());
		cal1.setTime(begin);
		Calendar cal2 = Calendar.getInstance(TimeZone.getDefault());
		cal2.setTime(end);
		// 不同年份处理
		if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
			return cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH) + 1;
		} else {
			return (cal2.get(Calendar.MONTH) + (cal2.get(Calendar.YEAR) - cal1
					.get(Calendar.YEAR)) * 12)
					- cal1.get(Calendar.MONTH) + 1;
		}
	}

	/**
	 * 跨越月数
	 * 
	 * @param begin
	 * @param end
	 */
	public static int crossMonth(String begin, String end){
		return crossMonth(DateUtils.getDate(begin, DATE_FORMAT), DateUtils
				.getDate(end, DATE_FORMAT));
	}

	/**
	 * date本周 周一到 date日期
	 * 
	 * @return
	 */
	public static Date[] getBeginAndEndOfWeek(Date date) {
		Calendar cal = Calendar.getInstance(Locale.getDefault());
		cal.setTime(date);
		Date[] dates = new Date[2];
		dates[1] = cal.getTime();
		int count = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == count) {
			cal.add(Calendar.DAY_OF_YEAR, -6);
		} else {
			cal.add(Calendar.DAY_OF_YEAR, -(count - 2));
		}
		dates[0] = cal.getTime();
		return dates;
	}

	/**
	 * date本周 周一到 周日日期
	 * 
	 * @return
	 */
	public static Date[] getBeginAndEndOfWeeks(Date date) {
		Calendar cal = Calendar.getInstance(Locale.getDefault());
		cal.setTime(date);
		Date[] dates = new Date[7];
		int count = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == count) {
			cal.add(Calendar.DAY_OF_YEAR, -6);
		} else {
			cal.add(Calendar.DAY_OF_YEAR, -(count - 2));
		}
		count = 0;
		while (count < 7) {
			dates[count] = cal.getTime();
			// 日期往后推一天
			cal.add(Calendar.DAY_OF_YEAR, 1);
			count++;
		}
		return dates;
	}

	/**
	 * date本周 周一到 周日日期
	 * 
	 * @return
	 */
	public static Long[] getBeginAndEndOfWeekTimes(Date date) {
		Calendar cal = Calendar.getInstance(Locale.getDefault());
		cal.setTime(date);
		Long[] dates = new Long[7];
		int count = cal.get(Calendar.DAY_OF_WEEK);
		if (1 == count) {
			cal.add(Calendar.DAY_OF_YEAR, -6);
		} else {
			cal.add(Calendar.DAY_OF_YEAR, -(count - 2));
		}
		count = 0;
		while (count < 7) {
			dates[count] = cal.getTime().getTime();
			// 日期往后推一天
			cal.add(Calendar.DAY_OF_YEAR, 1);
			count++;
		}
		return dates;
	}

	/**
	 * date 的第index周之后的周一到周日的日期
	 */
	public static Date[] getBeginAndEndOfWeekAfter(Date date, int index) {
		Calendar cal = Calendar.getInstance(Locale.getDefault());
		cal.setTime(date);
		cal.add(Calendar.WEEK_OF_YEAR, index);
		return getBeginAndEndOfWeeks(cal.getTime());
	}

	/**
	 * 获取月底日期
	 */
	public static Date getDateOfMonthEnd(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
				- cal.get(Calendar.DAY_OF_MONTH);
		cal.add(Calendar.DAY_OF_MONTH, count);
		return cal.getTime();
	}

	public static HashMap<String, Date> getDayCoincidence(Date begindate1,
			Date enddate1, Date begindate2, Date enddate2) {
		long b1 = begindate1.getTime();
		long e1 = enddate1.getTime();
		long b2 = begindate2.getTime();
		long e2 = enddate2.getTime();
		HashMap<String, Date> map = new HashMap<String, Date>();
		if (b1 <= b2 && e1 >= e2) {// （b1---【b2-----e2】--e1）
			map.put("start", begindate2);
			map.put("end", enddate2);
		} else if (b1 >= b2 && e1 <= e2) {// 【b2---（b1-----e1）--e2】
			map.put("start", begindate1);
			map.put("end", enddate1);
		} else if (b1 >= b2 && b1 <= e2 && e2 <= e1) {// 【b2---(b1---e2】----e1)
			map.put("start", begindate1);
			map.put("end", enddate2);
		} else if (b1 <= b2 && e1 <= e2 && e1 >= b2) {// （b1---【b2---e1）----e2】
			map.put("start", begindate2);
			map.put("end", enddate1);
		} else if (e1 <= b2 ) {
			map.put("start", null);
			map.put("end", null);
		} else if (b1 >= e2) {
			map.put("start", null);
			map.put("end", null);
		} else {
			map.put("start", null);
			map.put("end", null);
		}
		return map;
	}

	public static void main(String[] args) throws ParseException {
		Date date1 = DateUtils.getDate("2013-04-30", DateHelper.DATE_FORMAT);
		Date date2 = DateUtils.getDate("2013-05-02", DateHelper.DATE_FORMAT);
		Date date3 = DateUtils.getDate("2013-07-30", DateHelper.DATE_FORMAT);
		Date date4 = DateUtils.getDate("2014-07-30", DateHelper.DATE_FORMAT);

		HashMap<String,Date> map = DateHelper.getDayCoincidence(date1, date2,date3,date4);
		System.out.println("start="+DateUtils.dateToDateString(map.get("start"), DateUtils.DATE_FORMAT));
		System.out.println("end="+DateUtils.dateToDateString(map.get("end"), DateUtils.DATE_FORMAT));
		date1 = DateUtils.getDate("2014-04-30", DateUtils.DATE_FORMAT);
		date2 = DateUtils.getDate("2014-05-02", DateUtils.DATE_FORMAT);
		
		map = DateHelper.getDayCoincidence(date1, date2,date3,date4);
		System.out.println("start="+DateUtils.dateToDateString(map.get("start"), DateUtils.DATE_FORMAT));
		System.out.println("end="+DateUtils.dateToDateString(map.get("end"), DateUtils.DATE_FORMAT));
	}
}
