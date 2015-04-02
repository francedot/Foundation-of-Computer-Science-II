package ztl.ui;

import ztl.model.*;

public interface Controller
{
	Iterable<Transit> getAllTransits();
	Ticket manageTransit(Transit transit);
	public abstract double getTotalAmount();
}
