package Controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Model.ClassInformation;
import javafx.scene.control.Label;

public class updateClassesDAO {

	private function fun = new function();
	
	
	// ---------------------------------------------------- 직원
		// -------------------------------------------------- 수강과목 올리기

		public int classUpdateLoad(String classYear, String classID, String className, String profName, String classDiv,
				String classPeople, String classtime1, String classtime2, String classLocation, String classScore) {
			Connection con = null;
			PreparedStatement pstmt = null, pstmt2 = null;
			int queryResultNumber = 0;

			try {
				con = DBUtil.getConnection();
				String dml2 = "select * from signupclassdata where clas_no = ?";
				pstmt2 = con.prepareStatement(dml2);
				pstmt2.setString(1, classID);
				ResultSet queryResultSet = pstmt2.executeQuery();

				// ------------------------------------------ 이미 등록된 과목번호일 경우,등록X
				if (queryResultSet.next()) {
					fun.alertDisplay(1, "수강등록 실패", "수강등록할 수 없음", "이미 등록된 과목번호입니다. 확인해주세요.");
				}

				// ------------------------------------------ 등록된 과목번호가 아닐 경우,등록O
				else {

					String dml = "insert into SIGNUPCLASSDATA (Clas_year, Clas_no, Clas_name, Clas_div, Clas_prof, Clas_people, Clas_time1, Clas_time2, Clas_location, Clas_score) values (?,?,?,?,?,?,?,?,?,?)";

					int intclassYear = Integer.parseInt(classYear);
					long intclassID = Long.parseLong(classID);
					int intclassPeople = Integer.parseInt(classPeople);
					int intclassScore = Integer.parseInt(classScore);

					pstmt = con.prepareStatement(dml);
					pstmt.setLong(1, intclassYear);
					pstmt.setLong(2, intclassID);
					pstmt.setString(3, className);
					pstmt.setString(4, classDiv);
					pstmt.setString(5, profName);
					pstmt.setLong(6, intclassPeople);
					pstmt.setString(7, classtime1);
					pstmt.setString(8, classtime2);
					pstmt.setString(9, classLocation);
					pstmt.setLong(10, intclassScore);

					queryResultNumber = pstmt.executeUpdate();
					fun.alertDisplay(2, "수강등록 완료", "수강등록", "수강등록을 완료했습니다.");
				}
			} catch(NumberFormatException e) {
				fun.alertDisplay(1, "수강등록 실패", "수강등록할 수 없음", "개설학년, 수강번호, 강의이름은 필수입력란 입니다. 문자유형이 올바르지 않을 수도 있습니다.");
			} catch (SQLException e2) {
				fun.alertDisplay(1, "수강등록 실패", "수강등록할 수 없음", "개설학년, 수강번호, 강의이름은 필수입력란 입니다.");
			} catch (Exception e3) {
				System.out.println("error = [" + e3 + "]");
			} finally {

				try {
					if ((pstmt != null) || (pstmt2 != null)) {
						pstmt.close();
						pstmt2.close();
					}

					if (con != null) {
						con.close();
					}
				} catch (SQLException e1) {
					System.out.println("error = [" + e1 + "]");
				} catch (NullPointerException e4) {
					
				}
			} // end to finally
			return queryResultNumber;
		}

		// ---------------------------------------------------- 직원
		// -------------------------------------------------- 수강과목 삭제하기

		public int classDeleteLoad(String classID) {

			Connection con = null;
			PreparedStatement pstmt = null, pstmt2 = null;
			int queryResultNumber = 0;

			try {
				con = DBUtil.getConnection();
				String dml2 = "select clas_no from SIGNUPCLASSDATA where clas_no = ?";
				pstmt2 = con.prepareStatement(dml2);
				pstmt2.setString(1, classID);
				ResultSet queryResultSet = pstmt2.executeQuery();

				// ------------------------------------------- 이미 존재하는 수간번호일 경우,삭제O
				if (queryResultSet.next()) {
					String dml = "delete from SIGNUPCLASSDATA where clas_no = ?";
					pstmt = con.prepareStatement(dml);
					pstmt.setString(1, classID);

					queryResultNumber = pstmt.executeUpdate();

					fun.alertDisplay(2, "수강번호삭제 완료", "수강번호삭제", "수강번호 삭제를 완료했습니다.");
				}

				// ------------------------------------------ 존재하지 않는 수강번호일 경우,삭제X
				else {
					fun.alertDisplay(1, "수강번호삭제 실패", "수강 삭제할 수 없음", "존재하지 않는 수강번호입니다.");
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

		// ---------------------------------------------------- 직원
		// -------------------------------------------------- 수강과목 전체 조회하기

		public ArrayList<ClassInformation> classIDLoad() {

			Connection con = null;
			PreparedStatement pstmt = null;
			ArrayList<ClassInformation> newClassList = new ArrayList<ClassInformation>();

			try {
				con = DBUtil.getConnection();
				String dml = "select * from SIGNUPCLASSDATA order by CLAS_NAME";
				pstmt = con.prepareStatement(dml);
				ResultSet queryResultSet = pstmt.executeQuery();

				while (queryResultSet.next()) {

					String Clas_year = String.valueOf(queryResultSet.getString("Clas_year"));
					String Clas_no = String.valueOf(queryResultSet.getString("Clas_no"));
					String Clas_name = String.valueOf(queryResultSet.getString("Clas_name"));
					String Clas_div = String.valueOf(queryResultSet.getString("Clas_div"));
					String Clas_prof = String.valueOf(queryResultSet.getString("Clas_prof"));
					String Clas_people = String.valueOf(queryResultSet.getString("Clas_people"));
					String Clas_time1 = String.valueOf(queryResultSet.getString("Clas_time1"));
					String Clas_time2 = String.valueOf(queryResultSet.getString("Clas_time2"));
					String Clas_location = String.valueOf(queryResultSet.getString("Clas_location"));
					String Clas_Score = String.valueOf(queryResultSet.getString("Clas_Score"));

					ClassInformation classInformation = new ClassInformation(Clas_year, Clas_no, Clas_name, Clas_div,
							Clas_prof, Clas_people, Clas_time1, Clas_time2, Clas_location, Clas_Score);

					newClassList.add(classInformation);
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
			return newClassList;
		}

		
		// ---------------------------------------------------- 직원
		// ---------------------------------------------- 과목명으로 검색하기

		public ArrayList<ClassInformation> classNameLoad(String className) {

			String dml = null, dml2 = null;
			Connection con = null;
			PreparedStatement pstmt = null, pstmt2 = null;
			ArrayList<ClassInformation> classNameSearchList = new ArrayList<ClassInformation>();

			try {
				con = DBUtil.getConnection();
				
				//---------------------------------------------------- 등록되지 않은 강의를 검색한 경우
				if (!className.equals("")) {
					dml2 = "select count(*) from SIGNUPCLASSDATA where clas_name like ?";
					pstmt2 = con.prepareStatement(dml2);
					pstmt2.setString(1, "%"+className+"%");
					ResultSet queryResultSet2 = pstmt2.executeQuery();
					
					while (queryResultSet2.next()) {
						String classJudgment = String.valueOf(queryResultSet2.getString(1));

						if (classJudgment.equals("0")) {
							fun.alertDisplay(1, "조회결과 없음", "강의이름 확인", "등록되지 않은 강의입니다.");
						}
					}
				}
				
				//---------------------------------------------------- 등록된 강의를 검색한 경우
				if (!className.equals("")) {

					dml = "select * from SIGNUPCLASSDATA where clas_name like ? order by CLAS_NAME";
					pstmt = con.prepareStatement(dml);
					pstmt.setString(1, "%"+className+"%");
					ResultSet queryResultSet = pstmt.executeQuery();

					while (queryResultSet.next()) {

						String Clas_year = String.valueOf(queryResultSet.getString("Clas_year"));
						String Clas_no = String.valueOf(queryResultSet.getString("Clas_no"));
						String Clas_name = String.valueOf(queryResultSet.getString("Clas_name"));
						String Clas_div = String.valueOf(queryResultSet.getString("Clas_div"));
						String Clas_prof = String.valueOf(queryResultSet.getString("Clas_prof"));
						String Clas_people = String.valueOf(queryResultSet.getString("Clas_people"));
						String Clas_time1 = String.valueOf(queryResultSet.getString("Clas_time1"));
						String Clas_time2 = String.valueOf(queryResultSet.getString("Clas_time2"));
						String Clas_location = String.valueOf(queryResultSet.getString("Clas_location"));
						String Clas_Score = String.valueOf(queryResultSet.getString("Clas_Score"));

						ClassInformation classInformation = new ClassInformation(Clas_year, Clas_no, Clas_name, Clas_div,
								Clas_prof, Clas_people, Clas_time1, Clas_time2, Clas_location, Clas_Score);

						classNameSearchList.add(classInformation);
					} 
				} else {
					fun.alertDisplay(1, "잘못된 교과목명입니다.", "교과목명 입력", "검색할 교과목명을 입력하세요.");
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
			return classNameSearchList;
		}
	
}
