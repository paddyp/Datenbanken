package business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

	public static boolean sendInsertIntoQuery(String table, String... values)
			throws SQLException {
		String insertValues = "";
		String query = "INSERT INTO " + table + " VALUES(";

		for (int i = 0; i < values.length; i++) {
			insertValues += "'" + values[i] + "'"
					+ ((i == values.length - 1) ? "" : ",");
		}
		query += insertValues + ");";
		System.out.println(query);

		return stmt.execute(query);

	}

	public static boolean sendInsertIntoQueryID(String table,
			String[] spaltennamen, String... values) throws SQLException {
		String insertValues = "";
		String query = "INSERT INTO " + table + " (";
		for (int i = 0; i < spaltennamen.length; i++) {
			insertValues += "'" + spaltennamen[i] + "'"
					+ ((i == values.length - 1) ? "" : ",");
		}
		query += insertValues + " )" + " VALUES(";
		for (int i = 0; i < values.length; i++) {
			insertValues += "'" + values[i] + "'"
					+ ((i == values.length - 1) ? "" : ",");
		}
		query += insertValues + ");";

		return stmt.execute(query);
	}
	
	public static boolean sendUpdateQuery(String table,String where,String... values) throws SQLException{
		String query = "UPDATE " + table + " SET";
		
		for(int i =0;i < values.length;i++){
			query += " " + values[i] + ((i == values.length - 1) ? "" : ",");
		}
		
		query += " WHERE email='" + where + "'";
		
		return stmt.execute(query);
	}

	public static boolean sendTransaktion(String query){
		query = "BEGIN;" + query + ";";
		try {
			 stmt.execute(query);
			 stmt.execute("COMMIT;");
			 return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				stmt.execute("ROLLBACK;");
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		}

	}

	/**
	 * Takes a resultset and formats a string with the given columns on the
	 * console
	 * 
	 * @param rs
	 *            The resultset to format
	 * @param collums
	 *            The collums to use
	 * @throws SQLException
	 *             when a database error occures
	 */
	public static String[] toString(ResultSet rs, String... collums)
			throws SQLException {

		StringBuilder sb;
		ArrayList<String> strings = new ArrayList<String>();

		while (rs.next()) {
			sb = new StringBuilder();
			sb.append(rs.getString(collums[0]));
			for (int i = 1; i < collums.length; i++) {
				sb.append(" | " + rs.getString(collums[i]));
			}

			strings.add(sb.toString());
		}

		String[] output = strings.toArray(new String[strings.size()]);

		return output;
	}

	/**
	 * This fills placeholders in a query with the actual data
	 * 
	 * @param query
	 *            The query with placeholders
	 * @param replacements
	 *            The replacements. The first one replaces %1%, the second one
	 *            %2% and so on
	 * @return Returns the query with replacements
	 * @throws SQLException
	 */
	public static String fillPlaceholders(String query, String... replacements)
			throws SQLException {
		for (int i = 0; i < replacements.length; i++) {
			query = query.replaceAll("%" + (i + 1) + "%", replacements[i]);
		}
		return query;
	}

}
