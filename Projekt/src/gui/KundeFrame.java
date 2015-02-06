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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import business.DBQuery;

public class KundeFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton speichern;
	private JButton abbrechen;

	private JPanel namePanel;
	private JLabel nameLabel;
	private JTextField nameTextField;

	private JPanel vornamePanel;
	private JLabel vornameLabel;
	private JTextField vornameTextField;

	private JPanel emailPanel;
	private JLabel emailLabel;
	private JTextField emailTextField;

	private JPanel passwortPanel;
	private JLabel passwortLabel;
	private JPasswordField passwortTextField;

	private JPanel geburtstagPanel;
	private JLabel geburtstagLabel;
	private JTextField geburtsTagTextField;
	private JTextField geburtsMonatTextField;
	private JTextField geburtsJahrTextField;

	private JPanel adressPanel;
	private JLabel ortLabel;
	private JLabel plzLabel;
	private JLabel strasseLabel;
	private JTextField ortTextField;
	private JTextField plzTextField;
	private JTextField strasseTextField;

	private JPanel kontaktPanel;
	private JLabel mobilLabel;
	private JLabel telefonLabel;
	private JTextField mobileTextField;
	private JTextField telefonTextField;

	public KundeFrame() {
		setSize(500, 700);
		setTitle("Neuen Kunden hinzufügen");
		setLayout(new GridLayout(9, 2));

		// Name
		namePanel = new JPanel();
		nameLabel = new JLabel();
		nameTextField = new JTextField(10);

		nameLabel.setText("Name: ");

		namePanel.add(nameLabel);
		namePanel.add(nameTextField);

		add(namePanel);

		// Vorname
		vornamePanel = new JPanel();
		vornameLabel = new JLabel();
		vornameTextField = new JTextField(10);

		vornameLabel.setText("Vorname :");

		vornamePanel.add(vornameLabel);
		vornamePanel.add(vornameTextField);

		add(vornamePanel);

		// E-Mail
		emailPanel = new JPanel();
		emailLabel = new JLabel("E-mail :");
		emailTextField = new JTextField(10);

		emailPanel.add(emailLabel);
		emailPanel.add(emailTextField);

		add(emailPanel);

		// Password
		passwortPanel = new JPanel();
		passwortLabel = new JLabel("Password :");
		passwortTextField = new JPasswordField(10);

		passwortPanel.add(passwortLabel);
		passwortPanel.add(passwortTextField);

		add(passwortPanel);

		// Geburtstag
		geburtstagPanel = new JPanel();
		geburtstagLabel = new JLabel();
		geburtsTagTextField = new JTextField(2);
		geburtsMonatTextField = new JTextField(2);
		geburtsJahrTextField = new JTextField(4);

		geburtstagLabel.setText("Geburtstag :");

		geburtstagPanel.add(geburtstagLabel);
		geburtstagPanel.add(geburtsTagTextField);
		geburtstagPanel.add(geburtsMonatTextField);
		geburtstagPanel.add(geburtsJahrTextField);

		add(geburtstagPanel);

		// Adresse
		adressPanel = new JPanel();
		adressPanel.setLayout(new GridLayout(2, 2));
		plzTextField = new JTextField(6);
		ortTextField = new JTextField(10);
		strasseTextField = new JTextField(10);
		plzLabel = new JLabel("PLZ :");
		ortLabel = new JLabel("Ort :");
		strasseLabel = new JLabel("Strasse :");

		adressPanel.add(strasseLabel);
		adressPanel.add(plzLabel);
		adressPanel.add(ortLabel);

		adressPanel.add(strasseTextField);
		adressPanel.add(plzTextField);
		adressPanel.add(ortTextField);

		add(adressPanel);

		// Kontakt
		kontaktPanel = new JPanel();
		telefonLabel = new JLabel("Telefon :");
		telefonTextField = new JTextField(10);
		mobilLabel = new JLabel("Mobil :");
		mobileTextField = new JTextField(10);

		kontaktPanel.add(telefonLabel);
		kontaktPanel.add(telefonTextField);
		kontaktPanel.add(mobilLabel);
		kontaktPanel.add(mobileTextField);

		add(kontaktPanel);

		// Buttons - Speichern
		speichern = new JButton();
		speichern.setText("Hinzufuegen");
		speichern.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String date = geburtsTagTextField.getText() + "-"
						+ geburtsMonatTextField.getText() + "-"
						+ geburtsJahrTextField.getText();

				try {
					DBQuery.sendInsertIntoQuery("Kunde",
							emailTextField.getText(), nameTextField.getText(),
							vornameTextField.getText(), date.toString(),
							passwortTextField.getPassword().toString(),
							plzTextField.getText(), strasseTextField.getText(),
							telefonTextField.getText(),
							mobileTextField.getText());

					DBQuery.sendInsertIntoQuery("Ort_PLZ",
							plzTextField.getText(), ortTextField.getText());

					JOptionPane.showMessageDialog(null,
							"Kunde wurde hinzugefuegt!");

					// Zurücksetzten aller Eingebefelder
					emailTextField.setText("");
					nameTextField.setText("");
					vornameTextField.setText("");
					geburtsTagTextField.setText("");
					geburtsMonatTextField.setText("");
					geburtsJahrTextField.setText("");
					passwortTextField.setText("");
					strasseTextField.setText("");
					plzTextField.setText("");
					ortTextField.setText("");
					telefonTextField.setText("");
					mobileTextField.setText("");

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

					JOptionPane.showMessageDialog(null,
							"Fehler!\nKunde konnte nicht hinzugefuegt werden!");
				}

				// Testausgabe, ob das Einfügen funktioniert hat
				try {
					ResultSet rs1 = DBQuery.sendQuery("SELECT * FROM Kunde");
					DBQuery.toString(rs1, "email", "name", "vorname",
							"passwort");

					ResultSet rs2 = DBQuery.sendQuery("SELECT * FROM Ort_PLZ");
					DBQuery.toString(rs2, "plz", "ort");

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// Buttons - Abbruch
		abbrechen = new JButton();
		abbrechen.setText("Abbrechen");
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

}
