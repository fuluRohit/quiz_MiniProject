package Project.Quiz;

public class Student /*implements Comparable*/{
   private String MobileNo;
   private String name;
   private int mark;
   private String attendExam ;



    public Student(String mobileNo, String name, String attendExam) {
        MobileNo = mobileNo;
        this.name = name;
        this.mark=0;
        this.attendExam=attendExam;
    }

    public Student(String mobileNo, String name, int mark,String attendExam) {
        MobileNo = mobileNo;
        this.name = name;
        this.mark = mark;
        this.attendExam=attendExam;
    }
    public void setAttendExam(String attendExam) {
        this.attendExam = attendExam;
    }

    public String getAttendExam() {
        return attendExam;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public String getName() {
        return name;
    }

    public int getMark() {
        return mark;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Student createNewStudent(String name, String mobileNumber,String attendExam) {
        return new Student(name, mobileNumber,attendExam);
    }
    public static Student createNewStudent(String name, String mobileNumber,int mark,String attendExam) {
        return new Student(name, mobileNumber,mark,attendExam);
    }

    //@Override
    //public int compareTo(Student student) {
     //   if(mark>student.getMark()){
     //       return 1;
     //   }
     //   if (mark<student.getMark()){
      //      return -1;
      //  }
      //  return 0;
    //}
}

