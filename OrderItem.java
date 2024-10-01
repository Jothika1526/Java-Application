package Code_2;

public class OrderItem {
    private int orderItemId;
    private int orderId;
    private int itemId;
    private int quantity;

    public OrderItem(int orderItemId, int orderId, int itemId, int quantity) {
        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public OrderItem(int itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

//    public int getOrderItemId() {
//        return orderItemId;
//    }
//
//    public int getOrderId() {
//        return orderId;
//    }

    public int getItemId() {
        return itemId;
    }

    public int getQuantity() {
        return quantity;
    }
}
