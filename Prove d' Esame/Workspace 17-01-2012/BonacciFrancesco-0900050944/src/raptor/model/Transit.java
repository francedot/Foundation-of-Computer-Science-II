package raptor.model;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Transit
{
	private String sectionName;
	private Gate gate;
	private Date date;
	private String plate;
	
	public Transit(String section, Gate gate, Date date, String plate)
	{
		this.sectionName = section;
		this.gate = gate;
		this.date = date;
		this.plate = plate;
	}
	
	public String getSectionName()
	{
		return sectionName;
	}
	
	public Gate getGate()
	{
		return gate;
	}

	public Date getDate()
	{
		return date;
	}

	public String getPlate()
	{
		return plate;
	}
	
	@Override
	public String toString()
	{
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM, Locale.ITALY);
		return sectionName + "(" + gate + ") - " + dateFormat.format(date) + " - " + plate;
	}
}
