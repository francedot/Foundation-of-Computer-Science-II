package raptor.ui;

import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import raptor.model.RaptorSystem;
import raptor.model.Section;
import raptor.model.SpeedingTicket;
import raptor.model.Transit;
import raptor.persistence.MyTransitReader;

public class MyController implements Controller {

	private RaptorSystem raptorSystem;
	private MyTransitReader myTransitReader;
	
	private ArrayList<Transit> transiti;
	
	public MyController(RaptorSystem raptorSystem) {
		
		this.raptorSystem = raptorSystem;
		
		myTransitReader = new MyTransitReader();
		
		try {
			
			transiti = new ArrayList<Transit>(myTransitReader.readTransits(new FileReader("transits.txt")));
			//già ordinati per ora!
		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(null, "Impossibile caricare il file dei transiti " 
													+ e.getMessage());
		}
		
		try {
			
			raptorSystem.loadData();
			
		} catch (Exception e) {
			
			JOptionPane.showMessageDialog(null, "Impossibile caricare il file delle sezioni " 
													+ e.getMessage());
		}
		
	}

	@Override
	public Iterable<Section> getSections() {

		return raptorSystem.getSections();
		
	}

	@Override
	public Iterable<Transit> getAvailableTransits() {
		
		return transiti;
		
	}

	@Override
	public Iterable<SpeedingTicket> getSpeedingTickets() {

		return raptorSystem.getSpeedingTickets();
		
	}

	@Override
	public boolean hasTransits() {
		
		return transiti.size() != 0;
	}

	@Override
	public void putTransitInSystem() {
	
		if (hasTransits()) {
			
			Transit daTogliere = transiti.get(0);
			transiti.remove(daTogliere);
			
			raptorSystem.putTransit(daTogliere);
	
		}


		
		
	}

}
