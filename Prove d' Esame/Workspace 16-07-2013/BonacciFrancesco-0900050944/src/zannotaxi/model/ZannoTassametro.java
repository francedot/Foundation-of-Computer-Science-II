package zannotaxi.model;

import java.util.List;

public class ZannoTassametro extends Tassametro {

	public ZannoTassametro(double velocitaLimite, double scattoInizialeGiorno,
			double scattoInizialeNotte, TariffaATempo tariffaATempo,
			List<TariffaADistanza> tariffeADistanza) {
		super(velocitaLimite, scattoInizialeGiorno, scattoInizialeNotte, tariffaATempo,
				tariffeADistanza);
		
	}

	
	@Override
	public double calcolaCostoVariabile(CorsaTaxi corsa) {
		
		double costo = 0;
		
		double[] rilevazioniDistanze = corsa.getRilevazioniDistanze();
		
		int tempoTrascorsoDaScatto = 0;
		double spaziopercorsoDaScatto = 0;
		
		TariffaATempo tariffaATempo = getTariffaATempo();
		List<TariffaADistanza> listaTariffeADistanza = getTariffeADistanza();
		
		int durataCorsa = rilevazioniDistanze.length;

		for (int i=1; i < durataCorsa; i++) {
			
			spaziopercorsoDaScatto += rilevazioniDistanze[i] - rilevazioniDistanze[i-1];
			tempoTrascorsoDaScatto++;
			
			double velCorrente = 3.6 * spaziopercorsoDaScatto / tempoTrascorsoDaScatto; //velocità media calcolata a partire dall' ultimo scatto in km/h
			
			if (velCorrente < getVelocitaLimite()) {
				
				if (tempoTrascorsoDaScatto >= tariffaATempo.getDurataScatto()) {
					
					costo += tariffaATempo.getValoreScattoInEuro();
					tempoTrascorsoDaScatto = 0;
					spaziopercorsoDaScatto= 0;
					
				}
				
			}
			else {
				
				TariffaADistanza currentTar = getTariffaADistanzaCorrente(listaTariffeADistanza, costo);
				
				if (Math.round(spaziopercorsoDaScatto) >= currentTar.getDistanzaDiScatto()) {
					
					costo += currentTar.getValoreScattoInEuro();
					tempoTrascorsoDaScatto = 0;
					spaziopercorsoDaScatto = 0;
					
				}
				
			}
			
			
		}
		
		return costo;
		
	}


	private TariffaADistanza getTariffaADistanzaCorrente(List<TariffaADistanza> listaTariffeADistanza, double costo) {

		for (TariffaADistanza t : listaTariffeADistanza) {
			
			if (t.getLimiteMinimoApplicabilitaEuro() <= costo && costo < t.getLimiteMassimoApplicabilitaEuro())
				return t;
			
		}
		
		throw new IllegalArgumentException("non esiste questa tariffa a distanza");
		
	}
	

}
