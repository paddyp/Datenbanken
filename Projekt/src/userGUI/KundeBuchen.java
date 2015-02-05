package userGUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.DBQuery;

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
				if(!vorstellungs_id.isEmpty() && 
						!reihe.getText().isEmpty() &&
						!nummer.getText().isEmpty()){
					try {
						DBQuery.sendTransaktion(DBQuery.fillPlaceholders("INSERT INTO Reservierung VALUES (DEFAULT, %1%,%2%);"
								+"INSERT INTO Platz_Reservierung VALUES ((SELECT id FROM Reservierung WHERE kunde_email = %1% "
								+ "AND vorstellung_id = %2%),%3%, %4%, (SELECT saal_bezeichnung FROM vorstellung v WHERE v.id = %2%));", KundeBuchen.this.email, KundeBuchen.this.vorstellungs_id, reihe.getText(), nummer.getText()));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		buchunspanel.add(buchenbtn);
		
	}
	
	public void update(String id, String zeit, String saal){
		vorstellungs_id = id;
		hinweis.setText("Datum: " + zeit + ", Saal: " + saal);
	}
}
