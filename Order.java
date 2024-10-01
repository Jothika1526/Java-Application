package Code_2;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int orderId;
    private int userId;
    private int restaurantId;
    private LocalDateTime orderDate;
    private double totalAmount;
    private List<OrderItem> orderItems;

    public Order(int orderId, int userId, int restaurantId, LocalDateTime orderDate, double totalAmount, List<OrderItem> orderItems) {
        this.orderId = orderId;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderItems = orderItems;
    }

    public Order(int userId, int restaurantId, double totalAmount, List<OrderItem> orderItems) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.totalAmount = totalAmount;
        this.orderItems = orderItems;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getUserId() {
        return userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
