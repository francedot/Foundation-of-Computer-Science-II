package oroscopo.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import oroscopo.model.Previsione;
import oroscopo.model.SegnoZodiacale;

public class MyStrategiaSelezione implements StrategiaSelezione {

	@Override
	public Previsione seleziona(List<Previsione> previsioni,
			SegnoZodiacale segno) {
		
		/*
		 * restituire l' estrazione a sorte fra le previsioni disponibili nel repository
		 * 
		 * 
		 */
		
		//ciclo per le previsioni
		
		//prendo dalle previsioni solo quelle che sono valide per quel segno
		//usco il metodo coll shuffle
		//restituisco una a caso
		
		ArrayList<Previsione> previsioniPerSegno = new ArrayList<Previsione>();
		
		for (Previsione previsione : previsioni) {
			
			if (previsione.validaPerSegno(segno)) {
				
				previsioniPerSegno.add(previsione);
				
			}
			
		}
		
		Collections.shuffle(previsioniPerSegno);
		
		if (previsioniPerSegno.size() != 0) {
		
			return previsioniPerSegno.get(0);
			
		} else
			return null;
	}

}
