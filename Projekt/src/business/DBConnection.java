package business;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private Connection con = null;

	public DBConnection() {
		String url = "jdbc:postgresql://db.intern.mi.hs-rm.de:5432/bring001"; // Local
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
			con = DriverManager.getConnection(url, "bring001", "BRdatenbank");
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
