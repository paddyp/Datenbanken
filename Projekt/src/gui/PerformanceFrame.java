package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import business.DBQuery;

public class PerformanceFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel datumPanel;
	private JLabel datumLabel;
	private JPanel zeitPanel;
	private JLabel zeitLabel;
	private JComboBox<String> tagComboBox;
	private JComboBox<String> monatComboBox;
	private JComboBox<String> jahrComboBox;
	private JComboBox<String> stundeComboBox;
	private JComboBox<String> minuteComboBox;

	private JLabel filmLabel;
	private JLabel saalLabel;
	private JComboBox<String> filmComboBox;
	private JComboBox<String> saalComboBox;

	private JButton speichern;
	private JButton abbrechen;

	public PerformanceFrame() {
		setSize(400, 300);
		setTitle("Neue Vorstellung hinzuf�gen");

		setLayout(new GridLayout(10, 2));
		
		// Datum & Zeit
		datumPanel = new JPanel();
		datumPanel.setLayout(new GridLayout(1,3));
		datumLabel = new JLabel("Datum:");
		filmComboBox = new JComboBox<String>();
		saalComboBox = new JComboBox<String>();
		tagComboBox = new JComboBox<String>();
		monatComboBox = new JComboBox<String>();
		jahrComboBox = new JComboBox<String>();
		stundeComboBox = new JComboBox<String>();
		minuteComboBox = new JComboBox<String>();
		zeitPanel = new JPanel();
		zeitPanel.setLayout(new GridLayout(1, 2));
		zeitLabel = new JLabel("Zeit:");

		waehleDatumComboBox();
		waehleZeitComboBox();
		
		add(datumLabel);
		
		datumPanel.add(tagComboBox);
		datumPanel.add(monatComboBox);
		datumPanel.add(jahrComboBox);
		
		add(datumPanel);
		
		add(zeitLabel);
		
		zeitPanel.add(stundeComboBox);
		zeitPanel.add(minuteComboBox);
		
		add(zeitPanel);
		
		// Film & Saal
		filmLabel = new JLabel("Film :");
		saalLabel = new JLabel("Saal :");
		
		try {
			waehleFilm();
			waehleSaal();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		add(filmLabel);
		add(filmComboBox);
		add(saalLabel);
		add(saalComboBox);
		
		
		// Buttons - Hinzufuegen
		speichern = new JButton("Hinzufuegen");
		speichern.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String timestep = tagComboBox.getSelectedItem().toString() + "-"
						+ monatComboBox.getSelectedItem().toString() + "-"
						+ jahrComboBox.getSelectedItem().toString() + " "
						+ stundeComboBox.getSelectedItem().toString() + ":"
						+ minuteComboBox.getSelectedItem().toString() + ":00";
				
				try {
					// Vorstellung einfuegen
					String[] spaltennamen = { "zeit", "saal_bezeichnung" };
					DBQuery.sendInsertIntoQueryID("Vorstellung", spaltennamen,
							timestep.toString(),
							saalComboBox.getSelectedItem().toString());

//					DBQuery.sendInsertIntoQuery("Vorstellung_Film",
//							vorstellung_id, film_id);
					
					JOptionPane.showMessageDialog(null,
							"Vorstellung wurde hinzugefuegt!");

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

					JOptionPane.showMessageDialog(null,
							"Fehler!\nVorstellung konnte nicht hinzugefuegt werden!");
				}

				// Testausgabe, ob das Einf�gen funktioniert hat
				try {
					ResultSet rs1 = DBQuery.sendQuery("SELECT * FROM Vorstellung");
					DBQuery.toString(rs1, "id", "zeit", "saal_bezeichnung");
					
					ResultSet rs2 = DBQuery.sendQuery("SELECT * FROM Vorstellung_Film");
					DBQuery.toString(rs2, "vorstellung_id", "film_id");
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		
		// Buttons - Abbruch
		abbrechen = new JButton("Abbrechen");
		abbrechen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});

		add(speichern);
		add(abbrechen);
		

	}

	private void waehleFilm() throws SQLException {
		filmComboBox.removeAllItems();
		ResultSet rs = DBQuery.sendQuery("SELECT * FROM film");
		while (rs.next()) {
			filmComboBox.addItem(rs.getString("titel"));
		}
	}

	private void waehleSaal() throws SQLException {
		saalComboBox.removeAllItems();
		ResultSet rs = DBQuery.sendQuery("SELECT * FROM saal");
		while (rs.next()) {
			saalComboBox.addItem(rs.getString("bezeichnung"));
		}
	}

	private void waehleDatumComboBox() {
		tagComboBox.removeAllItems();
		for (int i = 1; i < 32; i++) {
			if (i < 10) {
				tagComboBox.addItem("0" + i);
			} else {
				tagComboBox.addItem("" + i);
			}

		}
		monatComboBox.removeAllItems();
		for (int i = 1; i < 13; i++) {
			monatComboBox.addItem("" + i);
		}

		Calendar kalender = Calendar.getInstance();
		jahrComboBox.removeAllItems();
		for (int i = kalender.get(Calendar.YEAR); i < kalender.get(Calendar.YEAR) + 100; i++) {
			jahrComboBox.addItem("" + i);
		}
	}

	private void waehleZeitComboBox() {
		stundeComboBox.removeAllItems();
		for (int i = 0; i < 25; i++) {
			if (i < 10) {
				stundeComboBox.addItem("0" + i);
			} else {
				stundeComboBox.addItem("" + i);
			}
		}

		minuteComboBox.removeAllItems();
		for (int i = 0; i < 60; i++) {
			if (i < 10) {
				minuteComboBox.addItem("0" + i);
			} else {
				minuteComboBox.addItem("" + i);
			}
		}
	}
}
