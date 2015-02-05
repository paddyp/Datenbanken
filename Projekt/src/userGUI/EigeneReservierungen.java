package userGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Objekte.KundeObjekt;
import Objekte.ReservierungObjekt;
import business.DBQuery;


public class EigeneReservierungen extends JPanel{
	private JList<ReservierungObjekt> reservierungListe;
	
	private ResultSet rs;
	
	private JPanel abfragePanel;
	
	private JLabel sucheLabel;
	
	private JTextField sucheFeld;
	
	private JButton abschicken;
	
	public EigeneReservierungen(){
		setLayout(new BorderLayout());
		sucheFeld = new JTextField(6);
		abfragePanel = new JPanel();
		abschicken = new JButton("Suchen");
		sucheLabel = new JLabel("Reservierungsid :");

		abfragePanel.add(sucheLabel);
		abfragePanel.add(sucheFeld);
		abfragePanel.add(abschicken);
		
		reservierungListe = new JList<ReservierungObjekt>();
		
		reservierungListe.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				int abfrage = JOptionPane.showConfirmDialog(null, "Wollen sie diese Reservieren stonieren ?");
				if(abfrage == JOptionPane.YES_OPTION){
					
					try {
						DBQuery.sendTransaktion("DELETE FROM Platz_Reservierung WHERE reservierung_id =" + reservierungListe.getSelectedValue().getId());
						DBQuery.sendTransaktion("DELETE FROM Reservierung WHERE id =" + reservierungListe.getSelectedValue().getId());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
							
				}
			}
		});
		add(abfragePanel, BorderLayout.NORTH);
		
		rs = null;
		abschicken.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					createList(Integer.parseInt(sucheFeld.getText()));
					revalidate();
					
				} catch (SQLException fehler) {
					// TODO Auto-generated catch block
					fehler.printStackTrace();
				
				}
				
			}
		});
		add(reservierungListe,BorderLayout.CENTER);
	
		
	}
	
	private void createList(int id) throws SQLException{
		//TODO SQL Injection !!!!! sehr möglich :( muss noch unmöglich gemacht werden
		
		rs = DBQuery.sendQuery("select r.id,f.titel, f.fsk, v.saal_bezeichnung, v.zeit, "
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
				+ "WHERE r.id = "+ id +" ;");
ArrayList<ReservierungObjekt> liste= new ArrayList<ReservierungObjekt>();
		
		
		
		while(rs.next())
		{
			System.out.println(rs.toString());
			liste.add(new ReservierungObjekt(rs.getString("id"), rs.getString("titel"), rs.getString("fsk"), rs.getString("saal_bezeichnung"), rs.getString("zeit"), rs.getString("plaetze")));
			}
		
		ReservierungObjekt[] string = liste.toArray(new ReservierungObjekt[liste.size()]);
		
		reservierungListe.setListData(string);
		
	}
	
	public void zeigeReservierungen(KundeObjekt kundeObjekt) throws SQLException{
		rs = DBQuery.sendQuery("select r.id,f.titel, f.fsk, v.saal_bezeichnung, v.zeit, "
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
				+ "WHERE r.kunde_email='"+ kundeObjekt.getEmail()+"'");
		
		ArrayList<ReservierungObjekt> liste= new ArrayList<ReservierungObjekt>();
		
		
		
		while(rs.next())
		{
			System.out.println(rs.toString());
			liste.add(new ReservierungObjekt(rs.getString("id"), rs.getString("titel"), rs.getString("fsk"), rs.getString("saal_bezeichnung"), rs.getString("zeit"), rs.getString("plaetze")));
			}
		
		ReservierungObjekt[] string = liste.toArray(new ReservierungObjekt[liste.size()]);
		
		reservierungListe.setListData(string);
		
	}
	
	public void update(){
		
		reservierungListe.setListData(new ReservierungObjekt[0]);
		sucheFeld.setText("");
	}
}
