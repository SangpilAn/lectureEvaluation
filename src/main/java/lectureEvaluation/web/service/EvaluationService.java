package lectureEvaluation.web.service;

import java.sql.SQLException;
import java.util.ArrayList;

import lectureEvaluation.web.entity.Evaluation;

public interface EvaluationService {
	public int write(Evaluation evaluation) throws ClassNotFoundException, SQLException;
	public ArrayList<Evaluation> getList (String lectureDivide,String searchType,String search,int pageNumber) throws ClassNotFoundException, SQLException;
	public int like(String evaluationID)throws ClassNotFoundException, SQLException;
	public int delete(String evaluationID)throws ClassNotFoundException, SQLException;
	public String getUserID(String evaluationID)throws ClassNotFoundException, SQLException;
	public int getAllList()throws ClassNotFoundException, SQLException;
}
