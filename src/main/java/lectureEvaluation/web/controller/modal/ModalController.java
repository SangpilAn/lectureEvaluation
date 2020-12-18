package lectureEvaluation.web.controller.modal;

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
import org.springframework.web.bind.annotation.RequestMethod;

import lectureEvaluation.web.entity.Evaluation;
import lectureEvaluation.web.service.EvaluationService;
import lectureEvaluation.web.util.Gmail;

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
	
	@RequestMapping(value="report", method=RequestMethod.POST)
	public String report (String userID,String reportTitle,String reportContent,
			HttpSession session, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException {
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/html; charset=UTF-8");
		System.out.println(reportTitle);
		userID=(String) session.getAttribute("userID");
		if(reportTitle==""||reportContent==""){
			PrintWriter script=response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안 된 사항이 있습니다.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
		}else {
			String host="localhost:8081/";
			String from ="asp4303@gmail.com";
			String to = "asp4303@naver.com";
			String subject="강의평가 사이트에서 접수된 신고 메일입니다.";
			String content="신고자: "+userID+"<br>제목: "+reportTitle+"<br>내용: "+reportContent;
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
			PrintWriter script=response.getWriter();
			script.println("<script>");
			script.println("alert('정상적으로 신고되었습니다.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
			
			
			
			return "location.href='../../index';";
		}
		return "location.href='../../index';";
	}
}
