package com.csis.reminder.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.csis.reminder.dao.resources.Resources;
import com.csis.reminder.entity.Event;
import com.csis.reminder.entity.Notification;
import com.csis.reminder.entity.User;

/**
 * 
 * @author Reminder Group Class is responsible for managing the notification
 *         Data Access Object it contains the methods which allows users to
 *         perform CRUD operations on their notifications
 */
public class NotificationDAO implements Serializable {
	private static final long serialVersionUID = 5584599198950863626L;

	/**
	 * Method to insert (persist) a new notification into our database
	 * 
	 * @param notification
	 *            {@link Notification} - object which holds an notification's data
	 */
	public void save(Notification notification) {
		EntityManager manager = Resources.getEntityManager();
		EntityTransaction transaction = manager.getTransaction();

		try {
			transaction.begin();
			if (notification.getId() > 0)
				manager.merge(notification);
			else
				manager.persist(notification);
			transaction.commit();
		} catch (RuntimeException e) {
			if (transaction != null)
				transaction.rollback();
			throw e;
		} finally {
			manager.close();
		}
	}

	/**
	 * Method which retrieves all the notifications of a user and returns a list of
	 * them
	 * 
	 * @param user
	 *            - provides the user which the program will load the notifications
	 *            from
	 * @return - returns a list of notifications objects from that user
	 */
	@SuppressWarnings("unchecked")
	public List<Notification> getAllNotifications(User user) {
		EntityManager manager = Resources.getEntityManager();
		List<Notification> notifications = manager
				.createQuery("SELECT x FROM Notification x WHERE x.event.course.user.id = :id")
				.setParameter("id", user.getId()).getResultList();
		return notifications;
	}

	/**
	 * Method which retrieves all the notification of a user, filtered by a event
	 * 
	 * @param user
	 *            - provides the user which the program will load the notifications
	 *            from
	 * @return - returns a list of notifications objects from that event
	 */
	@SuppressWarnings("unchecked")
	public List<Notification> getAllNotificationsByEvent(Event event) {
		EntityManager manager = Resources.getEntityManager();
		List<Notification> notifications = manager.createQuery("SELECT x FROM Notification x WHERE x.event.id = :id")
				.setParameter("id", event.getId()).getResultList();
		return notifications;
	}

	/**
	 * Method which deletes an notification, based on its unique id
	 * 
	 * @param notificationId
	 *            - the unique identifier for each notification
	 */
	public void deleteNotification(Long notificationId) {
		EntityManager manager = Resources.getEntityManager();
		EntityTransaction transaction = manager.getTransaction();

		try {
			manager.getTransaction().begin();
			manager.createQuery("DELETE FROM Notification x WHERE x.id = :id").setParameter("id", notificationId)
					.executeUpdate();
			manager.getTransaction().commit();
		} catch (RuntimeException e) {
			if (transaction != null)
				transaction.rollback();
			throw e;
		} finally {
			manager.close();
		}

	}

	public Notification getNotification(long notificationId) {
		EntityManager manager = Resources.getEntityManager();
		return manager.find(Notification.class, notificationId);
	}

	@SuppressWarnings("unchecked")
	public List<Notification> getNotificationsByDate(Date dateEnd) {
		EntityManager manager = Resources.getEntityManager();
		List<Notification> notifications = manager
				.createQuery("SELECT x FROM Notification x WHERE x.isNotified = false AND x.date < :edate")
				.setParameter("edate", dateEnd)
				.getResultList();
		return notifications;
	}

}
