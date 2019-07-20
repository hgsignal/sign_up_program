package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.RecordInformation;

public class stuRecordDAO {
	
	
	// ------------------------------------------ 수강성적을 수강년도로 검색하기
	
	public ArrayList<RecordInformation> recordInformationLoad(String userID, String classYear) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ArrayList<RecordInformation> recordLoadList = new ArrayList<RecordInformation>();

		try {

			con = DBUtil.getConnection();
			String dml = "select substr(Clas_no,1,4), clas_div, clas_year, clas_name, clas_no, clas_score, clas_prof, nvl(CLAS_MIDTERM, '미평가'), nvl(CLAS_FINALS, '미평가') from SIGNUPCLASSDATA natural join STUDENTCLASSDATA where clas_no like ? and stu_no = ? order by substr(Clas_no,1,4) desc";
			
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, classYear);
			pstmt.setString(2, userID);
			
			ResultSet queryResultSet = pstmt.executeQuery();
			while (queryResultSet.next()) {
				String Record_openYear = String.valueOf(queryResultSet.getString(1));
				String Record_div = String.valueOf(queryResultSet.getString(2));
				String Record_year = String.valueOf(queryResultSet.getString(3));
				String Record_name = String.valueOf(queryResultSet.getString(4));
				String Record_no = String.valueOf(queryResultSet.getString(5));
				String Record_score = String.valueOf(queryResultSet.getString(6));
				String Record_prof = String.valueOf(queryResultSet.getString(7));
				String Record_midterm = String.valueOf(queryResultSet.getString(8));
				String Record_finals = String.valueOf(queryResultSet.getString(9));

				RecordInformation recordInformation = new RecordInformation(Record_openYear, Record_div, Record_year, Record_name, Record_no, Record_score, Record_prof, Record_midterm, Record_finals);

				recordLoadList.add(recordInformation);
			}
		} catch (SQLException e1) {
			System.out.println("error = [" + e1 + "]");
		} catch (Exception e) {
			System.out.println("error = [" + e + "]");
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
			} // end of try
		}
		return recordLoadList;
	}
}
