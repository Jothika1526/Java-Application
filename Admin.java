package Code_2;

public class Admin extends AbstractUser {
    public Admin(int userId, String username, String password, String email, boolean isAdmin, boolean isVerified) {
        super(userId, username, password, email, isAdmin, isVerified);
    }

    @Override
    public void displayUserInfo() {
        System.out.println("Admin User ID: " + getUserId() + ", Username: " + getUsername() + ", Email: " + getEmail());
    }
}
