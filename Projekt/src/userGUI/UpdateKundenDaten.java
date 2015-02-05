package userGUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.DBQuery;

public class UpdateKundenDaten extends JPanel {

	private String email;
	private JTextField nameTF;
	private JTextField vornameTF;
	private JTextField passwortTF;
	private JTextField geb;
	private JTextField plz;
	private JTextField strasse;
	private JTextField ort;
	private JTextField mobil;
	private JTextField tel;
	
	
	private JLabel emailL;
	
	private JButton aendern;
	
	
	
	public UpdateKundenDaten(String email){
		setLayout(new GridLayout(8, 2));
		
		this.email = email;
		
		nameTF = new JTextField(10);
		vornameTF = new JTextField(10);
		passwortTF = new JTextField(10);
		geb = new JTextField(10);
		plz = new JTextField(6);
		strasse = new JTextField(10);
		mobil = new JTextField(6);
		tel = new JTextField(6);
		
		add(new JLabel(" Name:"));
		add(nameTF);
		add(new JLabel(" Vorname:"));
		add(vornameTF);
		add(new JLabel(" Passwort:"));
		add(passwortTF);
		add(new JLabel(" Geburtsdatum:"));
		add(geb);
		add(new JLabel(" PLZ:"));
		add(plz);
		add(new JLabel(" Mobil:"));
		add(mobil);
		add(new JLabel(" Telefon:"));
		add(tel);
		add(new JLabel(""));
		
		
		try {
			fillData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		aendern = new JButton("Änderungen übernehmen");
		aendern.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// Update
		        //email         |     name     | vorname  | geburtsdatum |     passwort      |  plz   |          strasse           |    handy     |   festnetz 
				try {
					
					DBQuery.sendUpdateQuery("kunde",UpdateKundenDaten.this.email, 
							 "name='" + UpdateKundenDaten.this.nameTF.getText() + "' ", 
							 "vorname='" + UpdateKundenDaten.this.vornameTF.getText()+ "' ",
							 "geburtsdatum='" + UpdateKundenDaten.this.geb.getText()+ "' ",
							 "passwort='" + UpdateKundenDaten.this.passwortTF.getText()+ "' ",
							 "plz='" + UpdateKundenDaten.this.plz.getText()+ "' ",
							 "strasse='" + UpdateKundenDaten.this.strasse.getText()+ "' ",
							 "handy='" + UpdateKundenDaten.this.mobil.getText()+ "' ",
							 "festnetz='" + UpdateKundenDaten.this.tel.getText()+ "' "
							
							);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("aendern");
				
			}
		});
		
		add(aendern);
		
		
	}
	
	
	private void fillData() throws SQLException{
		ResultSet rs = DBQuery.sendQuery(DBQuery.fillPlaceholders("SELECT * FROM kunde WHERE email='%1%'",this.email));
		while(rs.next()){
			nameTF.setText(rs.getString("name"));
			vornameTF.setText(rs.getString("vorname"));
			passwortTF.setText(rs.getString("passwort"));
			geb.setText(rs.getString("geburtsdatum"));
			plz.setText(rs.getString("plz"));
			strasse.setText(rs.getString("strasse"));
			// ort merge mit plz
			//
			mobil.setText(rs.getString("handy"));
			tel.setText(rs.getString("festnetz"));
		}
		
		
	}
}
