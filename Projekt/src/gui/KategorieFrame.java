package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.DBQuery;

public class KategorieFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel kategorie;
	private JLabel bezLabel;
	private JLabel preisLabel;
	private JTextField bezTF;
	private JTextField preisTF;
	
	private JButton insert;
	
	public KategorieFrame(){
		setSize(300,100);
		
		kategorie = new JPanel();
		bezLabel = new JLabel("Bezeichnung :");
		bezTF = new JTextField(10);
		preisLabel = new JLabel("Preis :");
		preisTF = new JTextField(5);
		
		insert = new JButton("insert");
		insert.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				try {
					DBQuery.sendInsertIntoQuery("sitzplatzkategorie", bezTF.getText(),preisTF.getText());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		kategorie.add(bezLabel);
		kategorie.add(bezTF);
		kategorie.add(preisLabel);
		kategorie.add(preisTF);
		kategorie.add(insert);
		
		add(kategorie);
		
		
	}
	
	
}