public void salvaEsami(Writer writer, List<Esame> esami) throws IOException {
		
	PrintWriter pw = new PrintWriter(writer, true);	//SVUOTTA IL BUFFER AD OGNI LINEA
		//per modalità append non si può fare nulla a meno che non l'abbia richiesta il writer passato!
	for (Esame esame : esami) {
			
		writer.println(esame.toCanonicalString()); //OGGETTI GIà SEPARATI
							 //DA NEW LINE
			
	}
		
	pw.close();

}
===========

PrintWriter pw = null;
			
try {
				
	pw = new PrintWriter(new FileWriter(f, true), true);
	pw.println(areaItinerario.getText());// solo printf e println usano autoflash

			
} catch (IOException e) {
	
// should never occur here due to pre-condition check

} finally {

	if (pw != null)
		pw.close();

}