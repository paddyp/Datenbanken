package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private JComboBox box;
	
	private JButton insert;
	
	public PlaceFrame(){
		setSize(300,100);
		
		platz = new JPanel();
		reiheLabel = new JLabel("Bezeichnung :");
		reiheTF = new JTextField(5);
		nummerLabel = new JLabel("Preis :");
		nummerTF = new JTextField(5);
		box = new JComboBox();
		
		try {
			ResultSet rs = DBQuery.sendQuery("SELECT * FROM saal");
			System.out.println(rs.toString());
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		
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
		
		add(platz);
		
		
	}
	
	
}
