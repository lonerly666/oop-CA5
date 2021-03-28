package com.dkit.oop.sd2.BusinessObjects;

import com.dkit.oop.sd2.DAOs.MySqlStudentDao;
import com.dkit.oop.sd2.DAOs.StudentDaoInterface;
import com.dkit.oop.sd2.DTOs.Student;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StudentManager
{
    // Store all students in a Map <caoNumber => student>
    private Map<Integer, Student> studentsMap = new HashMap<>();

    public StudentManager()
    {
        StudentDaoInterface studentDao = new MySqlStudentDao();
        try
        {
            List<Student> studentList = studentDao.findAllStudents();
            for (Student s : studentList)
            {
                studentsMap.put(s.getCaoNumber(), s);
            }
            System.out.println("StudentMap dump: "+studentsMap);
        } catch (DaoException e)
        {
            e.printStackTrace();
        }
    }
        // load from text file "students.dat" and populate studentsMap

        // Hardcode some values to get started
//        studentsMap.put(22224444, new Student(22224444, "1999-03-15", "Annie$101", "annie@gmail.com"));
//        studentsMap.put(50501111, new Student(50501111, "2003-12-25", "banAnna$%", "pat.crean@yahoo.com"));
//        studentsMap.put(10103333, new Student(10103333, "2002-04-01", "cats&dogs:)", "cat.stephens@digiweb.ie"));
//        studentsMap.put(33330000, new Student(33330000, "2000-07-04", "guessMe1984", "malone_j@icloud.com"));


    public Student getStudent(Integer caoNumber) {
        Student student = studentsMap.get(caoNumber); // get a reference to student object (could be null)
        Student clone=null;
        if(student!=new Student(student));
        clone = new Student(student);
        return clone;  // return a clone of the student
    }

    public void addStudent(Student student) {
        Integer caoNumber = student.getCaoNumber();
        studentsMap.put(caoNumber, new Student(student));
    }

    public void removeStudent(Integer caoNumber) {
        studentsMap.remove(caoNumber);
    }

    //    isRegistered( caoNumber)
    //        students.isValid()
}


