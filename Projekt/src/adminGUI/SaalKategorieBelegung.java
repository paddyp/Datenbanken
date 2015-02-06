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
			ResultSet rs = DBQuery.sendQuery(DBQuery.fillPlaceholders("SELECT plaetzebelegt, "
					+ "plaetzegesamt, a.kategorie_bezeichnung FROM (SELECT count(*) AS plaetzegesamt,"
					+ " kategorie_bezeichnung FROM platz WHERE saal_bezeichnung = '%1%' GROUP BY "
					+ "kategorie_bezeichnung) a LEFT OUTER JOIN (SELECT p.kategorie_bezeichnung ,count(*)"
					+ " AS plaetzebelegt FROM vorstellung v JOIN platz_reservierung pr ON v.id = reservierung_id "
					+ "join platz p ON pr.platz_reihe=p.reihe AND pr.platz_nummer=p.nummer"
					+ " AND pr.platz_saal_bezeichnung = p.saal_bezeichnung WHERE id =%2% GROUP BY "
					+ "kategorie_bezeichnung) b ON a.kategorie_bezeichnung = b.kategorie_bezeichnung;",
					vorstellungObjekt.getSaal(), vorstellungObjekt.getId()));
			
			jliste.setListData(DBQuery.toString(rs, "plaetzebelegt", "plaetzegesamt", "kategorie_bezeichnung"));
		}
	}
	
}
