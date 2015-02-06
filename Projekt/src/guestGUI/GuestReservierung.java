package guestGUI;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JPanel;

import Objekte.ReservierungObjekt;

public class GuestReservierung extends JPanel {
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<ReservierungObjekt> datenListe;
	private JList<String> liste;
	public GuestReservierung(){
		datenListe = new ArrayList<ReservierungObjekt>();
		setLayout(new BorderLayout());
		liste = new JList<String>();
		listeAnzeigen();
		add(liste,BorderLayout.CENTER);
	}

	public void add(String[] daten){
		//datenListe.add(daten);
		listeAnzeigen();
	}
	
	private void listeAnzeigen(){
		
		for(int i = 0; i < datenListe.size(); i++){
			//liste.setListData(datenListe.get(i));
		}
	}
	

	
}
