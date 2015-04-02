package teethcollege.pianidistudi.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import teethcollege.model.Insegnamento;
import teethcollege.model.PianoDiStudi;
import teethcollege.pianidistudi.ui.Controller;

public class TeethCollegePanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	private Controller controller;	
	
	private JLabel jl1;	//etichetta studenti
	private JComboBox<PianoDiStudi> jcbStudenti;
	private JTextArea jta;
	private JLabel jl2;	//etichetta cambiainsegnamento
	private JComboBox<Insegnamento> jcbInsegnamenti1;
	private JLabel jl3;	//etichetta con
	private JComboBox<Insegnamento> jcbInsegnamenti2;
	private JButton jb;
	private JLabel jl4;//etichetta esito operazione
	private JTextField jtf5;//campotesto risultato operazione
	
	
	public TeethCollegePanel(Controller controller) {

		this.controller = controller;
		
		initiGui();
		
		bindData();
		
		jcbStudenti.addActionListener(this);
		
		jb.addActionListener(this);
		
		
	}

	private void bindData() {
		
		Vector<PianoDiStudi> pianiValidi = new Vector<PianoDiStudi>(controller.getPianiDiStudi());
		for (PianoDiStudi plan : pianiValidi) {
			jcbStudenti.addItem(plan);
		}
		
		Vector<Insegnamento> insegnamenti = new Vector<Insegnamento>(controller.getInsegnamenti());
		for (Insegnamento ins : insegnamenti) {
			jcbInsegnamenti1.addItem(ins);
			jcbInsegnamenti2.addItem(ins);
		}
		
	}

	private void initiGui() {
		

		JPanel northPanel = new JPanel();
		{
			northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
			
			jl1 = new JLabel("Studenti");
			northPanel.add(jl1);
			
			jcbStudenti = new JComboBox<PianoDiStudi>();
			northPanel.add(jcbStudenti);
			
		}
		this.add(northPanel, BorderLayout.NORTH);
		
		jta = new JTextArea(50, 50);
		jta.setEditable(false);
		this.add(jta, BorderLayout.CENTER);
		
		JPanel southPanel = new JPanel();
		{
			southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
			
			jl2 = new JLabel("Cambia Insegnamento");
			southPanel.add(jl2);
			
			jcbInsegnamenti1 = new JComboBox<Insegnamento>();
			southPanel.add(jcbInsegnamenti1);
			
			jl3 = new JLabel("con");
			southPanel.add(jl3);
			
			jcbInsegnamenti2 = new JComboBox<Insegnamento>();
			southPanel.add(jcbInsegnamenti2);
			
			jb = new JButton("Sostituisci");
			southPanel.add(jb);
			
			jl4 = new JLabel("Esito Operazione");
			southPanel.add(jl4);
			
			jtf5 = new JTextField(30);
			jtf5.setEditable(false);
			southPanel.add(jtf5);
		}
		this.add(southPanel, BorderLayout.SOUTH);
		
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		if (event.getSource() == jcbStudenti)
			printOnTa();
		
		if ((event.getSource() == jb)) {
			
			PianoDiStudi currentPlan = (PianoDiStudi) jcbStudenti.getSelectedItem();
			
			Insegnamento insToRemove = (Insegnamento) jcbInsegnamenti1.getSelectedItem();
			Insegnamento insToAdd = (Insegnamento) jcbInsegnamenti2.getSelectedItem();
			
			String opSucced = controller.sostituisci(currentPlan, insToRemove, insToAdd);
			
			if (opSucced == null) {
				jtf5.setForeground(Color.GREEN);
				jtf5.setText("Insegnamento correttamente sostituito");
			}
			else {
				jtf5.setForeground(Color.RED);
				jtf5.setText(opSucced);
			}
		}
		
		
	}

	private void printOnTa() {
		
		jta.setText("");

		//prendi il plan selezionato da jcbStudenti
		
		PianoDiStudi currentPlan = jcbStudenti.getItemAt(jcbStudenti.getSelectedIndex()); //in qst modo NO CAST

		jta.setText(currentPlan.toFullString());
		
	}
	
	
	
}
