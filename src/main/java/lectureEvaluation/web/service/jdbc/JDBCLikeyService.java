package lectureEvaluation.web.service.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import lectureEvaluation.web.service.LikeyService;

public class JDBCLikeyService implements LikeyService {
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public int like(String userID, String evaluationID, String userIP) throws SQLException {
		int result=0; //ȸ������ ����
		int exist=existLike(userID, evaluationID);
		if(exist==1) {
			result=-1; //�̹� ��õ��
		}
		
		String sql="insert into likey values (?,?,?)";
		Connection con=dataSource.getConnection();
		PreparedStatement st=con.prepareStatement(sql);
		st.setString(1, userID);
		st.setString(2, evaluationID);
		st.setString(3, userIP);
		result=st.executeUpdate();
		
		st.close();
		con.close();
		
		return result;
	}

	@Override
	public int existLike(String userID, String evaluationID) throws SQLException {
		int result=0;	// �����ͺ��̽� ����
		String sql="select userID from user where userID=? and evaluationID=?";
		Connection con=dataSource.getConnection();
		PreparedStatement st=con.prepareStatement(sql);
		st.setString(1, userID);
		st.setString(2, evaluationID);
		ResultSet rs=st.executeQuery();
		if(rs.next()) {
			result=-1;
		}else {
			result=1;
		}
		
		rs.close();
		st.close();
		con.close();
		
		return result;
	}

}
