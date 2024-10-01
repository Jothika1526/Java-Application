package Code_2;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        CreateTableIfNotExists.createTables();

        Scanner scanner = new Scanner(System.in);
        AuthManager authManager = new AuthManager();
        RestaurantManager restaurantManager = new RestaurantManager();
        MenuManager menuManager = new MenuManager();
        OrderManager orderManager = new OrderManager();
        ReviewManager reviewManager = new ReviewManager();
        PromotionManager promotionManager = new PromotionManager();

        while (true) {
            System.out.println("1. Signup");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    // Signup
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    if (username == null || username.isEmpty() || username.contains(" ")) {
                        System.out.println("Invalid username. It should not be null, empty, or contain spaces.");
                        break;
                    }
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    if (password == null || password.length() < 6) {
                        System.out.println("Invalid password. It should be at least 6 characters long.");
                        break;
                    }
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    if (email == null || !email.matches("[^@\\s]+@gmail\\.com")) {
                        System.out.println("Invalid email. It should be a valid Gmail address.");
                        break;
                    }
                    System.out.print("Enter user type (1 for regular, 2 for admin): ");
                    int userType = scanner.nextInt();
                    scanner.nextLine();
                    if (userType != 1 && userType != 2) {
                        System.out.println("Invalid user type. Please enter 1 for regular or 2 for admin.");
                        break;
                    }


                    if (authManager.isUsernameTaken(username)) {
                        System.out.println("User registered already with same username");
                    } else if (authManager.isEmailTaken(email)) {
                        System.out.println("User registered already with same email id");
                    } else {
                        boolean isAdmin = userType == 2;

                        if (isAdmin) {
                            System.out.print("Enter restaurant name: ");
                            String restaurantName = scanner.nextLine();
                            if (restaurantName == null || restaurantName.isEmpty()) {
                                System.out.println("Invalid restaurant name. It should not be null or empty.");
                                break;
                            }
                            System.out.print("Enter restaurant address: ");
                            String restaurantAddress = scanner.nextLine();
                            if (restaurantAddress == null || restaurantAddress.isEmpty()) {
                                System.out.println("Invalid restaurant address. It should not be null or empty.");
                                break;
                            }
                            System.out.print("Enter restaurant phone: ");
                            String restaurantPhone = scanner.nextLine();
                            if (restaurantPhone == null || !restaurantPhone.matches("\\d{10}")) {
                                System.out.println("Invalid restaurant phone number. It should be a 10-digit number.");
                                break;
                            }

                            Admin newAdmin = new Admin(0, username, password, email, true, false);
                            if (authManager.registerUser(newAdmin)) {
                                int adminId = authManager.getUserIdByUsername(username);
                                Restaurant newRestaurant = new Restaurant(0, restaurantName, restaurantAddress, restaurantPhone, adminId);
                                if (restaurantManager.addRestaurant(newRestaurant)) {
                                    System.out.println("Admin and restaurant registered successfully!");
                                } else {
                                    System.out.println("Failed to register restaurant. Please try again.");
                                }
                            } else {
                                System.out.println("Failed to register admin. Please try again.");
                            }
                        } else {
                            User newUser = new User(0, username, password, email, false, false);
                            if (authManager.registerUser(newUser)) {
                                System.out.println("User registered successfully!");
                            } else {
                                System.out.println("Failed to register user. Please try again.");
                            }
                        }
                    }
                    break;

                case 2:
                    // Login
                    System.out.print("Enter username: ");
                    username = scanner.nextLine();
                    System.out.print("Enter password: ");
                    password = scanner.nextLine();

                    AbstractUser user = authManager.loginUser(username, password);
                    if (user == null) {
                        System.out.println("Invalid username or password. Please try again.");
                    } else {
                        System.out.println("Login successful!");
                        if (user.isAdmin()) {
                            boolean adminLoggedIn = true;
                            int adminId = user.getUserId();
                            int restaurantId = restaurantManager.getRestaurantIdByAdminId(adminId);
                            while (adminLoggedIn) {
                                System.out.println("Menu Items for your Restaurant:");
                                List<MenuItem> menuItems = menuManager.getMenu(restaurantId);
                                if (menuItems.isEmpty()) {
                                    System.out.println("Sorry, there are no menu items for this restaurant.");
                                } else {
                                    for (MenuItem item : menuItems) {
                                        System.out.println("Item ID: " + item.getItemId() + ", Name: " + item.getName() + ", Description: " + item.getDescription() + ", Price: " + item.getPrice() + ", Availability: " + item.getAvailability());
                                    }
                                }

                                System.out.println("1. Add Menu Item");
                                System.out.println("2. Update Menu Item");
                                System.out.println("3. Delete Menu Item");
                                System.out.println("4. View Orders");
                                System.out.println("5. Manage Promotions");
                                System.out.println("6. View Reviews");
                                System.out.println("7. Logout");
                                System.out.print("Choose an option: ");
                                int adminChoice = scanner.nextInt();
                                scanner.nextLine();

                                switch (adminChoice) {
                                    case 1:
                                        // Add Menu Item
                                        System.out.print("Enter item name: ");
                                        String itemName = scanner.nextLine();
                                        if (itemName == null || itemName.isEmpty()) {
                                            System.out.println("Invalid item name. It should not be null or empty.");
                                            break;
                                        }
                                        System.out.print("Enter description: ");
                                        String description = scanner.nextLine();
                                        if (description == null || description.isEmpty()) {
                                            System.out.println("Invalid description. It should not be null or empty.");
                                            break;
                                        }
                                        System.out.print("Enter price: ");
                                        double price = scanner.nextDouble();
                                        if (price < 0) {
                                            System.out.println("Invalid price. It should not be negative.");
                                            break;
                                        }
                                        System.out.print("Enter availability: ");
                                        int availability = scanner.nextInt();
                                        if (availability < 0) {
                                            System.out.println("Invalid availability. It should not be negative.");
                                            break;
                                        }
                                        scanner.nextLine();

                                        MenuItem newItem = new MenuItem(0, restaurantId, itemName, description, price, availability);
                                        if (menuManager.addItem(newItem)) {
                                            System.out.println("Menu item added successfully!");
                                        } else {
                                            System.out.println("Failed to add menu item. Please try again.");
                                        }
                                        break;

                                    case 2:
                                        // Update Menu Item
                                        System.out.print("Enter item ID: ");
                                        int itemId = scanner.nextInt();
                                        scanner.nextLine();
                                        System.out.print("Enter new item name: ");
                                        String newItemName = scanner.nextLine();
                                        if (newItemName == null || newItemName.isEmpty()) {
                                            System.out.println("Invalid item name. It should not be null or empty.");
                                            break;
                                        }
                                        System.out.print("Enter new description: ");
                                        String newDescription = scanner.nextLine();
                                        if (newDescription == null || newDescription.isEmpty()) {
                                            System.out.println("Invalid description. It should not be null or empty.");
                                            break;
                                        }
                                        System.out.print("Enter new price: ");
                                        double newPrice = scanner.nextDouble();
                                        if (newPrice < 0) {
                                            System.out.println("Invalid price. It should not be negative.");
                                            break;
                                        }
                                        System.out.print("Enter new availability: ");
                                        int newAvailability = scanner.nextInt();
                                        if (newAvailability < 0) {
                                            System.out.println("Invalid availability. It should not be negative.");
                                            break;
                                        }
                                        scanner.nextLine();

                                        MenuItem updateItem = new MenuItem(itemId, restaurantId, newItemName, newDescription, newPrice, newAvailability);
                                        if (menuManager.updateItem(updateItem)) {
                                            System.out.println("Menu item updated successfully!");
                                        } else {
                                            System.out.println("Failed to update menu item. Please try again.");
                                        }
                                        break;

                                    case 3:
                                        // Delete Menu Item
                                        System.out.print("Enter item ID: ");
                                        itemId = scanner.nextInt();
                                        scanner.nextLine();

                                        if (menuManager.deleteItem(itemId)) {
                                            System.out.println("Menu item deleted successfully!");
                                        } else {
                                            System.out.println("Failed to delete menu item. Please try again.");
                                        }
                                        break;

                                    case 4:
                                        // View Orders
                                        List<Order> orders = orderManager.getOrdersByRestaurant(restaurantId);
                                        for (Order order : orders) {
                                            System.out.println("Order ID: " + order.getOrderId());
                                            System.out.println("User ID: " + order.getUserId());
                                            System.out.println("Total Amount: " + order.getTotalAmount());
                                            System.out.println("Order Date: " + order.getOrderDate());
                                            System.out.println("Order Items: ");
                                            List<OrderItem> orderItems = orderManager.getOrderItems(order.getOrderId());
                                            for (OrderItem item : orderItems) {
                                                System.out.println("  Item ID: " + item.getItemId() + ", Quantity: " + item.getQuantity());
                                            }
                                        }
                                        break;

                                    case 5:
                                        // Manage Promotions
                                        boolean managePromotions = true;
                                        while (managePromotions) {
                                            System.out.println("1. Add Promotion");
                                            System.out.println("2. Update Promotion");
                                            System.out.println("3. Delete Promotion");
                                            System.out.println("4. View Promotions");
                                            System.out.println("5. Exit Promotion Management");
                                            System.out.print("Choose an option: ");
                                            int promoChoice = scanner.nextInt();
                                            scanner.nextLine();

                                            switch (promoChoice) {
                                                case 1:
                                                    // Add Promotion
                                                    System.out.print("Enter promotion description: ");
                                                    String promoDescription = scanner.nextLine();
                                                    if (promoDescription == null || promoDescription.isEmpty()) {
                                                        System.out.println("Invalid promotion description. It should not be null or empty.");
                                                        break;
                                                    }
                                                    System.out.print("Enter discount percentage: ");
                                                    double discountPercentage = scanner.nextDouble();
                                                    if (discountPercentage < 0) {
                                                        System.out.println("Invalid discount percentage. It should not be negative.");
                                                        break;
                                                    }
                                                    System.out.print("Enter minimum order amount: ");
                                                    double minOrderAmount = scanner.nextDouble();
                                                    if (minOrderAmount < 0) {
                                                        System.out.println("Invalid minimum order amount. It should not be negative.");
                                                        break;
                                                    }
                                                    System.out.print("Enter maximum discount amount: ");
                                                    double maxDiscountAmount = scanner.nextDouble();
                                                    if (maxDiscountAmount < 0) {
                                                        System.out.println("Invalid maximum discount amount. It should not be negative.");
                                                        break;
                                                    }
                                                    scanner.nextLine();

                                                    Promotion newPromotion = new Promotion(0, promoDescription, discountPercentage, minOrderAmount, maxDiscountAmount);
                                                    if (promotionManager.addPromotion(newPromotion)) {
                                                        System.out.println("Promotion added successfully!");
                                                    } else {
                                                        System.out.println("Failed to add promotion. Please try again.");
                                                    }
                                                    break;

                                                case 2:
                                                    // Update Promotion
                                                    System.out.print("Enter promotion ID: ");
                                                    int promoId = scanner.nextInt();
                                                    scanner.nextLine();
                                                    System.out.print("Enter new promotion description: ");
                                                    String newPromoDescription = scanner.nextLine();
                                                    if (newPromoDescription == null || newPromoDescription.isEmpty()) {
                                                        System.out.println("Invalid promotion description. It should not be null or empty.");
                                                        break;
                                                    }
                                                    System.out.print("Enter new discount percentage: ");
                                                    double newDiscountPercentage = scanner.nextDouble();
                                                    if (newDiscountPercentage < 0) {
                                                        System.out.println("Invalid discount percentage. It should not be negative.");
                                                        break;
                                                    }
                                                    System.out.print("Enter new minimum order amount: ");
                                                    double newMinOrderAmount = scanner.nextDouble();
                                                    if (newMinOrderAmount < 0) {
                                                        System.out.println("Invalid minimum order amount. It should not be negative.");
                                                        break;
                                                    }
                                                    System.out.print("Enter new maximum discount amount: ");
                                                    double newMaxDiscountAmount = scanner.nextDouble();
                                                    if (newMaxDiscountAmount < 0) {
                                                        System.out.println("Invalid maximum discount amount. It should not be negative.");
                                                        break;
                                                    }
                                                    scanner.nextLine();

                                                    Promotion updatedPromotion = new Promotion(promoId, newPromoDescription, newDiscountPercentage, newMinOrderAmount, newMaxDiscountAmount);
                                                    if (promotionManager.updatePromotion(updatedPromotion)) {
                                                        System.out.println("Promotion updated successfully!");
                                                    } else {
                                                        System.out.println("Failed to update promotion. Please try again.");
                                                    }
                                                    break;

                                                case 3:
                                                    // Delete Promotion
                                                    System.out.print("Enter promotion ID: ");
                                                    promoId = scanner.nextInt();
                                                    scanner.nextLine();

                                                    if (promotionManager.deletePromotion(promoId)) {
                                                        System.out.println("Promotion deleted successfully!");
                                                    } else {
                                                        System.out.println("Failed to delete promotion. Please try again.");
                                                    }
                                                    break;

                                                case 4:
                                                    // View Promotions
                                                    List<Promotion> promotions = promotionManager.getAllPromotions();
                                                    for (Promotion promotion : promotions) {
                                                        System.out.println("Promotion ID: " + promotion.getPromotionId() + ", Description: " + promotion.getDescription() + ", Discount Percentage: " + promotion.getDiscountPercentage() + ", Minimum Order Amount: " + promotion.getMinOrderAmount() + ", Maximum Discount Amount: " + promotion.getMaxDiscountAmount());
                                                    }
                                                    break;

                                                case 5:
                                                    // Exit Promotion Management
                                                    managePromotions = false;
                                                    break;

                                                default:
                                                    System.out.println("Invalid choice. Please try again.");
                                            }
                                        }
                                        break;

                                    case 6:
                                        // View Reviews
                                        List<Review> reviews = reviewManager.getReviewsByRestaurant(restaurantId);
                                        if (reviews.isEmpty()) {
                                            System.out.println("No reviews found for the restaurant.");
                                        } else {
                                            for (Review review : reviews) {
                                                System.out.println("Review ID: " + review.getReviewId());
                                                System.out.println("User ID: " + review.getUserId());
                                                System.out.println("Rating: " + review.getRating());
                                                System.out.println("Comment: " + review.getComment());
                                                System.out.println("----");
                                            }
                                        }
                                        break;

                                    case 7:
                                        // Logout
                                        adminLoggedIn = false;
                                        break;

                                    default:
                                        System.out.println("Invalid choice. Please try again.");
                                }
                            }
                        } else {
                            boolean userLoggedIn = true;
                            while (userLoggedIn) {
                                System.out.println("1. View Restaurants");
                                System.out.println("2. Place Order");
                                System.out.println("3. View Reviews");
                                System.out.println("4. Logout");
                                System.out.print("Choose an option: ");
                                int userChoice = scanner.nextInt();
                                scanner.nextLine();

                                switch (userChoice) {
                                    case 1:
                                        // View Restaurants
                                        List<Restaurant> restaurants = restaurantManager.getRestaurants();
                                        for (Restaurant restaurant : restaurants) {
                                            System.out.println("Restaurant ID: " + restaurant.getRestaurantId());
                                            System.out.println("Name: " + restaurant.getName());
                                            System.out.println("Address: " + restaurant.getAddress());
                                            System.out.println("Phone: " + restaurant.getPhone());
                                        }
                                        break;

                                    case 2:
                                        // Place Order
                                        System.out.print("Enter restaurant ID: ");
                                        int restaurantId = scanner.nextInt();
                                        scanner.nextLine();
                                        Restaurant restaurant = restaurantManager.getRestaurantById(restaurantId);
                                        if (restaurant == null) {
                                            System.out.println("Invalid restaurant ID. Please try again.");
                                            break;
                                        }

                                        List<MenuItem> menu = menuManager.getMenu(restaurantId);
                                        if (menu.isEmpty()) {
                                            System.out.println("Sorry, there are no menu items for this restaurant.");
                                            break;
                                        }

                                        System.out.println("Menu Items:");
                                        for (MenuItem item : menu) {
                                            System.out.println("Item ID: " + item.getItemId() + ", Name: " + item.getName() + ", Price: " + item.getPrice() + ", Availability: " + item.getAvailability());
                                        }

                                        List<OrderItem> orderItems = new ArrayList<>();
                                        while (true) {
                                            System.out.print("Enter item ID to order (or -1 to finish): ");
                                            int itemId = scanner.nextInt();
                                            if (itemId == -1) break;

                                            MenuItem menuItem = menuManager.getMenuItemById(itemId);
                                            if (menuItem == null) {
                                                System.out.println("Invalid item ID. Please try again.");
                                                continue;
                                            }

                                            System.out.print("Enter quantity: ");
                                            int quantity = scanner.nextInt();
                                            scanner.nextLine();
                                            if (quantity <= 0 || quantity > menuItem.getAvailability()) {
                                                System.out.println("Invalid quantity. It should be positive and not exceed availability.");
                                                continue;
                                            }

                                            orderItems.add(new OrderItem(itemId, quantity));
                                            menuItem.setAvailability(menuItem.getAvailability() - quantity);
                                            menuManager.updateItem(menuItem);
                                        }

                                        double totalAmount = 0.0;
                                        for (OrderItem item : orderItems) {
                                            MenuItem menuItem = menuManager.getMenuItemById(item.getItemId());
                                            totalAmount += menuItem.getPrice() * item.getQuantity();
                                        }

                                        List<Promotion> promotions = promotionManager.getAllPromotions();
                                        if (!promotions.isEmpty()) {
                                            System.out.println("Available Promotions:");
                                            for (Promotion promotion : promotions) {
                                                System.out.println("Promotion ID: " + promotion.getPromotionId() + ", Description: " + promotion.getDescription() + ", Discount Percentage: " + promotion.getDiscountPercentage() + ", Minimum Order Amount: " + promotion.getMinOrderAmount() + ", Maximum Discount Amount: " + promotion.getMaxDiscountAmount());
                                            }
                                        } else {
                                            System.out.println("No promotions available.");
                                        }

                                        System.out.print("Enter promotion ID to apply (or -1 if none): ");
                                        int promoId = scanner.nextInt();
                                        if (promoId != -1) {
                                            totalAmount = promotionManager.applyPromotion(promoId, totalAmount);
                                        }

                                        Order order = new Order(user.getUserId(), restaurantId, totalAmount, orderItems);
                                        if (orderManager.placeOrder(order)) {
                                            System.out.println("Order placed successfully!");
                                            System.out.println("Total Amount: " + totalAmount);

                                            System.out.print("Would you like to leave a review? (yes/no): ");
                                            scanner.nextLine();
                                            String leaveReview = scanner.nextLine();
                                            if (leaveReview.equalsIgnoreCase("yes")) {
                                                System.out.print("Enter your rating (1-5): ");
                                                int rating = scanner.nextInt();
                                                if (rating < 1 || rating > 5) {
                                                    System.out.println("Invalid rating. It should be between 1 and 5.");
                                                    break;
                                                }
                                                scanner.nextLine();
                                                System.out.print("Enter your comment: ");
                                                String comment = scanner.nextLine();
                                                if (comment == null || comment.isEmpty()) {
                                                    System.out.println("Invalid comment. It should not be null or empty.");
                                                    break;
                                                }

                                                Review review = new Review(0, user.getUserId(), restaurantId, rating, comment);
                                                boolean reviewSuccess = reviewManager.addReview(review);

                                                if (reviewSuccess) {
                                                    System.out.println("Review added successfully!");
                                                } else {
                                                    System.out.println("Failed to add review.");
                                                }
                                            }
                                        } else {
                                            System.out.println("Failed to place order. Please try again.");
                                        }

                                        break;

                                    case 3:
                                        // View Reviews
                                        List<Restaurant> res = restaurantManager.getRestaurants();
                                        System.out.println("Available Restaurants:");
                                        for (Restaurant r : res) {
                                            System.out.println("Restaurant ID: " + r.getRestaurantId());
                                            System.out.println("Name: " + r.getName());
                                            System.out.println("Address: " + r.getAddress());
                                            System.out.println("Phone: " + r.getPhone());
                                            System.out.println("----");
                                        }

                                        System.out.print("Enter restaurant ID to view reviews: ");
                                        int reviewRestaurantId = scanner.nextInt();
                                        scanner.nextLine();
                                        Restaurant reviewRestaurant = restaurantManager.getRestaurantById(reviewRestaurantId);
                                        if (reviewRestaurant == null) {
                                            System.out.println("Invalid restaurant ID. Please try again.");
                                            break;
                                        }

                                        List<Review> restaurantReviews = reviewManager.getReviewsByRestaurant(reviewRestaurantId);
                                        if (restaurantReviews.isEmpty()) {
                                            System.out.println("No reviews found for the restaurant.");
                                        } else {
                                            for (Review review : restaurantReviews) {
                                                System.out.println("Review ID: " + review.getReviewId());
                                                System.out.println("User ID: " + review.getUserId());
                                                System.out.println("Rating: " + review.getRating());
                                                System.out.println("Comment: " + review.getComment());
                                                System.out.println("----");
                                            }
                                        }
                                        break;

                                    case 4:
                                        // Logout
                                        userLoggedIn = false;
                                        break;

                                    default:
                                        System.out.println("Invalid choice. Please try again.");
                                }
                            }
                        }
                    }
                    break;

                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
