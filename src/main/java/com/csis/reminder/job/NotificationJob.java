package com.csis.reminder.job;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.csis.reminder.service.EmailService;
import com.csis.reminder.util.ScreenUtil;

public class NotificationJob implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		String format = "MMM dd, yyyy HH:mm";
		System.out.println("============================================");
		System.out.println("EXECUTING NOTIFICATION JOB STARTED AT "+ScreenUtil.getStringDeData(new Date(), format));
		System.out.println("============================================");

		EmailService emailService = new EmailService();
		try {
			//	emailService.dosomething;
		} catch (Exception e) {
			System.out.println("ERROR in NotificationJob: " + e.getMessage());
		}
		System.out.println("============================================");
		System.out.println("EXECUTING NOTIFICATION JOB ENDED AT "+ScreenUtil.getStringDeData(new Date(), format));
		System.out.println("============================================");
	}
}