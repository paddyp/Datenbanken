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
	private JPanel bewertungPanel;
	private JLabel bewertungLabel;
	private JTextField bewertungTextField;

	private JPanel titelPanel;
	private JLabel titelLabel;
	private JTextField titelTextField;

	private JPanel fskPanel;
	private JLabel fskLabel;
	private JTextField fskTextField;

	private JPanel genrePanel;
	private JLabel genreLabel;
	private JTextField genreTextField;

	private JPanel darstellerPanel;
	private JLabel darstellerLabel;
	private JTextField darstellerTextField;

	private JButton speichern;
	private JButton abbrechen;

	public FilmFrame() {
		setSize(500, 500);
		setTitle("Neuen Film hinzufügen");
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
		genreLabel = new JLabel("Genre :");
		genreTextField = new JTextField(10);

		genrePanel.add(genreLabel);
		genrePanel.add(genreTextField);

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