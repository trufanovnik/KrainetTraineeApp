package by.krainet;

import java.io.Serializable;

public class UserEvent implements Serializable {
    private String operationType;
    private String username;
    private String password;
    private String email;

    public UserEvent() {}

    public UserEvent(String operationType, String username, String password, String email) {
        this.operationType = operationType;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getOperationType() { return operationType; }
    public void setOperationType(String operationType) { this.operationType = operationType; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "UserEvent{" +
                "operationType='" + operationType + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
