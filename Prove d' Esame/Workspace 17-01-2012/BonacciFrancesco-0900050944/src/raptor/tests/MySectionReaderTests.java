package raptor.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.Test;

import raptor.model.Section;
import raptor.persistence.MalformedFileException;
import raptor.persistence.MySectionReader;

public class MySectionReaderTests
{

	@Test
	public void testReadSections() throws IOException, MalformedFileException
	{
		String v = "A14-BO1-N 5 130\nA14-BO1-S 6.5 110\n";
		StringReader reader = new StringReader(v);
		
		MySectionReader sr = new MySectionReader();
		
		DummySpeedingTicketCollector collector = new DummySpeedingTicketCollector();
		List<Section> sections = sr.readSections(collector, reader);
		
		assertEquals(2, sections.size());
		
		Section s = sections.get(0);
		assertEquals("A14-BO1-N", s.getName());
		assertEquals(5, s.getLength(), 0);
		assertEquals(130, s.getSpeedLimit(), 0);
		
		s = sections.get(1);
		assertEquals("A14-BO1-S", s.getName());
		assertEquals(6.5, s.getLength(), 0);
		assertEquals(110, s.getSpeedLimit(), 0);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadSections_Errors1() throws IOException, MalformedFileException
	{
		String v = "A14-BO1-N 5x 130\nA14-BO1-S 6.5 110\n";
		StringReader reader = new StringReader(v);
		
		MySectionReader sr = new MySectionReader();
		
		DummySpeedingTicketCollector collector = new DummySpeedingTicketCollector();
		sr.readSections(collector, reader);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadSections_Errors2() throws IOException, MalformedFileException
	{
		String v = "A14-BO1-N 5 1x30\nA14-BO1-S 6.5 110\n";
		StringReader reader = new StringReader(v);
		
		MySectionReader sr = new MySectionReader();
		
		DummySpeedingTicketCollector collector = new DummySpeedingTicketCollector();
		sr.readSections(collector, reader);
	}
	
	@Test(expected = MalformedFileException.class)
	public void testReadSections_Errors3() throws IOException, MalformedFileException
	{
		String v = "A14-BO1-N 5 130\nA14-BO1-S 6.5110\n";
		StringReader reader = new StringReader(v);
		
		MySectionReader sr = new MySectionReader();
		
		DummySpeedingTicketCollector collector = new DummySpeedingTicketCollector();
		sr.readSections(collector, reader);
	}

}
