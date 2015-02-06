package adminGUI;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JPanel;

import Objekte.VorstellungObjekt;
import business.DBQuery;


public class AktuelleVorstellung extends JPanel{
	private JList<VorstellungObjekt> vorstellungen;
	
	private ResultSet rs;
	private int anzahl;
	
	public AktuelleVorstellung(){
		vorstellungen = new JList<VorstellungObjekt>();
		setLayout(new BorderLayout());
		rs = null;
		
		try {
			createList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	private void createList() throws SQLException{
		anzahl = 0;
		rs = DBQuery.sendQuery("select * "
				+ "from aktuellevorstellungen "
				+ ";");
		
		
		ArrayList<VorstellungObjekt> liste= new ArrayList<VorstellungObjekt>();
		
		while(rs.next())
		{
			liste.add(new VorstellungObjekt(rs.getString("id"), rs.getString("zeit"), rs.getString("saal_bezeichnung"), rs.getString("titel")));
		}
		
		VorstellungObjekt[] string = liste.toArray(new VorstellungObjekt[0]);
		
		vorstellungen.setListData(string);
	
		
		add(vorstellungen,BorderLayout.CENTER);
	}
	public void update(){
		try {
			createList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
