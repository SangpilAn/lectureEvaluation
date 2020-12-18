package lectureEvaluation.web.service;

import java.sql.SQLException;

import lectureEvaluation.web.entity.User;

public interface UserService {
	public int login(String userID, String userPassword) throws SQLException;
	public int reg(User user) throws SQLException;
	public String getUserEmail(String userID) throws SQLException;
	public boolean getUserEmailChecked(String userID) throws SQLException;
	public boolean setUserEmailChecked(String userID) throws SQLException;
	public boolean getUser(String userID) throws SQLException;
}
