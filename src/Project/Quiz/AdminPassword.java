package Project.Quiz;

import java.io.Serializable;

public class AdminPassword implements Serializable{
    private static final long serialVersionUID = 1L;
        private String password;

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}

