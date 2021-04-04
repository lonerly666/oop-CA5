package com.dkit.oop.sd2.BusinessObjects;

import com.dkit.oop.sd2.Constants.*;
import com.dkit.oop.sd2.DAOs.MySqlCourseChoicesDao;
import com.dkit.oop.sd2.DAOs.MySqlCourseDao;
import com.dkit.oop.sd2.DTOs.Course;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class Client
{
    public Scanner keyboard  = new Scanner(System.in);
    public static void main(String[] args)
    {
        Client client = new Client();
        client.start();

    }
       public void start()
    {
        try
        {

            Socket s = new Socket("localhost",8080);
            OutputStream os = s.getOutputStream();
            PrintWriter socketWriter = new PrintWriter(os,true);
            Scanner socketReader = new Scanner(s.getInputStream());
            System.out.println("Client : Port# of this client : "+s.getLocalPort());
            System.out.println("Client : Port# of Server : "+s.getPort());
            System.out.println("Client: The Client is running and has connected to the server");
            mainMenu(s,os,socketWriter,socketReader);
        }
        catch (IOException e)
        {
            System.out.println("Client : IOException : "+e);
        }

    }

        public void mainMenu (Socket s,OutputStream os, PrintWriter socketWriter,Scanner socketReader)
        {

            boolean loop = true;
            HomeMenuOption options;
            int choose;
            while (loop)
            {
                printHomeMenu();
                try
                {
                    choose = keyboard.nextInt();
                    options = HomeMenuOption.values()[choose];
                    switch (options)
                    {
                        case QUIT_APP:
                            s.close();
                            os.close();
                            socketReader.close();
                            socketWriter.close();
                            loop = false;
                            break;
                        case LOGIN:
                            studentMenu(s,os,socketWriter,socketReader);
                            break;
                        case REGISTER:
                            registration(s,os,socketWriter,socketReader);
                            break;
                        default:
                            System.out.println("Invalid Input");
                    }
                } catch (InputMismatchException e)
                {
                    System.out.println("Invalid Input");
                    keyboard.nextLine();
                } catch (NoSuchElementException e)
                {
                    System.out.println("DO NOT USE CTRL+D! THIS WILL FORCE THE SYSTEM TO SHUTDOWN! PLEASE RESTART!");
                    loop = false;
                } catch (IndexOutOfBoundsException e)
                {
                    System.out.println("Invalid Input");
                    keyboard.nextLine();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        public void printHomeMenu () {
        System.out.println("\n Option to select:");
        for (int i = 0; i < HomeMenuOption.values().length; i++)
        {
            System.out.println("\t" + i + "- " + HomeMenuOption.values()[i].toString());
        }
        System.out.print("Enter a number to select (0 to quit):> ");
    }

        public void studentMenu (Socket s,OutputStream os, PrintWriter socketWriter,Scanner socketReader)
        {
            boolean isLoggedIn=false,cao =false;
            int caoNum = 0;
            String dob,pwd;
            try
            {
                while (!cao)
                {
                    try
                    {
                        System.out.println("Please enter your CAO number: ");
                        caoNum = keyboard.nextInt();
                        cao = true;
                    } catch (InputMismatchException e)
                    {
                        System.out.println("Please enter DIGITS only!");
                        keyboard.next();
                        System.out.println();
                    }
                }
                System.out.println("Please enter your Date Of Birth (yyyy-mm-dd) : ");
                dob = keyboard.next();
                System.out.println("Please enter your password: ");
                pwd = keyboard.next();
                String breaks = CAOService.BREAKING_CHARACTER;
                String task = CAOService.LOGIN_COMMAND;
                String command = task+breaks+caoNum+breaks+dob+breaks+pwd;
                os = s.getOutputStream();
                socketWriter = new PrintWriter(os,true);
                socketWriter.println(command);
                socketReader = new Scanner(s.getInputStream());
                String taskString =socketReader.nextLine();
                System.out.println(taskString);
                if(taskString.equals("Welcome Back!"))
                {
                    isLoggedIn=true;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if (isLoggedIn)
            {
                boolean loop = true;
                StudentMenuOption options;
                int choose;
                while (loop)
                {
                    printStudentMainMenu();
                    try
                    {
                        choose = keyboard.nextInt();
                        options = StudentMenuOption.values()[choose];
                        switch (options)
                        {
                            case LOGOUT:
                                loop = false;
                                break;
                            case DISPLAY_COURSE:
                                displayCourse(s,os,socketWriter,socketReader);
                                break;
                            case DISPLAY_ALL_COURSE:
                                displayAllCourse(s,os,socketWriter,socketReader,caoNum);
                                break;
                            case DISPLAY_CURRENT_CHOICES:
                                displayCurrentChoice(s,os,socketWriter,socketReader,caoNum);
                               break;
                            case UPDATE_CURRENT_CHOICES:
                                updateChoice(s,os,socketWriter,socketReader,caoNum);
                                break;
                            default:
                                System.out.println("Invalid Input");
                        }
                    } catch (InputMismatchException e)
                    {
                        System.out.println("Invalid Input");
                        keyboard.nextLine();
                    } catch (NoSuchElementException e)
                    {
                        System.out.println("DO NOT USE CTRL+D! THIS WILL FORCE THE SYSTEM TO SHUTDOWN! PLEASE RESTART!");
                        loop = false;
                    } catch (IndexOutOfBoundsException e)
                    {
                        System.out.println("Invalid Input");
                        keyboard.nextLine();
                    }
                }
            }
        }

        public void printStudentMainMenu () {
        System.out.println("\n Option to select:");
        for (int i = 0; i < StudentMenuOption.values().length; i++)
        {
            System.out.println("\t" + i + "- " + StudentMenuOption.values()[i].toString());
        }
        System.out.print("Enter a number to select (0 to quit):> ");
    }
        public void printUpdateChoiceMenu () {
        System.out.println("\n Option to select:");
        for (int i = 0; i < UpdateChoiceOption.values().length; i++)
        {
            System.out.println("\t" + i + "- " + UpdateChoiceOption.values()[i].toString());
        }
        System.out.print("Enter a number to select (0 to quit):> ");
    }
        public void registration(Socket s,OutputStream os,PrintWriter socketWriter,Scanner socketReader)
        {
            try
            {
                int caoNum = 0;
                String dob=null,pwd = null;
                boolean cao=false,date=false,pass = false;
                while(!cao)
                {
                    try
                    {
                        System.out.println("Please enter CAO number: ");
                        caoNum = keyboard.nextInt();
                        if (Integer.toString(caoNum).matches(RegexValidator.cao) && Integer.toString(caoNum).length() == 8)
                        {
                            cao = true;
                        } else
                        {
                            System.out.println("Please enter 8 digits of number");
                            System.out.println();
                        }
                    }
                    catch (InputMismatchException e)
                    {
                        System.out.println("Please Enter DIGITS ONLY!");
                        keyboard.next();
                        System.out.println();
                    }
                }
                while(!date)
                {
                    System.out.println("Please enter your Date Of Birth (yyyy-mm-dd): ");
                    dob = keyboard.next();
                    if(dob.matches(RegexValidator.date))
                    {
                        date=true;
                    }
                    else
                    {
                        System.out.println("Please enter the correct format of date of birth (yyyy-mm-dd)\n");
                    }
                }
                while(!pass)
                {
                    System.out.println("Please enter your password: ");
                    pwd = keyboard.next();
                    if(pwd.length()>=8)
                    {
                        pass=true;
                    }
                    else
                    {
                        System.out.println("Password must be at least 8 characters long\n");
                    }
                }

                    String task = CAOService.REGISTER_COMMAND;
                    String breaks = CAOService.BREAKING_CHARACTER;
                    String command = task+breaks+caoNum+breaks+dob+breaks+pwd;
                    os = s.getOutputStream();
                    socketWriter = new PrintWriter(os,true);
                    socketWriter.println(command);
                    socketReader = new Scanner(s.getInputStream());
                    String taskString = socketReader.nextLine();
                    System.out.println("Client : Response from server: "+ taskString);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        public void displayCourse(Socket s,OutputStream os, PrintWriter socketWriter,Scanner socketReader)
        {
            try
            {
                String breaks = CAOService.BREAKING_CHARACTER;
                String task   = CAOService.DISPLAY_COURSE;
                System.out.println("Please enter a course ID: ");
                String courseId = keyboard.next();
                os = s.getOutputStream();
                socketWriter = new PrintWriter(os,true);
                String command = task+breaks+courseId;
                socketWriter.println(command);
                socketReader = new Scanner(s.getInputStream());
                String response = socketReader.nextLine();
                Object obj=JSONValue.parse(response);
                if(obj==null)
                {
                    System.out.println(response);
                }
                else {
                    JSONObject jsonObject = (JSONObject) obj;
                    String status = (String) jsonObject.get("course");
                    System.out.println(status);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        public void displayAllCourse(Socket s,OutputStream os, PrintWriter socketWriter,Scanner socketReader,int caoNum)
        {
            try
            {
                String task = CAOService.DISPLAY_ALL_COURSE;
                os = s.getOutputStream();
                socketWriter = new PrintWriter(os,true);
                socketWriter.println(task);
                socketReader = new Scanner(s.getInputStream());
                String response = socketReader.nextLine();
                Object obj=JSONValue.parse(response);
                JSONObject jsonObject = (JSONObject) obj;
                JSONArray jsonArray = (JSONArray) jsonObject.get("course");
                Iterator<String> iterator = jsonArray.iterator();
                while(iterator.hasNext())
                {
                    System.out.println(iterator.next());
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        public void displayCurrentChoice(Socket s,OutputStream os, PrintWriter socketWriter,Scanner socketReader,int caoNum)
        {
            try {
                String task = CAOService.DISPLAY_CURRENT_COURSE;
                String breaks = CAOService.BREAKING_CHARACTER;
                String command = task + breaks + caoNum;
                os = s.getOutputStream();
                socketWriter = new PrintWriter(os, true);
                socketWriter.println(command);
                socketReader = new Scanner(s.getInputStream());
                String response = socketReader.nextLine();
                Object obj = JSONValue.parse(response);
                if (obj == null) {
                    System.out.println(response);
                }
                else {
                    JSONObject jsonObject = (JSONObject) obj;
                    JSONArray jsonArray = (JSONArray) jsonObject.get("choice");
                    Iterator<String> iterator = jsonArray.iterator();
                    System.out.println("\nHere are your choices: ");
                    int i =1;
                    while (iterator.hasNext()) {
                        System.out.println(i+": "+iterator.next());
                        i++;
                    }
                    System.out.println();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        public void updateChoice (Socket s,OutputStream os, PrintWriter socketWriter,Scanner socketReader,int caoNum)
        {
            boolean loop = true;
            UpdateChoiceOption options;
            int choose;
            while (loop)
            {
                printUpdateChoiceMenu();
                try
                {
                    choose = keyboard.nextInt();
                    options = UpdateChoiceOption.values()[choose];
                    switch (options)
                    {
                        case EXIT:
                            loop = false;
                            break;
                        case REMOVE_COURSE:
                            removeCourse(s,os,socketWriter,socketReader,caoNum);
                            break;
                        case ADD_COURSE:
                            addCourse(s,os,socketWriter,socketReader,caoNum);
                            break;
                        default:
                            System.out.println("Invalid Input");
                    }
                } catch (InputMismatchException e)
                {
                    System.out.println("Invalid Input");
                    keyboard.nextLine();
                } catch (NoSuchElementException e)
                {
                    System.out.println("DO NOT USE CTRL+D! THIS WILL FORCE THE SYSTEM TO SHUTDOWN! PLEASE RESTART!");
                    loop = false;
                } catch (IndexOutOfBoundsException e)
                {
                    System.out.println("Invalid Input");
                    keyboard.nextLine();
                }
            }

        }

        public void removeCourse(Socket s,OutputStream os, PrintWriter socketWriter,Scanner socketReader,int caoNum)
        {
            try
            {
                String breaks = CAOService.BREAKING_CHARACTER;
                String task = CAOService.REMOVE_COURSE;
                MySqlCourseChoicesDao courseChoicesDao = new MySqlCourseChoicesDao();
                List<String> courseList = courseChoicesDao.getStudentChoices(caoNum);
                if(courseList.isEmpty())
                {
                    System.out.println("You have no selected course, please go add some course");
                }
                else
                {
                    System.out.println("Here is your choices: ");
                    int i = 1;
                    for (String a : courseList)
                    {
                        System.out.println(i + ": " + a);
                        i++;
                    }
                    System.out.println("Please enter course ID that you want to remove: ");
                    String courseId = keyboard.next();
                    String command = task+breaks+courseId+breaks+caoNum;
                    os = s.getOutputStream();
                    socketWriter = new PrintWriter(os,true);
                    socketWriter.println(command);
                    socketReader = new Scanner(s.getInputStream());
                    String res = socketReader.nextLine();
                    System.out.println(res);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        public void addCourse(Socket s,OutputStream os, PrintWriter socketWriter,Scanner socketReader,int caoNum)
        {
            try
            {
                String breaks = CAOService.BREAKING_CHARACTER;
                String task = CAOService.ADD_COURSE;
                MySqlCourseDao courseDao = new MySqlCourseDao();
                List<Course> courseList = courseDao.findAllCourse();
                System.out.println("Here are all available courses: ");
                int i =1;
                for(Course c : courseList)
                {
                    System.out.println(i+": "+c);
                    i++;
                }
                System.out.println("Please enter a course ID that you want to add: ");
                String courseId = keyboard.next();

                if(courseDao.getSpecificCourse(courseId)==null)
                {
                    System.out.println("Please enter a correct course ID");
                }
                else
                {
                    String command = task+breaks+courseId+breaks+caoNum;
                    os = s.getOutputStream();
                    socketWriter = new PrintWriter(os,true);
                    socketWriter.println(command);
                    socketReader = new Scanner(s.getInputStream());
                    String res = socketReader.nextLine();
                    System.out.println(res);
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }