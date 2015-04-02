package weather.ui;

import java.util.Date;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

public class DateSpinner extends JSpinner {

	

	private static final long serialVersionUID = 1L;

	public DateSpinner()
	{
		super(new SpinnerDateModel());
		JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(this, "E dd/MM/yyyy");
		setEditor(dateEditor);
		
	}
	
	public Date getDateValue()
	{
		return (Date) getValue();
		
	}
	

}
