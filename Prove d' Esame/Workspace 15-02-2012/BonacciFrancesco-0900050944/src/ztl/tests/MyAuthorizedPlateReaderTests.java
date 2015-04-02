package ztl.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.junit.Test;

import ztl.persistence.MalformedFileException;
import ztl.persistence.MyAuthorizedPlateReader;

public class MyAuthorizedPlateReaderTests
{

	@Test
	public void testRead() throws IOException, MalformedFileException
	{
		String v = "AS123PZ\nQW321ZX\n";
		StringReader reader = new StringReader(v);
		
		MyAuthorizedPlateReader sr = new MyAuthorizedPlateReader();
		List<String> plates = sr.read(reader);
		
		assertEquals(2, plates.size());
		
		String plate = plates.get(0);
		assertEquals("AS123PZ", plate);
		
		plate = plates.get(1);
		assertEquals("QW321ZX", plate);
	}

}
