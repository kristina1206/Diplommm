package ru.serujimir.petstore_slepcovak;

public class User {
    int id;
    String username;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getfirstName() {
        return firstName;
    }

    public void setfirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getlastName() {
        return lastName;
    }

    public void setlastName(String lastname) {
        this.lastName = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setStatus(int status) {
        this.userStatus = status;
    }

    String firstName;
    String lastName;
    String email;
    String password;
    String phone;
    int userStatus;
}
