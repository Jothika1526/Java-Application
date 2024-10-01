package Code_2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthManager {
    public boolean registerUser(AbstractUser user) {
        if (isUsernameTaken(user.getUsername())) {
            System.out.println("User registered already with same username");
            return false;
        }

        if (isEmailTaken(user.getEmail())) {
            System.out.println("User registered already with same email id");
            return false;
        }

        String sql = "INSERT INTO Users (username, password, email, isAdmin, isVerified) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            pstmt.setBoolean(4, user.isAdmin());
            pstmt.setBoolean(5, user.isVerified());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isUsernameTaken(String username) {
        String sql = "SELECT COUNT(*) FROM Users WHERE username = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isEmailTaken(String email) {
        String sql = "SELECT COUNT(*) FROM Users WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public AbstractUser loginUser(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password); // Password should be hashed
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("userId");
                boolean isAdmin = rs.getBoolean("isAdmin");
                if (isAdmin) {
                    return new Admin(
                            userId,
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email"),
                            rs.getBoolean("isAdmin"),
                            rs.getBoolean("isVerified")
                    );
                } else {
                    return new User(
                            userId,
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email"),
                            rs.getBoolean("isAdmin"),
                            rs.getBoolean("isVerified")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getUserIdByUsername(String username) {
        String sql = "SELECT userId FROM Users WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("userId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Indicate failure
    }
}
