package lectureEvaluation.web.service.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lectureEvaluation.web.entity.User;
import lectureEvaluation.web.service.UserService;

@Service
public class JDBCUserService implements UserService {
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public int login(String userID, String userPassword) throws SQLException {
		
		int result=-2;	// 데이터베이스 오류
		String sql="select userpassword from user where userID=?";
		Connection con=dataSource.getConnection();
		PreparedStatement st=con.prepareStatement(sql);
		st.setString(1, userID);
		ResultSet rs=st.executeQuery();
		if(rs.next()) {
			if(rs.getString(1).equals(userPassword)) {
				result=1; //로그인 성공
			}else {
				result=0; //비밀번호 틀림
			}
		}else {
			result=-1; //아이디 없음
		}
		rs.close();
		st.close();
		con.close();
		
		return result;
	}

	@Override
	public int reg(User user) throws SQLException {
		
		String userId=user.getUserID();
		String userPassword=user.getUserPassword();
		String userEmail=user.getUserEmail();
		String userEmailHash=user.getUserEmailHash();
		
		int result=0; //회원가입 실패
		String sql="insert into user values (?,?,?,?,false)";
		Connection con=dataSource.getConnection();
		PreparedStatement st=con.prepareStatement(sql);
		st.setString(1, userId);
		st.setString(2, userPassword);
		st.setString(3, userEmail);
		st.setString(4, userEmailHash);
		result=st.executeUpdate();
		
		st.close();
		con.close();
		
		return result;
	}

	@Override
	public String getUserEmail(String userID) throws SQLException {
		
		String result=null;
		String sql="select userEmail from user where userID=?";
		Connection con=dataSource.getConnection();
		PreparedStatement st=con.prepareStatement(sql);
		st.setString(1, userID);
		ResultSet rs=st.executeQuery();
		if(rs.next()) {
			result=rs.getString(1);
		}
		
		rs.close();
		st.close();
		con.close();
		
		return result;
	}

	@Override
	public boolean getUserEmailChecked(String userID) throws SQLException {
		boolean result=false;
		String sql="select userEmailChecked from user where userID=?";
		Connection con=dataSource.getConnection();
		PreparedStatement st=con.prepareStatement(sql);
		st.setString(1, userID);
		ResultSet rs=st.executeQuery();
		if(rs.next()) {
			result=rs.getBoolean(1);
		}
		
		rs.close();
		st.close();
		con.close();
		
		return result;
	}

	@Override
	public boolean setUserEmailChecked(String userID) throws SQLException {
		boolean result=false;
		String sql="update user set userEmailChecked=true where userID=?";
		Connection con=dataSource.getConnection();
		PreparedStatement st=con.prepareStatement(sql);
		st.setString(1,userID);
		if(st.executeUpdate()==1) {
			result=true;
		}
		
		st.close();
		con.close();
		
		return result;
	}

}
