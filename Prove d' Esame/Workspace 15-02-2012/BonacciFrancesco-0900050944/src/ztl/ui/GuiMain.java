package ztl.ui;

import java.io.FileReader;
import java.io.IOException;

import ztl.model.RestrictedArea;
import ztl.persistence.AuthorizedPlateReader;
import ztl.persistence.MalformedFileException;
import ztl.persistence.MyAuthorizedPlateReader;

public class GuiMain
{

	public static void main(String[] args) throws IOException, MalformedFileException
	{
		AuthorizedPlateReader sectionReader = new MyAuthorizedPlateReader();
		RestrictedArea restrictedArea = new RestrictedArea(sectionReader.read(new FileReader("autorizzati.txt")));
		
		Controller controller = new MyController(restrictedArea);
		MainFrame frame = new MainFrame(controller);
		frame.setBounds(500,150,700,300);
		frame.setVisible(true);
	}

}
