package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.GeneralInformation;

public class generalLoginDAO {

	public int generalLogin(GeneralInformation generalInformation) {

		Connection con = null;
		PreparedStatement pstmt = null;
		int queryResultNumber = 0;

		try {
			con = DBUtil.getConnection();
			String dml = "select STU_NO, GENERAL_PASSWORD from STUDENT where STU_NO = ?";

			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, generalInformation.getStu_no());

			ResultSet queryResultSet = pstmt.executeQuery();

			while (queryResultSet.next()) {
				String Stu_no = String.valueOf(queryResultSet.getString(1));
				String General_password = String.valueOf(queryResultSet.getString(2));

				if (Stu_no.equals(generalInformation.getStu_no())
						&& General_password.equals(generalInformation.getGeneral_password())) {
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

	
	
	
	public int editInformation(GeneralInformation generalInformation, String userID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int queryResultNumber = 0;

		try {
			con = DBUtil.getConnection();
			String dml = "update STUDENT set STU_CONTACT = ? , STU_EMAIL = ?, STU_ADDRESS = ?, GENERAL_PASSWORD = ?  where STU_NO = ?";

			pstmt = con.prepareStatement(dml);

			pstmt.setString(1, generalInformation.getStu_contact());
			pstmt.setString(2, generalInformation.getStu_email());
			pstmt.setString(3, generalInformation.getStu_address());
			pstmt.setString(4, generalInformation.getGeneral_password());
			pstmt.setString(5, userID);

			queryResultNumber = pstmt.executeUpdate();

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
	
	
	

	public GeneralInformation loadMemberEdit(String userID) {

		GeneralInformation generalInformation1 = null;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.getConnection();
			String dml = "select STU_NAME, STU_NO, STU_BIRTH, nvl(STU_CONTACT, '미입력'), nvl(STU_EMAIL, '미입력'), nvl(STU_ADDRESS, '미입력'), GENERAL_PASSWORD from STUDENT where STU_NO = ?";

			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, userID);

			ResultSet queryResultSet = pstmt.executeQuery();

			while (queryResultSet.next()) {
				String Stu_name = String.valueOf(queryResultSet.getString(1));
				String Stu_no = String.valueOf(queryResultSet.getString(2));
				String Stu_birth = String.valueOf(queryResultSet.getString(3));
				String Stu_contact = String.valueOf(queryResultSet.getString(4));
				String Stu_email = String.valueOf(queryResultSet.getString(5));
				String Stu_address = String.valueOf(queryResultSet.getString(6));
				String General_password = String.valueOf(queryResultSet.getString(7));

				generalInformation1 = new GeneralInformation(Stu_name, Stu_no, Stu_birth, Stu_contact, Stu_email,
						Stu_address, General_password);
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

		return generalInformation1;
	}

	public GeneralInformation loadMemberInformation(String userID) {

		GeneralInformation generalInformation1 = null;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.getConnection();
			String dml = "select * from STUDENT where STU_NO = ?";

			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, userID);

			ResultSet queryResultSet = pstmt.executeQuery();

			while (queryResultSet.next()) {

				String Stu_no = String.valueOf(queryResultSet.getString("STU_NO"));
				String Stu_name = String.valueOf(queryResultSet.getString("STU_NAME"));
				String Dept_name = String.valueOf(queryResultSet.getString("DEPT_NAME"));
				String Stu_year = String.valueOf(queryResultSet.getString("STU_YEAR"));
				String Dept_no = String.valueOf(queryResultSet.getString("DEPT_NO"));
				String Dept_college = String.valueOf(queryResultSet.getString("DEPT_COLLEGE"));
				String Stu_state = String.valueOf(queryResultSet.getString("STU_STATE"));
				String Stu_email = String.valueOf(queryResultSet.getString("STU_EMAIL"));
				generalInformation1 = new GeneralInformation(Stu_no, Stu_name, Dept_name, Stu_year, Dept_no, Dept_college, Stu_state,Stu_email);
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

		return generalInformation1;

	}
	
	
	
	
	public GeneralInformation loadSubCount(String userID) {
		GeneralInformation generalInformation1 = null;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.getConnection();
			String dml = "select count(nvl(clas_no,0)) from STUDENTCLASSDATA where CLAS_NO like '2018%' and STU_NO = ?";

			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, userID);

			ResultSet queryResultSet = pstmt.executeQuery();

			while (queryResultSet.next()) {

				String Stu_count = String.valueOf(queryResultSet.getString(1));
	
				generalInformation1 = new GeneralInformation(Stu_count);

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

		return generalInformation1;
	}
	
}
