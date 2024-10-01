package Code_2;

public class User extends AbstractUser {
    public User(int userId, String username, String password, String email, boolean isAdmin, boolean isVerified) {
        super(userId, username, password, email, isAdmin, isVerified);
    }

    @Override
    public void displayUserInfo() {
        System.out.println("User ID: " + getUserId() + ", Username: " + getUsername() + ", Email: " + getEmail());
    }
}