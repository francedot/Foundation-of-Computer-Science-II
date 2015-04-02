package dentorestaurant.ui;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import dentorestaurant.model.Categoria;
import dentorestaurant.model.Menu;
import dentorestaurant.model.Ordine;
import dentorestaurant.model.Portata;
import dentorestaurant.persistence.MalformedFileException;
import dentorestaurant.persistence.MenuReader;
import dentorestaurant.persistence.PortateReader;

public class MyController implements Controller {

	private Collection<Menu> listaMenu;
	
	public MyController(PortateReader portateReader, MenuReader menuReader,
			UserInteractor userInteractor) {
		
		Map<Categoria, List<Portata>> portateCaricate = null;
		try {
			
			portateCaricate = portateReader.caricaPortate();
			
		} catch (MalformedFileException | IOException e) {
			
			userInteractor.showMessage("errore caricamento portate");
			userInteractor.shutDownApplication();
			return; //controlla bene
			
		} finally {
			
			portateReader.close();
			
		}
		
		try {
			
			listaMenu = menuReader.caricaMenu(portateCaricate);
		
		} catch (MalformedFileException | IOException e) {
			
			userInteractor.showMessage("errore caricamento menu");
			
			userInteractor.shutDownApplication();
			
		} finally {
			
			menuReader.close();
			
		}
		
	}

	@Override
	public String sostituisciPortata(Ordine ordine, Portata daMettere) {
		
		Portata daTogliere = ordine.getElencoPortate().get(daMettere.getCategoria());
		
		try {
			
			ordine.sostituisciPortata(daTogliere, daMettere);
			
		} catch (IllegalArgumentException e) {
			
			return e.getMessage();
			
		}
		
		return null;
			
	}

	@Override
	public Collection<Menu> getMenus() {
		
		return listaMenu;
		
	}

	@Override
	public Ordine creaOrdine(Menu m, String nomeCliente) {
		
		return new Ordine(m, nomeCliente);
		
	}

}
