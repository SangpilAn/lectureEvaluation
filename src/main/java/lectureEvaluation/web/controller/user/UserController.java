package lectureEvaluation.web.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/")
public class UserController{

	@RequestMapping("userLogin")
	public String userLogin() {

		return "user.userLogin";
	}
	
	@RequestMapping("userReg")
	public String userReg() {

		return "user.userReg";
	}
	

}
