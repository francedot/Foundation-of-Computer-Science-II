package ztl.model;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Transit
{
	private String gateName;
	private Direction direction;
	private Date date;
	private String plate;
	
	public Transit(String gateName, Direction direction, Date date, String plate)
	{
		this.gateName = gateName;
		this.direction = direction;
		this.date = date;
		this.plate = plate;
	}
	
	public String getGateName()
	{
		return gateName;
	}
	
	public Direction getDirection()
	{
		return direction;
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
		return gateName + "(" + direction + ") - " + dateFormat.format(date) + " - " + plate;
	}
}
