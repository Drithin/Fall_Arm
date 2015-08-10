import java.util.Properties;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;

public class MailHandler {	
	public static void sendEmail(int deviceId, Boolean isSMS) {
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
		   DatabaseDaoImpl dbService = new DatabaseDaoImpl(isSMS);
		   String staffEmail = dbService.getStaffEmailId();
		   //dbService.getStaffEmailId(deviceId);
		   
		   try {
		      Message message = new MimeMessage(session);
		      message.setFrom(new InternetAddress("dropmeter.cs595@gmail.com", "Bilg")); // Use Sender Email
		      message.setRecipients(Message.RecipientType.TO,
		           InternetAddress.parse(staffEmail));
		      message.setSubject("Alert for XXX");
		      message.setText("Dear Staff,"
		         + "\n\n This is an alert send for patient from Device:"
		         + deviceId);
		      // To Do: get patientID from DB and attach to message
		      Transport.send(message);

		      System.out.println("Done");

		   } catch (MessagingException e) {
		       throw new RuntimeException(e);
		   }
		   catch (Exception e) {
			   e.printStackTrace();
		   }
		}
	
	 public  static class DatabaseDaoImpl {
		  public Boolean isSMS; 
	      public String StaffEmailId;
	      public int StudentID;
	      
	      public DatabaseDaoImpl(Boolean pIsSMS) {
	    	  this.isSMS = pIsSMS;
	      }
	      
	      public String getStaffEmailId() {
	         return isSMS ? "2247700674@tmomail.net" : "bilg21@yahoo.com";
	      }
	      public void setStaffEmailId(String staffEmailId) {
	         StaffEmailId = staffEmailId;
	      }
	      public int getStudentID() {
	         return 10686;
	      }
	      public void setStudentID(int studentID) {
	         StudentID = studentID;
	      }
	   }
	 
	 public static void main(String[] args) {
		 MailHandler.sendEmail(101, false);
	 }
}
