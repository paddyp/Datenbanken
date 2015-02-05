package business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBQuery {

	private static Statement stmt;
	private static Connection con;

	// connection errors never throws because connection is tested before in
	// DBConnection

	public DBQuery(Connection con) throws SQLException {
		this.con = con;
		this.stmt = con.createStatement();
	}
	
	/**
	 * Send String Query and get the result back
	 * 
	 * @param query
	 * @return
	 * @throws SQLException
	 */

	public static ResultSet sendQuery(String query) throws SQLException {
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("rs" + rs);
			return rs;

	}

	public static boolean sendInsertIntoQuery(String table, String... values) throws SQLException
	{
		String insertValues = "";
		String query = "INSERT INTO " + table +" VALUES(";
		
		for(int i = 0; i < values.length; i++)
		{
			insertValues += "'" + values[i] + "'" + ((i==values.length-1)?"":",");
		}
		query += insertValues + ");";
		System.out.println(query);
		
		
		return stmt.execute(query);
		
	}
	
	public static boolean sendTransaktion(String query) throws SQLException{
		query = "BEGIN;" + query + ";COMMIT;";
		return stmt.execute(query);
		
		
	}
	
	/**
	 * Takes a resultset and formats a string with the given columns on the console
	 * @param rs The resultset to format
	 * @param collums The collums to use
	 * @throws SQLException when a database error occures
	 */
	public static String toString(ResultSet rs,String... collums) throws SQLException{
	
		StringBuilder sb = new StringBuilder();
		
		while(rs.next())
		{
			sb.append(rs.getString(collums[0]));
			for(int i=1;i<collums.length;i++)
			{
				sb.append(" | " + rs.getString(collums[i]));
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * This fills placeholders in a query with the actual data
	 * @param query The query with placeholders
	 * @param replacements The replacements. The first one replaces %1%, the second one %2% and so on
	 * @return Returns the query with replacements
	 * @throws SQLException 
	 */
	public String fillPlaceholders(String query, String... replacements) throws SQLException{
		for(int i = 1; i<replacements.length; i++){
			query.replaceAll("%"+i+"%", replacements[i]);
		}
		return query;
	}
	
}
