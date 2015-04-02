package ztl.ui;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import ztl.model.RestrictedArea;
import ztl.model.Ticket;
import ztl.model.Transit;
import ztl.persistence.MalformedFileException;
import ztl.persistence.MyTransitReader;
import ztl.persistence.TransitReader;

public class MyController implements Controller {

	private RestrictedArea restrictedArea;
	private List<Transit> listaTransiti;
	private double totaleIncassato;
	
	@Override
	public Iterable<Transit> getAllTransits() {

		return listaTransiti;
		
	}

	@Override
	public Ticket manageTransit(Transit transit) {

		Ticket ticketCorrente = restrictedArea.manageTransit(transit);
		
		if (ticketCorrente != null)
			totaleIncassato += ticketCorrente.getCost();
		
		return ticketCorrente;
	
	}

	@Override
	public double getTotalAmount() {
		
		return totaleIncassato;
		
	}


	public MyController(RestrictedArea restrictedArea) throws MalformedFileException , IOException{

		this.restrictedArea = restrictedArea;
		
		TransitReader transitReader = new MyTransitReader();
		listaTransiti = transitReader.read(new FileReader("transiti.txt"));
		
		totaleIncassato = 0;
		
	}

	
	
}
