package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.Constants.CAOService;
import com.dkit.oop.sd2.DTOs.Course;
import com.dkit.oop.sd2.DTOs.Student;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientHandler implements Runnable {

    BufferedReader socketReader;
    PrintWriter socketWriter;
    Socket socket;
    int numOfClient;

    public ClientHandler(Socket clientSocket, int numOfClient)
    {
        try
        {
            InputStreamReader isReader = new InputStreamReader(clientSocket.getInputStream());
            this.socketReader = new BufferedReader(isReader);
            OutputStream os = clientSocket.getOutputStream();
            this.socketWriter = new PrintWriter(os,true);
            this.numOfClient = numOfClient;
            this.socket = clientSocket;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String message;
        try
        {
            while((message = socketReader.readLine())!=null)
            {
                String arr[] = message.split(CAOService.BREAKING_CHARACTER);
                if(arr[0].equals(CAOService.REGISTER_COMMAND))
                {
                    Student s = new Student((Integer.parseInt(arr[1])),arr[2],arr[3]);
                    MySqlStudentDao studentDao = new MySqlStudentDao();
                    if(studentDao.addStudent(s.getCaoNumber(),s.getDayOfBirth(),s.getPassword()))
                    {
                        socketWriter.println("Welcome! You have successfully registered!");
                        System.out.println("Server: Register student result is " + CAOService.SUCCESSFUL_REGISTER );
                    }
                    else
                    {
                        socketWriter.println("This cao number has been taken!");
                        System.out.println("Server: Register student result is " + CAOService.FAILED_REGISTER);
                    }
                }
                else if(arr[0].equals(CAOService.LOGIN_COMMAND))
                {
                    int caoNum = Integer.parseInt(arr[1]);
                    String dob = arr[2];
                    String pwd = arr[3];
                    MySqlStudentDao studentDao = new MySqlStudentDao();
                    if(studentDao.login(caoNum,dob,pwd))
                    {
                        socketWriter.println("Welcome Back!");
                        System.out.println("Server: Login status is "+ CAOService.SUCCESSFUL_LOGIN);
                    }
                    else
                    {
                        socketWriter.println("Invalid Credentials");
                        System.out.println("Server: Login status is "+ CAOService.FAILED_LOGIN);
                    }
                }
                else if(arr[0].equals(CAOService.DISPLAY_COURSE))
                {
                    MySqlCourseDao courseDao= new MySqlCourseDao();
                    Course c = courseDao.getSpecificCourse(arr[1]);
                    if(c!=null)
                    {
                        Map obj=new HashMap();
                        obj.put("course",courseDao.getSpecificCourse(arr[1]).toString());
                        String jsonText = JSONValue.toJSONString(obj);
                        socketWriter.println(jsonText);
                    }
                    else
                    {

                        socketWriter.println("No Such Course!");
                    }
                    System.out.println("Server Status [Display Specific Course]: Succeed");
                }
                else if(arr[0].equals(CAOService.DISPLAY_ALL_COURSE))
                {
                    MySqlCourseDao courseDao = new MySqlCourseDao();
                    if(courseDao.findAllCourse().isEmpty())
                    {
                        socketWriter.println("There is no course available");
                    }
                    else {
                        Map obj=new HashMap();
                        List<String> courseList = new ArrayList<>();
                        for(Course c : courseDao.findAllCourse())
                        {
                            courseList.add(c.toString());
                        }
                        obj.put("course",courseList);
                        String jsonText = JSONValue.toJSONString(obj);
                        socketWriter.println(jsonText);
                    }
                    System.out.println("Server Status [Display All Course]: Succeed");
                }
                else if(arr[0].equals(CAOService.DISPLAY_CURRENT_COURSE)) {
                    MySqlCourseChoicesDao courseChoicesDao = new MySqlCourseChoicesDao();
                    List <String>studentChoice = courseChoicesDao.getStudentChoices(Integer.parseInt(arr[1]));
                    if (studentChoice.isEmpty())
                    {
                        socketWriter.println("You have not chosen any course yet, please go and add some");
                    }
                    else {
                        Map obj = new HashMap();
                        obj.put("choice", studentChoice);
                        String jsonText = JSONValue.toJSONString(obj);
                        socketWriter.println(jsonText);
                    }
                    System.out.println("Server status [Display current choice]: Succeed");
                }
                else if(arr[0].equals(CAOService.REMOVE_COURSE))
                {
                    MySqlCourseChoicesDao courseChoicesDao = new MySqlCourseChoicesDao();
                    if(courseChoicesDao.getStudentChoices(Integer.parseInt(arr[2])).contains(arr[1]))
                    {
                        if(courseChoicesDao.removeChoice(Integer.parseInt(arr[2]),arr[1]))
                        {
                            socketWriter.println("Course Removed");
                        }
                        else
                        {
                            socketWriter.println("Opps, something went wrong");
                        }
                    }
                    else
                    {
                        socketWriter.println("Please enter a correct courseID");
                    }
                    System.out.println("Server status [Remove Course]: Succeed");
                }
                else if(arr[0].equals(CAOService.ADD_COURSE))
                {
                    MySqlCourseChoicesDao courseChoicesDao = new MySqlCourseChoicesDao();
                    int caoNum = Integer.parseInt(arr[2]);
                    String courseId = arr[1];
                    if(courseChoicesDao.getStudentChoices(caoNum).contains(courseId))
                    {
                        socketWriter.println("You have already added this course");

                    }
                    else
                    {
                        if(courseChoicesDao.addChoice(caoNum,courseId))
                        {
                            socketWriter.println("Course Added");
                        }
                        else
                        {
                            socketWriter.println("Opps,Something went wrong");
                        }
                    }
                    System.out.println("Server status [Add Course]: Succeed");
                }
            }
            socket.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("Server: (ClientHandler): Handler for Client " + numOfClient + " is terminating .....");
    }
}
