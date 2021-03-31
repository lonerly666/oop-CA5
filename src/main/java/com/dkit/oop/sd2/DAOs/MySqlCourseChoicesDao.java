package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.BusinessObjects.CourseChoiceManager;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlCourseChoicesDao extends MySqlDao implements CourseChoicesDaoInterface
{
    @Override
    public List<String> getStudentChoices(int caoNum) throws DaoException
    {
        Connection con = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        List<String> choiceList = new ArrayList<>();
        try
        {
            con = this.getConnection();
            String query = "SELECT courseId FROM STUDENT_COURSES WHERE caoNumber = ? ";
            prep = con.prepareStatement(query);
            prep.setInt(1,caoNum);
            rs = prep.executeQuery();
            while(rs.next())
            {
                String courseId = rs.getString("courseId");
                choiceList.add(courseId);
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("getStudentChoices() "+e.getMessage());
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
                throw new DaoException("getStudentChoices() "+e.getMessage());
            }
        }
        return choiceList;
    }
    @Override
    public boolean addChoice(int caoNum,String courseId) throws DaoException
    {
        Connection con = null;
        PreparedStatement prep = null;
        boolean success = true;
        try
        {
            con = this.getConnection();
            String query = "INSERT INTO STUDENT_COURSES (caoNumber,courseId) VALUES (?,?)";
            prep = con.prepareStatement(query);
            prep.setInt(1,caoNum);
            prep.setString(2,courseId);
            prep.executeUpdate();
        }
        catch (SQLException e)
        {
            success = false;
        }
        finally
        {
            try
            {
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
                throw new DaoException("updateChoice() "+e.getMessage());
            }
        }
        return  success;
    }

    @Override
    public boolean removeChoice(int caoNum, String courseId) throws DaoException
    {
        boolean success = true;
        Connection con = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        try
        {
            con = this.getConnection();
            String query = "DELETE FROM STUDENT_COURSES WHERE caoNumber = ? AND courseId = ?";
            prep = con.prepareStatement(query);
            prep.setInt(1,caoNum);
            prep.setString(2,courseId);
            prep.executeUpdate();

        } catch (SQLException e)
        {
            success = false;
        }
        return success;
    }
}
