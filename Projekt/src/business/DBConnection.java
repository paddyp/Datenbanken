package business;

import java.sql.*;

import javax.swing.text.StyleContext.SmallAttributeSet;

public class DBConnection {

	private Connection con = null;

	public DBConnection() {
		String url = "jdbc:postgresql://localhost:5432/dbs"; // Local
																		// database
																		// url

		// Load Driver or exit Programm
		Driver d = new org.postgresql.Driver();
		try {
			System.out.println("Define URL is " + d.acceptsURL(url));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// connection to database
		try {
			con = DriverManager.getConnection(url, "postgres", "nadine");
			System.out.println("--- connection success ---");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Couldn't connect - aborting");
			System.exit(-1);
		}

	}

	public Connection getConnection() {
		return this.con;

	}

}
