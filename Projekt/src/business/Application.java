package business;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			DBConnection dbcon = new DBConnection();
			try {
				DBQuery query = new DBQuery(dbcon.getConnection());
				ResultSet rs = query.sendQuery("SELECT * FROM film");
				while(rs.next())
				{
					System.out.println(rs.getString("titel"));
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

	}

}
