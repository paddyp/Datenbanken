package guestGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import business.DBQuery;
import Objekte.PlatzObjekt;
import Objekte.VorstellungObjekt;

public class GuestReservierung extends JPanel {
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<PlatzObjekt> datenListe;
	private JList<PlatzObjekt> liste;
	private VorstellungObjekt vorstellung;
	
	private JButton buchen;
	public GuestReservierung(){
		datenListe = new ArrayList<PlatzObjekt>();
		setLayout(new BorderLayout());
		liste = new JList<PlatzObjekt>();
		
		liste.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if(liste.getSelectedValue() != null)
				{
					datenListe.remove(liste.getSelectedValue());
					listeAnzeigen();
					
				}
				
			}
		});
		buchen = new JButton("Buchen");
		
		buchen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(vorstellung != null)
				{
					
				
					ResultSet rs; 
					String reservierungsID;
					try {
						rs = DBQuery.sendQuery(DBQuery.fillPlaceholders("INSERT INTO reservierung (kunde_email,vorstellung_id) VALUES(NULL,%1%) RETURNING id", vorstellung.getId()));
						rs.next();
						reservierungsID = rs.getString("id");
						System.out.println(reservierungsID);
						for(int i = 0; i < datenListe.size(); i++){
							
							DBQuery.sendInsertIntoQuery("platz_reservierung", reservierungsID,datenListe.get(i).getReihe(),datenListe.get(i).getNummer(),vorstellung.getSaal());
						}
						
						datenListe = new ArrayList<PlatzObjekt>();
						listeAnzeigen();
						
						rs = DBQuery.sendQuery(DBQuery.fillPlaceholders("select (sum(k.preis) + (select pa.preis from reservierung r "
								+ "join vorstellung_preisaufschlag v "
								+ "on r.vorstellung_id = v.vorstellung_id "
								+ "join preisaufschlag pa on v.preisaufschlag_name = pa.bezeichnung "
								+ "where r.id = 1)) * 1 - (0.05*(select count(*) "
								+ "from kunde where email = '%2')) as gesamtpreis from reservierung "
								+ "r join platz_reservierung pr on r.id = pr.reservierung_id join platz p "
								+ "on pr.platz_reihe=p.reihe and pr.platz_nummer=p.nummer and "
								+ "pr.platz_saal_bezeichnung=p.saal_bezeichnung join sitzplatzkategorie k "
								+ "on p.kategorie_bezeichnung = k.bezeichnung where id = 1;",reservierungsID,null));
						
						rs.next();
						JOptionPane.showMessageDialog(null, "Die Buchung wurde erfolgreich ausgefuehrt.Ihre ID lautet: " + reservierungsID + ". Der Preis belauft sich auf " + rs.getString("gesamtpreis"));
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Die Buchung war nicht erfolgreich. Bitte versuchen wie es erneut");
						e1.printStackTrace();
					}
					
				}
				
			}
		});
		listeAnzeigen();
		add(liste,BorderLayout.CENTER);
		add(buchen,BorderLayout.SOUTH);
	}

	public void add(PlatzObjekt platz){
		datenListe.add(platz);
		listeAnzeigen();
	}
	
	private void listeAnzeigen(){
		
		PlatzObjekt[] daten= datenListe.toArray(new PlatzObjekt[datenListe.size()]);
		liste.setListData(daten);
	
	}
	
	public void update(VorstellungObjekt vorstellung){
		this.vorstellung = vorstellung;
		
	}
	

	
}
