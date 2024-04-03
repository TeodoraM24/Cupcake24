package entities;

public class Admin {
    private String name;
    private String email;
    private int phone;
    private String role;
    private int userid;
    private String password;

    public Admin(String name, String email, int phone, String role, int userid, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.userid = userid;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }

    public int getUserid() {
        return userid;
    }

    public String getPassword() {
        return password;
    }
}
