package Code_2;

public class MenuItem {
    private int itemId;
    private int restaurantId;
    private String name;
    private String description;
    private double price;
    private int availability;

    public MenuItem(int itemId, int restaurantId, String name, String description, double price, int availability) {
        this.itemId = itemId;
        this.restaurantId = restaurantId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availability = availability;
    }

    public int getItemId() {
        return itemId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public int getAvailability() {
        return availability;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }
}
