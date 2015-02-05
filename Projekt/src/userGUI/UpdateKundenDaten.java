package userGUI;

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
		
		this.email = email;
		try {
			fillData();
			addToPanel();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		aendern = new JButton("update");
		aendern.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// Update
		        //email         |     name     | vorname  | geburtsdatum |     passwort      |  plz   |          strasse           |    handy     |   festnetz 
				try {
					DBQuery.sendQuery(DBQuery.fillPlaceholders("UPDATE kunde SET name='%1%', vorname='%2%',geburtsdatum='%3%',passwort='%4%',plz='%5%',strasse='%6%',handy='%7%',festnetz='%8%' WHERE email='%9%' ", 
							UpdateKundenDaten.this.nameTF.getText(), 
							UpdateKundenDaten.this.vornameTF.getText(), 
							UpdateKundenDaten.this.geb.getText(), 
							UpdateKundenDaten.this.passwortTF.getText(), 
							UpdateKundenDaten.this.plz.getText(),
							UpdateKundenDaten.this.strasse.getText(),
							UpdateKundenDaten.this.mobil.getText(),
							UpdateKundenDaten.this.tel.getText(),
							UpdateKundenDaten.this.email));
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
		System.out.println(DBQuery.fillPlaceholders("SELECT * FROM kunde WHERE email='%1%'",this.email));
		ResultSet rs = DBQuery.sendQuery(DBQuery.fillPlaceholders("SELECT * FROM kunde WHERE email='%1%'",this.email));
		nameTF = new JTextField(10);
		while(rs.next()){
		nameTF.setText(rs.getString("name"));
		vornameTF = new JTextField(10);
		vornameTF.setText(rs.getString("vorname"));
		passwortTF = new JTextField(10);
		passwortTF.setText(rs.getString("passwort"));
		geb = new JTextField(10);
		geb.setText(rs.getString("geburtsdatum"));
		plz = new JTextField(6);
		plz.setText(rs.getString("plz"));
		strasse = new JTextField(10);
		strasse.setText(rs.getString("strasse"));
		// ort merge mit plz
		//
		mobil = new JTextField(6);
		mobil.setText(rs.getString("handy"));
		tel = new JTextField(6);
		tel.setText(rs.getString("festnetz"));
		}
		
		
	}
	
	private void addToPanel(){
		add(nameTF);
		add(vornameTF);
		add(passwortTF);
		add(geb);
		add(plz);
		add(mobil);
		add(tel);
	}
}
