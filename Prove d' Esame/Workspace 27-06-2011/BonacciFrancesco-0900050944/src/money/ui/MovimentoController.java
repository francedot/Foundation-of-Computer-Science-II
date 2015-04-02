package money.ui;

import java.util.Collection;
import java.util.Date;

import money.model.Movimento;

public interface MovimentoController
{

	Collection<String> getConti();
	Collection<Movimento> getMovimenti(Date inizio, Date fine);
	Collection<String> checkVincoli(Movimento movimento);
	boolean inserisciMovimento();

}
