package raptor.ui;

import java.io.IOException;

import raptor.model.RaptorSystem;
import raptor.persistence.MalformedFileException;
import raptor.persistence.MySectionReader;
import raptor.persistence.SectionReader;

public class GuiMain
{

	public static void main(String[] args) throws IOException, MalformedFileException
	{
		SectionReader sectionReader = new MySectionReader();
		RaptorSystem raptor = new RaptorSystem(sectionReader);
		raptor.loadData();
		
		Controller controller = new MyController(raptor);
		MainFrame frame = new MainFrame(controller);
		frame.setVisible(true);
	}

}
