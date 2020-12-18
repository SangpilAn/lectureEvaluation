package lectureEvaluation.web.controller.user;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lectureEvaluation.web.service.UserService;

@Controller
@RequestMapping("/user/")
public class UserController{
	
	@Autowired
	private UserService userService;

	@RequestMapping("userLogin")
	public String userLogin(String userID,String userPassword,HttpSession session) throws SQLException {
		int result=userService.login(userID, userPassword);
		if(result==1) {
			session.setAttribute("userID", userID);
			return "redirect:../index";
		}
		return "user.userLogin";
	}
	@RequestMapping("userLogout")
	public String userLogout(HttpSession session) {
		session.removeAttribute("userID");
		return "redirect:../index";
	}
	
	@RequestMapping("userReg")
	public String userReg() {

		return "user.userReg";
	}
	

}
