package adminGUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JPanel;

import business.DBQuery;


public class AktuelleSaalbelegung extends JPanel{
	private JList<String> vorstellungen;
	
	private ResultSet rs;
	private int anzahl;
	
	public AktuelleSaalbelegung(){
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
		//SELECT * FROM (SELECT count(r.*) AS belegt, vorstellung_id FROM reservierung r group by vorstellung_id) a join vorstellung v on a.vorstellung_id = v.id;
		//SELECT * FROM (SELECT * FROM (SELECT count(r.*) AS belegt, vorstellung_id FROM reservierung r group by vorstellung_id) a join vorstellung v on a.vorstellung_id = v.id) b;
		rs = DBQuery.sendQuery("SELECT * from saalbelegung;");
		
		vorstellungen = new JList<String>();
		
		
		
	
		vorstellungen.setListData(DBQuery.toString(rs, "belegt","gesamt","zeit","saal_bezeichnung"));
		add(vorstellungen,BorderLayout.CENTER);
	}
	
	public void update() {
		try {
			createList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
