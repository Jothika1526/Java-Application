package Code_2;

public class Review {
    private int reviewId;
    private int userId;
    private int restaurantId;
    private int rating;
    private String comment;

    public Review(int reviewId, int userId, int restaurantId, int rating, String comment) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.rating = rating;
        this.comment = comment;
    }

    public int getReviewId() {
        return reviewId;
    }

    public int getUserId() {
        return userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}
