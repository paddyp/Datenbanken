package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
	private JPanel vorstellungPanel;
	
	private JPanel zeitPanel;
	private JLabel zeitLabel;
	private JComboBox<String> tagComboBox;
	private JComboBox<String> monatComboBox;
	private JComboBox<String> jahrComboBox;
	private JComboBox<String> stundeComboBox;
	private JComboBox<String> minuteComboBox;

	private JPanel filmSaalPanel;
	private JLabel filmLabel;
	private JLabel saalLabel;
	private JComboBox<String> filmComboBox;
	private JComboBox<String> saalComboBox;

	private JPanel knopfPanel;
	private JButton speichern;
	private JButton abbrechen;

	public PerformanceFrame() {
		setSize(500, 500);
		setTitle("Neue Vorstellung hinzufügen");

		vorstellungPanel = new JPanel();
		setLayout(new BorderLayout());
		
		// Datum & Zeit
		zeitPanel = new JPanel();
		zeitLabel = new JLabel("Datum & Zeit :");

		waehleDatumComboBox();
		waehleZeitComboBox();
		
		zeitPanel.add(zeitLabel);
		zeitPanel.add(tagComboBox);
		zeitPanel.add(monatComboBox);
		zeitPanel.add(jahrComboBox);
		zeitPanel.add(stundeComboBox);
		zeitPanel.add(minuteComboBox);
		
		// Film & Saal
		filmSaalPanel = new JPanel();
		filmLabel = new JLabel("Film :");
		saalLabel = new JLabel("Saal :");
		
		try {
			waehleFilm();
			waehleSaal();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		filmSaalPanel.add(filmLabel);
		filmSaalPanel.add(filmComboBox);
		filmSaalPanel.add(saalLabel);
		filmSaalPanel.add(saalComboBox);
		
		// Buttons - Hinzufuegen
		knopfPanel = new JPanel();
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

				// Testausgabe, ob das Einfügen funktioniert hat
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

		knopfPanel.add(speichern);
		knopfPanel.add(abbrechen);
		
		vorstellungPanel.add(zeitPanel, BorderLayout.NORTH);
		vorstellungPanel.add(filmSaalPanel, BorderLayout.CENTER);
		vorstellungPanel.add(knopfPanel, BorderLayout.SOUTH);

		add(vorstellungPanel);

		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void waehleFilm() throws SQLException {
		filmComboBox = new JComboBox<String>();
		ResultSet rs = DBQuery.sendQuery("SELECT * FROM film");
		while (rs.next()) {
			filmComboBox.addItem(rs.getString("titel"));
		}
	}

	private void waehleSaal() throws SQLException {
		saalComboBox = new JComboBox<String>();
		ResultSet rs = DBQuery.sendQuery("SELECT * FROM saal");
		while (rs.next()) {
			saalComboBox.addItem(rs.getString("bezeichnung"));
		}
	}

	private void waehleDatumComboBox() {
		tagComboBox = new JComboBox<String>();
		for (int i = 1; i < 32; i++) {
			if (i < 10) {
				tagComboBox.addItem("0" + i);
			} else {
				tagComboBox.addItem("" + i);
			}

		}
		monatComboBox = new JComboBox<String>();
		for (int i = 1; i < 13; i++) {
			monatComboBox.addItem("" + i);
		}

		Calendar kalender = Calendar.getInstance();
		jahrComboBox = new JComboBox<String>();
		for (int i = kalender.get(Calendar.YEAR); i < kalender.get(Calendar.YEAR) + 100; i++) {
			jahrComboBox.addItem("" + i);
		}
	}

	private void waehleZeitComboBox() {
		stundeComboBox = new JComboBox<String>();
		for (int i = 0; i < 25; i++) {
			if (i < 10) {
				stundeComboBox.addItem("0" + i);
			} else {
				stundeComboBox.addItem("" + i);
			}
		}

		minuteComboBox = new JComboBox<String>();
		for (int i = 0; i < 60; i++) {
			if (i < 10) {
				minuteComboBox.addItem("0" + i);
			} else {
				minuteComboBox.addItem("" + i);
			}
		}
	}
}
