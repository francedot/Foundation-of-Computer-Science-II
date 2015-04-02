package money.model;

import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils
{
	public static Date getMonthStart(Date date)
	{
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(GregorianCalendar.DAY_OF_MONTH, 1);
		cal.set(GregorianCalendar.HOUR_OF_DAY, 0);
		cal.set(GregorianCalendar.MINUTE, 0);
		cal.set(GregorianCalendar.SECOND, 0);
		cal.set(GregorianCalendar.MILLISECOND, 0);
		Date monthStart = cal.getTime();
		return monthStart;
	}
}
