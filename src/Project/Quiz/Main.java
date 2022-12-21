package Project.Quiz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/*
               Admin Process for only Admin
                    - Admin password -
                   1: for print Question paper
                   2: for Add Question
                   3: For Remove Question
                   4: List Of All Student
                   5: For Display Result
                   6 : Remove Student From List

                  Local Process for Every One

                   1: Register New Student
                   2: For Start Examination
                   3: For See Your Result
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===========================================\n"+
                "Welcome To Quiz App \nRegister Your Self Using Your Mobile Number....\n" +
                "===========================================");
        boolean flag = true;
        printOptions();
        while (flag) {

            System.out.println("Enter Your Option : ");
            int option = scanner.nextInt();
            switch (option) {
                case 0:
                    flag = false;
                    System.out.println("Exiting From Application");
                    break;
                case 1:
                    addStudent();
                    break;
                case 2:
                    startExam();
                    break;
                case 3:
                    showMyResult();
                    break;
                case 4:
                    ProcessExam.adminEditQuestionSheet();
                    break;
                case 5:
                    printOptions();
                default:
                    System.out.println("Invalid Choice...Try Again..");
                    break;

            }
            System.out.println("Enter 5 To See Options :");
        }
    }

    public static void showMyResult() {
        System.out.println("Enter Your Mobile Number: ");
        String mobileNumber = scanner.next();
        ProcessExam.individualResult(mobileNumber);
    }

    public static void startExam() {
        System.out.println("Enter Your Resister Mobile Number : ");
        String mobileNumber = scanner.next();
        System.out.println("Exam Start Now .......");

        int marks = ProcessExam.goForExam();

        try {
            Connection conn = DriverManager.getConnection(ProcessExam.CONNECTION + ProcessExam.DB_NAME, ProcessExam.CONNECTION_NAME, ProcessExam.CONNECTION_PASSWORD);
            Statement statement = conn.createStatement();

            statement.execute("update student set " + ProcessExam.COLUMN_MARK + " = " + marks + " where mobileNumber= '" + mobileNumber + "';");
            statement.execute("update student set " + ProcessExam.COLUMN_ATTEND + " = 'Yes' where mobileNumber= '" + mobileNumber + "';");
            statement.close();
            conn.close();
        } catch (SQLException sql) {
            System.out.println(sql.getMessage());
        }
        System.out.println("your Exam Is Done ..........");
        System.out.println("You Can See Your Result");
        System.out.println("=====================================================");

    }

    public static void addStudent() {
        System.out.println("Your Mobile Number Should Be Unique For Every Student");
        System.out.println("Enter Your Mobile Number");
        String mobileNO = scanner.next();
        System.out.println("Enter Your Name :");
        String name = scanner.next();
        ProcessExam.registerStudent(mobileNO, name);
        System.out.println("-------------------------------");
    }

    public static void printOptions() {
        System.out.println("Enter 0: For Exit From Application \nEnter 1: Register New Student \n" +
                "Enter 2: For Start Examination \n" +
                "Enter 3: For See Your Result \n" + "Enter 4: For Admin Process  \nEnter 5: For Print Option Again ");
    }
}


