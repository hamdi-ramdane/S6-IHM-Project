package application.database;

import java.sql.DriverManager;
import java.sql.Connection;
	

public class DataBase {
	public static String path;
	public static Connection connectDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost/ihm_project","root","");
			return connect;
		}catch(Exception e) {e.printStackTrace();}
		return null;
	}
}
