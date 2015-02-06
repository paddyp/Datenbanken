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
	private SaalKategorieBelegung saalkat;
	private GuestKunde guestKunde;
	private GuestReservierung guestRes;
	
	public FilmeBeliebtheitRes(SaalKategorieBelegung saalkat,GuestKunde guestKunde, GuestReservierung guestRes){
		this.saalkat = saalkat;
		this.guestKunde = guestKunde;
		this.guestRes = guestRes;
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
		rs = DBQuery.sendQuery("select * "
				+ "from film "
				+ ";");
		
		
		ArrayList<String[]> liste= new ArrayList<String[]>();
		
		while(rs.next())
		{
			liste.add(DBQuery.toString(rs, "titel"));
		}
		
		
	
		//vorstellungen.setListData(string);
	
		
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
