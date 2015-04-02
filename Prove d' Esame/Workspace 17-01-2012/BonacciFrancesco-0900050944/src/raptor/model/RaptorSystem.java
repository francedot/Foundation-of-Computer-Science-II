package raptor.model;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import raptor.persistence.*;

public class RaptorSystem implements SpeedingTicketCollector
{
	private Map<String, Section> sectionMap = new HashMap<String, Section>();

	private ArrayList<SpeedingTicket> speedingTickets = new ArrayList<SpeedingTicket>();
	private SectionReader sectionReader;

	public RaptorSystem(SectionReader sectionReader)
	{
		this.sectionReader = sectionReader;
	}

	public void loadData() throws IOException, MalformedFileException
	{
		Reader reader = null;
		try
		{
			reader = new FileReader("sections.txt");
			List<Section> sectionList = sectionReader.readSections(this, reader);
			for (Section section : sectionList)
			{
				sectionMap.put(section.getName(), section);
			}
			reader.close();
		}
		finally
		{
			if (reader != null)
			{
				reader.close();
			}
		}
	}
	
	public Iterable<Section> getSections()
	{
		return sectionMap.values();
	}
	
	public void putTransit(Transit transit)
	{
		Section section = sectionMap.get(transit.getSectionName());
		section.putTransit(transit);
	}

	@Override
	public void add(SpeedingTicket speedingTicket)
	{
		speedingTickets.add(speedingTicket);
	}

	public Iterable<SpeedingTicket> getSpeedingTickets()
	{
		return speedingTickets;
	}

	public void clearSpeedingTickets()
	{
		speedingTickets.clear();
	}
}
