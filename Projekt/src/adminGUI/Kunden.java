package adminGUI;

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
import business.DBQuery;

public class Kunden extends JPanel {
	private JList<KundeObjekt> kundenliste;

	private ResultSet rs;
	private int anzahl;

	private JPanel abfragePanel;

	private JLabel sucheLabel;

	private JTextField sucheFeld;

	private JButton abschicken;
	private Reservieren reservieren;

	public Kunden(Reservieren reservieren) {
		this.reservieren = reservieren;
		setLayout(new BorderLayout());
		sucheFeld = new JTextField(10);
		abfragePanel = new JPanel();
		abschicken = new JButton("Suchen");
		sucheLabel = new JLabel("Suche :");
		kundenliste = new JList<KundeObjekt>();
		
		kundenliste.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, kundenliste.getSelectedValue().getAll());
				try {
					Kunden.this.reservieren.zeigeReservierungen(kundenliste.getSelectedValue());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});

		abfragePanel.add(sucheLabel);
		abfragePanel.add(sucheFeld);
		abfragePanel.add(abschicken);

		add(abfragePanel, BorderLayout.NORTH);

		rs = null;
		abschicken.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					createList();
					revalidate();

				} catch (SQLException fehler) {
					// TODO Auto-generated catch block
					fehler.printStackTrace();

				}

			}
		});
		
		

		try {
			createList();
			revalidate();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		add(kundenliste, BorderLayout.CENTER);

	}

	private void createList() throws SQLException {
		anzahl = 0;
		// TODO SQL Injection !!!!! sehr möglich :( muss noch unmöglich gemacht
		// werden

		rs = DBQuery.sendQuery("select * FROM kunde WHERE name like('%"
				+ sucheFeld.getText() + "%') OR email like('%"
				+ sucheFeld.getText() + "%') OR vorname like('%"
				+ sucheFeld.getText() + "%');");
		ArrayList<KundeObjekt> liste = new ArrayList<KundeObjekt>();

		

		while (rs.next()) {
			System.out.println(rs.toString());
			liste.add(new KundeObjekt(rs.getString("email"), 
					rs.getString("name"), 
					rs.getString("vorname"),
					rs.getString("geburtsdatum"), 
					rs.getString("plz"), 
					rs.getString("strasse"), 
					rs.getString("festnetz"), 
					rs.getString("handy"), 
					rs.getString("passwort")));
		}
		
		KundeObjekt[] kunden = liste.toArray(new KundeObjekt[liste.size()]);

		kundenliste.setListData(kunden);
		
	}
	
	public void update(){
		sucheFeld.setText("");
		try {
			createList();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
