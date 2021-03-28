package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.Exceptions.DaoException;

import java.util.List;

public interface CourseChoicesDaoInterface
{
    public List<String> getStudentChoices(int caoNum) throws DaoException;
    public void updateChoice(int caoNum,String courseId)throws DaoException;
}
