package Code_2;

public abstract class AbstractUser {
    private int userId;
    private String username;
    private String password;
    private String email;
    private boolean isAdmin;
    private boolean isVerified;

    public AbstractUser(int userId, String username, String password, String email, boolean isAdmin, boolean isVerified) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isAdmin = isAdmin;
        this.isVerified = isVerified;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public abstract void displayUserInfo();
}
