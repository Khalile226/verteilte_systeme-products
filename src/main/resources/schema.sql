CREATE TABLE Category (
                          category_id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(100)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE Product (
                         product_id INT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(100),
                         category_id INT NOT NULL,
                         price INT,
                         weight INT,
                         length INT,
                         width INT,
                         FOREIGN KEY (category_id) REFERENCES Category(category_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE Unit (
                      unit_id INT AUTO_INCREMENT PRIMARY KEY,
                      product_id INT NOT NULL,
                      serial_nr VARCHAR(100),
                      FOREIGN KEY (product_id) REFERENCES Product(product_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

