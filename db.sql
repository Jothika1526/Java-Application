CREATE TABLE IF NOT EXISTS Users (
                                     userId INT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    isAdmin BOOLEAN DEFAULT FALSE,
    isVerified BOOLEAN DEFAULT FALSE
    );

CREATE TABLE IF NOT EXISTS Restaurants (
                                           restaurantId INT AUTO_INCREMENT PRIMARY KEY,
                                           name VARCHAR(100) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(15),
    createdBy INT NOT NULL,
    FOREIGN KEY (createdBy) REFERENCES Users(userId)
    );

CREATE TABLE IF NOT EXISTS MenuItems (
                                         itemId INT AUTO_INCREMENT PRIMARY KEY,
                                         restaurantId INT,
                                         name VARCHAR(100) NOT NULL,
    description TEXT,
    price DOUBLE NOT NULL,
    availability INT NOT NULL,
    FOREIGN KEY (restaurantId) REFERENCES Restaurants(restaurantId) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS Orders (
                                      orderId INT AUTO_INCREMENT PRIMARY KEY,
                                      userId INT,
                                      restaurantId INT,
                                      orderDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      totalAmount DOUBLE NOT NULL,
                                      FOREIGN KEY (userId) REFERENCES Users(userId),
    FOREIGN KEY (restaurantId) REFERENCES Restaurants(restaurantId)
    );

CREATE TABLE IF NOT EXISTS OrderItems (
                                          orderItemId INT AUTO_INCREMENT PRIMARY KEY,
                                          orderId INT,
                                          itemId INT,
                                          quantity INT NOT NULL,
                                          FOREIGN KEY (orderId) REFERENCES Orders(orderId) ON DELETE CASCADE,
    FOREIGN KEY (itemId) REFERENCES MenuItems(itemId) ON DELETE CASCADE
    );

CREATE TABLE IF NOT EXISTS Reviews (
                                       reviewId INT AUTO_INCREMENT PRIMARY KEY,
                                       userId INT,
                                       restaurantId INT,
                                       rating INT NOT NULL,
                                       comment TEXT,
                                       FOREIGN KEY (userId) REFERENCES Users(userId),
    FOREIGN KEY (restaurantId) REFERENCES Restaurants(restaurantId)
    );

    );
