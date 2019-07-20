package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.ClassInformation;
import Model.GeneralInformation;
import Model.IndispensableClassInformation;
import Model.PersonalClassInformation;

public class signupClassDAO {

	private function fun = new function();
	
	
	//---------------------------- 수강편람 첫 화면
	
	public ArrayList<ClassInformation> classAllLoad() {
		
		String dml = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ArrayList<ClassInformation> list = new ArrayList<ClassInformation>();

		try {
			con = DBUtil.getConnection();
			dml = "select * from SIGNUPCLASSDATA where clas_no like '2018%' order by clas_year";
			pstmt = con.prepareStatement(dml);
			
			ResultSet queryResultSet = pstmt.executeQuery();

			while (queryResultSet.next()) {
				String Year = String.valueOf(queryResultSet.getString(1));
				String ClassNumber = String.valueOf(queryResultSet.getString(2));
				String ClassName = String.valueOf(queryResultSet.getString(3));
				String FinishDiv = String.valueOf(queryResultSet.getString(4));
				String Profname = String.valueOf(queryResultSet.getString(5));
				String ClassPeople = String.valueOf(queryResultSet.getString(6));
				String ClassTime1 = String.valueOf(queryResultSet.getString(7));
				String ClassTime2 = String.valueOf(queryResultSet.getString(8));
				String ClassLocation = String.valueOf(queryResultSet.getString(9));
				String ClassScore = String.valueOf(queryResultSet.getString(10));

				ClassInformation classInformation = new ClassInformation(Year, ClassNumber, ClassName, FinishDiv,
						Profname, ClassPeople, ClassTime1, ClassTime2, ClassLocation, ClassScore);

				list.add(classInformation);
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
		return list;
		
	}
	
	

	//---------------------------- 수강편람에서 "학년/이수구분/수강번호/교수이름" 조건 검색

	public ArrayList<ClassInformation> classAllLoad(String classYear, String classDiv, String className,
			String profName) {

		String dml = null, dml2 = null;
		Connection con = null;
		PreparedStatement pstmt = null, pstmt2 = null;
		ArrayList<ClassInformation> list = new ArrayList<ClassInformation>();

		try {
			con = DBUtil.getConnection();

			// --------------------------- 교수 검색결과가 있는지, 없는지 판단

			if (!profName.equals("")) {

				dml2 = "SELECT count(*) FROM SIGNUPCLASSDATA WHERE clas_no like '2018%' and clas_prof like ?";
				pstmt2 = con.prepareStatement(dml2);
				pstmt2.setString(1, "%"+profName+"%");
				ResultSet queryResultSet2 = pstmt2.executeQuery();

				while (queryResultSet2.next()) {
					String classJudgment = String.valueOf(queryResultSet2.getString(1));

					if (classJudgment.equals("0")) {
						fun.alertDisplay(1, "강의조회 실패", "조회할 수 없음", "검색한 교수의 강의가 없습니다.");
					}
				}
			}

			// --------------------------- 강의이름 검색결과가 있는지, 없는지 판단

			if (!className.equals("")) {

				dml2 = "SELECT count(*) FROM SIGNUPCLASSDATA WHERE clas_no like '2018%' and clas_name like ?";
				pstmt2 = con.prepareStatement(dml2);
				pstmt2.setString(1, "%"+className+"%");
				ResultSet queryResultSet2 = pstmt2.executeQuery();

				while (queryResultSet2.next()) {
					String classJudgment = String.valueOf(queryResultSet2.getString(1));
					if (classJudgment.equals("0")) {
						fun.alertDisplay(1, "강의조회 실패", "조회할 수 없음", "강의가 없습니다.");
					}
				}
			}

			if (classYear.equals("전체") && classDiv.equals("전체") && className.equals("") && profName.equals("")) {
				dml = "select * from SIGNUPCLASSDATA where clas_no like '2018%' order by clas_name";
				pstmt = con.prepareStatement(dml);

			} else if (classYear.equals("전체") && !classDiv.equals("전체") && !className.equals("")
					&& !profName.equals("")) {
				dml = "select * from SIGNUPCLASSDATA where clas_no like '2018%' and clas_div = ? and clas_name like ? and clas_prof like ? order by clas_name";
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, classDiv);
				pstmt.setString(2, "%"+className+"%");
				pstmt.setString(3, "%"+profName+"%");

			} else if (classDiv.equals("전체") && !classYear.equals("전체") && !className.equals("")
					&& !profName.equals("")) {
				dml = "select * from SIGNUPCLASSDATA where clas_no like '2018%' and clas_year = ? and clas_name like ? and clas_prof like ? order by clas_name";
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, classYear);
				pstmt.setString(2, "%"+className+"%");
				pstmt.setString(3, "%"+profName+"%");

			} else if (profName.equals("") && !classYear.equals("전체") && !className.equals("")
					&& !classDiv.equals("전체")) {
				dml = "select * from SIGNUPCLASSDATA where clas_no like '2018%' and clas_year = ? and clas_div = ? and clas_name like ? order by clas_name";
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, classYear);
				pstmt.setString(2, classDiv);
				pstmt.setString(3, "%"+className+"%");

			} else if (className.equals("") && !classYear.equals("전체") && !profName.equals("")
					&& !classDiv.equals("전체")) {
				dml = "select * from SIGNUPCLASSDATA where clas_no like '2018%' and clas_year = ? and clas_div = ? and clas_prof like ? order by clas_name";
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, classYear);
				pstmt.setString(2, classDiv);
				pstmt.setString(3, "%"+profName+"%");

			} else if (classYear.equals("전체") && classDiv.equals("전체") && !profName.equals("")
					&& !className.equals("")) {
				dml = "select * from SIGNUPCLASSDATA where clas_no like '2018%' and clas_name like ? and clas_prof like ? order by clas_name";
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, "%"+className+"%");
				pstmt.setString(2, "%"+profName+"%");

			} else if (classYear.equals("전체") && className.equals("") && !profName.equals("")
					&& !classDiv.equals("전체")) {
				dml = "select * from SIGNUPCLASSDATA where clas_no like '2018%' and clas_div = ? and clas_prof like ? order by clas_name";
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, classDiv);
				pstmt.setString(2, "%"+profName+"%");

			} else if (classYear.equals("전체") && profName.equals("") && !className.equals("")
					&& !classDiv.equals("전체")) {
				dml = "select * from SIGNUPCLASSDATA where clas_no like '2018%' and clas_name like ? and clas_div = ? order by clas_name";
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, "%"+className+"%");
				pstmt.setString(2, classDiv);

			} else if (classDiv.equals("전체") && className.equals("") && !profName.equals("")
					&& !classYear.equals("전체")) {
				dml = "select * from SIGNUPCLASSDATA where clas_no like '2018%' and clas_year = ? and clas_prof like ? order by clas_name";
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, classYear);
				pstmt.setString(2, "%"+profName+"%");

			} else if (classDiv.equals("전체") && profName.equals("") && !className.equals("")
					&& !classYear.equals("전체")) {
				dml = "select * from SIGNUPCLASSDATA where clas_no like '2018%' and clas_year = ? and clas_name like ? order by clas_name";
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, classYear);
				pstmt.setString(2, "%"+className+"%");

			} else if (className.equals("") && profName.equals("") && !classDiv.equals("전체")
					&& !classYear.equals("전체")) {
				dml = "select * from SIGNUPCLASSDATA where clas_no like '2018%' and clas_year=? and clas_div = ? order by clas_name";
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, classYear);
				pstmt.setString(2, classDiv);

			} else if (classYear.equals("전체") && classDiv.equals("전체") && className.equals("")
					&& !profName.equals("")) {
				dml = "select * from SIGNUPCLASSDATA where clas_no like '2018%' and clas_prof like ? order by clas_name";
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, "%"+profName+"%");

			} else if (!classYear.equals("전체") && className.equals("") && profName.equals("")
					&& classDiv.equals("전체")) {
				dml = "select * from SIGNUPCLASSDATA where clas_no like '2018%' and clas_year = ? order by clas_name";
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, classYear);

			} else if (classYear.equals("전체") && className.equals("") && profName.equals("")
					&& !classDiv.equals("전체")) {
				dml = "select * from SIGNUPCLASSDATA where clas_no like '2018%' and clas_div = ? order by clas_name";
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, classDiv);

			} else if (classYear.equals("전체") && classDiv.equals("전체") && profName.equals("")
					&& !className.equals("")) {
				dml = "select * from SIGNUPCLASSDATA where clas_no like '2018%' and clas_name like ? order by clas_name";
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, "%"+className+"%");

			} else if (!classYear.equals("전체") && !classDiv.equals("전체") && !profName.equals("")
					&& !className.equals("")) {
				dml = "select * from SIGNUPCLASSDATA where clas_no like '2018%' and clas_year=? and clas_div=? and clas_name like ? and clas_prof like ? order by clas_name";
				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, classYear);
				pstmt.setString(2, classDiv);
				pstmt.setString(3, "%"+className+"%");
				pstmt.setString(4, "%"+profName+"%");
			} else {
				fun.alertDisplay(1, "수강조회 실패", "수강조회할 수 없음", "검색조건을 확인해주세요.");
			}

			ResultSet queryResultSet = pstmt.executeQuery();

			while (queryResultSet.next()) {
				String Year = String.valueOf(queryResultSet.getString(1));
				String ClassNumber = String.valueOf(queryResultSet.getString(2));
				String ClassName = String.valueOf(queryResultSet.getString(3));
				String FinishDiv = String.valueOf(queryResultSet.getString(4));
				String Profname = String.valueOf(queryResultSet.getString(5));
				String ClassPeople = String.valueOf(queryResultSet.getString(6));
				String ClassTime1 = String.valueOf(queryResultSet.getString(7));
				String ClassTime2 = String.valueOf(queryResultSet.getString(8));
				String ClassLocation = String.valueOf(queryResultSet.getString(9));
				String ClassScore = String.valueOf(queryResultSet.getString(10));

				ClassInformation classInformation = new ClassInformation(Year, ClassNumber, ClassName, FinishDiv,
						Profname, ClassPeople, ClassTime1, ClassTime2, ClassLocation, ClassScore);

				list.add(classInformation);
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
		return list;
	}
	
	

	// ------------------------------------------ 해당 로그인 학생의 수강과목 조회하기

	public ArrayList<PersonalClassInformation> personalClassLoad(String userID) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ArrayList<PersonalClassInformation> personalList = new ArrayList<PersonalClassInformation>();

		try {

			con = DBUtil.getConnection();
			String dml = "select * from SIGNUPCLASSDATA natural join STUDENTCLASSDATA where clas_no like '2018%' and stu_no = ? order by clas_name";

			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, userID);

			ResultSet queryResultSet = pstmt.executeQuery();

			while (queryResultSet.next()) {
				
				String Year = String.valueOf(queryResultSet.getString("CLAS_YEAR"));
				String ClassNumber = String.valueOf(queryResultSet.getString("CLAS_NO"));
				String ClassName = String.valueOf(queryResultSet.getString("CLAS_NAME"));
				String FinishDiv = String.valueOf(queryResultSet.getString("CLAS_DIV"));
				String Profname = String.valueOf(queryResultSet.getString("CLAS_PROF"));
				String ClassPeople = String.valueOf(queryResultSet.getString("CLAS_PEOPLE"));
				String ClassTime1 = String.valueOf(queryResultSet.getString("CLAS_TIME1"));
				String ClassTime2 = String.valueOf(queryResultSet.getString("CLAS_TIME2"));
				String ClassLocation = String.valueOf(queryResultSet.getString("CLAS_LOCATION"));
				String ClassScore = String.valueOf(queryResultSet.getString("CLAS_SCORE"));

				PersonalClassInformation personalClassInformation = new PersonalClassInformation(Year, ClassNumber,
						ClassName, FinishDiv, Profname, ClassPeople, ClassTime1, ClassTime2, ClassLocation, ClassScore);

				personalList.add(personalClassInformation);
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
		return personalList;
	}
	
	

	// ------------------------------------------ 해당 로그인 학생의 필수과목 조회하기

	public ArrayList<IndispensableClassInformation> indispensableClassLoad(String userID) {

		Connection con = null;
		PreparedStatement pstmt = null;
		ArrayList<IndispensableClassInformation> list = new ArrayList<IndispensableClassInformation>();

		try {
			
			con = DBUtil.getConnection();
			String dml = "select * from signupclassdata where clas_no in (select clas_no from indispensableclass natural join student where stu_no = ?) order by clas_name";
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, userID);

			ResultSet queryResultSet = pstmt.executeQuery();
			while (queryResultSet.next()) {
				String classNo = String.valueOf(queryResultSet.getString("clas_no"));
				String indiClass = String.valueOf(queryResultSet.getString("clas_name"));
				String classScore = String.valueOf(queryResultSet.getString("clas_score"));
				String classProf = String.valueOf(queryResultSet.getString("clas_prof"));

				IndispensableClassInformation indispensableClass = new IndispensableClassInformation(classNo,
						indiClass, classScore, classProf);

				list.add(indispensableClass);

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
		return list;
	}

	
	
	// ------------------------------------------ 해당 로그인한 학생의 신청한 학점 확인하기

	public ClassInformation loadSignupScore(String userID) {

		ClassInformation classInformation1 = null;
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = DBUtil.getConnection();
			String dml = "select nvl(sum(nvl(clas_score, 0)),0) from STUDENTCLASSDATA where clas_no like '2018%' and stu_no = ?";
			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, userID);
			ResultSet queryResultSet = pstmt.executeQuery();

			while (queryResultSet.next()) {
				String Clas_Score = String.valueOf(queryResultSet.getString(1));
				classInformation1 = new ClassInformation(Clas_Score);

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
		return classInformation1;
	}
	

	
	// ------------------------------------------ 수강신청하기

	public int siunupClass(String userID, String subNo, String strSubName, String subScore) {

		String dml = null, dml2 = null; 
		Connection con = null;
		PreparedStatement pstmt = null, pstmt2 = null;
		int queryResultNumber = 0;

		try {
			con = DBUtil.getConnection();
			dml2 = "select * from studentclassdata where stu_no = ? and clas_no in (select clas_no from signupclassdata where clas_name = ?)";
			pstmt2 = con.prepareStatement(dml2);
			pstmt2.setString(1, userID);
			pstmt2.setString(2, strSubName);
			ResultSet queryResultSet = pstmt2.executeQuery();

			// ------------------------------------------ 이미 신청한 과목일 경우,신청X
			if (queryResultSet.next()) {
				fun.alertDisplay(1, "수강신청 실패", "수강신청할 수 없음", "이미 신청한 과목입니다. 확인해주세요.");
			}

			// ------------------------------------------ 신청한 과목이 아닐 경우,신청O
			else {
				dml = "insert into studentclassdata values (?,?,?,null,null)";

				pstmt = con.prepareStatement(dml);
				pstmt.setString(1, userID);
				pstmt.setString(2, subNo);
				pstmt.setString(3, subScore);
				queryResultNumber = pstmt.executeUpdate();

				fun.alertDisplay(2, "수강신청 확인", "수강신청", "수강신청을 완료했습니다.");

			}
		} catch (SQLException e) {
			System.out.println("error = [" + e + "]");
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

	
	
	// ------------------------------------------ 수강삭제하기

	public int deleteClass(String userID, String subno) {

		Connection con = null;
		PreparedStatement pstmt = null;
		int queryResultNumber = 0;

		try {
			con = DBUtil.getConnection();

			String dml = "delete from studentclassdata where stu_no=? and clas_no= ? ";

			pstmt = con.prepareStatement(dml);
			pstmt.setString(1, userID);
			pstmt.setString(2, subno);

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
}
