package com.dkit.oop.sd2;

import static org.junit.Assert.assertTrue;

import com.dkit.oop.sd2.DAOs.MySqlCourseChoicesDao;
import com.dkit.oop.sd2.DAOs.MySqlCourseDao;
import com.dkit.oop.sd2.DAOs.MySqlStudentDao;
import com.dkit.oop.sd2.DTOs.Course;
import com.dkit.oop.sd2.DTOs.Student;
import com.dkit.oop.sd2.Exceptions.DaoException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class ClientTest
{

    @Test
    public void TestRegistration() throws DaoException {
        MySqlStudentDao studentDao = new MySqlStudentDao();
        int caoNum = 11119999;
        String dob = "2000-04-29";
        String pwd = "1234678";
        Student expected = new Student(caoNum,dob,pwd);
        studentDao.addStudent(caoNum,dob,pwd);
        Student actual = studentDao.getStudent(caoNum);
        assertTrue(actual.equals(expected));
    }
    @Test
    public void TestRegistration1()throws DaoException //test for registered student cao number
    {
        MySqlStudentDao studentDao = new MySqlStudentDao();
        int caoNum = 11119999;
        String dob = "2000-04-29";
        String pwd = "1234678";
        boolean actual = studentDao.addStudent(caoNum,dob,pwd);
        assertTrue(!actual);
    }
    @Test
    public void TestLogin() throws DaoException {
        MySqlStudentDao studentDao = new MySqlStudentDao();
        int caoNum = 11119999;
        String dob = "2000-04-29";
        String pwd = "1234678";
        boolean actual =studentDao.login(caoNum,dob,pwd);
        assertTrue(actual);
    }
    @Test
    public void TestLogin1() throws DaoException //test for wrong credential login
    {
        MySqlStudentDao studentDao = new MySqlStudentDao();
        int caoNum = 11119999;
        String dob = "2000-04-21";
        String pwd = "1234678";
        boolean actual =studentDao.login(caoNum,dob,pwd);
        assertTrue(!actual);
    }
    @Test
    public void TestDisplayCourse()throws DaoException
    {
        MySqlCourseDao courseDao = new MySqlCourseDao();
        String courseId = "A001";
        int level = 7;
        String title = "Engineering";
        String institution = "DIT";
        Course expected = new Course(courseId,level,title,institution);
        Course actual = courseDao.getSpecificCourse(courseId);
        assertTrue(actual.toString().equals(expected.toString()));
    }
    @Test
    public void TestAddCourse() throws DaoException
    {
        MySqlCourseChoicesDao courseChoicesDao = new MySqlCourseChoicesDao();
        int caoNum  = 11437765;
        String courseId = "A001";
        boolean result = courseChoicesDao.addChoice(caoNum,courseId);
        assertTrue(result);
    }
    @Test
    public void TestRemoveCourse()throws DaoException
    {
        MySqlCourseChoicesDao courseChoicesDao = new MySqlCourseChoicesDao();
        int caoNum = 11437765;
        String courseId = "A001";
        boolean result = courseChoicesDao.removeChoice(caoNum,courseId);
        assertTrue(result);
    }
    @Test
    public void TestGetCourseChoice()throws DaoException
    {
        MySqlCourseChoicesDao courseChoicesDao = new MySqlCourseChoicesDao();
        int caoNum = 11324476;
        List<String> res = courseChoicesDao.getStudentChoices(caoNum);
        List<String>expect = new ArrayList<>();
        expect.add("D002");
        expect.add("D003");
        assertTrue(expect.equals(res));
    }
}
