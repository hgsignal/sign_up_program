package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.AdminInformation;
import Model.ClassInformation;
import Model.GeneralInformation;
import javafx.scene.control.Label;

public class memberEnrolDAO {

	private function fun = new function();

	//---------------------------- 학번 등록하기

	public int stuEnrolClassLoad(String Admission, String College, String StuDepartment, String StuName,
			String StuDateOfBirth, String StudentID) {

		Connection con = null;
		PreparedStatement pstmt = null, pstmt2 = null, pstmt3 = null;
		int queryResultNumber = 0;

		try {
			con = DBUtil.getConnection();
			String dml2 = "select stu_no from student where stu_no = ?";
			pstmt2 = con.prepareStatement(dml2);
			pstmt2.setString(1, StudentID);
			ResultSet queryResultSet2 = pstmt2.executeQuery();

			//------------------------------------------- 이미 존재하는 학번일 경우,등록X
			if (queryResultSet2.next()) {
				fun.alertDisplay(1, "학생등록 실패", "학생등록할 수 없음", "이미 존재하는 학번입니다. 확인해주세요.");
			}
		
			//------------------------------------------- 존재하지 않는 학번일 경우,등록O
			else {
			
				//--------------------------------------------------- 입력한 학과 이름의 학과코드를 추출
				
				String dml3 = "select dept_no from student where dept_name = ?";
				pstmt3 = con.prepareStatement(dml3);
				pstmt3.setString(1, StuDepartment);
				ResultSet queryResultSet3 = pstmt3.executeQuery();
				queryResultSet3.next();
				String DeptNo = queryResultSet3.getString(1);
				
				
				//--------------------------------------- 입학년도, 학번 입력값 속성변환
				int intAdmission = Integer.parseInt(Admission);		
				int intStudentID = Integer.parseInt(StudentID);
				int intStuDateOfBirth = Integer.parseInt(StuDateOfBirth);
				//
				int intDeptNo = Integer.parseInt(DeptNo);
				
				
				//--------------------------------------- 신입생 등록시 최초 비밀번호, 학과코드 자동입력
				
				String dml = "insert into student (Stu_admission, Dept_college, Dept_name, Stu_name, Stu_birth, Stu_no, Dept_no, GENERAL_PASSWORD, Stu_year, Stu_state) values (?, ?, ?, ?, ?, ?, ?, ?, '1', '입학 신입생')";
				
				pstmt = con.prepareStatement(dml);
				pstmt.setLong(1, intAdmission); 			
				pstmt.setString(2, College); 
				pstmt.setString(3, StuDepartment);
				pstmt.setString(4, StuName); 
				pstmt.setString(5, StuDateOfBirth); 
				pstmt.setLong(6, intStudentID);         	
				pstmt.setLong(7, intDeptNo);                
				pstmt.setLong(8, intStuDateOfBirth);

				queryResultNumber = pstmt.executeUpdate();

				fun.alertDisplay(2, "학생등록 성공", "학생등록 완료", "학번 부여를 완료했습니다. 최초 비밀번호는 학생의 생년월일입니다.");

				}

		} catch(NumberFormatException e2) {
			fun.alertDisplay(1, "학생등록 실패", "학생등록할 수 없음", "입학년도, 생년월일, 학번은 반드시 숫자를 입력해야 합니다. ");		
		} catch (SQLException e) {
			fun.alertDisplay(1, "학생등록 실패", "학생등록할 수 없음", "문자열이 올바르지 않거나 빈 칸없이 모두 입력해야 합니다.");
		} catch (Exception e1) {
			System.out.println("error = [" + e1 + "]");
		} finally {

			try {
				if (pstmt != null) {
					pstmt.close();
					pstmt2.close();
					pstmt3.close();
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
	
	
	
	//------------------------------------------- 학번 삭제하기

	public int stuDeletelClassLoad(String StudentID) {

		Connection con = null;
		PreparedStatement pstmt = null, pstmt2 = null;
		int queryResultNumber = 0;

		try {
			con = DBUtil.getConnection();
			String dml2 = "select stu_no from student where stu_no = ?";
			pstmt2 = con.prepareStatement(dml2);
			pstmt2.setString(1, StudentID);
			ResultSet queryResultSet = pstmt2.executeQuery();

			//------------------------------------------- 이미 존재하는 학번일 경우,삭제O
			if (queryResultSet.next()) {
				String dml = "delete from student where stu_no = ?";
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, StudentID);

				queryResultNumber = pstmt.executeUpdate();

				fun.alertDisplay(2, "학생번호삭제 완료", "학번삭제", "학생번호 삭제를 완료했습니다.");
			}

			// ------------------------------------------ 존재하지 않는 학번일 경우,삭제X
			else {
				fun.alertDisplay(1, "학생번호삭제 실패", "학번 삭제할 수 없음", "존재하지 않는 학번입니다.");
			}

		} catch (SQLException e) {
			fun.alertDisplay(1, "학생번호삭제 실패", "학번 삭제 실패", "학번의 문자유형이 잘못됐습니다. 학번을 다시 확인해주세요.");
		} catch (Exception e1) {
			System.out.println("error = [" + e1 + "]");
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
			} 
		} // end to finally
		return queryResultNumber;
	}
	
	
	
	//---------------------------------------------------- 학번조회
	
	public ArrayList<GeneralInformation> studentMemberLoad() {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ArrayList<GeneralInformation> studentAllList = new ArrayList<GeneralInformation>();

		try {

			con = DBUtil.getConnection();
			String dml = "select * from student order by Dept_name";
			pstmt = con.prepareStatement(dml);
			ResultSet queryResultSet = pstmt.executeQuery();

			while (queryResultSet.next()) {

				String Stu_admission = String.valueOf(queryResultSet.getString("Stu_admission"));
				String Stu_no = String.valueOf(queryResultSet.getString("Stu_no"));
				String General_password = String.valueOf(queryResultSet.getString("General_password"));
				String Stu_name = String.valueOf(queryResultSet.getString("Stu_name"));
				String Stu_birth = String.valueOf(queryResultSet.getString("Stu_birth"));
				String Stu_contact = String.valueOf(queryResultSet.getString("Stu_contact"));
				String Stu_email = String.valueOf(queryResultSet.getString("Stu_email"));
				String Stu_address = String.valueOf(queryResultSet.getString("Stu_address"));
				String Stu_year = String.valueOf(queryResultSet.getString("Stu_year"));
				String Dept_college = String.valueOf(queryResultSet.getString("Dept_college"));
				String Dept_name = String.valueOf(queryResultSet.getString("Dept_name"));
				String Dept_no = String.valueOf(queryResultSet.getString("Dept_no"));
				String Stu_state = String.valueOf(queryResultSet.getString("Stu_state"));

				GeneralInformation generalInformation = new GeneralInformation(Stu_admission, Stu_no, 
						General_password, Stu_name, Stu_birth, Stu_contact, Stu_email, 
						Stu_address, Stu_year, Dept_college, Dept_name, Dept_no, Stu_state);

				studentAllList.add(generalInformation);
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
		return studentAllList;
	}
	
	
	
	// ---------------------------------------------------- 학생이름으로 검색하기
	
	public ArrayList<GeneralInformation> studentNameLoad(String stuName) {
		
		String dml = null, dml2 = null;
		Connection con = null;
		PreparedStatement pstmt = null, pstmt2 = null;
		ArrayList<GeneralInformation> studentNameSearchList = new ArrayList<GeneralInformation>();

		try {
			con = DBUtil.getConnection();
			
			//---------------------------------------------------- 등록되지 않은 학생을 검색한 경우
			if (!stuName.equals("")) {
				dml2 = "select count(*) from STUDENT where stu_name like ?";
				pstmt2 = con.prepareStatement(dml2);
				pstmt2.setString(1, "%"+stuName+"%");
				ResultSet queryResultSet2 = pstmt2.executeQuery();
				
				while (queryResultSet2.next()) {
					String studentJudgment = String.valueOf(queryResultSet2.getString(1));

					if (studentJudgment.equals("0")) {
						fun.alertDisplay(1, "조회결과 없음", "학생이름 확인", "등록되지 않은 학생입니다.");
					}
				}
			}
			
			//---------------------------------------------------- 등록된 학생을 검색한 경우
			if (!stuName.equals("")) {

				dml = "select * from STUDENT where stu_name like ? order by STU_NAME";
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, "%"+stuName+"%");
				ResultSet queryResultSet = pstmt.executeQuery();
				
					while (queryResultSet.next()) {

					String Stu_admission = String.valueOf(queryResultSet.getString("STU_ADMISSION"));
					String Stu_no = String.valueOf(queryResultSet.getString("STU_NO"));
					String General_password = String.valueOf(queryResultSet.getString("GENERAL_PASSWORD"));
					String Stu_name = String.valueOf(queryResultSet.getString("STU_NAME"));
					String Stu_birth = String.valueOf(queryResultSet.getString("STU_BIRTH"));
					String Stu_contact = String.valueOf(queryResultSet.getString("STU_CONTACT"));
					String Stu_email = String.valueOf(queryResultSet.getString("STU_EMAIL"));
					String Stu_address = String.valueOf(queryResultSet.getString("STU_ADDRESS"));
					String Stu_year = String.valueOf(queryResultSet.getString("STU_YEAR"));
					String Dept_college = String.valueOf(queryResultSet.getString("DEPT_COLLEGE"));
					String Dept_name = String.valueOf(queryResultSet.getString("DEPT_NAME"));
					String Dept_no = String.valueOf(queryResultSet.getString("DEPT_NO"));
					String Stu_state = String.valueOf(queryResultSet.getString("STU_STATE"));

					GeneralInformation generalInformation = new GeneralInformation(Stu_admission, Stu_no, General_password, Stu_name, Stu_birth, 
							Stu_contact, Stu_email, Stu_address, Stu_year, Dept_college, Dept_name, Dept_no, Stu_state);

					studentNameSearchList.add(generalInformation);
				} 
			} else {
				fun.alertDisplay(1, "잘못된 학생이름입니다.", "학생이름 입력", "검색할 학생이름을 입력하세요.");
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
		return studentNameSearchList;
	}
	
	// ---------------------------------------------------- 사번 등록하기
	
	public int staffEnrolClassLoad(String StaffJoin, String StaffDepartment, String StaffName, String StaffDateOfBirth, String StaffID, String Staffphone) {

		Connection con = null;
		PreparedStatement pstmt = null, pstmt2 = null;
		int queryResultNumber = 0;

		try {
			con = DBUtil.getConnection();
			String dml2 = "select admin_no from ADMINUSER where admin_no = ?";
			pstmt2 = con.prepareStatement(dml2);
			pstmt2.setString(1, StaffID);
			ResultSet queryResultSet2 = pstmt2.executeQuery();


			// ------------------------------------------ 이미 존재하는 사번일 경우,등록X
			if (queryResultSet2.next()) {
				fun.alertDisplay(1, "직원등록 실패", "직원등록할 수 없음", "이미 존재하는 사번입니다. 확인해주세요.");
			}
		
			// ------------------------------------------ 존재하지 않는 사번일 경우,등록O
			else {
				
				String dml = "insert into ADMINUSER (admin_joincompany, admin_department, admin_name, admin_birth, admin_no, admin_password, admin_contact) values (?, ?, ?, ?, ?, ?, ?)";
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, StaffJoin); 			
				pstmt.setString(2, StaffDepartment); 
				pstmt.setString(3, StaffName);
				pstmt.setString(4, StaffDateOfBirth); 
				pstmt.setString(5, StaffID); 
				pstmt.setString(6, StaffDateOfBirth); // 최초비밀번호 = 직원의 생년월일
				pstmt.setString(7, Staffphone);

				queryResultNumber = pstmt.executeUpdate();

				fun.alertDisplay(2, "직원등록 성공", "직원등록 완료", "사번 부여를 완료했습니다. 최초 비밀번호는 직원의 생년월일입니다.");

				}

		} catch (SQLException e) {
			fun.alertDisplay(1, "직원등록 실패", "직원등록할 수 없음", "문자열이 올바르지 않습니다. 사번, 입사년도, 생년월일 확인필요");
		} catch (Exception e1) {
			fun.alertDisplay(1, "직원등록 실패", "직원등록할 수 없음", "사번, 직원이름, 입사년도, 생년월일은 필수입력란 입니다.");
		} 
			finally {
			try {
				if (pstmt != null) {
					pstmt.close();
					pstmt2.close();
				}
				if (con != null) {
					con.close();			
				}
			} catch (SQLException e1) {
				System.out.println("error = [" + e1 + "]"+"2");
			}
		} // end to finally
		return queryResultNumber;
	}
	
	
	//------------------------------------------- 사번 삭제하기
	

	public int staffDeletelClassLoad(String staffID) {

		Connection con = null;
		PreparedStatement pstmt = null, pstmt2 = null;
		int queryResultNumber = 0;

		try {
			con = DBUtil.getConnection();
			String dml2 = "select admin_no from adminuser where admin_no = ?";
			pstmt2 = con.prepareStatement(dml2);
			pstmt2.setString(1, staffID);
			ResultSet queryResultSet = pstmt2.executeQuery();

			//------------------------------------------- 이미 존재하는 사번일 경우,삭제O
			if (queryResultSet.next()) {
				String dml = "delete from adminuser where admin_no = ?";
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, staffID);
				queryResultNumber = pstmt.executeUpdate();

				fun.alertDisplay(2, "직원번호삭제 완료", "사번삭제", "직원번호 삭제를 완료했습니다.");
			}

			// ------------------------------------------ 존재하지 않는 사번일 경우,삭제X
			else {
				fun.alertDisplay(1, "직원번호삭제 실패", "사번 삭제할 수 없음", "존재하지 않는 사번입니다.");
			}

		} catch (SQLException e) {
			fun.alertDisplay(1, "학생번호삭제 실패", "학번 삭제 실패", "학번의 문자유형이 잘못됐습니다. 학번을 다시 확인해주세요.");
		} catch (Exception e1) {
			System.out.println("error = [" + e1 + "]");
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
			}
		} // end to finally
		return queryResultNumber;
	}
	
	
	
	//---------------------------------------------------- 직원조회
	
	public ArrayList<AdminInformation> staffMemberLoad() {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ArrayList<AdminInformation> staffAllList = new ArrayList<AdminInformation>();

		try {
			con = DBUtil.getConnection();
			
			String dml = "select Admin_joincompany, nvl(Admin_department,'부서배치중'), Admin_name, Admin_Birth, Admin_no, nvl(Admin_contact,'미입력') from adminuser order by Admin_department";
			pstmt = con.prepareStatement(dml);
			ResultSet queryResultSet = pstmt.executeQuery();

			while (queryResultSet.next()) {

				String Admin_joincompany = String.valueOf(queryResultSet.getString("Admin_joincompany").substring(0,11));
				String Admin_department = String.valueOf(queryResultSet.getString("nvl(Admin_department,'부서배치중')"));
				String Admin_name = String.valueOf(queryResultSet.getString("Admin_name"));
				String Admin_Birth = String.valueOf(queryResultSet.getString("Admin_Birth"));
				String Admin_no = String.valueOf(queryResultSet.getString("Admin_no"));
				String Admin_contact = String.valueOf(queryResultSet.getString("nvl(Admin_contact,'미입력')"));

				
				AdminInformation adminInformation = new AdminInformation(Admin_joincompany, Admin_department, 
						Admin_name,Admin_Birth, Admin_no, Admin_contact);
				staffAllList.add(adminInformation);
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
		return staffAllList;
	}
	
	
	
	//---------------------------------------------------- 직원이름 검색하기
	
	public ArrayList<AdminInformation> staffNameLoad(String staffName) {

		String dml = null, dml2 = null;
		Connection con = null;
		PreparedStatement pstmt = null, pstmt2 = null;
		ArrayList<AdminInformation> staffNameSearchList = new ArrayList<AdminInformation>();

		try {
			con = DBUtil.getConnection();
			
			//---------------------------------------------------- 등록되지 않은 직원을 검색한 경우
			if (!staffName.equals("")) {
				dml2 = "select count(*) from ADMINUSER where admin_name like ?";
				pstmt2 = con.prepareStatement(dml2);
				pstmt2.setString(1, "%"+staffName+"%");
				ResultSet queryResultSet2 = pstmt2.executeQuery();
				
				while (queryResultSet2.next()) {
					String studentJudgment = String.valueOf(queryResultSet2.getString(1));

					if (studentJudgment.equals("0")) {
						fun.alertDisplay(1, "조회결과 없음", "직원이름 확인", "등록되지 않은 직원입니다.");
					}
				}
			}

			//---------------------------------------------------- 등록된 직원을 검색한 경우
			if (!staffName.equals("")) {

				dml = "select Admin_joincompany, nvl(Admin_department,'부서배치중'), Admin_name, Admin_Birth, Admin_no, nvl(Admin_contact,'미입력') from ADMINUSER where admin_name like ? order by ADMIN_NAME";
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, "%"+staffName+"%");
				ResultSet queryResultSet = pstmt.executeQuery();

				while (queryResultSet.next()) {

					String Admin_joincompany = String.valueOf(queryResultSet.getString("ADMIN_JOINCOMPANY").substring(0,11));
					String Admin_department = String.valueOf(queryResultSet.getString("nvl(Admin_department,'부서배치중')"));
					String Admin_name = String.valueOf(queryResultSet.getString("ADMIN_NAME"));
					String Admin_Birth = String.valueOf(queryResultSet.getString("ADMIN_BIRTH"));
					String Admin_no = String.valueOf(queryResultSet.getString("ADMIN_NO"));
					String Admin_contact = String.valueOf(queryResultSet.getString("nvl(Admin_contact,'미입력')"));

					AdminInformation adminInformation = new AdminInformation(Admin_joincompany, Admin_department, 
							Admin_name, Admin_Birth, Admin_no, Admin_contact );

					staffNameSearchList.add(adminInformation);
				} 
			} else {
				fun.alertDisplay(1, "잘못된 직원이름입니다.", "직원이름 입력", "검색할 직원이름을 입력하세요.");
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
		return staffNameSearchList;
	}
}
	

