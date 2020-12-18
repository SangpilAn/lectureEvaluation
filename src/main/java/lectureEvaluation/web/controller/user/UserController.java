package lectureEvaluation.web.controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lectureEvaluation.web.service.UserService;

@Controller
@RequestMapping("/user/")
public class UserController{
	
	@Autowired
	private UserService userService;

	@RequestMapping("userLogin")
	public String userLogin(String userID,String userPassword,HttpSession session,HttpServletResponse response,Model model) throws SQLException, IOException {
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/html; charset=UTF-8");
		int result;
		if(userID!=null) {
			result=userService.login(userID, userPassword);
			if(result==1) {
				session.setAttribute("userID", userID);
				return "redirect:../index";
			}else if(result==0){
				PrintWriter script=response.getWriter();
				script.println("<script>");
				script.println("alert('비밀번호가 틀립니다.');");
				script.println("history.back();");
				script.println("</script>");
				script.close();
				return "user.userLogin";
			}else if(result==-1) {
				PrintWriter script=response.getWriter();
				script.println("<script>");
				script.println("alert('존재하지 않는 아이디입니다.');");
				script.println("history.back();");
				script.println("</script>");
				script.close();
				return "user.userLogin";
			}else if(result==-2) {
				PrintWriter script=response.getWriter();
				script.println("<script>");
				script.println("alert('데이터베이스 오류가 발생하였습니다.');");
				script.println("history.back();");
				script.println("</script>");
				script.close();
				return "user.userLogin";
			}
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
