package edu.eci.ieti.takeiteasysk.model.auth;

public class LoginWrapper {
    private String username;
    private String password;

    public LoginWrapper(String email, String password) {
        this.username = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginWrapper{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
