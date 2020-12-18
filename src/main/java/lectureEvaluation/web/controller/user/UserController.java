package lectureEvaluation.web.controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lectureEvaluation.web.entity.User;
import lectureEvaluation.web.service.UserService;
import lectureEvaluation.web.util.Gmail;
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
	public String userReg(String userID,String userPassword,String userEmail,
			HttpSession session,HttpServletResponse response) throws SQLException, IOException {
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/html; charset=UTF-8");
		int result;
		if(userID!=null) {
			User user=new User(userID,userPassword,userEmail,SHA256.getSHA256(userEmail),false);
			result=userService.reg(user);
			if(result==1) {
				session.setAttribute("userID", userID);
				PrintWriter script=response.getWriter();
				script.println("<script>");
				script.println("location.href='emailSend';");
				script.println("</script>");
				script.close();
				return "user.userLogin";
			}else if(result==-1) {
				PrintWriter script=response.getWriter();
				script.println("<script>");
				script.println("alert('이미 존재하는 아이디입니다.');");
				script.println("history.back();");
				script.println("</script>");
				script.close();
				return "user.userReg";
			}
			
		}
		return "user.userReg";
	}
	
	@RequestMapping("emailSend")
	public String emailSend(HttpSession session,HttpServletResponse response) throws SQLException, IOException {
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/html; charset=UTF-8");
		String userID=(String) session.getAttribute("userID");
		boolean emailChecked=userService.getUserEmailChecked(userID);
		if(emailChecked==true){
			PrintWriter script=response.getWriter();
			script.println("<script>");
			script.println("alert('이미 인증된 회원입니다.');");
			script.println("location.href='../index';");
			script.println("</script>");
			script.close();
		}
		
		// 메일 발송
		String host="localhost:8081/";
		String from ="asp4303@gmail.com";
		String to = userService.getUserEmail(userID);
		String subject="강의평가를 위한 이메일 인증 메일입니다.";
		String content="다음 링크에 접속하여 이메일 인증을 진행하세요. "+
			"<a href='"+host+"/user/emailCheck?code="+new SHA256().getSHA256(to)+"'>이메일 인증하기</a>";
		Properties p=new Properties();
		p.put("mail.smtp.user",from);
		p.put("mail.smtp.host","smtp.googlemail.com");
		p.put("mail.smtp.port","465");
		p.put("mail.smtp.starttls.enable","true");
		p.put("mail.smtp.auth","true");
		p.put("mail.smtp.debug","true");
		p.put("mail.smtp.socketFactory.port","465");
		p.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		p.put("mail.smtp.socketFactory.fallback","false");
				
		try{
			Authenticator auth=new Gmail();
			Session ses=Session.getInstance(p,auth);
			ses.setDebug(true);
			MimeMessage msg=new MimeMessage(ses);
			msg.setSubject(subject);
			Address fromAddr=new InternetAddress(from);
			msg.setFrom(fromAddr);
			Address toAddr=new InternetAddress(to);
			msg.addRecipient(Message.RecipientType.TO,toAddr);
			msg.setContent(content,"text/html;charset=UTF8");
			Transport.send(msg);
		}catch(Exception e){
			e.printStackTrace();
			PrintWriter script=response.getWriter();
			script.println("<script>");
			script.println("alert('오류가 발생했습니다.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
		}
		
		
		
		return "user.emailSendConfirm";
	}
	
	@RequestMapping("emailSendConfirm")
	public String emailSendConfirm() {
		return "user.emailSendConfirm";
	}
	
	@RequestMapping("emailCheck")
	public String emailCheck(String code,HttpSession session,HttpServletResponse response) throws SQLException, IOException {
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/html; charset=UTF-8");
		String userID=(String) session.getAttribute("userID");
		String userEmail=userService.getUserEmail(userID);
		
		boolean isRight=(new SHA256().getSHA256(userEmail).equals(code))?true:false;
		if(isRight==true){
			userService.setUserEmailChecked(userID);
			PrintWriter script=response.getWriter();
			script.println("<script>");
			script.println("alert('인증에 성공했습니다.');");
			script.println("location.href='../../index';");
			script.println("</script>");
			script.close();
			
		}else{
			PrintWriter script=response.getWriter();
			script.println("<script>");
			script.println("alert('유효하지 않은 코드입니다.');");
			script.println("location.href='../../index';");
			script.println("</script>");
			script.close();
			
		}
		return "user.emailCheck";
	}
}
