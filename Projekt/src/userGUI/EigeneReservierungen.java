package userGUI;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Objekte.ReservierungObjekt;
import business.DBQuery;


public class EigeneReservierungen extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JList<ReservierungObjekt> reservierungListe;
	
	private ResultSet rs;
	
	private JPanel abfragePanel;
	private String email;
	
	
	
	
	public EigeneReservierungen(String email){
		this.email = email;
		setLayout(new BorderLayout());
		abfragePanel = new JPanel();

		
		reservierungListe = new JList<ReservierungObjekt>();
		
		reservierungListe.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if(reservierungListe.getSelectedValue() != null){
				int abfrage = JOptionPane.showConfirmDialog(null, "Wollen sie diese Reservieren stonieren ?");
				if(abfrage == JOptionPane.YES_OPTION){
					
					
						DBQuery.sendTransaktion("DELETE FROM Platz_Reservierung WHERE reservierung_id =" + reservierungListe.getSelectedValue().getId());
						DBQuery.sendTransaktion("DELETE FROM Reservierung WHERE id =" + reservierungListe.getSelectedValue().getId());
						update();
						JOptionPane.showMessageDialog(null, "Ihre Reservierung wurde gelöscht");
						
				
							
				}
			}
				}
		});
		add(abfragePanel, BorderLayout.NORTH);
		try {
			zeigeReservierungen();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		rs = null;
		
		add(reservierungListe,BorderLayout.CENTER);
	
		
	}
	
	
	public void zeigeReservierungen() throws SQLException{
		rs = DBQuery.sendQuery(DBQuery.fillPlaceholders("select r.id,f.titel, f.fsk, v.saal_bezeichnung, v.zeit, "
				+ "(SELECT count(*) AS plaetze "
				+ "FROM reservierung res "
				+ "join platz_reservierung pr "
				+ "on res.id = pr.reservierung_id "
				+ "WHERE res.id = r.id) "
			+ "from reservierung r "
			+ "join vorstellung v "
			+ "on (r.vorstellung_id = v.id) "
				+ "join vorstellung_film vf "
				+ "on (vf.vorstellung_id = v.id) "
					+ "join film f "
					+ "on (f.id = vf.film_id) "
				+ "WHERE r.kunde_email='%1%'",this.email));
		System.out.println(DBQuery.fillPlaceholders("select r.id,f.titel, f.fsk, v.saal_bezeichnung, v.zeit, "
				+ "(SELECT count(*) AS plaetze "
				+ "FROM reservierung res "
				+ "join platz_reservierung pr "
				+ "on res.id = pr.reservierung_id "
				+ "WHERE res.id = r.id) "
			+ "from reservierung r "
			+ "join vorstellung v "
			+ "on (r.vorstellung_id = v.id) "
				+ "join vorstellung_film vf "
				+ "on (vf.vorstellung_id = v.id) "
					+ "join film f "
					+ "on (f.id = vf.film_id) "
				+ "WHERE r.kunde_email='%1%'",this.email));
		
		
		ArrayList<ReservierungObjekt> liste= new ArrayList<ReservierungObjekt>();
		
		
		
		while(rs.next())
		{
		
			liste.add(new ReservierungObjekt(rs.getString("id"), rs.getString("titel"), rs.getString("fsk"), rs.getString("saal_bezeichnung"), rs.getString("zeit"), rs.getString("plaetze")));
			}
		
		ReservierungObjekt[] string = liste.toArray(new ReservierungObjekt[liste.size()]);
		
		reservierungListe.setListData(string);
		
	}
	
	public void update(){
		try {
			zeigeReservierungen();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
