package com.csis.reminder.servlet;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.csis.reminder.job.NotificationJob;
import com.csis.reminder.util.ScreenUtil;


public class AppConfigServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		super.init();
		System.out.println("==============================================");
		System.out.println("Application init");
		System.out.println("==============================================");
		createSchedule();
	}

	private void createSchedule() {
		try {
			JobDetail job1 = JobBuilder.newJob(NotificationJob.class).withIdentity("webCrawlerJog", "webCrawlerJogGroup").build();

			Date dateAddMinute = ScreenUtil.getDateAddMinute(new Date(), 1);
			String format = "MMM dd, yyyy HH:mm";
			System.out.println("============================================");
			System.out.println("Scheduling Job for "+ScreenUtil.getStringDeData(dateAddMinute,format));
			System.out.println("============================================");
			Trigger trigger1 = TriggerBuilder.newTrigger()
										.startAt(dateAddMinute)
												.withIdentity("webCrawlerJogTrigger", "webCrawlerJogGroup")
												.withSchedule(SimpleScheduleBuilder.repeatHourlyForever(3))
												.build();
			Scheduler scheduler1 = new StdSchedulerFactory().getScheduler();
			scheduler1.start();
			scheduler1.scheduleJob(job1, trigger1);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
