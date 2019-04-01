package com.csis.reminder.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.csis.reminder.dao.resources.Resources;
import com.csis.reminder.entity.Course;
import com.csis.reminder.entity.User;

/**
 * 
 * @author Reminder Group Class is responsible for managing the course Data
 *         Access Object it contains the methods which allows users to perform
 *         CRUD operations on their courses
 */
public class CourseDAO implements Serializable {

	private static final long serialVersionUID = 7385285317285391401L;

	/**
	 * Method to run a first query to initialize the database connection context
	 */
	public void initSelec() {
		EntityManager manager = Resources.getEntityManager();
		manager.find(Course.class, new Long(1));
	}

	@SuppressWarnings("unchecked")
	public List<Course> getAllCourses(User user) {
		EntityManager manager = Resources.getEntityManager();

		List<Course> courses = manager.createQuery("SELECT  x FROM Course x WHERE x.user.id =" + user.getId())
				.getResultList();
		return courses;
	}

	/**
	 * Method to insert (persist) a new course into our database or update (merge )
	 * a course into our database
	 * 
	 * @param course
	 *            {@link Course} - object which holds a course's data
	 * 
	 */
	public void saveCourse(Course course) {
		EntityManager manager = Resources.getEntityManager();
		EntityTransaction transaction = manager.getTransaction();

		try {
			transaction.begin();
			if (course.getId() > 0)
				manager.merge(course);
			else
				manager.persist(course);
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
	 * Method to delete a course into our database
	 * 
	 * @param courseID
	 *            {@link CourseID} - object which holds a courseID (key)
	 * @throws Exception
	 */
	public void deleteCourse(Long id) throws Exception {
		EntityManager manager = Resources.getEntityManager();
		EntityTransaction transaction = manager.getTransaction();

		@SuppressWarnings("rawtypes")
		List list = manager.createQuery("Select x FROM Event x WHERE x.course.id = :id").setParameter("id", id)
				.getResultList();

		if (list != null && list.size() > 0)
			throw new Exception(
					"The course is related to events. Delete the events first to be able to delete the course!");

		try {
			manager.getTransaction().begin();
			manager.createQuery("DELETE FROM Course x WHERE x.id = :id").setParameter("id", id).executeUpdate();
			manager.getTransaction().commit();
		} catch (RuntimeException e) {
			if (transaction != null)
				transaction.rollback();
			throw e;
		} finally {
			manager.close();
		}

	}

	// get courseID
	public Course getCourse(long id) {
		EntityManager manager = Resources.getEntityManager();
		return manager.find(Course.class, id);
	}

}
