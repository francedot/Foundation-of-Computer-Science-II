package money.model;

import java.util.Date;

public interface Bilancio
{

	public abstract double getTotaleEntrate(Date inizio, Date fine);

	public abstract double getTotaleUscite(Date inizio, Date fine);

	double getSaldo(Date fine);

}
