package lectureEvaluation.web.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lectureEvaluation.web.entity.Evaluation;
import lectureEvaluation.web.service.EvaluationService;

@Controller
@RequestMapping("/")
public class IndexController{
	@Autowired
	private EvaluationService evaluationService;

	@RequestMapping("index")
	public String index(@RequestParam(name="pageNum",defaultValue = "0") String pageNum,
			@RequestParam(name="lectureDivide",defaultValue = "전체")String lectureDivide, 
			@RequestParam(name="searchType",defaultValue = "최신순")String searchType, 
			@RequestParam(name="search",defaultValue = "")String search, 
			ModelMap modelMap) throws ClassNotFoundException, SQLException {
		// 리스트 출력
		ArrayList<Evaluation> list=null;
		modelMap.addAttribute("pageNum", pageNum);
		
		list = evaluationService.getList(lectureDivide, searchType, search, Integer.parseInt(pageNum));
		modelMap.addAttribute("lectureDivide_m", lectureDivide);
		modelMap.addAttribute("searchType_m", searchType);
		modelMap.addAttribute("search_m", search);
		modelMap.addAttribute("list", list);
		ArrayList<Evaluation> pagingList=evaluationService.getList(lectureDivide, searchType, search, 0);
		int page=0;
		for(int i=1;i<pagingList.size()+1;i++) {
			if(i==1) {
				page+=1;
			}else if(i!=1&&i%5==1) {
				page+=1;
			}
		}
		modelMap.addAttribute("size", page);
		return "root.index";
	}

}
