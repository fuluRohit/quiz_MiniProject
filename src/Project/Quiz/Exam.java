package Project.Quiz;

//   one method  Exam createNewExam(int index, String question, String option_A, String option_B, String option_C, String option_D, String answer)

public class Exam {
    private String question;
    private String option_A;
    private String option_B;
    private String option_C;
    private String option_D;
    private String answer;
    private int index;

    public Exam(int index, String question, String option_A, String option_B, String option_C, String option_D, String answer) {
        this.question = question;
        this.option_A = option_A;
        this.option_B = option_B;
        this.option_C = option_C;
        this.option_D = option_D;
        this.answer = answer;
        this.index = index;
    }

    public String getQuestion() {
        return question;
    }

    public String getOption_A() {
        return option_A;
    }

    public String getOption_B() {
        return option_B;
    }

    public String getOption_C() {
        return option_C;
    }

    public String getOption_D() {
        return option_D;
    }

    public String getAnswer() {
        return answer;
    }

    public static Exam createNewExam(int index, String question, String option_A, String option_B, String option_C, String option_D, String answer){
        return new Exam(index,question,option_A,option_B,option_C,option_D ,answer);
    }
}
