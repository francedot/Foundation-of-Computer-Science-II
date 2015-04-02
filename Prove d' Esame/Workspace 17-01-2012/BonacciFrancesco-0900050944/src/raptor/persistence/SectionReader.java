package raptor.persistence;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import raptor.model.Section;
import raptor.model.SpeedingTicketCollector;

public interface SectionReader
{
	List<Section> readSections(SpeedingTicketCollector collector, Reader reader) throws IOException,
			MalformedFileException;
}
