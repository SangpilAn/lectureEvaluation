package lectureEvaluation.web.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import lectureEvaluation.web.entity.Evaluation;
import lectureEvaluation.web.service.EvaluationService;
import lectureEvaluation.web.service.jdbc.JDBCEvaluationService;

public class IndexController implements Controller{
	private EvaluationService evaluationService;

	public void setEvaluationService(EvaluationService evaluationService) {
		this.evaluationService = evaluationService;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv=new ModelAndView("root.index");
		mv.addObject("data","Hello Spring MVC");
		ArrayList<Evaluation> list=null;
		if(request.getParameter("lectureDivide")!=null) {
			mv.addObject("lectureDivide_m", request.getParameter("lectureDivide"));
			mv.addObject("searchType_m", request.getParameter("searchType"));
			list=evaluationService.getList(request.getParameter("lectureDivide"),
					request.getParameter("searchType"),request.getParameter("search"), 0);
		}else {
			list=evaluationService.getList("", "ÃÖ½Å¼ø", "", 0);
		}
		
		mv.addObject("list",list);
		return mv;
	}

}
