package lectureEvaluation.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lectureEvaluation.web.entity.Evaluation;
import lectureEvaluation.web.service.EvaluationService;
import lectureEvaluation.web.service.UserService;
import lectureEvaluation.web.service.jdbc.JDBCUserService;

@Controller
@RequestMapping("/")
public class IndexController{
	@Autowired
	private EvaluationService evaluationService;
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping("index")
	public String index(@RequestParam(name="pageNum",defaultValue = "0") String pageNum,
			@RequestParam(name="lectureDivide",defaultValue = "전체")String lectureDivide, 
			@RequestParam(name="searchType",defaultValue = "최신순")String searchType, 
			@RequestParam(name="search",defaultValue = "")String search,
			HttpServletResponse response,
			HttpSession session,
			ModelMap modelMap) throws ClassNotFoundException, SQLException, IOException {
		
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/html; charset=UTF-8");
		
		// 로그인 체크
		String sessionCheck=(String) session.getAttribute("userID");
		if(sessionCheck==null) {
			return "redirect: /user/userLogin";
		}
		
		// 이메일 체크
		boolean emailChecked=userService.getUserEmailChecked(sessionCheck);
		if(emailChecked==false){
			PrintWriter script=response.getWriter();
			script.println("<script>");
			script.println("location.href='/user/emailSendConfirm';");
			script.println("</script>");
			script.close();
		}

		// 리스트 출력
		ArrayList<Evaluation> list=null;
		list = evaluationService.getList(lectureDivide, searchType, search, Integer.parseInt(pageNum));
		modelMap.addAttribute("pageNum", pageNum);
		modelMap.addAttribute("lectureDivide_m", lectureDivide);
		modelMap.addAttribute("searchType_m", searchType);
		modelMap.addAttribute("search_m", search);
		modelMap.addAttribute("list", list);
		int page=evaluationService.getPage(lectureDivide,searchType,search);
		modelMap.addAttribute("size", page);
		return "root.index";
	}

}
