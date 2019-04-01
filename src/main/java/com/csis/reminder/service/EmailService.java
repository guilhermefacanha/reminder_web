package com.csis.reminder.service;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.csis.reminder.entity.Notification;
import com.csis.reminder.util.ScreenUtil;

public class EmailService {
	private static final String USERNAME = "reminder.csis.contact@gmail.com";
	private static final String PASSWORD = "Douglas@2019";
	private static final String DATE_FORMAT = "MMM dd, yyyy HH:mm";

	public void sendEmail(Notification notification) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});

		try {
			String text = "Dear " + notification.getEvent().getCourse().getUser().getFirstName()
					+ ",\n\n this a reminder to the upcoming event:\n\n" + "Course: "
					+ notification.getEvent().getCourse().getCourseName() + "\n" + "Event: "
					+ notification.getEvent().getDescription() + "\n" + "Date: "
					+ ScreenUtil.getStringDeData(notification.getEvent().getDate(), DATE_FORMAT) + "\n\n"
					+ "Best regards,\n Reminder Team";

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("reminder.csis.contact@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(notification.getUserEmail()));
			message.setSubject("[Reminder] Event Notification " + notification.getNotificationName());
			message.setText(text);

			Transport.send(message);

			System.out.println(
					"Sent notification for id: " + notification.getId() + ", user: " + notification.getUserEmail());

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
