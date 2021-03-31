package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.Student;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlStudentDao extends MySqlDao implements StudentDaoInterface
{
    @Override
    public List<Student> findAllStudents() throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Student> students = new ArrayList<>();

        try
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "SELECT * FROM STUDENT";
            ps = con.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            rs = ps.executeQuery();
            while (rs.next())
            {
                int caoNumber = rs.getInt("caoNumber");
                String dateOfBirth = rs.getString("dateOfBirth");
                String password = rs.getString("password");
                Student s = new Student(caoNumber,dateOfBirth, password);
                students.add(s);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findAllUsers() " + e.getMessage());
        } finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findAllUsers() " + e.getMessage());
            }
        }
        return students;     // may be empty
    }

    @Override
    public void addStudent(int caoNum,String dob,String pwd) throws DaoException
    {
        Connection con = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        try
        {
            con = this.getConnection();
            String query = "SELECT caoNumber FROM STUDENT WHERE caoNumber = ?";
            prep = con.prepareStatement(query);
            prep.setInt(1, caoNum);
            rs = prep.executeQuery();
            if(rs.next())
            {
                System.out.println("This cao number has been taken!");
            }
            else
            {
                query = "INSERT INTO STUDENT (caoNumber,dateOfBirth,password) VALUES (?,?,?)";
                prep = con.prepareStatement(query);
                prep.setInt(1,caoNum);
                prep.setString(2,dob);
                prep.setString(3,pwd);
                prep.executeUpdate();
                System.out.println("Welcome! You have successfully registered!");
            }
        }
        catch (SQLException e)
        {
            throw  new DaoException("addStudent() "+e.getMessage());
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
            } catch (SQLException e)
            {
                throw new DaoException("findAllUsers() " + e.getMessage());
            }
        }

    }

    @Override
    public boolean login(int caoNum, String dob, String pwd) throws DaoException
    {
        Connection con = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        try
        {
            con = this.getConnection();
            String query = "SELECT * FROM STUDENT WHERE caoNumber = ?";
            prep = con.prepareStatement(query);
            prep.setInt(1,caoNum);
            rs = prep.executeQuery();
            while(rs.next())
            {
                if (rs.getInt("caoNumber") == caoNum && rs.getString("dateOfBirth").equals(dob) && rs.getString("password").equals(pwd))
                {
                    return true;
                }
            }
        }
        catch (SQLException e)
        {
            throw  new DaoException("login() "+e.getMessage());
        }finally
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
            } catch (SQLException e)
            {
                throw new DaoException("login() " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public Student getStudent(int caoNum) throws DaoException
    {
        Connection con = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        try
        {
            con = this.getConnection();
            String query = "SELECT * FROM STUDENT WHERE caoNumber = ?";
            prep = con.prepareStatement(query);
            prep.setInt(1,caoNum);
            rs = prep.executeQuery();
            while(rs.next())
            {
                Student s = new Student(rs.getInt("caoNumber"),rs.getString("dateOfBirth"),rs.getString("password"));
                return s;
            }
        }
        catch (SQLException e)
        {
            throw new DaoException("getStudent() " + e.getMessage());
        }finally
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
            } catch (SQLException e)
            {
                throw new DaoException("getStudent() " + e.getMessage());
            }
        }

        return  null;
    }


}
