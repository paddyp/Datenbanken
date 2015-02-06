package userGUI;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Objekte.VorstellungObjekt;
import business.DBQuery;


public class AlleVorstellungen extends JPanel{
	private JList<VorstellungObjekt> vorstellungen;
	private UserBuchen userbuchen;
	private UserPlaetze userplaetze;
	
	private ResultSet rs;
	private int anzahl;
	
	public AlleVorstellungen(UserBuchen userbuchen, UserPlaetze userplaetze){
		this.userbuchen = userbuchen;
		this.userplaetze = userplaetze;
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
		rs = DBQuery.sendQuery("select v.id, v.zeit, v.saal_bezeichnung, f.titel "
				+ "from vorstellung v "
				+ "join vorstellung_film vf "
				+ "on v.id = vf.vorstellung_id "
				+ "join film f "
				+ "on vf.film_id = f.id "
				+ "WHERE zeit>=now() "
				+ "ORDER by v.zeit;");
		ArrayList<VorstellungObjekt> liste= new ArrayList<VorstellungObjekt>();
		
		vorstellungen = new JList<VorstellungObjekt>();
		vorstellungen.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting() && vorstellungen.getSelectedValue() != null){
					userbuchen.update(vorstellungen.getSelectedValue());
					userplaetze.update(vorstellungen.getSelectedValue());
				}
			}
		});
		
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
