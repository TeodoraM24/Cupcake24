package app.entities;

public class User {

    private String name;
    private String password;
    private String email;
    private String phone;
    private String role;
    private int bank;
    private int user_id;

    public User(String name, String password, String email, String phone, String role, int bank, int user_id) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.bank = bank;
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }

    public int getBank() {
        return bank;
    }

    public int getUser_id() {
        return user_id;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", role='" + role + '\'' +
                ", bank=" + bank +
                ", user_id=" + user_id +
                '}';
    }
}
