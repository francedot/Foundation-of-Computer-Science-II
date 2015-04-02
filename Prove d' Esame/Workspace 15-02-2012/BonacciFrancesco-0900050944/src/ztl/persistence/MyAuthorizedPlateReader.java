package ztl.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class MyAuthorizedPlateReader implements AuthorizedPlateReader {

	@Override
	public List<String> read(Reader reader) throws IOException,
			MalformedFileException {
		// TODO AS123PZ\tQW321ZX\nQW321ZK\n
		
		ArrayList<String> listaTarghe = new ArrayList<String>();
		
		BufferedReader br = new BufferedReader(reader);
		String line = null;
		
		while ((line = br.readLine()) != null) {
			
			StringTokenizer stk = new StringTokenizer(line, ", \t\n\r");
			
			listaTarghe.add(stk.nextToken().trim());
			
		}
		 
		return listaTarghe;
		
	}

}
