package oroscopo.controller;

import java.io.IOException;
import oroscopo.model.Oroscopo;
import oroscopo.model.OroscopoMensile;
import oroscopo.model.Previsione;
import oroscopo.model.SegnoZodiacale;
import oroscopo.persistence.OroscopoRepository;

public class MyController extends AbstractController {

	private StrategiaSelezione strategiaSelezione;
	
	public MyController(OroscopoRepository myRepo, StrategiaSelezione strategiaSelezione) throws IOException {
		
		super(myRepo);
		this.strategiaSelezione = strategiaSelezione;
		
	}

	@Override
	public SegnoZodiacale[] getSegni() {
		
		return SegnoZodiacale.values();
		
	}

	@Override
	public Oroscopo[] generaOroscopoAnnuale(SegnoZodiacale segno, int fortunaMin) {
		
		Oroscopo[] oroscopiPerAnno = new Oroscopo[12];
		
		for (int i = 0; i < 12; i++) {
			
			Previsione amore = strategiaSelezione.seleziona(myRepo.getPrevisioni("amore"), segno);
			Previsione lavoro = strategiaSelezione.seleziona(myRepo.getPrevisioni("lavoro"), segno);
			Previsione salute = strategiaSelezione.seleziona(myRepo.getPrevisioni("salute"), segno);
			
			if (amore.getValore() > fortunaMin && 
					 lavoro.getValore() > fortunaMin && 
						 salute.getValore() > fortunaMin ) {
				
				oroscopiPerAnno[i] = new OroscopoMensile(segno, amore, lavoro, salute);
				i++;
			}
			
		}

		return oroscopiPerAnno;
		
	}

	@Override
	public Oroscopo generaOroscopoCasuale(SegnoZodiacale segno) {

		Previsione amore = strategiaSelezione.seleziona(myRepo.getPrevisioni("amore"), segno);
		Previsione lavoro = strategiaSelezione.seleziona(myRepo.getPrevisioni("lavoro"), segno);
		Previsione salute = strategiaSelezione.seleziona(myRepo.getPrevisioni("salute"), segno);
		
		return new OroscopoMensile(segno, amore, lavoro, salute);

	}

}
