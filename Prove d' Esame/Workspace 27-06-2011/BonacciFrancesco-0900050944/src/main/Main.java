package main;

import money.persistence.*;
import money.ui.MyController;

public class Main
{

	public static void main(String[] args)
	{
		MovimentiManager movimentiManager = new MyMovimentiManager();
		ContiReader contiReader = new MyContiReader();
		MyController controller = new MyController(contiReader, movimentiManager);
		if (controller.load())
		{
			controller.showUI();
		}
	}

}
