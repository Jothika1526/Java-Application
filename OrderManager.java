package Code_2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    public boolean placeOrder(Order order) {
        String sqlOrder = "INSERT INTO Orders (userId, restaurantId, totalAmount) VALUES (?, ?, ?)";
        String sqlOrderItem = "INSERT INTO OrderItems (orderId, itemId, quantity) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmtOrder = conn.prepareStatement(sqlOrder, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement pstmtOrderItem = conn.prepareStatement(sqlOrderItem)) {

            conn.setAutoCommit(false); // Start transaction

            pstmtOrder.setInt(1, order.getUserId());
            pstmtOrder.setInt(2, order.getRestaurantId());
            pstmtOrder.setDouble(3, order.getTotalAmount());

            int affectedRows = pstmtOrder.executeUpdate();
            if (affectedRows == 0) {
                conn.rollback();
                System.err.println("Failed to insert order: No rows affected.");
                return false;
            }

            ResultSet generatedKeys = pstmtOrder.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderId = generatedKeys.getInt(1);

                for (OrderItem item : order.getOrderItems()) {
                    pstmtOrderItem.setInt(1, orderId);
                    pstmtOrderItem.setInt(2, item.getItemId());
                    pstmtOrderItem.setInt(3, item.getQuantity());
                    pstmtOrderItem.addBatch();
                }
                pstmtOrderItem.executeBatch();
            } else {
                conn.rollback();
                System.err.println("Failed to insert order: No order ID obtained.");
                return false;
            }

            conn.commit(); // End transaction
            return true;
        } catch (SQLException e) {
            System.err.println("SQL exception occurred while placing order: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Order> getOrdersByRestaurant(int restaurantId) {
        String sql = "SELECT * FROM Orders WHERE restaurantId = ?";
        List<Order> orders = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, restaurantId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int orderId = rs.getInt("orderId");
                int userId = rs.getInt("userId");
                double totalAmount = rs.getDouble("totalAmount");
                List<OrderItem> orderItems = getOrderItems(orderId);

                Order order = new Order(orderId, userId, restaurantId, rs.getTimestamp("orderDate").toLocalDateTime(), totalAmount, orderItems);
                orders.add(order);
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred while retrieving orders: " + e.getMessage());
            e.printStackTrace();
        }
        return orders;
    }

    public List<OrderItem> getOrderItems(int orderId) {
        String sql = "SELECT * FROM OrderItems WHERE orderId = ?";
        List<OrderItem> orderItems = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                OrderItem item = new OrderItem(
                        rs.getInt("orderItemId"),
                        rs.getInt("orderId"),
                        rs.getInt("itemId"),
                        rs.getInt("quantity")
                );
                orderItems.add(item);
            }
        } catch (SQLException e) {
            System.err.println("SQL exception occurred while retrieving order items: " + e.getMessage());
            e.printStackTrace();
        }
        return orderItems;
    }
}
