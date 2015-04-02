package ztl.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import ztl.model.Direction;
import ztl.model.RestrictedArea;
import ztl.model.Ticket;
import ztl.model.Transit;
import ztl.persistence.MalformedFileException;

public class ConsoleTesterX6
{

	private static List<String> getAuthPlates()
	{
		String[] authPlates = new String[] { "GZ666RR", "ED999AP" };
		return Arrays.asList(authPlates);
	}
	
	private static List<Transit> getTransits()
	{
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(GregorianCalendar.MILLISECOND, 0);
		
		ArrayList<Transit> transits = new ArrayList<Transit>();
		
		cal.set(2012, GregorianCalendar.JANUARY, 10, 12, 34, 0);
		transits.add(new Transit("ViaNovelli", Direction.ENTRY, cal.getTime(), "ED999AP"));
		
		cal.set(2012, GregorianCalendar.JANUARY, 10, 14, 20, 0);
		transits.add(new Transit("VialeTimavo", Direction.EXIT, cal.getTime(), "ED999AP"));
		
		cal.set(2012, GregorianCalendar.JANUARY, 11, 20, 34, 0);
		transits.add(new Transit("LargoChigi", Direction.ENTRY, cal.getTime(), "MP333BO"));
		
		cal.set(2012, GregorianCalendar.JANUARY, 12, 6, 55, 0);
		transits.add(new Transit("ViaOmbrosa", Direction.EXIT, cal.getTime(), "MP333BO"));
		
		cal.set(2012, GregorianCalendar.JANUARY, 12, 21, 05, 0);
		transits.add(new Transit("LargoChigi", Direction.ENTRY, cal.getTime(), "MP333BO"));
		
		cal.set(2012, GregorianCalendar.JANUARY, 13, 7, 35, 0);
		transits.add(new Transit("ViaOmbrosa", Direction.EXIT, cal.getTime(), "MP333BO"));
		
		cal.set(2012, GregorianCalendar.JANUARY, 13, 20, 04, 0);
		transits.add(new Transit("ViaNovelli", Direction.ENTRY, cal.getTime(), "GZ666RR"));
		
		cal.set(2012, GregorianCalendar.JANUARY, 16, 6, 56, 0);
		transits.add(new Transit("VialeSerio", Direction.EXIT, cal.getTime(), "GZ666RR"));
		
		return transits;
	}
	
	public static void main(String[] args) throws IOException, MalformedFileException
	{
		RestrictedArea restrictedArea = new RestrictedArea(getAuthPlates());
		
		for (Transit transit : getTransits())
		{
			Ticket t = restrictedArea.manageTransit(transit);
			if (t != null)
			{
				System.out.println(t);
			}
		}
	}

}
