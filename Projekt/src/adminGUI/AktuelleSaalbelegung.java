package adminGUI;

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
		setLayout(new GridLayout(3,2));
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
		rs = DBQuery.sendQuery("SELECT b.belegt,b.zeit,b.saal_bezeichnung, c.gesamt "
				+ "FROM (SELECT belegt,vorstellung_id,zeit,saal_bezeichnung FROM (SELECT count(r.*) AS belegt, vorstellung_id "
				+ "FROM reservierung r group by vorstellung_id) a "
				+ "join vorstellung v "
				+ "on a.vorstellung_id = v.id) b "
				+ "join "
				+ "(SELECT count(*) as gesamt, saal_bezeichnung "
				+ "FROM platz GROUP BY saal_bezeichnung) c "
				+ "on b.saal_bezeichnung = c.saal_bezeichnung "
				+ "WHERE zeit>=now() "
				+ "ORDER BY saal_bezeichnung ;");
		ArrayList<String> liste= new ArrayList<String>();
		liste.add("belegt/gesamt zeit saal");
		vorstellungen = new JList<String>();
		
		while(rs.next())
		{
			liste.add(rs.getString("belegt") + "/" +rs.getString("gesamt") + " " + rs.getString("zeit") + " " + rs.getString("saal_bezeichnung") );
		}
		
		String[] string = liste.toArray(new String[liste.size()]);
		
		vorstellungen.setListData(string);
		add(vorstellungen);
	}
	
}
