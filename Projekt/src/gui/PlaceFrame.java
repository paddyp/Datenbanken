package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import business.DBQuery;

public class PlaceFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JComboBox<String> saalComboBox;

	private JLabel reiheLabel;
	private JTextField reiheTextField;
	private JLabel nummerLabel;
	private JTextField nummerTextField;

	private JComboBox<String> kategorieComboBox;

	private JButton speichern;
	private JButton abbrechen;

	public PlaceFrame() {
		setSize(300, 200);
		setTitle("Neuen Platz hinzufuegen");
		setLayout(new GridLayout(5, 2));

		// Saal
		try {
			waehleSaal();
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}

		add(new JLabel("Saal :"));
		add(saalComboBox);

		// Reihe & Nummer
		reiheLabel = new JLabel("Reihe :");
		reiheTextField = new JTextField(5);
		nummerLabel = new JLabel("Nummer :");
		nummerTextField = new JTextField(5);

		add(reiheLabel);
		add(reiheTextField);
		add(nummerLabel);
		add(nummerTextField);

		// Kategorie
		try {
			waehleKategorie();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		add(new JLabel("Kategorie :"));
		add(kategorieComboBox);

		// Buttons
		speichern = new JButton("Hinzufuegen");
		speichern.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				try {
					// Platz einfuegen
					DBQuery.sendInsertIntoQuery("Platz", reiheTextField
							.getText(), nummerTextField.getText(), saalComboBox
							.getSelectedItem().toString(), kategorieComboBox
							.getSelectedItem().toString());

					// Platz_Reservierung einfuegen
					String[] spaltennamen = { "platz_reihe", "platz_nummer",
							"platz_saal_bezeichnung" };
					DBQuery.sendInsertIntoQueryID("Platz_Reservierung",
							spaltennamen, reiheTextField.getText(),
							nummerTextField.getText(), saalComboBox
									.getSelectedItem().toString());

					JOptionPane.showMessageDialog(null,
							"Platz wurde hinzugefuegt!");

					// Zuruecksetzten aller Felder
					reiheTextField.setText("");
					nummerTextField.setText("");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

					JOptionPane.showMessageDialog(null,
							"Fehler!\nPlatz konnte nicht hinzugefuegt werden!");
				}

				// Testausgabe, ob das Einfuegen funktioniert hat
				try {
					ResultSet rs1 = DBQuery.sendQuery("SELECT * FROM Platz");
					DBQuery.toString(rs1, "reihe", "nummer",
							"saal_bezeichnung", "kategorie_bezeichnung");

					ResultSet rs2 = DBQuery
							.sendQuery("SELECT * FROM Platz_Reservierung");
					DBQuery.toString(rs2, "reservierung_id", "platz_reihe",
							"platz_nummer", "platz_saal_bezeichnung");

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

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

	private void waehleSaal() throws SQLException {
		// TODO Auto-generated method stub
		saalComboBox = new JComboBox<String>();
		ResultSet rs = DBQuery.sendQuery("SELECT * FROM Saal");
		while (rs.next()) {
			saalComboBox.addItem(rs.getString("bezeichnung"));
		}
	}

	private void waehleKategorie() throws SQLException {
		// TODO Auto-generated method stub
		kategorieComboBox = new JComboBox<String>();
		ResultSet rs = DBQuery.sendQuery("SELECT * FROM Sitzplatzkategorie");
		while (rs.next()) {
			kategorieComboBox.addItem(rs.getString("bezeichnung"));
		}
	}

}
