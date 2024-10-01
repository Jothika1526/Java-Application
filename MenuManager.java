package Code_2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuManager {

    public boolean doesRestaurantExist(int restaurantId) {
        String sql = "SELECT COUNT(*) FROM Restaurants WHERE restaurantId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, restaurantId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addItem(MenuItem item) {
        if (!doesRestaurantExist(item.getRestaurantId())) {
            System.out.println("Restaurant ID does not exist.");
            return false;
        }

        String sql = "INSERT INTO MenuItems (restaurantId, name, description, price, availability) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, item.getRestaurantId());
            pstmt.setString(2, item.getName());
            pstmt.setString(3, item.getDescription());
            pstmt.setDouble(4, item.getPrice());
            pstmt.setInt(5, item.getAvailability());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateItem(MenuItem item) {
        String sql = "UPDATE MenuItems SET name = ?, description = ?, price = ?, availability = ? WHERE itemId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, item.getName());
            pstmt.setString(2, item.getDescription());
            pstmt.setDouble(3, item.getPrice());
            pstmt.setInt(4, item.getAvailability());
            pstmt.setInt(5, item.getItemId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteItem(int itemId) {
        String sql = "DELETE FROM MenuItems WHERE itemId = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, itemId);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<MenuItem> getMenu(int restaurantId) {
        String sql = "SELECT * FROM MenuItems WHERE restaurantId = ?";
        List<MenuItem> menu = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, restaurantId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                MenuItem item = new MenuItem(
                        rs.getInt("itemId"),
                        rs.getInt("restaurantId"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("availability")
                );
                menu.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menu;
    }

    public MenuItem getMenuItemById(int itemId) {
        String sql = "SELECT * FROM MenuItems WHERE itemId = ?";
        MenuItem menuItem = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, itemId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                menuItem = new MenuItem(
                        rs.getInt("itemId"),
                        rs.getInt("restaurantId"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getInt("availability")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuItem;
    }
}
