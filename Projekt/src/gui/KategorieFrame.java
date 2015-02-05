package gui;

import java.awt.BorderLayout;
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

public class KategorieFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel bezeichnungPanel;
	private JLabel bezeichnungLabel;
	private JTextField bezeichnungTextField;
	
	private JPanel preisPanel;
	private JLabel preisLabel;
	private JTextField preisTextField;

	private JPanel knopfPanel;
	private JButton speichern;
	private JButton abbrechen;

	public KategorieFrame() {
		setSize(300, 150);
		setLayout(new BorderLayout());

		// Bezeichnung
		bezeichnungPanel = new JPanel();
		bezeichnungLabel = new JLabel("Bezeichnung :");
		bezeichnungTextField = new JTextField(10);
		
		bezeichnungPanel.add(bezeichnungLabel);
		bezeichnungPanel.add(bezeichnungTextField);
		
		add(bezeichnungPanel,BorderLayout.NORTH);
		
		// Preis
		preisPanel = new JPanel();
		preisLabel = new JLabel("Preis :");
		preisTextField = new JTextField(5);
		preisLabel.setToolTipText("Geldbetraege durch Punkt . trennen(kein Komma)!");
		preisTextField.setToolTipText("Geldbetraege durch Punkt . trennen(kein Komma)!");
		
		preisPanel.add(preisLabel);
		preisPanel.add(preisTextField);
		
		add(preisPanel, BorderLayout.CENTER);

		// Hinzufuegen
		speichern = new JButton("Hinzufuegen");
		speichern.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				try {
					DBQuery.sendInsertIntoQuery("Sitzplatzkategorie",
							bezeichnungTextField.getText(),
							preisTextField.getText());

					JOptionPane.showMessageDialog(null,
							"Kategorie wurde hinzugefuegt!");

					bezeichnungTextField.setText("");
					preisTextField.setText("");

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

					JOptionPane
							.showMessageDialog(null,
									"Fehler!\nKategorie konnte nicht hinzugefuegt werden!");
				}
				
				try {
					ResultSet rs = DBQuery.sendQuery("SELECT * FROM Sitzplatzkategorie");
					DBQuery.toString(rs, "bezeichnung", "preis");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		// Abbruch
		abbrechen = new JButton();
		abbrechen.setText("Abbrechen");
		abbrechen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dispose();
			}
		});
		
		// Buttons am Schluss
		knopfPanel = new JPanel();
			
		knopfPanel.add(speichern);
		knopfPanel.add(abbrechen);

		add(knopfPanel, BorderLayout.SOUTH);
	}

}
