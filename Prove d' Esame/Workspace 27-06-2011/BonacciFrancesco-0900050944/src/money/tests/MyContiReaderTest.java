package money.tests;

import static org.junit.Assert.*;

import java.io.*;
import java.util.Collection;

import money.model.*;
import money.persistence.InvalidDataException;
import money.persistence.MyContiReader;

import org.junit.Test;

public class MyContiReaderTest
{
	MyContiReader reader = null;

	@Test
	public void testReadAll() throws IOException, InvalidDataException
	{
		String dati = "#define ccTopolino \t\t contocorrente 2000\n" + "#define\tvisaPaperino\t\t cartadicredito \t 1500\n"
				+ "#define \t  borsaMinnie	 liquidi";

		reader = new MyContiReader();
			Collection<Conto> conti = reader.readAll(new StringReader(dati));
			Conto[] cc = conti.toArray(new Conto[0]);
			assertEquals(3, cc.length);
			assertEquals("ccTopolino", cc[0].getName());
			assertEquals("visaPaperino", cc[1].getName());
			assertEquals("borsaMinnie", cc[2].getName());
			assertTrue(cc[0] instanceof ContoCorrente);
			ContoCorrente contoCorrente = (ContoCorrente) cc[0];
			assertEquals(2000, contoCorrente.getMassimoScoperto(), 0.001);
			assertTrue(cc[1] instanceof CartaCredito);
			CartaCredito ccr = (CartaCredito) cc[1];
			assertEquals(1500, ccr.getMassimaSpesa(), 0.001);
			assertTrue(cc[2] instanceof Liquidi);
	}

	public void readThis(String dati) throws IOException, InvalidDataException
	{
		reader = new MyContiReader();
		reader.readAll(new StringReader(dati));	
	}


	@Test(expected=InvalidDataException.class)
	public void testReadAllWrongFile1() throws IOException, InvalidDataException
	{
		readThis("#define ccTopolino	contocorrente 2AV0\n" + "#define visaPaperino		cartadicredito 1500\n"
				+ "#define   borsaMinnie	 liquidi");
	}
	
	@Test(expected=InvalidDataException.class)
	public void testReadAllWrongFile2() throws IOException, InvalidDataException
	{
		readThis("#define ccTopolino	contocorrente 2000\n" + "#define visaPaperino		cartacredito 1500\n"
				+ "#define   borsaMinnie	 liquidi");
	}

	
	@Test(expected=InvalidDataException.class)
	public void testReadAllWrongFile3() throws IOException, InvalidDataException
	{
		readThis("#define ccTopolino	conto-corrente 2000\n" + "#define visaPaperino cartadicredito 1500\n"
				+ "#define borsaMinnie liquidi");
	}

	
	@Test(expected=InvalidDataException.class)
	public void testReadAllWrongFile4() throws IOException, InvalidDataException
	{
		readThis("#define ccTopolino	contocorrente 2000\n" + "#define visaPaperino cartadicredito 1500\n"
				+ "#define borsaMinnie liquido");
	}

}
