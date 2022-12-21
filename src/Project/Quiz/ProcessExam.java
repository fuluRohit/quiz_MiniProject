package Project.Quiz;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

                    // Private methods -->
                                //
                                // 2-removeStudent(),
                                // 3-removeQuestion(),
                                // 4-addQuestion(),
                                // 5-printQuestionPaper(),
                                // 6-loadQuestions()
                                // 7-setAdminPassword(String)
                                // 8-String getAdminPassword()
                                // 9-changeAdminPassword()



                     // Public methods --
                                    // 1-printOptionsForAdmin(),
                                    // 2-int goForExam() ,
                                    // 3-adminEditQuestionSheet()
                                    //  4-registerStudent(String mobileNo , String name ) ,
                                    //  5-giveGrade(int mark),
                                    //  6-loadStudent()

public class ProcessExam implements Admin{


    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Student> studentArrayList = new ArrayList<>();
    private static ArrayList<Exam> exam = new ArrayList<>();

    public static final String CONNECTION = "jdbc:mysql://localhost:3306/";
    public static final String DB_NAME = "learn";
    public static final String CONNECTION_NAME = "root";
    public static final String CONNECTION_PASSWORD = "Rohit@1996";
    public static final String TABLE_NAME_Question = "questionsheet";
    public static final String COLUMN_Question = "Question";
    public static final String COLUMN_A = "A";
    public static final String COLUMN_B = "B";
    public static final String COLUMN_C = "C";
    public static final String COLUMN_D = "D";
    public static final String COLUMN_Ans = "Ans";
    public static final String COLUMN_Index = "index";
    public static final String TABLE_NAME_STUDENT = "Student";
    public static final String COLUMN_MOBILE_NUMBER = "mobileNumber";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_MARK = "mark";
    public static final String COLUMN_ATTEND ="attentd";



    private static void adminSetPassword(){
        AdminPassword adminPassword = new AdminPassword();
        System.out.println("Enter New PassWord For Admin");
        String password=scanner.next();
        adminPassword.setPassword(password);
        try{
            FileOutputStream fos = new FileOutputStream ("C:\\AdminPassword\\Admin.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(adminPassword);
            oos.flush();
            System.out.println("PassWord Is Changed Successfully....");
            System.out.println("--------------------------------------");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static String getAdminPassword(){
        String password ;
        try{
            FileInputStream fis = new FileInputStream("C:\\AdminPassword\\Admin.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object object = ois.readObject(); //Read the object
            AdminPassword adminPassword=(AdminPassword) object;//convert to student
            password=adminPassword.getPassword();

        }
        catch(Exception e){
            e.printStackTrace();
            password="Exception";
        }
        return password;
    }
    private static void changeAdminPassword(){
        System.out.println("Enter Old PassWord : ");
        String old_password =scanner.next();
        if(old_password.equals(getAdminPassword())){
            adminSetPassword();
        }else {
            System.out.println("Wrong Password , Try Again...");
        }
    }




    private static ArrayList<Student> loadStudent(){
        String COLUMN_PHONE = " mobileNumber ";
        String COLUMN_NAME = " name ";
        String COLUMN_MARK = " mark ";

        try {
            Connection conn = DriverManager.getConnection(CONNECTION + DB_NAME, CONNECTION_NAME, CONNECTION_PASSWORD);
            Statement statement = conn.createStatement();
            statement.execute("select * from "+TABLE_NAME_STUDENT);
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {

                String phoneNumber = resultSet.getString(1);
                String name = resultSet.getString(2);
                int mark = resultSet.getInt(3);
                String attend = resultSet.getString(4);
                Student studentObject = Student.createNewStudent(phoneNumber,name,mark,attend);
                studentArrayList.add(studentObject);
            }
            resultSet.close();
            statement.close();
            conn.close();
        } catch (
                SQLException sql) {
            System.out.println(sql.getMessage());
        }
        System.out.println("--------------------------------------------");
        System.out.println("All Student Added Into ArrayList");
        System.out.println("--------------------------------------------");
      return studentArrayList;
    }

    private static void printStudentDetails(ArrayList<Student>studentArrayList){
        System.out.println("Publishing The Details Of All Students.");
        for (int i=0;i<studentArrayList.size();i++){
            System.out.println("--------------------------------------------");
            System.out.println("Name : "+studentArrayList.get(i).getName()+"\n"+
                    "Mobile Number : "+studentArrayList.get(i).getMobileNo()+"\n"+
                    "Exam Attended : "+studentArrayList.get(i).getAttendExam());
            System.out.println("--------------------------------------------");
        }
    }

    private static void printStudentResult(ArrayList<Student>studentArrayList){
        System.out.println("Publishing The Result Of All Students.");
        for (int i=0;i<studentArrayList.size();i++){
            System.out.println("--------------------------------------------");
            System.out.println("Name : "+studentArrayList.get(i).getName()+"\n"+
                    "Mobile Number : "+studentArrayList.get(i).getMobileNo()+"\n"+
                    "marks = "+studentArrayList.get(i).getMark());
            ProcessExam.giveGrade(studentArrayList.get(i).getMark());
            System.out.println("--------------------------------------------");
        }
    }

    public static void saveStudentData(ArrayList<Student>students){

   }

    public static void individualResult(String mobileNumber) {
        try {
            Connection conn = DriverManager.getConnection(CONNECTION + DB_NAME, CONNECTION_NAME, CONNECTION_PASSWORD);
            Statement statement = conn.createStatement();
            statement.execute("select * from Student");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {

                String mobileNumber1= resultSet.getString(1);
                String name = resultSet.getString(2);
                int mark = resultSet.getInt(3);
                if(mobileNumber1.equals(mobileNumber)){
                    System.out.println("--------------------------------");
                    System.out.println("Your Name : "+name);
                    System.out.println("Your Mobile Number :" +mobileNumber1);
                    System.out.println("Marks : "+mark);
                    giveGrade(mark);
                    System.out.println("------------------------------");
                }
            }
            resultSet.close();
            statement.close();
            conn.close();
        } catch (
                SQLException sql) {
            System.out.println(sql.getMessage());
        }
    }

    private static void printStudentList(){
        printStudentDetails(loadStudent());
        /*
        try {
            Connection conn = DriverManager.getConnection(CONNECTION + DB_NAME, CONNECTION_NAME, CONNECTION_PASSWORD);
            Statement statement = conn.createStatement();
            statement.execute("select * from Student");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                System.out.println("------------------------------------------------");
                System.out.println("Student Mobile Number :" + resultSet.getString(1));
                System.out.println("Name : " + resultSet.getString(2));

                System.out.println("----------------------------------------------");
            }
            resultSet.close();
            statement.close();
            conn.close();
        } catch (
                SQLException sql) {
            System.out.println(sql.getMessage());
        }

         */
    }

    private static void printStudentsResultList(){
        ProcessExam.printStudentResult(loadStudent());
        /*try {
            Connection conn = DriverManager.getConnection(CONNECTION + DB_NAME, CONNECTION_NAME, CONNECTION_PASSWORD);
            Statement statement = conn.createStatement();
            statement.execute("select * from Student");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                System.out.println("-----------------------------------------------");
                System.out.println("Student Mobile Number :" + resultSet.getString(1));
                System.out.println("Name : " + resultSet.getString(2));
                System.out.println("Marks : " + resultSet.getInt(3));
                int mark = resultSet.getInt(3);
                giveGrade(mark);
                System.out.println("----------------------------------------------");
            }
            resultSet.close();
            statement.close();
            conn.close();
        } catch (
                SQLException sql) {
            System.out.println(sql.getMessage());
        }
         */
    }

    private static void giveGrade(int mark){
        if (mark>=8){
            System.out.println("Result = CLASS 'A' ");
            System.out.println("Congratulations");
        }
        if (mark<8 && mark>=5){
            System.out.println("Result = CLASS 'B' ");
            System.out.println("Improve Your Study");
        }
        if(mark<5){
            System.out.println("Result = Fail ");
            System.out.println("Try Next Time ");
        }
    }

    public static void registerStudent(String mobileNo , String name ){

        try {
            Connection conn = DriverManager.getConnection(CONNECTION + DB_NAME, CONNECTION_NAME, CONNECTION_PASSWORD);
            Statement statement = conn.createStatement();
            statement.execute("Create table if not exists Student(mobileNumber varchar(10) primary key,name varchar(100),mark int);");
             statement.execute("insert into "+TABLE_NAME_STUDENT+"("+COLUMN_MOBILE_NUMBER+","+COLUMN_NAME+","+COLUMN_ATTEND+")values('"+mobileNo+"','"+name+"','Not');");
            statement.close();
            conn.close();
            System.out.println(" Student Is Added In Record :");
        }catch (SQLException sql){
            System.out.println(sql.getMessage());
        }


    }

    public static void adminEditQuestionSheet() {
        System.out.println("------------------------");
        System.out.println("Enter Admin Password : ");
        String password = scanner.next();
        if (password.equals(getAdminPassword())) {

            boolean flag = true;
            printOptionsForAdmin();
            while (flag) {
                System.out.println("------------------");
                System.out.println("Enter Your Choice : ");
                int action = scanner.nextInt();
                switch (action) {
                    case 0:
                        System.out.println("\nExisting From Admin Page.. \n" +
                                "------------------------------------------");
                        flag = false;
                        break;
                    case 1:
                        printQuestionPaper();
                        break;
                    case 2:
                        addQuestion();
                        break;
                    case 3:
                        removeQuestion();
                        break;
                    case 4:
                        printStudentList();
                        break;
                    case 5:
                        printStudentsResultList();
                        break;
                    case 6:
                        removeStudent();
                        break;
                    case 7:
                        changeAdminPassword();
                        break;
                    case 8:
                        printOptionsForAdmin();
                        break;
                    default:
                        System.out.println(" Choose Correct Option..Try Once Again.. ");
                        break;
                }
                System.out.println("Enter 8: show Options Again");
            }
        } else {
            System.out.println(" Wrong Password..");
        }
    }

    private static void removeStudent(){
        printStudentList();
        System.out.println("--------------");
        System.out.println("Enter Mobile Number Of Student To remove from class ");
        String mobileNumber = scanner.next();
        try {
            Connection conn = DriverManager.getConnection(CONNECTION + DB_NAME, CONNECTION_NAME, CONNECTION_PASSWORD);
            Statement statement = conn.createStatement();
            statement.execute("delete from "+TABLE_NAME_STUDENT +" where "+COLUMN_MOBILE_NUMBER+"="+mobileNumber+";");
            System.out.println("--------------------");
            System.out.println("Student Is Removed SuccessFully..");
            System.out.println("--------------------");
            statement.close();
            conn.close();
        } catch (
                SQLException sql) {
            System.out.println(sql.getMessage());
        }
    }

    private static void removeQuestion(){
        printQuestionPaper();
        System.out.println("Enter The Index of Question For Delete ");
        int index = scanner.nextInt();
        try {
            Connection conn = DriverManager.getConnection(CONNECTION + DB_NAME, CONNECTION_NAME, CONNECTION_PASSWORD);
            Statement statement = conn.createStatement();
           // statement.execute("DELETE FROM "+TABLE_NAME_Question+" WHERE "+COLUMN_Index+"="+index+";");
            statement.execute("DELETE FROM "+TABLE_NAME_Question+" WHERE (`"+COLUMN_Index+"` = '"+index+"');");
            //statement.execute("delete from "+ TABLE_NAME_Question +" where "+COLUMN_Index+"="+index+";");
            System.out.println("--------------------");
            System.out.println("Question"+ index+" Is Removed SuccessFully..");
            System.out.println("--------------------");
            statement.close();
            conn.close();
        } catch (
                SQLException sql) {
            System.out.println(sql.getMessage());
        }
    }

    private static void addQuestion(){
        printQuestionPaper();
        System.out.println("Enter Question Index : ");
        int index = scanner.nextInt();
        System.out.println("Enter Question :");
        String Question =scanner.next();
        System.out.println("Enter Option A : ");
        String optionA = scanner.next();
        System.out.println("Enter Option B : ");
        String optionB =scanner.next();
        System.out.println("Enter Option C : ");
        String optionC =scanner.next();
        System.out.println("Enter Option D : ");
        String optionD =scanner.next();
        System.out.println("Enter Answer : ");
        String ans = scanner.next();
        try {
            Connection conn = DriverManager.getConnection(CONNECTION + DB_NAME, CONNECTION_NAME, CONNECTION_PASSWORD);
            Statement statement = conn.createStatement();
           // statement.execute(" Create table if not exists QuestionSheet(Question varchar(1000),a varchar(500)" +
                  //  ",b varchar(500),c varchar(500),d varchar(500),Ans varchar(500),index int(3) primary key);");
           // statement.execute("insert into "+ TABLE_NAME_Question +"("+COLUMN_Question+","+COLUMN_A+","+COLUMN_B+","+COLUMN_C+","
             //       +COLUMN_D+","+COLUMN_Ans+","+COLUMN_Index+")"+
               //     "values"+"('"+Question+"','"+optionA+"','"+optionB+"','"+optionC+"','"+optionD+"','"+ans+"',"+index+");");
            statement.execute("INSERT INTO "+TABLE_NAME_Question+"(`"+COLUMN_Question+"`, `"+COLUMN_A+"`, `"+COLUMN_B+"`, `"+COLUMN_C+"`, `"+COLUMN_D+"`, `"+COLUMN_Ans+"`, `index`) VALUES ('"+Question+"', '"+optionA+"', '"+optionB+"', '"+optionC+"', '"+optionD+"', '"+ans+"', '"+index+"');");
            System.out.println("--------------------");
            System.out.println("Question Is Added SuccessFully..");
            System.out.println("--------------------");
            statement.close();
            conn.close();
        } catch (
                SQLException sql) {
            System.out.println(sql.getMessage());
        }

    }

    private static void printQuestionPaper() {
        try {
            Connection conn = DriverManager.getConnection(CONNECTION + DB_NAME, CONNECTION_NAME, CONNECTION_PASSWORD);
            Statement statement = conn.createStatement();
            statement.execute("select * from QuestionSheet");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                System.out.println("Question No. :" + resultSet.getInt(7));
                System.out.println("Question : " + resultSet.getString(1));
                System.out.println("Option A : " + resultSet.getString(2));
                System.out.println("Option B : " + resultSet.getString(3));
                System.out.println("Option C : " + resultSet.getString(4));
                System.out.println("Option D : " + resultSet.getString(5));
                System.out.println("Answer : " + resultSet.getString(6));
                System.out.println("----------------------------------------------");
            }
            resultSet.close();
            statement.close();
            conn.close();
        } catch (
                SQLException sql) {
            System.out.println(sql.getMessage());
        }
    }

    private static void loadQuestions() {
        String COLUMN_Question = "Question";
        String COLUMN_A = "A";
        String COLUMN_B = "B";
        String COLUMN_C = "C";
        String COLUMN_D = "D";
        String COLUMN_Ans = "Ans";
        String COLUMN_Index = "index";

        try {
            Connection conn = DriverManager.getConnection(CONNECTION + DB_NAME, CONNECTION_NAME, CONNECTION_PASSWORD);
            Statement statement = conn.createStatement();
            statement.execute("select * from QuestionSheet");
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                int i = resultSet.getInt(COLUMN_Index);
                String Que = resultSet.getString(COLUMN_Question);
                String A = resultSet.getString(COLUMN_A);
                String B = resultSet.getString(COLUMN_B);
                String C = resultSet.getString(COLUMN_C);
                String D = resultSet.getString(COLUMN_D);
                String Ans = resultSet.getString(COLUMN_Ans);
                Exam examObject = Exam.createNewExam(i, Que, A, B, C, D, Ans);
                exam.add(examObject);
            }
            resultSet.close();
            statement.close();
            conn.close();
        } catch (
                SQLException sql) {
            System.out.println(sql.getMessage());
        }

        System.out.println(" Question Sheet Is Added ");
    }

    public static int goForExam() {
        loadQuestions();
        int marks = 0;
        for (int j = 0; j < 10; j++) {
            byte temp;
            System.out.println("Question" + (j + 1) + " : " + exam.get(j).getQuestion());
            System.out.println("Options : ");
            System.out.println("    1 ->  " + exam.get(j).getOption_A());
            System.out.println("    2 ->  " + exam.get(j).getOption_B());
            System.out.println("    3 ->  " + exam.get(j).getOption_C());
            System.out.println("    4 ->  " + exam.get(j).getOption_D());
            System.out.println("Enter Your Answer As Option 1 , 2 , 3 , 4 ");
            temp = scanner.nextByte();
            if (temp == 1) {
                if (exam.get(j).getOption_A().equals(exam.get(j).getAnswer())) {
                    marks++;
                    continue;
                }
            }
            if (temp == 2) {
                if (exam.get(j).getOption_B().equals(exam.get(j).getAnswer())) {
                    marks++;
                    continue;
                }
            }
            if (temp == 3) {
                if (exam.get(j).getOption_C().equals(exam.get(j).getAnswer())) {
                    marks++;
                    continue;
                }
            }
            if (temp == 4) {
                if (exam.get(j).getOption_D().equals(exam.get(j).getAnswer())) {
                    marks++;
                    continue;
                }
            }
        }
        return marks;
    }

    private static void printOptionsForAdmin(){
        System.out.println(" Enter 0: For Left Admin Page \n Enter 1: for print Question paper \n" +
                " Enter 2: for Add Question \n Enter 3: For Remove Question \n" +
                " Enter 4: List Of All Student \n Enter 5: For Display Result \n" +
                "Enter 6 : Remove Student From List \nEnter 7: To Change Admin Password \n" +
                "Enter 8: to Print Options Again ");
    }
}


