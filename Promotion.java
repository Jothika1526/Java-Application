package Code_2;

public class Promotion {
    private int promotionId;
    private String description;
    private double discountPercentage;
    private double minOrderAmount;
    private double maxDiscountAmount;

    public Promotion(int promotionId, String description, double discountPercentage, double minOrderAmount, double maxDiscountAmount) {
        this.promotionId = promotionId;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.minOrderAmount = minOrderAmount;
        this.maxDiscountAmount = maxDiscountAmount;
    }

    // Getters and Setters
    public int getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(int promotionId) {
        this.promotionId = promotionId;
    }

    public String getDescription() {
        return description;
    }

//    public void setDescription(String description) {
//        this.description = description;
//    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

//    public void setDiscountPercentage(double discountPercentage) {
//        this.discountPercentage = discountPercentage;
//    }

    public double getMinOrderAmount() {
        return minOrderAmount;
    }

//    public void setMinOrderAmount(double minOrderAmount) {
//        this.minOrderAmount = minOrderAmount;
//    }

    public double getMaxDiscountAmount() {
        return maxDiscountAmount;
    }

    public void setMaxDiscountAmount(double maxDiscountAmount) {
        this.maxDiscountAmount = maxDiscountAmount;
    }
}