package money.ui;

import money.model.Bilancio;

public interface Controller
{

	void save();

	Iterable<String> getConti();

	void openMovimenti();

	Bilancio getBilancioFamiliare();

	Bilancio getConto(String nome);

}
