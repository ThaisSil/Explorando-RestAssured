package Entities;

public class User {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phone;

    public User(String username, String firstname, String lastname, String email, String password, String phone) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public String getUsername() {return username;}

    public String getFirstname() {return firstname;}

    public String getLastname() {return lastname;}

    public String getEmail() {return email;}

    public String getPassword() {return password;}

    public String getPhone() {return phone;}

    public void setUsername(String username) {this.username = username;}

    public void setFirstname(String firstname) {this.firstname = firstname;}

    public void setLastname(String lastname) {this.lastname = lastname;}

    public void setEmail(String email) {this.email = email;}

    public void setPassword(String password) {this.password = password;}

    public void setPhone(String phone) {this.phone = phone;}

}

