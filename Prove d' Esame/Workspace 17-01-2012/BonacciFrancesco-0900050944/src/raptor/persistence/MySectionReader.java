package raptor.persistence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import raptor.model.Section;
import raptor.model.SpeedingTicketCollector;

public class MySectionReader implements SectionReader {

	@Override
	public List<Section> readSections(SpeedingTicketCollector collector,
			Reader reader) throws IOException, MalformedFileException {
		// A14-BO1-N 5 130\nA14-BO1-S 6.5 110\n
		
		BufferedReader bufferedReader = new BufferedReader(reader);
		
		ArrayList<Section> sectionList = new ArrayList<Section>();
		Section section;
		
		while ((section = readSection(bufferedReader, collector)) != null) {
			
			sectionList.add(section);
			
		}
		 
		return sectionList;
		

	}

	private Section readSection(BufferedReader bufferedReader, SpeedingTicketCollector collector) throws IOException, MalformedFileException {
		// A14-BO1-N 5 130\nA14-BO1-S 6.5 110\n
		
		String line = bufferedReader.readLine();		
		if (line == null || line.trim().isEmpty())
			return null;
		
		String[] tokens = line.split(" ");
		
		if (tokens.length != 3) {
			
			throw new MalformedFileException("numero token errato");
			
		}
		
		String id = tokens[0].trim();
		
		double lunghezza, velMassima;
		
		try {
			
			lunghezza = Double.parseDouble(tokens[1].trim());
			velMassima = Double.parseDouble(tokens[2].trim());
			
		} catch(NumberFormatException e) {
			
			throw new MalformedFileException("errore conversione campo numerico", e);
			
		}
		
		
		return new Section(id, velMassima, lunghezza, collector);
	}

}
