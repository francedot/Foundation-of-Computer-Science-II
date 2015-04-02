package happybank.model;

public class HappyBancomat extends AbstractBancomat {

	@Override
	public ImportoErogato erogaImporto(TesseraBancomat tessera, int importo,
			int taglio1, int taglio2) throws ImportoNonErogabileException {

		TaglioBanconota t1 = prendiTaglio(taglio1);
		TaglioBanconota t2 = prendiTaglio(taglio2);
		
		if (t1 == null || t2 == null)
			throw new IllegalArgumentException("tagli non validi");
		
		int maxPrelevabile = tessera.getMaxPrelevabile();
		
		if (importo > maxPrelevabile)
			throw new ImportoNonErogabileException("l’importo richiesto supera il massimo prelevabile");
		
		int disponibilitàConto = tessera.getContoCorrente().getSaldo();
		
		if (importo > disponibilitàConto)
			throw new ImportoNonErogabileException("l’importo richiesto non è disponibile sul conto");
		
		int s = importo;
		int n1 = s / taglio1; 
		int n2 = (s - n1*taglio1) / taglio2;

		while (n1*taglio1 + n2*taglio2 != s)  {
			
			if (n1 == 0 && n2 == 0)	break;
			
			n1--;
			n2 = (s - n1*taglio1) / taglio2;
			
			
		}
		
		if (n1 == 0 && n2 == 0) {
			
			throw new ImportoNonErogabileException("l’importo richiesto non è componibile con i tagli presenti");
			
		}
		
		if (n1 > super.getNumGrandeTaglio() || n2 > super.getNumPiccoloTaglio()) {
			
			throw new ImportoNonErogabileException("il numero di banconote disponibili in uno dei due tagli non consente di soddisfare la richiesta");
			
		}
		
		ImportoErogato importoErogato = null;
		
		if (importo <= 100) {
			
			super.setNumGrandeTaglio(super.getNumGrandeTaglio() - n2);
			super.setNumPiccoloTaglio(super.getNumPiccoloTaglio() - n1);
			importoErogato = new ImportoErogato(t1, n1, t2, n2);
			
		}
		else {
			
			super.setNumGrandeTaglio(super.getNumGrandeTaglio() - n1);
			super.setNumPiccoloTaglio(super.getNumPiccoloTaglio() - n2);
			importoErogato = new ImportoErogato(t2, n2, t1, n1);
			
		}
		
		return importoErogato;
		
		
	}

	public static TaglioBanconota prendiTaglio(int taglio) {

		TaglioBanconota[] tagliTotali = TaglioBanconota.values();
		
		for (int i = 0; i < tagliTotali.length; i++) {
			
			if (tagliTotali[i].getValore() == taglio)
				return (tagliTotali[i]);
			
		}
		
		return null;
	}

}
