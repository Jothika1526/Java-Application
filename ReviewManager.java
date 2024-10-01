package Code_2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewManager {
    public boolean addReview(Review review) {
        String sql = "INSERT INTO Reviews (userId, restaurantId, rating, comment) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, review.getUserId());
            pstmt.setInt(2, review.getRestaurantId());
            pstmt.setInt(3, review.getRating());
            pstmt.setString(4, review.getComment());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Review> getReviewsByRestaurant(int restaurantId) {
        String sql = "SELECT * FROM Reviews WHERE restaurantId = ?";
        List<Review> reviews = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, restaurantId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Review review = new Review(
                        rs.getInt("reviewId"),
                        rs.getInt("userId"),
                        rs.getInt("restaurantId"),
                        rs.getInt("rating"),
                        rs.getString("comment")
                );
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
}
