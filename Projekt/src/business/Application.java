package business;

import gui.GUI;

import java.sql.SQLException;

public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			
			
			DBConnection dbcon = new DBConnection();
			try {
				DBQuery query = new DBQuery(dbcon.getConnection());
				GUI g = new GUI();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();	
				System.exit(1);
			}
			

	}

}
