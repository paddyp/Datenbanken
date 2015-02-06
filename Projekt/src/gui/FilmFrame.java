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
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.DBQuery;

public class FilmFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel bewertungPanel;
	private JLabel bewertungLabel;
	private JTextField bewertungTextField;

	private JPanel titelPanel;
	private JLabel titelLabel;
	private JTextField titelTextField;

	private JPanel fskPanel;
	private JLabel fskLabel;
	private JTextField fskTextField;

	private JComboBox<String> genreComboBox;
	private JPanel genrePanel;

	private JPanel darstellerPanel;
	private JLabel darstellerLabel;
	private JTextField darstellerTextField;

	private JButton speichern;
	private JButton abbrechen;

	public FilmFrame() {
		setSize(500, 500);
		setTitle("Neuen Film hinzufuegen");
		setLayout(new GridLayout(7, 2));

		// Bewertung
		bewertungPanel = new JPanel();
		bewertungLabel = new JLabel("Bewertung :");
		bewertungTextField = new JTextField(2);

		bewertungPanel.add(bewertungLabel);
		bewertungPanel.add(bewertungTextField);

		add(bewertungPanel);

		// Titel
		titelPanel = new JPanel();
		titelLabel = new JLabel("Titel :");
		titelTextField = new JTextField(10);

		titelPanel.add(titelLabel);
		titelPanel.add(titelTextField);

		add(titelPanel);

		// FSK
		fskPanel = new JPanel();
		fskLabel = new JLabel("FSK :");
		fskTextField = new JTextField(2);

		fskPanel.add(fskLabel);
		fskPanel.add(fskTextField);

		add(fskPanel);

		// Genre
		genrePanel = new JPanel();

		try {
			waehleGenre();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		genrePanel.add(genreComboBox);
		add(genrePanel);

		// Darsteller
		darstellerPanel = new JPanel();
		darstellerLabel = new JLabel("Hauptdarsteller :");
		darstellerTextField = new JTextField(10);

		darstellerLabel
				.setToolTipText("Mehrere Darstellernamen mit Komma separieren!");
		darstellerTextField
				.setToolTipText("Mehrere Darstellernamen mit Komma separieren!");

		darstellerPanel.add(darstellerLabel);
		darstellerPanel.add(darstellerTextField);

		add(darstellerPanel);

		// Buttons
		speichern = new JButton("Hinzufuegen");
		speichern.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				try {
					// Film einfuegen
					String[] spaltennamenF = { "bewertung", "titel", "fsk",
							"genre_name" };
					DBQuery.sendInsertIntoQueryID("Film", spaltennamenF,
							bewertungTextField.getText(),
							titelTextField.getText(), fskTextField.getText(),
							genreComboBox.getSelectedItem().toString());

					// Hauptdarsteller einfuegen
					String[] alleDarsteller = darstellerTextField.getText()
							.split(",");
					for (int i = 0; i < alleDarsteller.length; i++) {
						String[] darstellerName = alleDarsteller[i].split(" ");
						DBQuery.sendInsertIntoQuery("Hauptdarsteller",
								darstellerName[1].toString(),
								darstellerName[0].toString());
					}

					// Film_Hauptdarsteller einfuegen
					String[] spaltennamenFH = { "hauptdarsteller_name",
							"hauptdarsteller_vorname" };

					for (int i = 0; i < alleDarsteller.length; i++) {
						String[] darstellerName = alleDarsteller[i].split(" ");
						DBQuery.sendInsertIntoQueryID("Film_Hauptdarsteller",
								spaltennamenFH, darstellerName[1].toString(),
								darstellerName[0].toString());
					}

					JOptionPane.showMessageDialog(null,
							"Film wurde hinzugefuegt!");

					// Zurücksetzten aller Felder
					bewertungTextField.setText("");
					titelTextField.setText("");
					fskTextField.setText("");
					darstellerTextField.setText("");

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

					JOptionPane.showMessageDialog(null,
							"Fehler!\nFilm konnte nicht hinzugefuegt werden!");
				}

				// Testausgabe, ob das Einfügen funktioniert hat
				try {
					ResultSet rs1 = DBQuery.sendQuery("SELECT * FROM Film");
					DBQuery.toString(rs1, "id", "bewertung", "titel", "fsk",
							"genre_name");

					ResultSet rs2 = DBQuery
							.sendQuery("SELECT * FROM Hauptdarsteller");
					DBQuery.toString(rs2, "name", "vorname");

					ResultSet rs3 = DBQuery
							.sendQuery("SELECT * FROM Film_Hauptdarsteller");
					DBQuery.toString(rs3, "film_id", "hauptdarsteller_name",
							"hauptdarsteller_vorname");

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

	private void waehleGenre() throws SQLException {
		// TODO Auto-generated method stub
		genreComboBox = new JComboBox<String>();
		ResultSet rs = DBQuery.sendQuery("SELECT * FROM genre");
		while (rs.next()) {
			genreComboBox.addItem(rs.getString("titel"));
		}
	}

	public void update() {
		try {
			waehleGenre();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}