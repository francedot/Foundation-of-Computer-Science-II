package ztl.ui;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import ztl.model.RestrictedArea;
import ztl.model.Ticket;
import ztl.model.Transit;
import ztl.persistence.AuthorizedPlateReader;
import ztl.persistence.MalformedFileException;
import ztl.persistence.MyAuthorizedPlateReader;
import ztl.persistence.MyTransitReader;
import ztl.persistence.TransitReader;

public class ConsoleTester
{

	/**
	 * @param args
	 * @throws MalformedFileException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, MalformedFileException
	{
		AuthorizedPlateReader plateReader = new MyAuthorizedPlateReader();
		RestrictedArea restrictedArea = new RestrictedArea(plateReader.read(new FileReader("plates.txt")));
		
		TransitReader transitReader = new MyTransitReader();
		Reader reader = new FileReader("transits.txt");
		List<Transit> transits = transitReader.read(reader);
		reader.close();
		
		for (Transit transit : transits)
		{
			Ticket t = restrictedArea.manageTransit(transit);
			if (t != null)
			{
				System.out.println(t);
			}
		}
	}

}
