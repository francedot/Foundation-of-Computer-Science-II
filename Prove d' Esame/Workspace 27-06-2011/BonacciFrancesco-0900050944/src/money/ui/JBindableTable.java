package money.ui;

import java.awt.BorderLayout;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;

/**
 * E' una implementazione di JTable che pemette di effettuare il binding
 * automatico passando una collezione di oggetti.<br/>
 * <p>
 * <b>Esempio:</b><br/>
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
 * 
 * public void initGUI()
 * {
 *    ...
 *    ArrayList<Person> collection = new ArrayList<Person>();
 *    collection.add(new Person("Gabriele"));
 *    collection.add(new Persona("Enrico"));
 *    JBindableTable<Person> table = new JBindableTable<Person>(Person.class);
 *    table.dataBind(collection);
 *    getContentPane().add(table);
 *    ...
 * }
 * </pre>
 * </code>
 * </p>
 * 
 * @author Fondamenti di Informatica T-2
 * @version May 21, 2010 5:33:59 PM
 * @param <T>
 *            Oggetti contenuti all'interno della collezione da passare
 */
@SuppressWarnings("serial")
public class JBindableTable<T> extends JPanel
{
	private JTable table;
	private Class<T> type;

	/**
	 * Costruttore di JBindableTable
	 * 
	 * @param type
	 *            Tipo degli oggetti della collezione da passare
	 */
	public JBindableTable(Class<T> type)
	{
		initGUI();
		this.type = type;
	}

	private void initGUI()
	{
		setLayout(new BorderLayout());
		table = new JTable();
		table.setFillsViewportHeight(true);

		JPanel tabPanel = new JPanel(new BorderLayout());
		tabPanel.add(table, "Center");
		tabPanel.add(table.getTableHeader(), BorderLayout.PAGE_START);

		JScrollPane scroll = new JScrollPane(tabPanel);

		add(scroll, "Center");
	}

	/**
	 * Effettua il bind e visualizza gli oggetti di tipo T contenuti all'interno
	 * della collezione passata
	 * 
	 * @param elements
	 *            Lista di oggetti
	 */
	public void dataBind(Iterable<T> elements)
	{
		List<T> list = new ArrayList<T>();
		for (T element : elements)
		{
			list.add(element);
		}
		BindableTableModel<T> model = new BindableTableModel<T>(list, type);
		table.setModel(model);
	}

	/**
	 * Ritorna l'oggetto attualmente selezionato.
	 * 
	 * @return Oggetto selezionato o null altrimenti.
	 */
	public T getSelectedItem()
	{
		if (table.getSelectedRow() < 0)
			return null;
		@SuppressWarnings("unchecked")
		BindableTableModel<T> bindableTableModel = (BindableTableModel<T>) table.getModel();
		return bindableTableModel.getElements().get(table.getSelectedRow());
	}

	/**
	 * Seleziona l'oggetto passato come parametro se presente nella tabella.
	 * @param item l'oggetto da selezionare
	 * @return true, se l'oggetto è correttamente selezionat, false altrimenti.
	 */
	public boolean setSelectedItem(T item)
	{
		ListSelectionModel selectionModel = table.getSelectionModel();
		@SuppressWarnings("unchecked")
		BindableTableModel<T> bindableTableModel = (BindableTableModel<T>) table.getModel();
		int itemIndex = bindableTableModel.getElements().indexOf(item);
		if (itemIndex >= 0)
		{
			selectionModel.setSelectionInterval(itemIndex, itemIndex);
			return true;
		}
		return false;
	}

	private class BindableTableModel<V> extends AbstractTableModel
	{
		private Class<V> type;

		public BindableTableModel(List<V> elements, Class<V> type)
		{
			this.elements = elements;
			this.type = type;
		}

		private List<V> elements;

		public List<V> getElements()
		{
			return elements;
		}

		@Override
		public int getColumnCount()
		{
			return getColumns().size();
		}

		@Override
		public int getRowCount()
		{
			return elements.size();
		}

		@Override
		public Object getValueAt(int row, int col)
		{
			if (elements.size() == 0)
				return null;
			V item = elements.get(row);
			Method method = getMethods().get(col);
			try
			{
				return method.invoke(item);
			}
			catch (Exception e)
			{
				return null;
			}
		}

		private List<Method> getMethods()
		{
			ArrayList<Method> methods = new ArrayList<Method>();
			for (Method method : type.getMethods())
			{
				ColumnTable annotation = method.getAnnotation(ColumnTable.class);
				if (annotation != null)
					methods.add(method);
			}
			return methods;
		}

		public List<String> getColumns()
		{
			ArrayList<String> columns = new ArrayList<String>();
			if (elements.size() > 0)
			{
				for (Method method : type.getMethods())
				{
					ColumnTable annotation = method.getAnnotation(ColumnTable.class);
					if (annotation != null)
						columns.add(((ColumnTable) annotation).name());
				}
			}
			return columns;
		}

		@Override
		public String getColumnName(int arg0)
		{
			return getColumns().get(arg0);
		};

		public boolean isCellEditable(int row, int col)
		{
			return false;
		}

		@Override
		public Class<?> getColumnClass(int c)
		{
			return getValueAt(0, c).getClass();
		}
	}
}
