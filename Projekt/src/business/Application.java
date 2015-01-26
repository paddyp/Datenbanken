package business;

import gui.GUI;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			GUI g = new GUI();
			
			DBConnection dbcon = new DBConnection();
			try {
				DBQuery query = new DBQuery(dbcon.getConnection());
				ResultSet rs = query.sendQuery("SELECT * FROM film");
				DBQuery.toString(rs,"titel","id");	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

	}

}
