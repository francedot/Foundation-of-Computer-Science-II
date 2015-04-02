package money.tests;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import money.model.Movimento;
import money.persistence.InvalidDataException;
import money.persistence.MyMovimentiManager;

import org.junit.Test;
import static org.junit.Assert.*;

public class MyMovimentiManagerTest
{

	@Test
	public void testLeggiMovimenti() throws IOException, InvalidDataException
	{
		MyMovimentiManager manager = new MyMovimentiManager();
		ArrayList<Movimento> lista1 = new ArrayList<Movimento>();
		GregorianCalendar cal = new GregorianCalendar();
		cal.set(2011, Calendar.JUNE, 10);
		Date data = cal.getTime();
		Movimento m = new Movimento("Gestione Conto", 8.50, "ccGabriele", "esterno", data);
		lista1.add(m);
		m = new Movimento("Rimborso soldi prestati", 20, "esterno", "portafogliED", data);
		lista1.add(m);

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		manager.scriviMovimenti(lista1, os);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(os.toByteArray());
		Collection<Movimento> lista2 = manager.leggiMovimenti(inputStream);

		assertNotNull(lista2);
		Movimento[] array1 = lista1.toArray(new Movimento[0]);
		Movimento[] array2 = lista2.toArray(new Movimento[0]);
		assertArrayEquals(array1, array2);
	}

}
