package business;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQuery {

	private Statement stmt;

	// connection errors never throws because connection is tested before in
	// DBConnection

	public DBQuery(Connection con) throws SQLException {
		this.stmt = con.createStatement();

	}
	
	/**
	 * 
	 * @param query
	 * @return
	 * @throws SQLException
	 * 
	 * Send String Query and get the result back
	 */

	public ResultSet sendQuery(String query) throws SQLException {
			ResultSet rs = stmt.executeQuery(query);
			return rs;

	}
	
	public static void toString(ResultSet rs,String collum) throws SQLException{
	
			while(rs.next())
			{
				System.out.println(rs.getString(collum));
			}

		
	}
}