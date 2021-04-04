package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.Course;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlCourseDao extends MySqlDao implements CourseDaoInterface
{
    @Override
    public List<Course> findAllCourse() throws DaoException
    {
        Connection con = null;
        PreparedStatement prep= null;
        ResultSet rs = null;
        List <Course> courseList = new ArrayList<>();
        try
        {
            con  = this.getConnection();
            String query = "SELECT * FROM COURSE";
            prep = con.prepareStatement(query);
            rs = prep.executeQuery();
            while(rs.next())
            {
                String courseId = rs.getString("courseId");
                int level = rs.getInt("level");
                String title = rs.getString("title");
                String institution = rs.getString("institution");
                Course c = new Course(courseId,level,title,institution);
                courseList.add(c);
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("findAllCourse() "+e.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (prep != null)
                {
                    prep.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("findAllCourse() "+e.getMessage());
            }
        }
        return courseList;
    }
    public Course getSpecificCourse(String courseId) throws DaoException
    {
        Connection con = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        try
        {
            con = this.getConnection();
            String query = "SELECT * FROM COURSE WHERE courseId = ?";
            prep = con.prepareStatement(query);
            prep.setString(1,courseId);
            rs = prep.executeQuery();
            while(rs.next())
            {
                String course = rs.getString("courseId");
                int level = rs.getInt("level");
                String title = rs.getString("title");
                String institution = rs.getString("institution");
                return new Course(course,level,title,institution);
            }
        }
        catch (SQLException e)
        {
            System.out.println("getSpecificCourse() "+e.getMessage());
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (prep != null)
                {
                    prep.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            }
            catch (SQLException e)
            {
                throw new DaoException("getSpecificCourse() "+e.getMessage());
            }
        }
        return null;
    }

}
