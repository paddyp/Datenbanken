package guestGUI;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Objekte.VorstellungObjekt;
import adminGUI.AktuelleVorstellung;
import adminGUI.SaalKategorieBelegung;
import business.DBQuery;


public class FilmeBeliebtheitRes extends JPanel{
private JList<String> vorstellungen;
	
	private ResultSet rs;

	
	public FilmeBeliebtheitRes(){

		vorstellungen = new JList<String>();
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
		rs = DBQuery.sendQuery("select count(*) as reservierungen, "
				+ "f.titel from vorstellung v join vorstellung_film vf "
				+ "on v.id = vf.vorstellung_id right outer join film "
				+ "f on vf.film_id = f.id join reservierung r on "
				+ "r.vorstellung_id = v.id group by f.titel order by count(*) desc;");
		
			
		
		
		vorstellungen.setListData(DBQuery.toString(rs, "reservierungen","titel"));
	
		
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
