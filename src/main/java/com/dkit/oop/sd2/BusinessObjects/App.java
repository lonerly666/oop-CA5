package com.dkit.oop.sd2.BusinessObjects;

/** OOP 2021
 * This App demonstrates the use of a Data Access Object (DAO)
 * to separate Business logic from Database specific logic.
 * It uses DAOs, Data Transfer Objects (DTOs), and
 * a DaoInterface to define a contract between Business Objects
 * and DAOs.
 *
 * "Use a Data Access Object (DAO) to abstract and encapsulate all
 * access to the data source. The DAO manages the connection with
 * the data source to obtain and store data" Ref: oracle.com
 *
 * Here we use one DAO per database table.
 *
 * Use the SQL script included with this project to create the
 * required MySQL user_database and user table
 */



import com.dkit.oop.sd2.Constants.HomeMenuOption;
import com.dkit.oop.sd2.Constants.StudentMenuOption;
import com.dkit.oop.sd2.DAOs.MySqlStudentDao;
import com.dkit.oop.sd2.DAOs.MySqlUserDao;
import com.dkit.oop.sd2.DAOs.StudentDaoInterface;
import com.dkit.oop.sd2.DAOs.UserDaoInterface;
import com.dkit.oop.sd2.DTOs.Course;
import com.dkit.oop.sd2.DTOs.Student;
import com.dkit.oop.sd2.DTOs.User;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.util.*;


public class App
{
    public Scanner keyboard  = new Scanner(System.in);
    public static void main(String[] args)
    {
        App app = new App();
        app.start();
        //        UserDaoInterface IUserDao = new MySqlUserDao();  //"IUse..." -> "I" for Interface
        //        // Notice that the userDao reference is an Interface type.
        //        // This allows for the use of different concrete implementations.
        //        // e.g. we could replace the MySqlUserDao with an OracleUserDao
        //        // (accessing an Oracle Database)
        //        // without changing anything in the Interface.
        //        // If the Interface doesn't change, then none of the
        //        // code below that uses the interface needs to change.
        //        // The 'contract' defined by the interface will not be broken.
        //        // This means that this code is independent of the code
        //        // used to access the database. (Reduced coupling).
        //
        //        // The Business Objects require that all User DAOs implement
        //        // the interface called "UserDaoInterface", as the code uses
        //        // only references of the interface type to access the DAO methods.
        //        try
        //        {
        //            System.out.println("\nCall findAllUsers()");
        //            List<User> users = IUserDao.findAllUsers();
        //
        //            if( users.isEmpty() )
        //                System.out.println("There are no Users");
        //
        //            for( User user : users )
        //                System.out.println("User: " + user.toString());
        //
        //            // test dao - with good username and password
        //            System.out.println("\nCall: findUserByUsernamePassword()");
        //            User user = IUserDao.findUserByUsernamePassword("smithj", "password");
        //            if(user != null)
        //                System.out.println("User found: " + user);
        //            else
        //                System.out.println("Username with that password not found");
        //
        //            // test dao - with bad username
        //            user = IUserDao.findUserByUsernamePassword("madmax", "thunderdome");
        //            if(user != null)
        //                System.out.println("User found: " + user);
        //            else
        //                System.out.println("Username with that password not found");
        //            List<User> u1 = IUserDao.findAllUsersLastNameContains("Smith");
        //            for(User u :u1)
        //            {
        //                    System.out.println(u);
        //            }
        //        }
        //        catch( DaoException e )
        //        {
        //            e.printStackTrace();
        //        }
    }
        private void start()
    {

        // load students
        StudentManager studentManager = new StudentManager();

        // load courses
        CourseManager courseManager = new CourseManager();
        CourseChoiceManager ccmg = new CourseChoiceManager(studentManager,courseManager);
        mainMenu(studentManager, courseManager,ccmg);


        //        Student s1 = new Student(9912333,"29/04/2000","JnJc0429","doremineo@gmail.com");
        //        Student s2 = new Student(9912110,"29/04/2000","JnJc0429","doremineo@gmail.com");
        //        Student s3 = new Student(12345678,"21/03/2000","J123456","doremineo@gmail.com");
        //        studentManager.addStudent(s1);
        //        studentManager.addStudent(s2);
        //        studentManager.addStudent(s3);
        //        s2.setCaoNumber(1122333);
        //        studentManager.removeStudent(s2);
        //        Map<Integer,Student> studentMap = studentManager.getAllStudent();
        //        for(Map.Entry<Integer,Student>entry:studentMap.entrySet())
        //        {
        //            System.out.println(entry.getValue());
        //        }
        // display a menu to do things
        // manual testing of mgr public interface

        //        if ( mgr.login(12345678, "2000-04-29","JnJc0429") )
        //        {
        //            Student student = mgr.getStudentDetails(12345678);
        //            Course course = mgr.getCourseDetails("dk001");
        //            System.out.println("Student: " + student);
        //            System.out.println("Course: "+course);
        //        }
        //        else
        //            System.out.println("Not logged in - try again");


        //mgr.saveToFile();

    }

        public void mainMenu (StudentManager smg, CourseManager cmg,CourseChoiceManager ccmg)
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
                            studentMenu(smg,cmg,ccmg);
                            break;
                        case REGISTER:
//                            registerMenu();
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

        public void studentMenu (StudentManager smg,CourseManager cmg,CourseChoiceManager ccmg)
        {
            boolean isLoggedIn = true;
            Student currentUsr = null;

//            try
//            {
//                System.out.println("Please Enter Your CAO Number: ");
//                int caoNum = keyboard.nextInt();
//                System.out.println("Please Enter Your Date Of Birth (yyyy-mm-dd): ");
//                String dateOfBirth = keyboard.next();
//                System.out.println("Please Enter Your Password: ");
//                String pass = keyboard.next();
//                if (ccmg.login(caoNum, dateOfBirth, pass))
//                {
//                    isLoggedIn = true;
//                    currentUsr = new Student(smg.getStudent(caoNum));
//                }
//
//            } catch (InputMismatchException e)
//            {
//                System.out.println("Invalid Input");
//                keyboard.nextLine();
//            } catch (NoSuchElementException e)
//            {
//                System.out.println("DO NOT USE CTRL+D! THIS WILL FORCE THE SYSTEM TO SHUTDOWN! PLEASE RESTART!");
//            }

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
                                break;
                            case DISPLAY_ALL_COURSE:
                                break;
                            case DISPLAY_CURRENT_CHOICES:
                               break;
                            case UPDATE_CURRENT_CHOICES:
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

        public void addCourse (CourseManager cmg)
        {
            try
            {
                System.out.println("Please enter new course id: ");
                String courseId = keyboard.next();
                System.out.println("Please enter level for this course: ");
                String level = keyboard.next();
                System.out.println("Please enter title for this course: ");
                String title = keyboard.next();
                title += keyboard.next();
                System.out.println("Please enter institution for this course: ");
                String institution = keyboard.next();
                Course newCourse = new Course(courseId, level, title, institution);
                cmg.addCourse(newCourse);
            } catch (InputMismatchException e)
            {
                System.out.println("Input mismatch!");
            }


        }

        public void updateChoice (CourseManager cmg, CourseChoiceManager ccmg, Student currentUsr)
        {
            String ans = "y";
            ArrayList<String> selectedCourseList = new ArrayList<>();
            while (ans.equals("y"))
            {
                try
                {
                    System.out.println("These are all available courses: ");
                    for (int i = 0; i < cmg.getAllCourses().size(); i++)
                    {
                        System.out.println(cmg.getAllCourses().get(i));
                    }
                    System.out.println("Please enter a courseId: ");
                    String courseId = keyboard.next();
                    if (cmg.getCourse(courseId) != null)
                    {
                        selectedCourseList.add(courseId);
                        System.out.println("Course Added");
                    } else
                    {
                        System.out.println("There is no such course!");
                    }
                    System.out.println("Do you want to choose again? y/(any key)");
                    ans = keyboard.next();
                } catch (InputMismatchException e)
                {
                    System.out.println("Invalid Input");
                }
            }
            ccmg.updateChoices(currentUsr.getCaoNumber(), selectedCourseList);
        }

        public void removeCourse (CourseManager cmg, CourseChoiceManager ccmg)
        {
            if (cmg.getAllCourses().isEmpty())
            {
                System.out.println("There is no course available");
            } else
            {
                System.out.println("These are available courses: ");
                for (Course c : cmg.getAllCourses())
                {
                    System.out.println(c);
                }
                System.out.println("Please enter courseId that you want to remove: ");
                String courseId = keyboard.next();
                cmg.removeCourse(courseId);
            }
        }

        public void addStudent (StudentManager smg)
        {
            try
            {
                System.out.println("Please enter a new cao number: ");
                int caoNum = keyboard.nextInt();
                System.out.println("Please enter a new date of birth: ");
                String dateOfBirth = keyboard.next();
                System.out.println("Please enter password: ");
                String password = keyboard.next();
                if (!dateOfBirth.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$"))
                {
                    System.out.println("Wrong Date of Birth Format (yyyy-mm-dd)");
                }
                else if (!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"))
                {
                    System.out.println("Wrong password Format ");
                }
                else if (!(Integer.toString(caoNum).matches("\\d+") && Integer.toString(caoNum).length() == 8))
                {
                    System.out.println("Wrong CAO Number format (must contain all numbers and 8 digits)");
                }
                else
                {
                    Student newStudent = new Student(caoNum, dateOfBirth, password);
                    smg.addStudent(newStudent);
                }
            } catch (InputMismatchException e)
            {
                System.out.println("Invalid Input!");
            }
        }
    }