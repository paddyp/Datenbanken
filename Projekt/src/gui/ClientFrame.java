package gui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientFrame extends JFrame {

	private JButton abourt;
	private JButton save;

	private JPanel namePanel;
	private JLabel nameLabel;
	private JTextField nameTextField;

	private JPanel vornamePanel;
	private JLabel vornameLabel;
	private JTextField vornameTextField;

	private JPanel birthPanel;
	private JLabel birthLabel;
	private JTextField birthdayTextField;
	private JTextField birthmonthTextField;
	private JTextField birthyearTextField;

	public ClientFrame() {
		setSize(500, 700);
		setLayout(new GridLayout(3, 10));
		setUndecorated(true);
		setBackground(new Color(50, 50, 50));

		// Name eingeben
		namePanel = new JPanel();
		nameLabel = new JLabel();
		nameTextField = new JTextField(10);

		nameLabel.setText("Name: ");

		namePanel.add(nameLabel);
		namePanel.add(nameTextField);

		add(namePanel);

		//
		// Vorname eingabe

		vornamePanel = new JPanel();
		vornameLabel = new JLabel();
		vornameTextField = new JTextField(10);

		vornameLabel.setText("Vorname :");

		vornamePanel.add(vornameLabel);
		vornamePanel.add(vornameTextField);

		add(vornamePanel);
		//
		// Geburtstag eingeben

		birthPanel = new JPanel();
		birthLabel = new JLabel();
		birthdayTextField = new JTextField(2);
		birthmonthTextField = new JTextField(2);
		birthyearTextField = new JTextField(4);

		// Limit have to me programmed
		
		birthLabel.setText("Geburtstag :");

		birthPanel.add(birthLabel);
		birthPanel.add(birthdayTextField);
		birthPanel.add(birthmonthTextField);
		birthPanel.add(birthyearTextField);

		add(birthPanel);
		//
		save = new JButton();
		save.setText("Hinzufügen");
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("--- neuer Kunde wird hinzugefügt");

			}
		});

		abourt = new JButton();
		abourt.setText("abourt");
		abourt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});

		add(abourt);
		add(save);
		setVisible(true);
	}
}
