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


public class AlleVorstell extends JPanel{
private JList<VorstellungObjekt> vorstellungen;
	
	private ResultSet rs;
	private SaalKategorieBelegung saalkat;
	private GuestKunde guestKunde;
	private GuestReservierung guestRes;
	
	public AlleVorstell(SaalKategorieBelegung saalkat,GuestKunde guestKunde, GuestReservierung guestRes){
		this.saalkat = saalkat;
		this.guestKunde = guestKunde;
		this.guestRes = guestRes;
		vorstellungen = new JList<VorstellungObjekt>();
		setLayout(new BorderLayout());
		rs = null;
		
		try {
			createList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		vorstellungen.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting() && vorstellungen.getSelectedValue() != null){
					AlleVorstell.this.saalkat.update(vorstellungen.getSelectedValue());
					AlleVorstell.this.guestKunde.update(vorstellungen.getSelectedValue());
					AlleVorstell.this.guestRes.update(vorstellungen.getSelectedValue());
				}
			}
		});
	
		
	}
	
	private void createList() throws SQLException{
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
