package teethcollege.esami.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import teethcollege.model.Carriera;
import teethcollege.model.Esame;
import teethcollege.model.Insegnamento;
import teethcollege.model.PianoDiStudi;

public class TeethCollegeExamPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Controller controller;
	
	private DateFormat df;
	
	private JLabel jl1;
	private JComboBox<PianoDiStudi> jcStudenti;
	private JLabel jl2;
	private JRadioButton buttonPiano;
	private JRadioButton buttonEsami;
	private JTextArea jta;
	private JLabel jl3;
	private JComboBox<Insegnamento> jcEsami;
	private JLabel jl4;
	private JComboBox<String> jcVoti;
	private JLabel jl5;
	private JTextField jDate;
	private JButton button;
	private ButtonGroup grp;
	
	
	public TeethCollegeExamPanel(Controller c) {

		this.controller = c;
		
		df = DateFormat.getDateInstance(DateFormat.SHORT);
		
		initGUI();
		
		bindData();
		
		jcStudenti.addActionListener(this);
		
	}


	private void bindData() {

		
		for (PianoDiStudi plan : controller.getPianiDiStudi())			
			jcStudenti.addItem(plan);

		for (Insegnamento e : controller.getInsegnamenti())
			jcEsami.addItem(e);

		for (int i = 1; i <= 31; i++) {
			
			if (i == 31) {
				
				jcVoti.addItem("30L");
				break;
				
			}
			
			jcVoti.addItem(String.valueOf(new Integer(i)));
			
		}
		
		
		
		
	}


	private void initGUI() {
	
		this.setLayout(new BorderLayout());
		
		JPanel north = new JPanel();
		north.setLayout(new BoxLayout(north, BoxLayout.X_AXIS));
		
		jl1 = new JLabel("Studenti");
		north.add(jl1);
		
		jcStudenti = new JComboBox<PianoDiStudi>();
		north.add(jcStudenti);
		
		jl2 = new JLabel("Studenti");
		north.add(jl2);
		
		buttonPiano = new JRadioButton("carriera");
		buttonEsami = new JRadioButton("esami");
		grp = new ButtonGroup();
		grp.add(buttonPiano);
		grp.add(buttonEsami);
		north.add(buttonPiano);
		north.add(buttonEsami);

		this.add(north, BorderLayout.NORTH);
		
		jta = new JTextArea(40, 40);
		this.add(jta);
		
		JPanel south = new JPanel();
		south.setLayout(new BoxLayout(south, BoxLayout.X_AXIS));
		
		jl3 = new JLabel("Nuovo Esame");
		south.add(jl3);
		
		jcEsami = new JComboBox<Insegnamento>();
		south.add(jcEsami);
		
		jl4 = new JLabel("voto");
		south.add(jl4);
		
		jcVoti = new JComboBox<String>();
		south.add(jcVoti);
		
		jl5 = new JLabel("Data");
		south.add(jl5);
		
		jDate = new JTextField(df.format(new Date()), 5);
		south.add(jDate);
		
		button = new JButton("INSERISCI");
		south.add(button);
		
		this.add(south, BorderLayout.SOUTH);
		
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		PianoDiStudi sel = (PianoDiStudi) jcStudenti.getSelectedItem();
		
		List<Esame> esamiDisp = controller.getEsami(sel.getMatricola());
		
		Carriera car = new Carriera(sel, esamiDisp);
		
		jta.setText(car.toFullString(false));
		
	}

	
	
	
}
