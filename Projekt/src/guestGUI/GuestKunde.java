package guestGUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Objekte.VorstellungObjekt;
import business.DBQuery;

public class GuestKunde extends JPanel {

	private String email;
	private JLabel hinweis;
	private JPanel buchunspanel;
	private JButton buchenbtn;
	private JTextField reihe;
	private JTextField nummer;
	private JLabel reihelbl;
	private JLabel nummerlbl;
	private VorstellungObjekt vorstellung;
	private GuestReservierung guestReservierung;
	private String[] daten = new String[3];
	
	
	public GuestKunde(GuestReservierung guestReservierung){
		this.guestReservierung = guestReservierung;
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
						daten[0] = vorstellung.getId();
						daten[1] = reihe.getText();
						daten[2] = nummer.getText();
						GuestKunde.this.guestReservierung.add(daten);
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
