package com.dkit.oop.sd2.DTOs;

import java.io.*;
import java.util.*;

//public class CourseChoicesManager {
//    private Scanner keyboard = new Scanner(System.in);
//    // reference to constructor injected studentManager
//    private StudentManager studentManager;
//
//    // reference to constructor injected courseManager
//    private CourseManager courseManager;
//
//    // Store all the Course details -  fast access
//
//    // caoNumber, course selection list - for fast access
//    Map<Integer, List<String>> selectedChoice = new HashMap<>();
//
//    // CourseChoicesManager DEPENDS on both the StudentManager and CourseManager to access
//    // student details and course details.  So, we receive a reference to each via
//    // the constructor.
//    // This is called "Dependency Injection", meaning that we
//    // inject (or pass in) objects that this class requires to do its job.
//    //
//    CourseChoicesManager(StudentManager studentManager, CourseManager courseManager)
//    {
//        this.studentManager = studentManager;
//        this.courseManager = courseManager;
//
//    }
//    //
//    public Student getStudentDetails(int caoNum)
//    {
//        return studentManager.getStudent(caoNum);
//    }
//    //
//    public Course getCourseDetails(String courseId)
//    {
//        return courseManager.getCourse(courseId);
//    }
//    //
//    public List<String> getStudentChoices(int caoNum)
//    {
//        return selectedChoice.get(caoNum);
//    }
//    //
//    public void updateChoices(int caoNum, ArrayList<String> courseId)
//    {
//        if(selectedChoice.containsKey(caoNum))
//        {
//            List<String> temp = selectedChoice.get(caoNum);
//            for (String s : courseId)
//            {
//                if (!temp.contains(s))
//                {
//                    temp.add(s);
//                }
//            }
//            selectedChoice.put(caoNum, temp);
//        }
//        else
//        {
//            selectedChoice.put(caoNum,courseId);
//        }
//        saveDetails();
//    }
//
//    public boolean login(int caoNum, String dateOfBirth,String pass)
//    {
//        if(studentManager.isRegistered(caoNum))
//        {
//            if(studentManager.loginValidation(caoNum,dateOfBirth,pass))
//            {
//                return true;
//            }
//            else
//            {
//                System.out.println("Input credential mismatch!");
//            }
//        }
//        else
//        {
//            System.out.println("You haven't registered yet!");
//            return false;
//        }
//        return false;
//    }
//    private void saveDetails()
//    {
//        try(BufferedWriter detailFile = new BufferedWriter(new FileWriter("detail.txt")))
//        {
//            for(Map.Entry<Integer,List<String>>entry:selectedChoice.entrySet())
//            {
//                detailFile.write(entry.getKey()+",");
//                for(String s : entry.getValue())
//                {
//                    detailFile.write(s+",");
//                }
//                detailFile.write("\n");
//            }
//
//        }
//        catch (IOException e)
//        {
//            System.out.println("Could not save file!");
//        }
//    }
//    protected void loadDetails()
//    {
//        try(Scanner detailFile = new Scanner(new BufferedReader(new FileReader("detail.txt"))))
//        {
//            String input ;
//            while(detailFile.hasNextLine())
//            {
//                ArrayList<String>choices = new ArrayList<>();
//                input = detailFile.nextLine();
//                if(input.equals(""))
//                {
//                    break;
//                }
//                String data[] = input.split(",");
//                String caoNum = data[0];
//                for(int i=1;i<data.length;i++)
//                {
//                    choices.add(data[i]);
//                }
//                selectedChoice.put(Integer.parseInt(caoNum),choices);
//            }
//
//        }
//        catch (FileNotFoundException e)
//        {
//            System.out.println("File not Found!");
//        }
//    }

//}
