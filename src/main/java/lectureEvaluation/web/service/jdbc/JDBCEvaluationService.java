package lectureEvaluation.web.service.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lectureEvaluation.web.entity.Evaluation;
import lectureEvaluation.web.service.EvaluationService;

@Service
public class JDBCEvaluationService implements EvaluationService{
//	private String url = "jdbc:mysql://localhost:3306/lectureEvaluation?serverTimezone=UTC";
//	private String uid = "root";
//	private String pwd = "1234";
//	private String driver = "com.mysql.cj.jdbc.Driver";
	@Autowired
	private DataSource dataSource;
	
	@Override
	public int write(Evaluation evaluation) throws ClassNotFoundException, SQLException {
		String userID=evaluation.getUserID();
		String lectureName=evaluation.getLectureName();
		String professorName=evaluation.getProfessorName();
		int lectureYear=evaluation.getLectureYear();
		String semesterDivide=evaluation.getSemesterDivide();
		String lectureDivide=evaluation.getLectureDivide();
		String evaluationTitle=evaluation.getEvaluationTitle();
		String evaluationContent=evaluation.getEvaluationContent();
		String totalScore=evaluation.getTotalScore();
		String creditScore=evaluation.getCreditScore();
		String comfortableScore=evaluation.getComfortableScore();
		String lectureScore=evaluation.getLectureScore();
		
//		String url="jdbc:mysql://localhost:3306/lectureEvaluation?serverTimezone=UTC";
		String sql="insert into evaluation values(null,?,?,?,?,?,?,?,?,?,?,?,?,0)";
		
//		Class.forName(driver);
//		Connection con = DriverManager.getConnection(url,uid, pwd);
		Connection con=dataSource.getConnection();
		PreparedStatement st=con.prepareStatement(sql);
		st.setString(1, userID);
		st.setString(2, lectureName);
		st.setString(3, professorName);
		st.setInt(4, lectureYear);
		st.setString(5, semesterDivide);
		st.setString(6, lectureDivide);
		st.setString(7, evaluationTitle);
		st.setString(8, evaluationContent);
		st.setString(9, totalScore);
		st.setString(10, creditScore);
		st.setString(11, comfortableScore);
		st.setString(12, lectureScore);
		
		int result=st.executeUpdate();
		
		st.close();
		con.close();

		return result;
	}
	@Override
	public ArrayList<Evaluation> getList(String lectureDivide, String searchType, String search, int pageNumber) throws ClassNotFoundException, SQLException {
		if(lectureDivide.equals("전체")) {
			lectureDivide="";
		}
		String url="jdbc:mysql://localhost:3306/lectureEvaluation?serverTimezone=UTC";
		String sql="";
		
		if(searchType.equals("최신순")) {
			sql="select * from evaluation where lectureDivide like ? and concat(lectureName,professorName,evaluationTitle,evaluationContent) like"+
					"? Order by evaluationID desc limit "+(pageNumber*5)+", "+(pageNumber*5+6);
		}else if(searchType.equals("추천순")) {
			sql="select * from evaluation where lectureDivide like ? and concat(lectureName,professorName,evaluationTitle,evaluationContent) like"+
					"? Order by likeCount desc limit "+(pageNumber*5)+", "+(pageNumber*5+6);
		}
//		Class.forName(driver);
//		Connection con = DriverManager.getConnection(url,uid, pwd);
		Connection con=dataSource.getConnection();
		PreparedStatement st=con.prepareStatement(sql);
		st.setString(1, "%"+lectureDivide+"%");
		st.setString(2, "%"+search+"%");
		ResultSet rs=st.executeQuery();
		ArrayList<Evaluation> evaluations=new ArrayList<Evaluation>();
		while(rs.next()) {
			Evaluation ev=new Evaluation(
					rs.getInt(1),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4),
					rs.getInt(5),
					rs.getString(6),
					rs.getString(7),
					rs.getString(8),
					rs.getString(9),
					rs.getString(10),
					rs.getString(11),
					rs.getString(12),
					rs.getString(13),
					rs.getInt(14)
			);
			evaluations.add(ev);
		}
		rs.close();
		st.close();
		con.close();
		
		return evaluations;
	}
	@Override
	public int like(String evaluationID) throws ClassNotFoundException, SQLException {
		String sql="update evaluation set likeCount=likeCount+1 where evaluationID=?";
//		Class.forName(driver);
//		Connection con = DriverManager.getConnection(url,uid, pwd);
		Connection con=dataSource.getConnection();
		PreparedStatement st=con.prepareStatement(sql);
		st.setInt(1, Integer.parseInt(evaluationID) );
		int result=st.executeUpdate();
		st.close();
		con.close();
		return result;
	}
	@Override
	public int delete(String evaluationID) throws ClassNotFoundException, SQLException {
		String sql="delete from evaluation where evaluationID=?";
//		Class.forName(driver);
//		Connection con = DriverManager.getConnection(url,uid, pwd);
		Connection con=dataSource.getConnection();
		PreparedStatement st=con.prepareStatement(sql);
		st.setInt(1, Integer.parseInt(evaluationID));
		int result=st.executeUpdate();
		
		st.close();
		con.close();
		return result;
	}
	@Override
	public String getUserID(String evaluationID) throws ClassNotFoundException, SQLException {
		String sql="delete from evaluation where evaluationID=?";
//		Class.forName(driver);
//		Connection con = DriverManager.getConnection(url,uid, pwd);
		Connection con=dataSource.getConnection();
		PreparedStatement st=con.prepareStatement(sql);
		st.setInt(1, Integer.parseInt(evaluationID));
		ResultSet rs=st.executeQuery();
		String result=null;
		if(rs.next()) {
			result=rs.getString(1);
		}
		
		rs.close();
		st.close();
		con.close();
		return result;
	}
	@Override
	public int getPage(String lectureDivide,String searchType,String search) throws ClassNotFoundException, SQLException {
		if(lectureDivide.equals("전체")) {
			lectureDivide="";
		}
		String sql="";
		if(searchType.equals("최신순")) {
			sql="select * from evaluation where lectureDivide like ? and concat(lectureName,professorName,evaluationTitle,evaluationContent) like"+
					"?";
		}else if(searchType.equals("추천순")) {
			sql="select * from evaluation where lectureDivide like ? and concat(lectureName,professorName,evaluationTitle,evaluationContent) like"+
					"?";
		}
		Connection con=dataSource.getConnection();
		PreparedStatement st=con.prepareStatement(sql);
		st.setString(1, "%"+lectureDivide+"%");
		st.setString(2, "%"+search+"%");
		ResultSet rs=st.executeQuery();
		int temp=0;
		int result=0;
		while(rs.next()) {
			temp+=1;
		}
		for(int i=1;i<temp+1;i++) {
			if(i==1) {
				result+=1;
			}else if(i!=1&&i%5==1) {
				result+=1;
			}
		}
		rs.close();
		st.close();
		con.close();
		return result;
	}
	
	
}
