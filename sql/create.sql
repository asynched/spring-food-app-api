-- Dialect: MySQL
-- Users
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `address` VARCHAR(255) NOT NULL,
  `city` VARCHAR(255) NOT NULL,
  `state` VARCHAR(255) NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Stores
CREATE TABLE IF NOT EXISTS `stores` (
  `store_id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `email` VARCHAR(255) NOT NULL UNIQUE,
  `password` VARCHAR(255) NOT NULL,
  `address` VARCHAR(255) NOT NULL,
  `city` VARCHAR(255) NOT NULL,
  `state` VARCHAR(255) NOT NULL,
  `logo_url` VARCHAR(255),
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Products
CREATE TABLE IF NOT EXISTS `products` (
  `id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `description` VARCHAR(255) NOT NULL,
  `price` DECIMAL(10, 2) NOT NULL,
  `store_id` INTEGER NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (`store_id`) REFERENCES `stores`(`store_id`)
);

-- Orders
CREATE TABLE IF NOT EXISTS `orders` (
  `order_id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `user_id` INTEGER NOT NULL,
  `store_id` INTEGER NOT NULL,
  `status` ENUM(
    'pending',
    'accepted',
    'rejected',
    'delivering',
    'delivered'
  ) NOT NULL DEFAULT ('pending'),
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (`user_id`) REFERENCES `users`(`user_id`),
  FOREIGN KEY (`store_id`) REFERENCES `stores`(`store_id`)
);

-- Order Items
CREATE TABLE IF NOT EXISTS `order_items` (
  `order_item_id` INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `order_id` INTEGER NOT NULL,
  `product_id` INTEGER NOT NULL,
  `quantity` INTEGER NOT NULL,
  `price` DECIMAL(10, 2) NOT NULL,
  `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (`order_id`) REFERENCES `orders`(`order_id`),
  FOREIGN KEY (`product_id`) REFERENCES `products`(`id`)
);