package money.ui;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

public class MyDateSpinner extends JSpinner
{
	private GregorianCalendar calendar;
	
	private static final long serialVersionUID = 1L;

	public MyDateSpinner()
	{
		super(new SpinnerDateModel());
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(this, "E dd/MM/yyyy");
		setEditor(dateEditor);
		calendar = new GregorianCalendar();
	}

	public Date getDateValue()
	{
		calendar.setTime((Date) getValue());
		calendar.set(GregorianCalendar.HOUR_OF_DAY, 0);
		calendar.set(GregorianCalendar.MINUTE, 0);
		calendar.set(GregorianCalendar.SECOND, 0);
		calendar.set(GregorianCalendar.MILLISECOND, 0);
		return calendar.getTime();
	}

}
