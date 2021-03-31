package com.dkit.oop.sd2.BusinessObjects;

import com.dkit.oop.sd2.Constants.HomeMenuOption;
import com.dkit.oop.sd2.Constants.StudentMenuOption;
import com.dkit.oop.sd2.Constants.UpdateChoiceOption;
import com.dkit.oop.sd2.DAOs.MySqlCourseChoicesDao;
import com.dkit.oop.sd2.DAOs.MySqlCourseDao;
import com.dkit.oop.sd2.DAOs.MySqlStudentDao;
import com.dkit.oop.sd2.DTOs.Course;
import java.util.*;

public class App
{
    public Scanner keyboard  = new Scanner(System.in);
    public static void main(String[] args)
    {
        App app = new App();
        app.start();

    }
        private void start()
    {

        // load students
        MySqlStudentDao studentDao = new MySqlStudentDao();
        MySqlCourseDao courseDao = new MySqlCourseDao();
        MySqlCourseChoicesDao courseChoiceDao = new MySqlCourseChoicesDao();
        mainMenu(studentDao,courseDao,courseChoiceDao);
    }

        public void mainMenu (MySqlStudentDao studentDao,MySqlCourseDao courseDao,MySqlCourseChoicesDao courseChoicesDao)
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
                            loop = false;
                            break;
                        case LOGIN:
                            studentMenu(studentDao,courseDao,courseChoicesDao);
                            break;
                        case REGISTER:
                            registration(studentDao);
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

        public void printHomeMenu () {
        System.out.println("\n Option to select:");
        for (int i = 0; i < HomeMenuOption.values().length; i++)
        {
            System.out.println("\t" + i + "- " + HomeMenuOption.values()[i].toString());
        }
        System.out.print("Enter a number to select (0 to quit):> ");
    }

        public void studentMenu (MySqlStudentDao studentDao,MySqlCourseDao courseDao,MySqlCourseChoicesDao courseChoicesDao)
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
                isLoggedIn =studentDao.login(caoNum,dob,pwd);
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
                                displayCourse(courseDao);
                                break;
                            case DISPLAY_ALL_COURSE:
                                displayAllCourse(courseDao);
                                break;
                            case DISPLAY_CURRENT_CHOICES:
                                displayCurrentChoice(courseChoicesDao, caoNum);
                               break;
                            case UPDATE_CURRENT_CHOICES:
                                updateChoice(courseDao,courseChoicesDao, caoNum);
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
            else
            {
                System.out.println("Invalid Credentials!");
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

//        public void adminMenu (StudentManager smg, CourseManager cmg, CourseChoicesManager ccmg)
//        {
//            boolean loop = true;
//            int choose;
//            AdminMenuOptions options;
//            while (loop)
//            {
//                printAdminMainMenu();
//                try
//                {
//                    choose = keyboard.nextInt();
//                    options = AdminMenuOptions.values()[choose];
//                    switch (options)
//                    {
//                        case EXIT:
//                            loop = false;
//                            break;
//                        case ADD_A_COURSE:
//                            addCourse(cmg);
//                            break;
//                        case REMOVE_A_COURSE:
//                            removeCourse(cmg, ccmg);
//                            break;
//                        case DISPLAY_ALL_COURSE:
//                            for (Course c : cmg.getAllCourses())
//                            {
//                                System.out.println(c);
//                            }
//                            break;
//                        case DISPLAY_COURSE_WITH_ID:
//                            try
//                            {
//                                System.out.print("Please enter a course Id that you want to view: ");
//                                String courseId = keyboard.next();
//                                System.out.println(ccmg.getCourseDetails(courseId));
//                            } catch (NullPointerException e)
//                            {
//                                System.out.println("No such course!");
//                            }
//                            break;
//                        case ADD_STUDENT:
//                            addStudent(smg);
//                            break;
//                        case REMOVE_STUDENT:
//                            try
//                            {
//                                System.out.println("Please enter CAO Number: ");
//                                int caoNum = keyboard.nextInt();
//                                if (ccmg.selectedChoice.containsKey(caoNum))
//                                {
//                                    ccmg.selectedChoice.remove(caoNum);
//                                }
//                                smg.removeStudent(caoNum);
//                            } catch (NullPointerException e)
//                            {
//                                System.out.println("Can't find specific student");
//                            }
//                            break;
//                        case DISPLAY_STUDENT:
//                            try
//                            {
//                                System.out.println("Please enter CAO Number :");
//                                int caoNum = keyboard.nextInt();
//                                System.out.println(ccmg.getStudentDetails(caoNum));
//                            } catch (NullPointerException e)
//                            {
//                                System.out.println("Can't find specific student");
//                            }
//                            break;
//                        default:
//                            System.out.println("Invalid Input");
//                    }
//                } catch (InputMismatchException e)
//                {
//                    System.out.println("Invalid Input");
//                    keyboard.nextLine();
//                } catch (NoSuchElementException e)
//                {
//                    System.out.println("DO NOT USE CTRL+D! THIS WILL FORCE THE SYSTEM TO SHUTDOWN! PLEASE RESTART!");
//                    loop = false;
//                } catch (IndexOutOfBoundsException e)
//                {
//                    System.out.println("Invalid Input");
//                    keyboard.nextLine();
//                }
//            }
//
//        }
//
//        public void printAdminMainMenu () {
//        System.out.println("\n Option to select:");
//        for (int i = 0; i < AdminMenuOptions.values().length; i++)
//        {
//            System.out.println("\t" + i + "- " + AdminMenuOptions.values()[i].toString());
//        }
//        System.out.print("Enter a number to select (0 to quit):> ");
//    }
        public void registration(MySqlStudentDao studentDao)
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
                        if (Integer.toString(caoNum).matches("\\d+") && Integer.toString(caoNum).length() == 8)
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
                    if(dob.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$"))
                    {
                        date=true;
                    }
                    else
                    {
                        System.out.println("Please enter the correct format of date of birth (yyyy-mm-dd)");
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
                        System.out.println("Password must be at least 8 characters long");
                    }
                }

                if(cao&&date&&pass)
                {

                    studentDao.addStudent(caoNum,dob,pwd);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        public void displayCourse(MySqlCourseDao courseDao)
        {
            try
            {
                System.out.println("Please enter a course ID: ");
                String courseId = keyboard.next();
                Course c = courseDao.getSpecificCourse(courseId);
                if(c!=null)
                {
                    System.out.println(c);
                }
                else
                {
                    System.out.println("No such course");
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        public void displayAllCourse(MySqlCourseDao courseDao)
        {
            try
            {
                List<Course> courseList = courseDao.findAllCourse();
                System.out.println("\nHere are all available courses: ");
                int i =1;
                for(Course c:courseList)
                {
                    System.out.println(i+": "+c);
                    i++;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        public void displayCurrentChoice(MySqlCourseChoicesDao courseChoicesDao,int caoNum)
        {
            try
            {
                List <String> courseList = courseChoicesDao.getStudentChoices(caoNum);
                System.out.println("\nHere are all your current choices: ");
                int i =1;
                for(String s:courseList)
                {
                    System.out.println(i+": "+s);
                    i++;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        public void updateChoice (MySqlCourseDao courseDao,MySqlCourseChoicesDao courseChoicesDao,int caoNum)
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
                            removeCourse(courseChoicesDao,caoNum);
                            break;
                        case ADD_COURSE:
                            addCourse(courseDao,courseChoicesDao,caoNum);
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

        public void removeCourse(MySqlCourseChoicesDao courseChoicesDao,int caoNum)
        {
            try
            {
                List<String> courseList = courseChoicesDao.getStudentChoices(caoNum);
                if(courseList.isEmpty())
                {
                    System.out.println("You have no selected course, please go add some course");
                }
                else
                {
                    System.out.println("Here is your choices: ");
                    int i = 1;
                    for (String s : courseList)
                    {
                        System.out.println(i + ": " + s);
                        i++;
                    }
                    System.out.println("Please enter course ID that you want to remove: ");
                    String courseId = keyboard.next();
                    if(courseChoicesDao.getStudentChoices(caoNum).contains(courseId))
                    {
                        if (courseChoicesDao.removeChoice(caoNum, courseId))
                        {
                            System.out.println("Course Removed");
                        }
                        else
                        {
                            System.out.println("Opps, Something went Wrong");
                        }
                    }
                    else
                    {
                        System.out.println("Please enter a correct course ID");
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        public void addCourse(MySqlCourseDao courseDao, MySqlCourseChoicesDao courseChoicesDao,int caoNum)
        {
            try
            {
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
                    if(courseChoicesDao.getStudentChoices(caoNum).contains(courseId))
                    {
                        System.out.println("You have already chosen this course!");
                    }
                    else
                    {
                        if(courseChoicesDao.addChoice(caoNum,courseId))
                        {
                            System.out.println("Course Added");
                        }
                        else
                        {
                            System.out.println("Opps, something went wrong please try again");
                        }
                    }
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }