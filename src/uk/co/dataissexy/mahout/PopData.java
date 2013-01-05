package uk.co.dataissexy.mahout;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class PopData {

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public PopData() {
		Random userid = new Random();
		Random itemid = new Random();
		Random rating = new Random();
		PreparedStatement pstmt = null;

		Connection c = null;
		try {

			c = DriverManager.getConnection("jdbc:mysql://localhost/mahout",
					"root", "");
			pstmt = c.prepareStatement("insert into recom values (?,?,?)");
			for (int i = 0; i < 1000000; i++) {
				pstmt.clearParameters();
				pstmt.setInt(1, userid.nextInt(5000));
				pstmt.setInt(2,  itemid.nextInt(45000));
				pstmt.setFloat(3, rating.nextFloat() * 5);
				pstmt.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				c.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		PopData pd = new PopData();
	}

}
