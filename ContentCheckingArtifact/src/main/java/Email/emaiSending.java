package Email;

import java.util.Properties;

import javax.activation.DataHandler;

import javax.activation.DataSource;

import javax.activation.FileDataSource;

import javax.mail.BodyPart;

import javax.mail.Message;

import javax.mail.MessagingException;

import javax.mail.Multipart;

import javax.mail.Session;

import javax.mail.Transport;

import javax.mail.internet.AddressException;

import javax.mail.internet.InternetAddress;

import javax.mail.internet.MimeBodyPart;

import javax.mail.internet.MimeMessage;

import javax.mail.internet.MimeMultipart;

public class emaiSending {
	public static void sendPDFReportByGMail(String from, String pass, String to,String cc, String subject, String body) {

		Properties props = System.getProperties();

		String host = "smtp.gmail.com";

		props.put("mail.smtp.starttls.enable", "true");

		props.put("mail.smtp.host", host);

		props.put("mail.smtp.user", from);

		props.put("mail.smtp.password", pass);

		props.put("mail.smtp.port", "587");

		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);

		MimeMessage message = new MimeMessage(session);

		try {

		    //Set from address

		message.setFrom(new InternetAddress(from));
		String[] toArray=to.split(";");
		for(int i=0;i<toArray.length;i++) {
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toArray[i]));
		}
		String[] ccArray=cc.split(";");
		for(int i=0;i<ccArray.length;i++) {
			message.setRecipients(Message.RecipientType.CC,
	                InternetAddress.parse(ccArray[i]));
		}
	/*	message.setRecipients(Message.RecipientType.CC,
                InternetAddress.parse(cc));*/
		//Set subject

		message.setSubject(subject);

		message.setText(body);

		BodyPart objMessageBodyPart = new MimeBodyPart();

	    /* BufferedReader br = null;
	     String content="";
	        try {

	            String sCurrentLine;

	            br = new BufferedReader(new FileReader(System.getProperty("user.dir")+"\\TestReport\\Test-Automation-Report.html"));

	            while ((sCurrentLine = br.readLine()) != null) {
	            	content=content+sCurrentLine;
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }*/
	      //  objMessageBodyPart.setContent(content, "text/html");
		objMessageBodyPart.setText("Please Find Attachment For Details");
		//objMessageBodyPart.setContent(content);
		Multipart multipart = new MimeMultipart();

		multipart.addBodyPart(objMessageBodyPart);

		objMessageBodyPart = new MimeBodyPart();

		//Set path to the pdf report file
		String filePath=System.getProperty("user.dir")+"\\TestReport\\Test-Automation-Report.html";
		String filename = "Test-Automation-Report.html";

		//Create data source to attach the file in mail

		DataSource source = new FileDataSource(filePath);

		objMessageBodyPart.setDataHandler(new DataHandler(source));

		objMessageBodyPart.setFileName(filename);

		multipart.addBodyPart(objMessageBodyPart);

		message.setContent(multipart);

		Transport transport = session.getTransport("smtp");

		transport.connect(host, from, pass);

		transport.sendMessage(message, message.getAllRecipients());

		transport.close();

		}

		catch (AddressException ae) {

		ae.printStackTrace();

		}

		catch (MessagingException me) {

		me.printStackTrace();

		}

		}
}
