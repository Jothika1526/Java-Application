package Code_2;

import java.util.ArrayList;
import java.util.List;

public class PromotionManager {
    private List<Promotion> promotions;

    public PromotionManager() {
        this.promotions = new ArrayList<>();
    }

    public boolean addPromotion(Promotion promotion) {
        return promotions.add(promotion);
    }

    public boolean updatePromotion(Promotion promotion) {
        for (int i = 0; i < promotions.size(); i++) {
            if (promotions.get(i).getPromotionId() == promotion.getPromotionId()) {
                promotions.set(i, promotion);
                return true;
            }
        }
        return false;
    }

    public boolean deletePromotion(int promotionId) {
        return promotions.removeIf(p -> p.getPromotionId() == promotionId);
    }

    public Promotion getPromotionById(int promotionId) {
        for (Promotion promotion : promotions) {
            if (promotion.getPromotionId() == promotionId) {
                return promotion;
            }
        }
        return null;
    }

    public List<Promotion> getAllPromotions() {
        return promotions;
    }

    public double applyPromotion(int promotionId, double orderAmount) {
        Promotion promotion = getPromotionById(promotionId);
        if (promotion != null && orderAmount >= promotion.getMinOrderAmount()) {
            double discount = orderAmount * (promotion.getDiscountPercentage() / 100);
            if (discount > promotion.getMaxDiscountAmount()) {
                discount = promotion.getMaxDiscountAmount();
            }
            return orderAmount - discount;
        }
        return orderAmount;
    }
}