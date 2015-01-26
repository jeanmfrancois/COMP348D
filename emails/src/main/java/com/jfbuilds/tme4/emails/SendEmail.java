/**
 * File Name: SendEmail.java<br>
 * I declare that this assignment is my own work and that all material
 * previously written or published in any source by any other person has been
 * duly acknowledged in the assignment. I have not submitted this work, or a
 * significant part thereof, previously as part of any academic program. In
 * submitting this assignment I give permission to copy it for assessment
 * purposes only. Jean-francois Nepton<br>
 * COMP 348 Network Programming<br>
 * Cordinator: Richard Huntrods<br>
 * Student ID# 2358976<br>
 * Created: Jan 22, 2015
 */
package com.jfbuilds.tme4.emails;

import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import utils.Email;
import utils.IO;
import utils.UserAuthenticator;

/**
 * SendEmail is a simple email program that will connect to your preferred email
 * server and send an email to a primary recipient, a list of at least three
 * secondary recipients (cc) and at least three tertiary recipients (bcc). The
 * entire email, including all recipients plus subject and body, should be read
 * from an input file specified on the command line when you start the program,
 * i.e., java SendEmail thisfile.txt.
 * 
 * @author Jean-francois Nepton
 * @version %I%, %G%
 * @since 1.0
 */
public class SendEmail {

	public static void main(String[] args) {
		System.out.println("Program has executed " + args[0]);
		HashMap<String, String> properties = IO.createProperties(IO.readFile(args[0]), ":");
		properties.toString();
		// composeEmailPropBased(properties);
		composeEmailPropBased(properties, true, "file_01.jpg", "file_02.png", "file_03.zip");
	}

	public static void composeEmailPropBased(HashMap<String, String> properties) {
		composeEmailPropBased(properties, false);
	}

	public static void composeEmailPropBased(HashMap<String, String> properties, boolean hasAttachment,
			String... filename) {
		Address[] toAddresses;
		Address[] ccAddresses;
		Address[] bccAddresses;
		Properties settings = new Properties();
		settings.put("mail.smtp.host", Email.DEFAULT_HOST);
		settings.put("mail.smtp.auth", "true");
		settings.put("mail.smtp.socketFactory.port", Email.DEFAULT_HOST_PORT);
		settings.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		settings.put("mail.smtp.socketFactory.fallback", "false");
		Transport transport = null;
		try {
			// SETUP
			UserAuthenticator authenticator =
					new UserAuthenticator(Email.getReqStringProp(properties, Email.USERNAME), Email.getReqStringProp(
							properties, Email.PASSWORD));
			Session session = Session.getInstance(settings, authenticator);
			Multipart multipart = new MimeMultipart();
			MimeMessage message = new MimeMessage(session);
			BodyPart messageBodyPart = new MimeBodyPart();
			transport = session.getTransport(Email.DEFAULT_TRANSPORT);
			// FROM
			message.setFrom(Email.getReqAddressProp(properties, Email.USERNAME));
			// TO
			toAddresses = Email.getReqAddressProps(properties, Email.TO);
			for (int i = 0; i < toAddresses.length; i++) {
				message.addRecipient(RecipientType.TO, toAddresses[i]);
			}
			// CC
			ccAddresses = Email.getAddressProps(properties, Email.CC);
			for (int i = 0; i < ccAddresses.length; i++) {
				message.addRecipient(RecipientType.CC, ccAddresses[i]);
			}
			// BCC
			bccAddresses = Email.getAddressProps(properties, Email.BCC);
			for (int i = 0; i < bccAddresses.length; i++) {
				message.addRecipient(RecipientType.BCC, bccAddresses[i]);
			}
			// BODY
			messageBodyPart.setText(Email.getStringProp(properties, Email.BODY, ""));
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			// ATTACHMENT
			if (hasAttachment) {
				for (String file : filename)
					multipart.addBodyPart(getAttachment(file));
			}
			// SUBJECT
			message.setSubject(Email.getStringProp(properties, Email.SUBJECT, "No Subject"));
			// DATE
			message.setSentDate(new Date());
			// SEND INFO
			System.out.println("Send.. " + Email.getStringProp(properties, Email.SERVER, Email.DEFAULT_HOST) + ":"
					+ Email.getStringProp(properties, Email.PORT, Email.DEFAULT_HOST_PORT) + ":"
					+ Email.getReqStringProp(properties, Email.USERNAME) + ":"
					+ Email.getReqStringProp(properties, Email.PASSWORD));
			// SEND
			transport.connect(Email.getStringProp(properties, Email.SERVER, Email.DEFAULT_HOST),
					Integer.parseInt(Email.getStringProp(properties, Email.PORT, Email.DEFAULT_HOST_PORT)),
					Email.getReqStringProp(properties, Email.USERNAME),
					Email.getReqStringProp(properties, Email.PASSWORD));
			Transport.send(message, message.getAllRecipients());
		} catch (MessagingException e) {
			e.printStackTrace();
		} finally {
			if (transport != null) {
				try {
					transport.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param file
	 * @return
	 */
	private static BodyPart getAttachment(String file) {
		BodyPart attachmentPart = new MimeBodyPart();
		try {
			DataSource src = new FileDataSource(file);
			attachmentPart.setDataHandler(new DataHandler(src));
			attachmentPart.setFileName(file);
			return attachmentPart;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return attachmentPart;
	}
}
