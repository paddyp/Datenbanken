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

public class FilmFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel idLabel;
	private JTextField idTextField;
	
	private JLabel bewertungLabel;
	private JTextField bewertungTextField;

	private JLabel titelLabel;
	private JTextField titelTextField;

	private JLabel fskLabel;
	private JTextField fskTextField;

	private JComboBox<String> genreComboBox;

	private JLabel darstellerLabel;
	private JTextField darstellerTextField;

	private JButton speichern;
	private JButton abbrechen;

	public FilmFrame() {
		setSize(400, 200);
		setTitle("Neuen Film hinzufuegen");
		setLayout(new GridLayout(7, 2));

		// ID
		idLabel = new JLabel("Film-ID :");
		idTextField = new JTextField(2);
		
		add(idLabel);
		add(idTextField);
		
		
		// Bewertung
		bewertungLabel = new JLabel("Bewertung :");
		bewertungTextField = new JTextField(2);

		add(bewertungLabel);
		add(bewertungTextField);


		// Titel
		titelLabel = new JLabel("Titel :");
		titelTextField = new JTextField(10);

		add(titelLabel);
		add(titelTextField);


		// FSK
		fskLabel = new JLabel("FSK :");
		fskTextField = new JTextField(2);

		add(fskLabel);
		add(fskTextField);


		// Genre
		try {
			waehleGenre();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		add(new JLabel("Genre"));
		add(genreComboBox);

		// Darsteller
		darstellerLabel = new JLabel("Hauptdarsteller :");
		darstellerTextField = new JTextField(10);

		darstellerLabel
				.setToolTipText("Mehrere Darstellernamen mit Komma separieren!");
		darstellerTextField
				.setToolTipText("Mehrere Darstellernamen mit Komma separieren!");

		add(darstellerLabel);
		add(darstellerTextField);


		// Buttons
		speichern = new JButton("Hinzufuegen");
		speichern.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				try {
					// Film einfuegen
					DBQuery.sendInsertIntoQuery("Film", 
							idTextField.getText(),
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

					// Zuruecksetzten aller Felder
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

				// Testausgabe, ob das Einfuegen funktioniert hat
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
			genreComboBox.addItem(rs.getString("name"));
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