package adminGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import business.DBQuery;


public class beliebtheitFilm extends JPanel{
	private JList<String> vorstellungen;
	
	
	private int anzahl;
	
	private JPanel abfragePanel;
	
	private JLabel sucheLabel;
	
	private JComboBox<String> sucheFeld;
	
	private JButton abschicken;
	
	public beliebtheitFilm(){
		setLayout(new BorderLayout());
		sucheFeld = new JComboBox<String>();
		abfragePanel = new JPanel();
		abschicken = new JButton("Suchen");
		sucheLabel = new JLabel("Suche :");

		abfragePanel.add(sucheLabel);
		try {
			createComboBox();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		abfragePanel.add(abschicken);
		
		add(abfragePanel, BorderLayout.NORTH);
		
		
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
		ResultSet rs = DBQuery.sendQuery("SELECT * FROM film WHERE genre_name like('%" + sucheFeld.getSelectedItem() + "%') ORDER BY bewertung ");
		ArrayList<String> liste= new ArrayList<String>();
		
		vorstellungen = new JList<String>();
		liste.add("");
		while(rs.next())
		{
			liste.add(rs.getString("titel") + " | " + rs.getString("bewertung") +" | " +  rs.getString("fsk") + rs.getString("genre_name"));
		}
		
		String[] string = liste.toArray(new String[liste.size()]);
		
		vorstellungen.setListData(string);
		add(vorstellungen,BorderLayout.CENTER);
	}
	
	private void createComboBox() throws SQLException{
		ResultSet rs = DBQuery.sendQuery("SELECT * FROM genre ORDER BY name;");
		sucheFeld.addItem("");
		while(rs.next()){
			sucheFeld.addItem(rs.getString("name"));
		}
		abfragePanel.add(sucheFeld,BorderLayout.NORTH);
	}
	
}
