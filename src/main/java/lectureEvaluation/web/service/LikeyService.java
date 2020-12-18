package lectureEvaluation.web.service;

import java.sql.SQLException;

public interface LikeyService {
	public int like(String userID,String evaluationID, String userIP) throws SQLException;
	public int existLike(String userID,String evaluationID)throws SQLException;
}
