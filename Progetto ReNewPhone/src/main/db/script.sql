DROP DATABASE IF EXISTS storage;
CREATE DATABASE storage;
USE storage;

DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS user;

CREATE TABLE product (	
  id int primary key AUTO_INCREMENT,
  name char(20) not null,
  description char(200),
  price int default 0,
  quantity int default 0,
  color char(20),
  brand char(20),
  category char(20)
);


CREATE TABLE user{
    id int primary key AUTO_INCREMENT,
    name char(20) not null,
    email char(40) not null,
    password char(40) not null,
    address char(40) not null,
    city char(20) not null,
    cap int not null,
    phone int not null
}

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
('Honor MagicPad 10.4', 'The most powerful MagicPad ever.', 599, 100, 'Midnight Black', 'Honor', 'Tablet');
