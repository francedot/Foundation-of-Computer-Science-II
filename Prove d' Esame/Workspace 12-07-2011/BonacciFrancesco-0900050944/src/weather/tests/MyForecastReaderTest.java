package weather.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.SortedSet;



import org.junit.Test;

import weather.model.PunctualForecast;
import weather.persistence.ForecastException;
import weather.persistence.text.MyForecastReader;

public class MyForecastReaderTest {
MyForecastReader reader= null;
	@Test
	public void testReadAll() throws IOException, ForecastException {
		String dati= "Bologna; 12/07/2011; 02.00; 18.5°; 91%; pioggia\n"+
                     "Bologna; 12/07/2011; 05.00; 16°; 92%; nuvoloso\n"+
                     "Bologna; 12/07/2011; 08.00; 20°; 87%; sereno\n" +
                     "Bologna; 12/07/2011; 11.00; 24°; 64%; sereno\n"+
                     "Bologna; 12/11/2011; 14.00; 27°; 58%; tempesta\n" +
                     "Ferrara; 12/12/2011; 02.00; 18°; 91%; nebbia\n"+
                     "Ferrara; 12/12/2011; 05.00; 16°; 92%; neve\n"+
                     "Ferrara; 12/12/2011; 08.00; 20.8°; 87%; nebbia\n";

		reader= new MyForecastReader();
		
		try{
			Map<String, SortedSet<PunctualForecast>> mappa = reader.readAll(new StringReader(dati));
			assertEquals(2, mappa.size());
			assertEquals(5, mappa.get("Bologna").size());
			assertEquals(3, mappa.get("Ferrara").size());
		} catch (IOException e) {
			fail("IOException");
			e.printStackTrace();
		} catch (ForecastException e) {
			fail("ForecastException");
			e.printStackTrace();
		}
	
	}
	
	
	
public void checkParseException(String dati) throws IOException{
		
		reader=new MyForecastReader();
		boolean ok = false;
		try{
		  reader.readAll(new StringReader(dati));
		   
		}catch (ForecastException e)
		{
			ok = true;
		}
		assertTrue(ok);
	}
	
	
	@Test
	public void testReadAllWrongFile() throws IOException {
		checkParseException(
				"Reggio Emilia; 12/07/2011; 02.00; 18.5°; 91%; pioggia\n"+
                "Reggio Emilia; 12/07/2011; 05.00; 16°; 92%; nuvoloso\n"+
                "Reggio Emilia; 12/07/2011; b.00; 20°; 87%; sereno\n"
                
		);

		checkParseException(
				"Pesaro; 12/27/2011; 02.00; 18.5°; 91%; pioggia\n"+
                "Pesaro; 12/07/2011; 05.00; 16°; 92%; nuvoloso\n"+
                "Pesaro; 12/07/2011; 08.00; 20°; 87%; sereno\n" 
			);

		checkParseException(
				"Bologna; 32/07/2011; 02.00; 18.5°; 91%; pioggia\n"+
                "Bologna; 12/07/2011; 05.00; 16°; 92%; nuvoloso\n"+
                "Bologna; 12/07/2011; 08.00; 20°; 87%; sereno\n" 
			);

		checkParseException(
				"Bologna; 12/07/2011; 02.00; bhf°; 91%; pioggia\n"+
                "Bologna; 12/07/2011; 05.00; 16°; 92%; nuvoloso\n"+
                "Bologna; 12/07/2011; 08.00; 20°; 87%; sereno\n" 
			);
		
		checkParseException(
				"Bologna; 12/07/2011; 02.00; 18°; 91%; pioggia\n"+
                "Bologna; 12/07/2011; 05.00; 16°; 92%; nuvoloso\n"+
                "Bologna; 12/07/2011; 08.00; 20°; mmmm%; sereno\n" 
			);
	}
	

}
