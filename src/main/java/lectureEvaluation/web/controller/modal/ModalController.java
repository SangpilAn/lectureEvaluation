package lectureEvaluation.web.controller.modal;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lectureEvaluation.web.entity.Evaluation;
import lectureEvaluation.web.service.EvaluationService;

@Controller
@RequestMapping("/inc/")
public class ModalController {
	
	@Autowired
	private EvaluationService evaluationService;
	
	@RequestMapping(value="evaluation", method=RequestMethod.POST)
	public String evaluation (Evaluation evaluation) throws ClassNotFoundException, SQLException {
		int result=evaluationService.write(evaluation);
		if(result==1) {
			return "redirect:../index";
		}else {
			return "redirect:../error/errorPage";
		}

	}
}
