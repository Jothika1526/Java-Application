package Code_2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantManager {
    public boolean addRestaurant(Restaurant restaurant) {
        String sql = "INSERT INTO Restaurants (name, address, phone, createdBy) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, restaurant.getName());
            pstmt.setString(2, restaurant.getAddress());
            pstmt.setString(3, restaurant.getPhone());
            pstmt.setInt(4, restaurant.getCreatedBy());
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Restaurant> getRestaurants() {
        String sql = "SELECT * FROM Restaurants";
        List<Restaurant> restaurants = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Restaurant restaurant = new Restaurant(
                        rs.getInt("restaurantId"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getInt("createdBy")
                );
                restaurants.add(restaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurants;
    }

    public Restaurant getRestaurantById(int restaurantId) {
        String sql = "SELECT * FROM Restaurants WHERE restaurantId = ?";
        Restaurant restaurant = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, restaurantId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                restaurant = new Restaurant(
                        rs.getInt("restaurantId"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getInt("createdBy")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurant;
    }

    public int getRestaurantIdByAdminId(int adminId) {
        String sql = "SELECT restaurantId FROM Restaurants WHERE createdBy = ?";
        int restaurantId = -1;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, adminId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                restaurantId = rs.getInt("restaurantId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurantId;
    }
}
