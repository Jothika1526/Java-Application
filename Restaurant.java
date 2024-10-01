package Code_2;

public class Restaurant {
    private int restaurantId;
    private String name;
    private String address;
    private String phone;
    private int createdBy;
    private double rating;
    private int ratingCount;

    public Restaurant(int restaurantId, String name, String address, String phone, int createdBy) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.createdBy = createdBy;
    }



    public int getRestaurantId() {
        return restaurantId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public double getRating() {
        return rating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public void updateRating(int newRating) {
        double totalRating = this.rating * this.ratingCount;
        this.ratingCount++;
        this.rating = (totalRating + newRating) / this.ratingCount;
    }
}
