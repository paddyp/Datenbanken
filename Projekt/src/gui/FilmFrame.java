package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
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
	private JLabel bewertungLabel;
	private JTextField bewertungTextField;

	private JLabel titelLabel;
	private JTextField titelTextField;

	private JLabel fskLabel;
	private JTextField fskTextField;

	private JLabel genreLabel;
	private JTextField genreTextField;

	private JLabel darstellerLabel;
	private JTextField darstellerTextField;

	private JButton speichern;
	private JButton abbrechen;

	public FilmFrame() {
		setSize(400, 180);
		setTitle("Neuen Film hinzufügen");
		setLayout(new GridLayout(6, 2));

		// Bewertung
		bewertungLabel = new JLabel(" Bewertung :");
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
		genreLabel = new JLabel("Genre :");
		genreTextField = new JTextField(10);

		add(genreLabel);
		add(genreTextField);


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
					String [] spaltennamen = {"bewertung", "titel", "fsk", "genre"};
					DBQuery.sendInsertIntoQueryID("Film",
							spaltennamen,
							bewertungTextField.getText(),
							titelTextField.getText(),
							fskTextField.getText(),
							genreTextField.getText());
					
					String[] alleDarsteller = darstellerTextField.getText()
							.split(",");
					for (int i = 0; i < alleDarsteller.length; i++) {
						String[] darstellerName = alleDarsteller[i].split(" ");
						DBQuery.sendInsertIntoQuery("Hauptdarsteller",
								darstellerName[1].toString(),
								darstellerName[0].toString());
					}

					JOptionPane.showMessageDialog(null,
							"Film wurde hinzugefuegt!");

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

					JOptionPane.showMessageDialog(null,
							"Fehler!\nFilm konnte nicht hinzugefuegt werden!");
				}

				try {
					ResultSet rs = DBQuery.sendQuery("SELECT * FROM Film");
					DBQuery.toString(rs, "bewertung", "titel", "fsk", "genre");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		add(speichern);
		abbrechen = new JButton("Abbrechen");
		abbrechen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();

			}
		});

		add(abbrechen);

	}

}