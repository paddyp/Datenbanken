package userGUI;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JPanel;

import business.DBQuery;

public class UpdateKundenDaten extends JPanel {

	private String email;
	public UpdateKundenDaten(String email){
		
		try {
			fillData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void fillData() throws SQLException{
		
		ResultSet rs = DBQuery.sendQuery(DBQuery.fillPlaceholders("SELECT * FROM kunde WHERE email='%1%'",this.email));
		
		
	}
}
