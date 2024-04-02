public class User {

    private String name;
    private String password;
    private String email;
    private int phone;
    private String role;
    private int bank;

    public User(String name, String password, String email, int phone, String role, int bank) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.bank = bank;
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

    public int getPhone() {
        return phone;
    }

    public String getRole() {
        return role;
    }

    public int getBank() {
        return bank;
    }
}
