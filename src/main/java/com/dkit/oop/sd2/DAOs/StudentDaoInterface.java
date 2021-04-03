package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.Student;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.util.List;

public interface StudentDaoInterface
{
    public List<Student> findAllStudents() throws DaoException;
    public boolean addStudent(int caoNum,String dob,String pwd) throws DaoException;
    public boolean login(int caoNum,String dob,String pwd)throws DaoException;
    public Student getStudent(int caoNum) throws DaoException;
}
