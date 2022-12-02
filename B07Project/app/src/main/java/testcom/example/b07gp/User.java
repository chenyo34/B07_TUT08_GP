package testcom.example.b07gp;

import java.io.Serializable;

public class User implements Serializable {
    String name;
    String email;
    String password;
    String type;
    String UTORid;

    public User(String name, String email, String password, String type, String UTROid) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.UTORid = UTROid;
    }

    //----
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password, String type) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
    }

    public User() {

    }
    //----

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setUTORid(String UTORid) {
        this.UTORid = UTORid;
    }

    public String getUTORid() {
        return UTORid;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
