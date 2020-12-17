package lectureEvaluation.web.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import lectureEvaluation.web.entity.Evaluation;
import lectureEvaluation.web.service.EvaluationService;

@Controller
@RequestMapping("/")
public class IndexController{
	@Autowired
	private EvaluationService evaluationService;

	@RequestMapping("index")
	public String index(String lectureDivide, String searchType, String search, ModelMap modelMap) throws ClassNotFoundException, SQLException {
		// 리스트 출력
		ArrayList<Evaluation> list=null;
		if(lectureDivide!=null) {
			list=evaluationService.getList(lectureDivide,
					searchType,search, 0);
			modelMap.addAttribute("lectureDivide_m", lectureDivide);
			modelMap.addAttribute("searchType_m", searchType);
		}else {
			list=evaluationService.getList("", "최신순", "", 0);
		}
		modelMap.addAttribute("list", list);
		return "root.index";
	}
	
//	@Override
//	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		ModelAndView mv=new ModelAndView("root.index");
//		mv.addObject("data","Hello Spring MVC");
//		ArrayList<Evaluation> list=null;
//		if(request.getParameter("lectureDivide")!=null) {
//			mv.addObject("lectureDivide_m", request.getParameter("lectureDivide"));
//			mv.addObject("searchType_m", request.getParameter("searchType"));
//			list=evaluationService.getList(request.getParameter("lectureDivide"),
//					request.getParameter("searchType"),request.getParameter("search"), 0);
//		}else {
//			list=evaluationService.getList("", "최신순", "", 0);
//		}
//		
//		mv.addObject("list",list);
//		return mv;
//	}

}
