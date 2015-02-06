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

	private JLabel nameLabel;
	private JTextField nameTextField;

	private JLabel vornameLabel;
	private JTextField vornameTextField;

	private JLabel emailLabel;
	private JTextField emailTextField;

	private JLabel passwortLabel;
	private JPasswordField passwortTextField;

	private JPanel geburtstagPanel;
	private JLabel geburtstagLabel;
	private JTextField geburtsTagTextField;
	private JTextField geburtsMonatTextField;
	private JTextField geburtsJahrTextField;

	private JLabel ortLabel;
	private JLabel plzLabel;
	private JLabel strasseLabel;
	private JTextField ortTextField;
	private JTextField plzTextField;
	private JTextField strasseTextField;

	private JLabel mobilLabel;
	private JLabel telefonLabel;
	private JTextField mobileTextField;
	private JTextField telefonTextField;

	public KundeFrame() {
		setSize(400, 300);
		setTitle("Neuen Kunden hinzuf�gen");
		setLayout(new GridLayout(11, 2));

		// Name
		nameLabel = new JLabel();
		nameTextField = new JTextField(10);

		nameLabel.setText("Name: ");

		add(nameLabel);
		add(nameTextField);


		// Vorname
		vornameLabel = new JLabel();
		vornameTextField = new JTextField(10);

		vornameLabel.setText("Vorname :");

		add(vornameLabel);
		add(vornameTextField);


		// E-Mail
		emailLabel = new JLabel("E-mail :");
		emailTextField = new JTextField(10);

		add(emailLabel);
		add(emailTextField);


		// Password
		passwortLabel = new JLabel("Password :");
		passwortTextField = new JPasswordField(10);

		add(passwortLabel);
		add(passwortTextField);


		// Geburtstag
		geburtstagPanel = new JPanel();
		geburtstagPanel.setLayout(new GridLayout(1, 3));
		geburtstagLabel = new JLabel();
		geburtsTagTextField = new JTextField(2);
		geburtsMonatTextField = new JTextField(2);
		geburtsJahrTextField = new JTextField(4);

		geburtstagLabel.setText("Geburtstag :");

		add(geburtstagLabel);
		geburtstagPanel.add(geburtsTagTextField);
		geburtstagPanel.add(geburtsMonatTextField);
		geburtstagPanel.add(geburtsJahrTextField);

		add(geburtstagPanel);

		// Adresse
		plzTextField = new JTextField(6);
		ortTextField = new JTextField(10);
		strasseTextField = new JTextField(10);
		plzLabel = new JLabel("PLZ :");
		ortLabel = new JLabel("Ort :");
		strasseLabel = new JLabel("Strasse :");

		add(strasseLabel);
		add(strasseTextField);
		add(plzLabel);
		add(plzTextField);
		add(ortLabel);
		add(ortTextField);


		// Kontakt
		telefonLabel = new JLabel("Telefon :");
		telefonTextField = new JTextField(10);
		mobilLabel = new JLabel("Mobil :");
		mobileTextField = new JTextField(10);

		add(telefonLabel);
		add(telefonTextField);
		add(mobilLabel);
		add(mobileTextField);


		// Buttons - Speichern
		speichern = new JButton();
		speichern.setText("Hinzufuegen");
		speichern.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				boolean funktioniert = true;
				
				String date = geburtsTagTextField.getText() + "-"
						+ geburtsMonatTextField.getText() + "-"
						+ geburtsJahrTextField.getText();
				
				// Kunde einfuegen
				try {
					DBQuery.sendInsertIntoQuery("Kunde",
							emailTextField.getText(), nameTextField.getText(),
							vornameTextField.getText(), date.toString(),
							passwortTextField.getPassword().toString(),
							plzTextField.getText(), strasseTextField.getText(),
							telefonTextField.getText(),
							mobileTextField.getText());
				} catch (SQLException e1) {
					e1.printStackTrace();
					funktioniert = false;
				}
				
				// Ort_PLZ einfuegen
				try {
					DBQuery.sendInsertIntoQuery("Ort_PLZ",
							plzTextField.getText(), ortTextField.getText());
				} catch (SQLException e2) {
					e2.printStackTrace();
					funktioniert = false;
				}
				
				if (funktioniert == true) {
					JOptionPane.showMessageDialog(null,
							"Kunde wurde hinzugefuegt!");

					// Zuruecksetzten aller Eingebefelder
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
				} else {
					JOptionPane.showMessageDialog(null,
							"Fehler!\nKunde konnte nicht hinzugefuegt werden!");
				}

				// Testausgabe, ob das Einfuegen funktioniert hat
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
