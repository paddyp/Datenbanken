package adminGUI;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JPanel;

import business.DBQuery;


public class AktuelleVorstellung extends JPanel{
	private JList<String> vorstellungen;
	
	private ResultSet rs;
	private int anzahl;
	
	public AktuelleVorstellung(){
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
		
		vorstellungen = new JList<String>();
	
		vorstellungen.setListData(DBQuery.toString(rs, "id","zeit","saal_bezeichnung","titel"));
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
