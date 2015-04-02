package money.tests;

import static org.junit.Assert.*;
import java.util.*;

import money.model.*;

import org.junit.Before;
import org.junit.Test;

public class BilancioFamiliareTest
{
	private BilancioFamiliare bilancio;
	private GregorianCalendar cal;

	@Before
	public void setUp() throws Exception
	{
		cal = new GregorianCalendar();

		ArrayList<Conto> conti = new ArrayList<Conto>();
		conti.add(new ContoCorrente("cc1", 1000));
		conti.add(new Liquidi("l1"));

		ArrayList<Movimento> movimenti = new ArrayList<Movimento>();
		movimenti.add(new Movimento("Causale1", 10, "esterno", "cc1", new Date()));
		movimenti.add(new Movimento("Causale2", 10, "esterno", "l1", new Date()));
		movimenti.add(new Movimento("Causale3", 10, "cc1", "l1", new Date()));

		bilancio = new BilancioFamiliare(conti, movimenti);
	}

	@Test
	public void testGetConti()
	{
		int c = 0;
		for (@SuppressWarnings("unused") Conto conto : bilancio.getConti())
			c++;
		assertEquals(3, c);
	}

	@Test
	public void testGetConto()
	{
		Conto c = bilancio.getConto("cc1");
		assertEquals("cc1", c.getName());

		c = bilancio.getConto("l1");
		assertEquals("l1", c.getName());

		c = bilancio.getConto("esterno");
		assertEquals("esterno", c.getName());

		assertNull(bilancio.getConto("AssenteGiustificato"));
	}

	@Test
	public void testInserisciMovimento()
	{
		cal.setTime(new Date());
		cal.set(GregorianCalendar.DAY_OF_MONTH, cal.get(GregorianCalendar.DAY_OF_MONTH) + 1);
		Date data = cal.getTime();
		Movimento m = new Movimento("Ponz!", 100, "cc1", "l1", data);
		bilancio.aggiungiMovimento(m);
		Conto c = bilancio.getConto("cc1");
		assertTrue(c.getMovimenti().contains(m));
		c = bilancio.getConto("l1");
		assertTrue(c.getMovimenti().contains(m));
		c = bilancio.getConto("esterno");
		assertFalse(c.getMovimenti().contains(m));
	}

	@Test
	public void testGetTotaleEntrate()
	{
		cal.setTime(new Date());
		cal.set(GregorianCalendar.DAY_OF_MONTH, cal.get(GregorianCalendar.DAY_OF_MONTH) + 1);
		Date data = cal.getTime();
		Movimento m = new Movimento("Ponz!", 100, "cc1", "l1", data);
		bilancio.aggiungiMovimento(m);
		Conto c = bilancio.getConto("cc1");
		assertEquals(10, c.getTotaleEntrate(data), 0.001);
		c = bilancio.getConto("l1");
		assertEquals(120, c.getTotaleEntrate(data), 0.001);
		assertEquals(20, bilancio.getTotaleEntrate(new Date(0), new Date()), 0.001);
		assertEquals(20, bilancio.getTotaleEntrate(new Date(0), data), 0.001);
	}

	@Test
	public void testGetTotaleUscite()
	{
		cal.setTime(new Date());
		cal.set(GregorianCalendar.DAY_OF_MONTH, cal.get(GregorianCalendar.DAY_OF_MONTH) + 1);
		Date data = cal.getTime();
		Movimento m = new Movimento("Ponz!", 100, "cc1", "l1", data);
		bilancio.aggiungiMovimento(m);
		Conto c = bilancio.getConto("cc1");
		assertEquals(110, c.getTotaleUscite(data), 0.001);
		c = bilancio.getConto("l1");
		assertEquals(0, c.getTotaleUscite(data), 0.001);
		assertEquals(0, bilancio.getTotaleUscite(new Date(0), data), 0.001);
		
		cal.set(GregorianCalendar.DAY_OF_MONTH, cal.get(GregorianCalendar.DAY_OF_MONTH) + 1);
		Date data1 = cal.getTime();
		m = new Movimento("Ponz!", 100, "l1", "esterno", data1);
		bilancio.aggiungiMovimento(m);
		assertEquals(100, bilancio.getTotaleUscite(new Date(0), data1), 0.001);
		assertEquals(0, bilancio.getTotaleUscite(new Date(0), data), 0.001);
	}

	@Test
	public void testGetSaldo()
	{
		cal.setTime(new Date());
		cal.set(GregorianCalendar.DAY_OF_MONTH, cal.get(GregorianCalendar.DAY_OF_MONTH) + 1);
		Date data = cal.getTime();
		Movimento m = new Movimento("Ponz!", 100, "cc1", "l1", data);
		bilancio.aggiungiMovimento(m);
		assertEquals(20, bilancio.getSaldo(new Date()), 0.001);
		assertEquals(20, bilancio.getSaldo(data), 0.001);
		
		cal.set(GregorianCalendar.DAY_OF_MONTH, cal.get(GregorianCalendar.DAY_OF_MONTH) + 1);
		Date data1 = cal.getTime();
		m = new Movimento("Ponz!", 100, "l1", "esterno", data1);
		bilancio.aggiungiMovimento(m);
		assertEquals(20, bilancio.getSaldo(data), 0.001);
		assertEquals(-80, bilancio.getSaldo(data1), 0.001);
	
	}

	@Test
	public void testGetListaMovimenti()
	{
		cal.setTime(new Date());
		cal.set(GregorianCalendar.DAY_OF_MONTH, cal.get(GregorianCalendar.DAY_OF_MONTH) + 1);
		Date data = cal.getTime();
		Movimento m = new Movimento("Ponz!", 100, "cc1", "l1", data);
		bilancio.aggiungiMovimento(m);
		assertFalse(bilancio.getMovimenti(new Date(0), new Date()).contains(m));
		assertEquals(1, Collections.frequency(bilancio.getMovimenti(), m));
		assertTrue(bilancio.getMovimenti(new Date(0), data).contains(m));
		
		cal.set(GregorianCalendar.DAY_OF_MONTH, cal.get(GregorianCalendar.DAY_OF_MONTH) + 1);
		Date data1 = cal.getTime();
		m = new Movimento("Ponz!", 100, "l1", "esterno", data1);
		bilancio.aggiungiMovimento(m);
		assertFalse(bilancio.getMovimenti(new Date(0), data).contains(m));
		assertEquals(1, Collections.frequency(bilancio.getMovimenti(), m));
		assertTrue(bilancio.getMovimenti(new Date(0), data1).contains(m));
	}

}
