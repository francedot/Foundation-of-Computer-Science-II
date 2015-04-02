package money.ui;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation utilizzata per marcare un metodo di una classe per l'utilizzo di JBindableTable
 * Marcando il metodo con questa annotation, verrà visualizzato il valore ritornato dal metodo 
 * come colonna della tabella. Il nome della colonna è dato da name.
 * <p>
 * <b>Esempio: </b>
 * </p>
 * <p>
 * <code><pre>
 * public class Person{
 *    private String firstName;
 *    
 *    public Person(String firstName)
 *    {
 *       this.firstName = firstName;
 *    }		
 * 	
 *    &#64;ColumnTable(name="Nome")
 *    public String getFirstName()
 *    {
 *       return firstName;
 *    }
 * }
 * </pre></code>
 * </p>
 * @author Fondamenti di Informatica T-2
 * @version May 21, 2010 5:32:25 PM
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.METHOD)
public @interface ColumnTable
{
	/**
	 * Nome da visaluzzare come colonna
	 * @return Nome
	 */
	public String name();
}
