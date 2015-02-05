package userGUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Objekte.VorstellungObjekt;
import business.DBQuery;

public class KundeBuchen extends JPanel {

	private String vorstellungs_id = "";
	private String email;
	private JLabel hinweis;
	private JPanel buchunspanel;
	private JButton buchenbtn;
	private JComboBox<String> reiheCB;
	private JTextField nummer;
	private JComboBox<String> nummerCB;
	private JLabel reihelbl;
	private JLabel nummerlbl;
	
	private VorstellungObjekt vorstellung;
	
	
	public KundeBuchen(String email){
		this.email = email;
		setLayout(new GridLayout(2, 1));
		
		hinweis = new JLabel("Bitte eine Vorstellung links oben auswählen", JLabel.CENTER);
		add(hinweis);
		
		buchunspanel = new JPanel();
		buchunspanel.setLayout(new GridLayout(3, 2));
		add(buchunspanel);
		
		reihelbl = new JLabel(" Reihe: ");
		buchunspanel.add(reihelbl);
<<<<<<< HEAD
		reiheCB = new JComboBox<String>();
		nummerCB = new JComboBox<String>();
		reiheCB.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				createNummerComboBox();
				
			}
		});

		createReiheComboBox();
		createNummerComboBox();
		buchunspanel.add(reiheCB);
		buchunspanel.add(nummerCB);
		reihelbl = new JLabel("Nummer: ");
		buchunspanel.add(reihelbl);
=======
		reihe = new JTextField(6);
		buchunspanel.add(reihe);
		nummerlbl = new JLabel(" Nummer: ");
		buchunspanel.add(nummerlbl);
>>>>>>> FETCH_HEAD
		nummer = new JTextField(6);
		buchunspanel.add(nummer);
		
		buchenbtn = new JButton("Reservieren");
		buchenbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(vorstellung != null && 
						reiheCB.getSelectedItem() != null &&
						!nummer.getText().isEmpty()){
					try {
						System.out.println(DBQuery.fillPlaceholders("INSERT INTO Reservierung VALUES (DEFAULT, '%1%',%2%);"
								+"INSERT INTO Platz_Reservierung VALUES ((SELECT id FROM Reservierung WHERE kunde_email = '%1%' "
								+ "AND vorstellung_id = %2%),%3%, %4%, %5%);", KundeBuchen.this.email, KundeBuchen.this.vorstellung.getId(), reiheCB.getSelectedItem().toString(), nummer.getText(), vorstellung.getSaal()));
						DBQuery.sendTransaktion(DBQuery.fillPlaceholders("INSERT INTO Reservierung VALUES (DEFAULT, '%1%',%2%);"
								+"INSERT INTO Platz_Reservierung VALUES ((SELECT id FROM Reservierung WHERE kunde_email = '%1%' "
								+ "AND vorstellung_id = %2%),%3%, %4%, '%5%');", KundeBuchen.this.email, KundeBuchen.this.vorstellung.getId(), reiheCB.getSelectedItem().toString(), nummer.getText(), vorstellung.getSaal()));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		buchunspanel.add(new JLabel(""));
		buchunspanel.add(buchenbtn);
		
	}
	
	private void createReiheComboBox(){
		ResultSet sr;
		try {
			
			if(vorstellung != null)
			{
				reiheCB.setFocusable(true);
				reiheCB.setEditable(true);
		
				 sr = DBQuery.sendQuery("SELECT DISTINCT reihe FROM platz WHERE saal_bezeichnung='" + vorstellung.getSaal() + "'");
				while(sr.next()){
					reiheCB.addItem(sr.getString("reihe"));
				}
			}else{
				reiheCB.setEditable(true);
				reiheCB.setFocusable(false);
				nummerCB.setEditable(true);
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createNummerComboBox(){
		ResultSet sr;
		try {
			if(reiheCB.getSelectedItem() != null){
			nummerCB.setEditable(true);
			sr = DBQuery.sendQuery("SELECT DISTINCT nummer FROM platz WHERE saal_bezeichnung='" + vorstellung.getSaal() + "' "
					+ "AND reihe='" + reiheCB.getSelectedItem().toString() + "'");
			
			while(sr.next())
			{
				nummerCB.addItem(sr.getString("nummer"));
			}
			}else{
				nummerCB.setEditable(false);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public void update(VorstellungObjekt vorstellung){
		this.vorstellung = vorstellung;
		createReiheComboBox();
		//createNummerComboBox();
		hinweis.setText("Datum: " + vorstellung.getZeit() + ", Saal: " + vorstellung.getSaal());
	}
}
