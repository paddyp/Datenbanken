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
	private JPanel platz;
	private JLabel reiheLabel;
	private JLabel nummerLabel;
	private JTextField reiheTF;
	private JTextField nummerTF;
	private JComboBox<String> box;
	
	private JButton insert;
	
	private JLabel deletLabel;
	private JComboBox<String> rheiheCB;
	private JComboBox<String> platzCB;
	private JComboBox<String> saalCB;
	
	public PlaceFrame(){
		setSize(300,100);
		setTitle("Neuen Platz hinzuf�gen");
		
		platz = new JPanel();
		reiheLabel = new JLabel("Bezeichnung :");
		reiheTF = new JTextField(5);
		nummerLabel = new JLabel("Preis :");
		nummerTF = new JTextField(5);
		box = new JComboBox<String>();
		
		try {
			ResultSet rs = DBQuery.sendQuery("SELECT * FROM saal");
			while(rs.next()){
				box.addItem(rs.getString("bezeichnung"));
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		platz.add(box);
		
		
		insert = new JButton("insert");
		insert.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				try {
					DBQuery.sendInsertIntoQuery("platz", reiheTF.getText(), nummerTF.getText());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		platz.add(reiheLabel);
		platz.add(reiheTF);
		platz.add(nummerLabel);
		platz.add(nummerTF);
		platz.add(insert);
		
		// delete Session
		deletLabel = new JLabel("Löschen :");
		try {
			setReihePlatz();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		platz.add(deletLabel);
		platz.add(rheiheCB);
		
		
		add(platz);
		
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
	
	private void setReihePlatz() throws SQLException{
		
		ResultSet rs = DBQuery.sendQuery("SELECT reihe FROM platz");
		rheiheCB = new JComboBox<String>();
		
		while(rs.next())
		{
			rheiheCB.addItem(rs.getString(1));
		}
		
		
		
	}
	
	
}
