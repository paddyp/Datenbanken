package userGUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class KundeBuchen extends JPanel {

	private String vorstellungs_id = "";
	private String email;
	private JLabel hinweis;
	private JPanel buchunspanel;
	private JButton buchenbtn;
	private JTextField reihe;
	private JTextField nummer;
	private JLabel reihelbl;
	private JLabel nummerlbl;
	
	
	public KundeBuchen(String email){
		this.email = email;
		setLayout(new GridLayout(3, 1));
		
		hinweis = new JLabel("Bitte eine Vorstellung links oben auswählen");
		add(hinweis);
		
		buchunspanel = new JPanel();
		add(buchunspanel);
		
		reihelbl = new JLabel("Reihe: ");
		buchunspanel.add(reihelbl);
		reihe = new JTextField(6);
		buchunspanel.add(reihe);
		reihelbl = new JLabel("Nummer: ");
		buchunspanel.add(reihelbl);
		nummer = new JTextField(6);
		buchunspanel.add(nummer);
		
		buchenbtn = new JButton("Reservieren");
		buchenbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!vorstellungs_id.isEmpty()){
					
				}
			}
		});
		buchunspanel.add(buchenbtn);
		
		update("1","15-12-12","Saal nope");
		
	}
	
	public void update(String id, String zeit, String saal){
		vorstellungs_id = id;
		hinweis.setText("Datum: " + zeit + ", Saal: " + saal);
	}
}
