package userGUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Objekte.PlatzObjekt;
import Objekte.VorstellungObjekt;
import business.DBQuery;

public class UserPlaetze extends JPanel {

	private JLabel hinweis;
	private JPanel buchunspanel;
	private JButton buchenbtn;
	private JTextField reihe;
	private JTextField nummer;
	private JLabel reihelbl;
	private JLabel nummerlbl;
	private VorstellungObjekt vorstellung;
	private UserBuchen userBuchen;
	private String[] daten = new String[3];
	
	
	public UserPlaetze(UserBuchen userBuchen){
		this.userBuchen = userBuchen;
		
		setLayout(new GridLayout(2, 1));
		
		hinweis = new JLabel("Bitte eine Vorstellung links oben auswählen", JLabel.CENTER);
		add(hinweis);
		
		buchunspanel = new JPanel();
		buchunspanel.setLayout(new GridLayout(3, 2));
		add(buchunspanel);
		
		reihelbl = new JLabel("Reihe: ");
		buchunspanel.add(reihelbl);
		reihe = new JTextField(6);
		buchunspanel.add(reihe);
		nummerlbl = new JLabel("Nummer: ");
		buchunspanel.add(nummerlbl);
		nummer = new JTextField(6);
		buchunspanel.add(nummer);
		
		buchenbtn = new JButton("Reservieren");
		buchenbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(vorstellung != null && 
						!reihe.getText().isEmpty() &&
						!nummer.getText().isEmpty()){
						System.out.println(UserPlaetze.this.userBuchen);
						UserPlaetze.this.userBuchen.add(new PlatzObjekt(reihe.getText(), nummer.getText()));
				}
			}
		});
		buchunspanel.add(new JLabel());
		buchunspanel.add(buchenbtn);
		
	}
	
	public void update(VorstellungObjekt vorstellung){
		this.vorstellung = vorstellung;
		hinweis.setText("Datum: " + vorstellung.getZeit() + ", Saal: " + vorstellung.getSaal() + ", Film: "+vorstellung.getTitel());
	}
}
