package com.whitehare.course;

import com.whitehare.util.HibernateUtil;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Iterator;
import java.util.List;

public class MainApp
{

  /**
   * @param args
   */
  public static void main(String[] args)
  {
    MainApp obj = new MainApp();

    Long courseId1 = obj.saveCourse("Physics");
    Long courseId2 = obj.saveCourse("Chemistry");
    Long courseId3 = obj.saveCourse("Maths");
    Long courseId4 = obj.saveCourse("History");
    Long courseId5 = obj.saveCourse("English");
    obj.listCourse();
    obj.updateCourse(courseId3, "Mathematics");
    obj.deleteCourse(courseId2);
    obj.listCourse();
  }

  /**
   * === C ===
   *
   * @param courseName
   */
  public Long saveCourse(String courseName)
  {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    Long courseId = null;
    try
    {
      transaction = session.beginTransaction();
      Course course = new Course();
      course.setCourseName(courseName);
      courseId = (Long) session.save(course);
      transaction.commit();

    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();
    } finally
    {
      session.close();
    }
    return courseId;
  }

  /**
   * === R ===
   */
  public void listCourse()
  {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    try
    {
      transaction = session.beginTransaction();
      List courses = session.createQuery("from Course").list();
      for (Iterator iterator = courses.iterator(); iterator.hasNext(); )
      {
        Course course = (Course) iterator.next();
        System.out.println(course.getCourseName());
      }
      transaction.commit();

    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();

    } finally
    {
      session.close();
    }
  }

  /**
   * === U ===
   *
   * @param courseId
   * @param courseName
   */
  public void updateCourse(Long courseId, String courseName)
  {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    try
    {
      transaction = session.beginTransaction();
      Course course = (Course) session.get(Course.class, courseId);
      course.setCourseName(courseName);
      transaction.commit();
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();
    } finally
    {
      session.close();
    }
  }

  /**
   * === D ===
   *
   * @param courseId
   */
  public void deleteCourse(Long courseId)
  {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Transaction transaction = null;
    try
    {
      transaction = session.beginTransaction();
      Course course = (Course) session.get(Course.class, courseId);
      session.delete(course);
      transaction.commit();
    } catch (HibernateException e)
    {
      transaction.rollback();
      e.printStackTrace();

    } finally
    {
      session.close();

    }
  }
}
