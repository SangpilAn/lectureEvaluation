package lectureEvaluation.web.controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lectureEvaluation.web.entity.User;
import lectureEvaluation.web.service.UserService;
import lectureEvaluation.web.util.SHA256;

@Controller
@RequestMapping("/user/")
public class UserController{
	
	@Autowired
	private UserService userService;

	@RequestMapping("userLogin")
	public String userLogin(String userID,String userPassword,HttpSession session,HttpServletResponse response) throws SQLException, IOException {
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
				script.println("alert('��й�ȣ�� Ʋ���ϴ�.');");
				script.println("history.back();");
				script.println("</script>");
				script.close();
				return "user.userLogin";
			}else if(result==-1) {
				PrintWriter script=response.getWriter();
				script.println("<script>");
				script.println("alert('�������� �ʴ� ���̵��Դϴ�.');");
				script.println("history.back();");
				script.println("</script>");
				script.close();
				return "user.userLogin";
			}else if(result==-2) {
				PrintWriter script=response.getWriter();
				script.println("<script>");
				script.println("alert('�����ͺ��̽� ������ �߻��Ͽ����ϴ�.');");
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
	public String userReg(String userID,String userPassword,String userEmail,
			HttpSession session,HttpServletResponse response) throws SQLException, IOException {
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/html; charset=UTF-8");
		int result;
		if(userID!=null) {
			User user=new User(userID,userPassword,userEmail,SHA256.getSHA256(userEmail),false);
			result=userService.reg(user);
			if(result==1) {
				PrintWriter script=response.getWriter();
				script.println("<script>");
				script.println("alert('ȸ������ ����! �α����� �ּ���.');");
				script.println("location.href='userLogin';");
				script.println("</script>");
				script.close();
				return "user.userLogin";
			}else if(result==-1) {
				PrintWriter script=response.getWriter();
				script.println("<script>");
				script.println("alert('�̹� �����ϴ� ���̵��Դϴ�.');");
				script.println("history.back();");
				script.println("</script>");
				script.close();
				return "user.userReg";
			}
			
		}
		return "user.userReg";
	}
	

}
