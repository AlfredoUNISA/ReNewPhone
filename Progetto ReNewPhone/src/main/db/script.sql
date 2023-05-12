DROP DATABASE IF EXISTS storage;
CREATE DATABASE storage;
USE storage;

DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS buys;

CREATE TABLE product (	
  id int primary key AUTO_INCREMENT,
  name char(50) not null,
  description char(200),
  price int default 0,
  quantity int default 0,
  color char(20),
  brand char(20),
  category char(20)
);

CREATE TABLE user(
    id int primary key AUTO_INCREMENT,
    name char(20) not null,
    email char(40) not null,
    password char(40) not null,
    address char(40) not null,
    city char(20) not null,
    cap int not null,
    phone int not null
);

CREATE TABLE buys(
    id int primary key AUTO_INCREMENT,
    id_user int not null,
    id_product int not null,
    quantity int not null,
    FOREIGN KEY (id_user) REFERENCES user(id),
    FOREIGN KEY (id_product) REFERENCES product(id)
);

INSERT INTO product (name, description, price, quantity, color, brand, category) VALUES
('iPhone 13 Pro', 'The most advanced iPhone ever.', 999, 100, 'Silver', 'Apple', 'Smartphone'),
('Samsung Galaxy S22 Ultra', 'The most powerful Galaxy S ever.', 1199, 100, 'Black', 'Samsung', 'Smartphone'),
('Google Pixel 6 Pro', 'The most advanced Pixel ever.', 899, 100, 'Sorta Sunny', 'Google', 'Smartphone'),
('OnePlus 10 Pro', 'The most powerful OnePlus ever.', 899, 100, 'Black', 'OnePlus', 'Smartphone'),
('Xiaomi 12 Pro', 'The most advanced Xiaomi ever.', 799, 100, 'Black', 'Xiaomi', 'Smartphone'),
('Realme GT 2 Pro', 'The most powerful Realme ever.', 699, 100, 'Paper White', 'Realme', 'Smartphone'),
('iPad Pro (2022)', 'The most powerful iPad ever.', 1099, 100, 'Space Gray', 'Apple', 'Tablet'),
('Samsung Galaxy Tab S8 Ultra', 'The most powerful Galaxy Tab ever.', 1199, 100, 'Black', 'Samsung', 'Tablet'),
('Microsoft Surface Pro 8', 'The most powerful Surface ever.', 999, 100, 'Platinum', 'Microsoft', 'Tablet'),
('Lenovo Yoga Tab 13', 'The most powerful Yoga Tab ever.', 799, 100, 'Storm Grey', 'Lenovo', 'Tablet'),
('Asus ZenPad 13 8GB', 'The most powerful ZenPad ever.', 699, 100, 'Star Black', 'Asus', 'Tablet'),
('Huawei MatePad Pro 12.6', 'The most powerful MatePad ever.', 899, 100, 'Midnight Black', 'Huawei', 'Tablet'),
('Honor MagicPad 10.4', 'The most powerful MagicPad ever.', 599, 100, 'Midnight Black', 'Honor', 'Tablet'),
('Nokia XR20', 'The most durable smartphone ever.', 499, 100, 'Midnight Blue', 'Nokia', 'Smartphone'),
('Motorola Moto G Power (2022)', 'The most affordable smartphone with a long-lasting battery.', 199, 100, 'Black', 'Motorola', 'Smartphone'),
('TCL 20 Pro 5G', 'The most affordable 5G smartphone.', 299, 100, 'Moonlight Black', 'TCL', 'Smartphone'),
('Oppo Reno 7 5G', 'The most stylish 5G smartphone.', 399, 100, 'Cosmic Black', 'Oppo', 'Smartphone'),
('Vivo V23 5G', 'The most camera-centric 5G smartphone.', 499, 100, 'Sunshine Gold', 'Vivo', 'Smartphone'),
('iQOO 9 5G', 'The most powerful 5G smartphone.', 599, 100, 'Legendary Edition', 'iQOO', 'Smartphone'),
('Amazon Fire HD 10 (2021)', 'The most affordable 10-inch tablet.', 149, 100, 'Black', 'Amazon', 'Tablet'),
('Lenovo Tab M10 Plus (2021)', 'The most powerful 10-inch tablet.', 249, 100, 'Storm Grey', 'Lenovo', 'Tablet'),
('Asus ZenPad 10 4GB (2021)', 'The most affordable 10-inch tablet.', 199, 100, 'Star Black', 'Asus', 'Tablet'),
('Huawei MatePad T10s (2021)', 'The most powerful 10-inch tablet.', 199, 100, 'Midnight Black', 'Huawei', 'Tablet'),
('Honor MagicPad 10 (2021)', 'The most affordable 10-inch tablet.', 149, 100, 'Midnight Black', 'Honor', 'Tablet'),
('Google Pixel 6a', 'The most affordable Pixel ever.', 499, 100, 'Sorta Seafoam', 'Google', 'Smartphone'),
('OnePlus Nord CE 2 Lite 5G', 'The most affordable 5G smartphone.', 249, 100, 'Black', 'OnePlus', 'Smartphone'),
('Realme 9 Pro 5G', 'The most powerful 5G smartphone.', 349, 100, 'Midnight Black', 'Realme', 'Smartphone'),
('Motorola Moto G Stylus 5G (2022)', 'The most affordable smartphone with a stylus.', 299, 100, 'Black', 'Motorola', 'Smartphone'),
('TCL 20 SE 5G', 'The most affordable 5G smartphone with a large display.', 249, 100, 'Moonlight Black', 'TCL', 'Smartphone'),
('Oppo A96 5G', 'The most stylish 5G smartphone.', 399, 100, 'Cosmic Black', 'Oppo', 'Smartphone');
