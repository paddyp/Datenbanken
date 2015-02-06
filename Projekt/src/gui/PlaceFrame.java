package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.DBQuery;

public class PlaceFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel saalLabel;
	private JComboBox<String> saalComboBox;
	
	private JPanel platzPanel;
	private JLabel reiheLabel;
	private JTextField reiheTextField;
	private JLabel nummerLabel;
	private JTextField nummerTextField;
	
	private JLabel kategorieLabel;
	private JComboBox<String> kategorieComboBox;
	
	private JButton speichern;
	private JButton abbrechen;
	
	public PlaceFrame(){
		setSize(300,100);
		setTitle("Neuen Platz hinzufügen");
		
		// Saal
		saalLabel = new JLabel("Saal :");
		try {
			waehleSaal();
		} catch (SQLException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		add(saalLabel);
		add(saalComboBox);
		
		// Reihe & Nummer
		platzPanel = new JPanel();
		reiheLabel = new JLabel("Reihe :");
		reiheTextField = new JTextField(5);
		nummerLabel = new JLabel("Nummer :");
		nummerTextField = new JTextField(5);
		
		platzPanel.add(reiheLabel);
		platzPanel.add(reiheTextField);
		platzPanel.add(nummerLabel);
		platzPanel.add(nummerTextField);
		
		// Kategorie
		kategorieLabel = new JLabel("Kategorie :");
		kategorieComboBox = new JComboBox<String>();
		
		try {
			ResultSet rs = DBQuery.sendQuery("SELECT * FROM Sitzplatzkategorie");
			while(rs.next()){
				kategorieComboBox.addItem(rs.getString("bezeichnung"));
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		platzPanel.add(kategorieComboBox);
		
		
		speichern = new JButton("Hinzufuegen");
		speichern.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				try {
					DBQuery.sendInsertIntoQuery("platz", reiheTextField.getText(), nummerTextField.getText());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
			
		
		add(platzPanel);
		
		addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				try {
					setReihePlatz();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
	}
	
	private void waehleSaal() throws SQLException {
		// TODO Auto-generated method stub
		saalComboBox = new JComboBox<String>();
		ResultSet rs = DBQuery.sendQuery("SELECT * FROM Saal");
		while (rs.next()) {
			saalComboBox.addItem(rs.getString("bezeichnung"));
		}
	}

	private void setReihePlatz() throws SQLException{
		
		ResultSet rs = DBQuery.sendQuery("SELECT reihe FROM platz");
//		reiheCB = new JComboBox<String>();
//		
//		while(rs.next())
//		{
//			reiheCB.addItem(rs.getString(1));
//		}
		
		
		
	}
	
	
}
