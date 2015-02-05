package gui;

import java.awt.Color;
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

	private JLabel passwordLabel;
	private JPasswordField passwordTextField;

	private JPanel birthPanel;
	private JLabel birthLabel;
	private JTextField birthdayTextField;
	private JTextField birthmonthTextField;
	private JTextField birthyearTextField;

	private JLabel placeLabel;
	private JLabel plzLabel;
	private JLabel streetLabel;
	private JTextField placeTextField;
	private JTextField plzTextField;
	private JTextField streetTextField;

	private JLabel mobilLabel;
	private JLabel phoneLabel;
	private JTextField mobileTextField;
	private JTextField phoneTextField;

	public KundeFrame() {
		setSize(400, 300);
		setTitle("Neuen Kunden hinzuf�gen");
		setLayout(new GridLayout(11, 2));
		setBackground(new Color(50, 50, 50));

		// Name eingeben
		nameLabel = new JLabel();
		nameTextField = new JTextField(10);

		nameLabel.setText(" Name: ");

		add(nameLabel);
		add(nameTextField);


		// Vorname eingeben

		vornameLabel = new JLabel();
		vornameTextField = new JTextField(10);

		vornameLabel.setText(" Vorname :");

		add(vornameLabel);
		add(vornameTextField);

		//
		// email eingeben

		emailLabel = new JLabel(" E-mail :");
		emailTextField = new JTextField(10);

		add(emailLabel);
		add(emailTextField);

		//
		// Password
		passwordLabel = new JLabel(" Password :");
		passwordTextField = new JPasswordField(10);

		add(passwordLabel);
		add(passwordTextField);

		//
		// Geburtstag eingeben

		birthPanel = new JPanel();
		birthPanel.setLayout(new GridLayout(1, 3));
		birthLabel = new JLabel();
		birthLabel.setText(" Geburtstag (Tag|Monat|Jahr) :");
		birthdayTextField = new JTextField(2);
		birthmonthTextField = new JTextField(2);
		birthyearTextField = new JTextField(4);

		add(birthLabel);
		birthPanel.add(birthdayTextField);
		birthPanel.add(birthmonthTextField);
		birthPanel.add(birthyearTextField);
		
		add(birthPanel);


		// Adresse
		plzTextField = new JTextField(6);
		placeTextField = new JTextField(10);
		streetTextField = new JTextField(10);
		plzLabel = new JLabel(" PLZ :");
		placeLabel = new JLabel(" Ort :");
		streetLabel = new JLabel(" Strasse :");

		add(streetLabel);
		add(streetTextField);

		add(plzLabel);
		add(plzTextField);

		add(placeLabel);
		add(placeTextField);


		phoneLabel = new JLabel(" Telefon :");
		phoneTextField = new JTextField(10);
		mobilLabel = new JLabel(" Mobil :");
		mobileTextField = new JTextField(10);

		add(phoneLabel);
		add(phoneTextField);
		add(mobilLabel);
		add(mobileTextField);


		//
		speichern = new JButton();
		speichern.setText("Hinzufuegen");
		speichern.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String date = birthyearTextField.getText() + "-"
						+ birthmonthTextField.getText() + "-"
						+ birthdayTextField.getText();

				try {
					DBQuery.sendInsertIntoQuery("Kunde",
							emailTextField.getText(), nameTextField.getText(),
							vornameTextField.getText(), date.toString(),
							passwordTextField.getPassword().toString(),
							plzTextField.getText(), streetTextField.getText(),
							phoneTextField.getText(), mobileTextField.getText());

					JOptionPane.showMessageDialog(null,
							"Kunde wurde hinzugefuegt!");

					emailTextField.setText("");
					nameTextField.setText("");
					vornameTextField.setText("");
					birthdayTextField.setText("");
					birthmonthTextField.setText("");
					birthyearTextField.setText("");
					passwordTextField.setText("");
					streetTextField.setText("");
					plzTextField.setText("");
					placeTextField.setText("");
					phoneTextField.setText("");
					mobileTextField.setText("");

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

					JOptionPane.showMessageDialog(null,
							"Fehler!\nKunde konnte nicht hinzugefuegt werden!");
				}

				try {
					ResultSet rs = DBQuery.sendQuery("SELECT * FROM Kunde");
					DBQuery.toString(rs, "email", "name", "vorname", "passwort");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// Kunde hinzuf�gen - Abbruch
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
