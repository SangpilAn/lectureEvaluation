package lectureEvaluation.web.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Gmail extends Authenticator{

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("asp4303@gmail.com","dkstkd0879!");
	}
	
}
