package adminGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.DBQuery;


public class Reservieren extends JPanel{
	private JList<String> vorstellungen;
	
	private ResultSet rs;
	private int anzahl;
	
	private JPanel abfragePanel;
	
	private JLabel sucheLabel;
	
	private JTextField sucheFeld;
	
	private JButton abschicken;
	
	public Reservieren(){
		setLayout(new BorderLayout());
		sucheFeld = new JTextField(10);
		abfragePanel = new JPanel();
		abschicken = new JButton("Buchen");
		sucheLabel = new JLabel("Suche :");

		abfragePanel.add(sucheLabel);
		abfragePanel.add(sucheFeld);
		abfragePanel.add(abschicken);
		
		add(abfragePanel, BorderLayout.NORTH);
		
		rs = null;
		abschicken.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					createList();
					revalidate();
					
				} catch (SQLException fehler) {
					// TODO Auto-generated catch block
					fehler.printStackTrace();
				
				}
				
			}
		});
		
	
		
	}
	
	private void createList() throws SQLException{
		anzahl = 0;
		//TODO SQL Injection !!!!! sehr möglich :( muss noch unmöglich gemacht werden
		
		rs = DBQuery.sendQuery("select * FROM kunde WHERE name like('%"+ sucheFeld.getText() +"%') OR email like('%"+ sucheFeld.getText()+"%') OR vorname like('%"+ sucheFeld.getText()+"%');");
		ArrayList<String> liste= new ArrayList<String>();
		
		vorstellungen = new JList<String>();
		liste.add("email name vorname");
		while(rs.next())
		{
			System.out.println(rs.toString());
			liste.add(rs.getString("email") + " | " + rs.getString("name") +" | " +  rs.getString("vorname"));
		}
		
		String[] string = liste.toArray(new String[liste.size()]);
		
		vorstellungen.setListData(string);
		add(vorstellungen,BorderLayout.CENTER);
	}
	
}
