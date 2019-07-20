package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.GeneralInformation;

public class memberIDCheckViewDAO {

	private function fun = new function();
	
	public ArrayList<GeneralInformation> memberInfromationLoad(String memberName, String memberBirth) {
		
		Connection con = null;
		PreparedStatement pstmt = null, pstmt2 = null;
		ArrayList<GeneralInformation> memberSearchList = new ArrayList<GeneralInformation>();

		try {

			con = DBUtil.getConnection();
			
			
			
			//---------------------------------------------- 조건에 만족하는 사용자가 있는지 판단
			
			String dml2 = "SELECT NVL(max(stu_no), '00') FROM student WHERE stu_name = ? and stu_birth = ?";
			pstmt2 = con.prepareStatement(dml2);
			pstmt2.setString(1, memberName);
			pstmt2.setString(2, memberBirth);
			
			ResultSet queryResultSet2 = pstmt2.executeQuery();
			
			while (queryResultSet2.next()) {
			String studentJudgment = String.valueOf(queryResultSet2.getString(1));
			
			
			
			//---------------------------------------------- 조건에 만족하는 사용자가 있는 경우
			if(!studentJudgment.equals("0")) {
			
			String dml = "select * from student where stu_name = ? and stu_birth = ?";
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, memberName);
			pstmt.setString(2, memberBirth);
			
			ResultSet queryResultSet = pstmt.executeQuery();
			
			while (queryResultSet.next()) {

				String StuAdmission = String.valueOf(queryResultSet.getString("Stu_admission"));
				String stuNo = String.valueOf(queryResultSet.getString("Stu_no"));
				String generalPassword = String.valueOf(queryResultSet.getString("General_password"));
				String stuName= String.valueOf(queryResultSet.getString("Stu_name"));
				String stuBirth = String.valueOf(queryResultSet.getString("Stu_birth"));
				String memberID = String.valueOf(queryResultSet.getString("Stu_contact"));
				String stuContact = String.valueOf(queryResultSet.getString("Stu_email"));
				String stuAddress = String.valueOf(queryResultSet.getString("Stu_address"));
				String stuYear = String.valueOf(queryResultSet.getString("Stu_year"));
				String deptCollege = String.valueOf(queryResultSet.getString("Dept_college"));
				String deptName = String.valueOf(queryResultSet.getString("Dept_name"));
				String deptNo = String.valueOf(queryResultSet.getString("Dept_no"));
				String stuState = String.valueOf(queryResultSet.getString("Stu_state"));

				GeneralInformation generalInformation = new GeneralInformation(StuAdmission, stuNo, generalPassword, stuName, stuBirth, memberID, 
						stuContact, stuAddress, stuYear, deptCollege, deptName, deptNo, stuState);

						memberSearchList.add(generalInformation);
				
				}			
			
			
			
			//---------------------------------------------- 조건에 만족하는 사용자가 없는 경우
			} else if(studentJudgment.equals("0")){
				fun.alertDisplay(1, "사용자조회 실패", "조회할 수 없음", "일치하는 사용자가 없습니다."); 
			}}} 
		catch (SQLException e1) {
			fun.alertDisplay(1, "사용자조회 실패", "조회할 수 없음", "문자열이 올바르지 않습니다. 올바른 문자를 입력하세요.");
		} catch (Exception e) {
			System.out.println("error = [" + e + "]");
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();			
					pstmt2.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e1) {
				System.out.println("error = [" + e1 + "]");
			} // end of try
		}
		return memberSearchList;
	}
}
