import java.sql.ResultSet;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;

public class MailHandler {	
	public static void sendEmail(int deviceId, DBHandler dbHandler, ResultSet patientInfo, Boolean isSMS) {
		   Properties properties = new Properties();
		   properties.put("mail.smtp.host", "smtp.gmail.com");
		   properties.put("mail.smtp.socketFactory.port", "465");
		   properties.put("mail.smtp.socketFactory.class",
		           "javax.net.ssl.SSLSocketFactory");
		   properties.put("mail.smtp.auth", "true");
		   properties.put("mail.smtp.port", "465");
		   final String password = Auth.getSenderPwd();
		   Session session = Session.getDefaultInstance(properties,
		              new javax.mail.Authenticator() {
		              protected PasswordAuthentication getPasswordAuthentication() {
		                 return new PasswordAuthentication("dropmeter.cs595@gmail.com", password);
		              }
		   });
		   
		   try {
			   
			ResultSet emails = dbHandler.getStaffEmail();
			
			String staffEmail = "";
			while(emails.next()) {
				staffEmail = emails.getString("Staff_Email");
				if(isSMS) {
					  staffEmail = emails.getString("Staff_Phone") + "@tmomail.net";
					}
				  Message message = new MimeMessage(session);
				  message.setFrom(new InternetAddress("dropmeter.cs595@gmail.com", "Bilg")); // Use Sender Email
				  message.setRecipients(Message.RecipientType.TO,
				       InternetAddress.parse(staffEmail));
				  message.setSubject("Alert for " + patientInfo.getString("Patient_Name"));
				  message.setText("Dear Staff,"
				 + "\n\n This is an alert send for patient from Device:" + deviceId
				 + "\n Patient id : " + patientInfo.getString("Patient_id")
				 + "\n Patient name : " + patientInfo.getString("Patient_Name")
				 /*+ "\n Address : " + patientInfo.getString("ADDRESS")
				 + "\n Phone : " + patientInfo.getString("Phone")*/);
				  Transport.send(message);
				  Thread.sleep(100);
			}
			  
		    

		      System.out.println("Done");

		   } catch (MessagingException e) {
		       throw new RuntimeException(e);
		   }
		   catch (Exception e) {
			   e.printStackTrace();
		   }
		}
	 
	 public static void main(String[] args) {
		 //MailHandler.sendEmail(101, , false);
	 }
}
