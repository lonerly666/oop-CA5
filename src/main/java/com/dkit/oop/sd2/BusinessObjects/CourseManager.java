package com.dkit.oop.sd2.BusinessObjects;

import com.dkit.oop.sd2.DAOs.CourseDaoInterface;
import com.dkit.oop.sd2.DAOs.MySqlCourseDao;
import com.dkit.oop.sd2.DTOs.Course;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.io.*;
import java.util.*;

public class CourseManager
{
    private Map<String, Course> courseMap = new HashMap<>();

    public CourseManager()
    {
        CourseDaoInterface courseDao = new MySqlCourseDao();
        try
        {
            List<Course> courseList = courseDao.findAllCourse();
            for(Course c : courseList)
            {
                courseMap.put(c.getCourseId(),c);
            }
            System.out.println("Course Map Dump: "+courseMap);
        }
        catch (DaoException e)
        {
            e.printStackTrace();
        }
    }
    public Course getCourse(String courseId)
    {
        for(Map.Entry<String,Course>entry:courseMap.entrySet())
        {
            if(entry.getKey().equals(courseId))
            {
                return new Course(entry.getValue());
            }
        }
        throw new NullPointerException();
    }

    public List<Course> getAllCourses()
    {
        List<Course> courseList = new ArrayList<>();
        for(Map.Entry<String,Course>entry:courseMap.entrySet())
        {
            courseList.add(new Course(entry.getValue()));
        }
        return courseList;
    }

    public void addCourse(Course c)
    {
        if(c==null)
        {
            throw new NullPointerException();
        }
        if(courseMap.containsKey(c.getCourseId()))
        {
            System.out.println("Course already Existed!");
        }
        else
        {
            Course clone = new Course(c);
            courseMap.put(clone.getCourseId(), clone);
        }
    }

    public void removeCourse(String courseId)
    {
        if(courseMap.containsKey(courseId))
        {
            courseMap.remove(courseId);
        }
        else
        {
            System.out.println("There is no such course!");
        }
    }


    public void loadCourse()
    {
        try(Scanner courseFile = new Scanner(new BufferedReader(new FileReader("course.txt"))))
        {
            String input;
            while(courseFile.hasNext())
            {
                input = courseFile.nextLine();
                if (input.equals(""))
                {
                    break;
                }
                String data[] = input .split(",");
                String courseId = data[0];
                String level = data[1];
                String title = data[2];
                String institution = data[3];
                courseMap.put(courseId,new Course(courseId,level,title,institution));
            }

        }
        catch (FileNotFoundException e)
        {
            System.out.println("Could not load specific file! Please check properly!");
        }
    }
    public void saveCourse()
    {
        try(BufferedWriter courseFile = new BufferedWriter(new FileWriter("course.txt")))
        {
            for(Map.Entry<String,Course>entry : courseMap.entrySet())
            {
                courseFile.write(entry.getValue().getCourseId()+","+
                        entry.getValue().getLevel()+","+
                        entry.getValue().getTitle()+","+
                        entry.getValue().getInstitution()+",");
                courseFile.write("\n");
            }

        }
        catch (IOException e)
        {
            System.out.println("");
        }
    }
}
