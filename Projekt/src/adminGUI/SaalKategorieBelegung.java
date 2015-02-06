package adminGUI;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JPanel;

import Objekte.VorstellungObjekt;
import business.DBQuery;

public class SaalKategorieBelegung extends JPanel {

	private VorstellungObjekt vorstellungObjekt = null;
	private JList<String> jliste = new JList<String>();
	
	public SaalKategorieBelegung(){
		setLayout(new BorderLayout());
		add(jliste, BorderLayout.CENTER);
	}
	
	public void update(VorstellungObjekt vorstellungObjekt){
		this.vorstellungObjekt = vorstellungObjekt;
		try {
			createListe();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createListe() throws SQLException{
		if(vorstellungObjekt != null){
			ResultSet rs = DBQuery.sendQuery("select * "
					+ "from aktuellevorstellungen "
					+ ";");
			
			jliste.setListData(DBQuery.toString(rs, "titel"));
		}
	}
	
}
