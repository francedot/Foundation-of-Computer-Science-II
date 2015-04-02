package money.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import money.model.BilancioFamiliare;
import money.model.Conto;
import money.model.Movimento;

public class MyMovimentoController implements MovimentoController
{

	private BilancioFamiliare bilancio;

	public MyMovimentoController(BilancioFamiliare bilancio)
	{
		this.bilancio = bilancio;
	}

	public void showUI()
	{
		MyMovimentiDialog view = new MyMovimentiDialog();
		view.setController(this);
		view.showDialog();
	}

	public Collection<Movimento> getMovimenti(Date inizio, Date fine)
	{
		return bilancio.getMovimenti(inizio, fine);
	}

	@Override
	public Collection<String> getConti()
	{
		ArrayList<String> result = new ArrayList<String>();
		result.add("esterno");
		for (Conto c : bilancio.getConti())
		{
			result.add(c.getName());
		}
		return result;
	}

	public boolean inserisciMovimento()
	{
		InsertMovimentoDialog view = new InsertMovimentoDialog(this);

		Movimento result = null;

		while (result == null)
		{
			if (view.showDialog() == DialogResult.Ok)
			{
				result = view.getMovimento();
			}
			else
			{
				return false;
			}
		}
		bilancio.aggiungiMovimento(result);
		return true;
	}

	@Override
	public Collection<String> checkVincoli(Movimento m)
	{
		return bilancio.checkVincoli(m);
	}

}
