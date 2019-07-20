package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.AdminInformation;

public class adminLoginDAO {

	public int adminLogin(AdminInformation adminInformation) {

		Connection con = null;
		PreparedStatement pstmt = null;
		int queryResultNumber = 0;

		try {
			con = DBUtil.getConnection();
			String dml = "select Admin_no, Admin_password from ADMINUSER where Admin_no = ?";

			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, adminInformation.getAdmin_no());

			ResultSet queryResultSet = pstmt.executeQuery();

			while (queryResultSet.next()) {
				String Admin_no = String.valueOf(queryResultSet.getString(1));
				String Admin_password = String.valueOf(queryResultSet.getString(2));

				if (Admin_no.equals(adminInformation.getAdmin_no())
						&& Admin_password.equals(adminInformation.getAdmin_no())) {
					queryResultNumber++;
				}

			}

		} catch (SQLException e) {
			System.out.println("error = [" + e + "]");
		} catch (Exception e1) {
			System.out.println("error = [" + e1 + "]");
		} finally {

			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (con != null) {
					con.close();
				}
			} catch (SQLException e1) {
				System.out.println("error = [" + e1 + "]");
			}
		} // end to finally
		return queryResultNumber;
	}
}
