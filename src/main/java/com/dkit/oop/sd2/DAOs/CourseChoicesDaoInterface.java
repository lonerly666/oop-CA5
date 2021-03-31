package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.Exceptions.DaoException;

import java.util.List;

public interface CourseChoicesDaoInterface
{
    public List<String> getStudentChoices(int caoNum) throws DaoException;
    public boolean addChoice(int caoNum,String courseId)throws DaoException;
    public boolean removeChoice(int caoNum,String courseId) throws  DaoException;
}
