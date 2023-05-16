DROP DATABASE IF EXISTS renewphonedb;
CREATE DATABASE renewphonedb;
USE renewphonedb;

DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS buys;

CREATE TABLE products (	
  id int primary key AUTO_INCREMENT,
  name varchar(150) not null,
  description varchar(255),
  price int default 0,
  quantity int default 0,
  color varchar(20),
  brand varchar(25),
  category varchar(15),
  state char(12)
);

CREATE TABLE users (
  id int primary key AUTO_INCREMENT,
  name varchar(25) not null,
  email varchar(100) not null unique,
  password varchar(50) not null,
  address varchar(60) not null,
  city varchar(35) not null,
  cap char(7) not null,
  phone varchar(20) not null
);

CREATE TABLE buys (
  id int primary key AUTO_INCREMENT,
  id_user int not null,
  id_product int not null,
  quantity int not null,
  FOREIGN KEY (id_user) REFERENCES users(id),
  FOREIGN KEY (id_product) REFERENCES products(id)
);

INSERT INTO products (name, description, price, quantity, color, brand, category, state) VALUES
('Iphone 13 Pro', 'Il più avanzato iphone di sempre.', 999, 100, 'Argento', 'Apple', 'Smartphone', 'Ottimo'),
('Samsung Galaxy S22 Ultra', 'Il Galaxy S più potente di sempre.', 1199, 100, 'Nero', 'Samsung', 'Smartphone', 'Ottimo'),
('Google Pixel 6 Pro', 'Il Pixel più avanzato di sempre.', 899, 100, 'Sorta Sunny', 'Google', 'Smartphone', 'Ottimo'),
('Oneplus 10 Pro', 'Il Oneplus più potente di sempre.', 899, 100, 'Nero', 'Oneplus', 'Smartphone', 'Ottimo'),
('Xiaomi 12 Pro', 'Lo Xiaomi più avanzato di sempre.', 799, 100, 'Nero', 'Xiaomi', 'Smartphone', 'Buono'),
('Realme Gt 2 Pro', 'Il Realme più potente di sempre.', 699, 100, 'Bianco Carta', 'Realme', 'Smartphone', 'Buono'),
('Ipad Pro (2022)', 'Il più potente Ipad di sempre.', 1099, 100, 'Grigio Siderale', 'Apple', 'Tablet', 'Ottimo'),
('Samsung Galaxy Tab S8 Ultra', 'Il Galaxy Tab più potente di sempre.', 1199, 100, 'Nero', 'Samsung', 'Tablet', 'Ottimo'),
('Microsoft Surface Pro 8', 'Il Surface più potente di sempre.', 999, 100, 'Platino', 'Microsoft', 'Tablet', 'Ottimo'),
('Lenovo Yoga Tab 13', 'Il Yoga Tab più potente di sempre.', 799, 100, 'Grigio Tempesta', 'Lenovo', 'Tablet', 'Buono'),
('Asus Zenpad 13 8gb', 'Il Zenpad più potente di sempre.', 699, 100, 'Nero Stella', 'Asus', 'Tablet', 'Buono'),
('Huawei Matepad Pro 12.6', 'Il Matepad più potente di sempre.', 899, 100, 'Nero Mezzanotte', 'Huawei', 'Tablet', 'Buono'),
('Honor Magicpad 10.4', 'Il Magicpad più potente di sempre.', 599, 100, 'Nero Mezzanotte', 'Honor', 'Tablet', 'Accettabile'),
('Nokia Xr20', 'Lo smartphone più resistente di sempre.', 499, 100, 'Blu Mezzanotte', 'Nokia', 'Smartphone', 'Buono'),
('Motorola Moto G Power (2022)', 'Lo Smartphone più economico con una batteria a lunga durata.', 199, 100, 'Nero', 'Motorola', 'Smartphone', 'Accettabile'),
('Tcl 20 Pro 5G', 'Lo smartphone 5G più economico.', 299, 100, 'Nero Luce Di Luna', 'Tcl', 'Smartphone', 'Buono'),
('Oppo Reno 7 5G', 'Lo smartphone 5G più elegante.', 399, 100, 'Nero Cosmico', 'Oppo', 'Smartphone', 'Buono'),
('Vivo V23 5G', 'Lo smartphone 5G più incentrato sulla fotocamera.', 499, 100, 'Oro Sunshine', 'Vivo', 'Smartphone', 'Accettabile'),
('Iqoo 9 5G', 'Lo smartphone 5G più potente.', 599, 100, 'Edizione Leggendaria', 'Iqoo', 'Smartphone', 'Ottimo'),
('Amazon Fire Hd 10 (2021)', 'Il Tablet da 10 pollici più conveniente.', 149, 100, 'Nero', 'Amazon', 'Tablet', 'Accettabile'),
('Lenovo Tab M10 Plus (2021)', 'Il Tablet da 10 pollici più potente.', 249, 100, 'Grigio Tempesta', 'Lenovo', 'Tablet', 'Buono'),
('Asus Zenpad 10 4gb (2021)', 'Il Tablet da 10 pollici più conveniente.', 199, 100, 'Nero Stella', 'Asus', 'Tablet', 'Buono'),
('Huawei Matepad T10s (2021)', 'Il Tablet da 10 pollici più potente.', 199, 100, 'Nero Notte', 'Huawei', 'Tablet', 'Buono'),
('Honor Magicpad 10 (2021)', 'Il Tablet da 10 pollici più conveniente.', 149, 100, 'Nero Notte', 'Honor', 'Tablet', 'Accettabile'),
('Google Pixel 6a', 'Il Pixel più conveniente di sempre.', 499, 100, 'Sorta Seafoam', 'Google', 'Smartphone', 'Buono'),
('Oneplus Nord Ce 2 Lite 5G', 'Lo smartphone 5G più conveniente.', 249, 100, 'Nero', 'Oneplus', 'Smartphone', 'Buono'),
('Realme 9 Pro 5G', 'Lo smartphone 5G più potente.', 349, 100, 'Nero Notte', 'Realme', 'Smartphone', 'Buono'),
('Motorola Moto G Stylus 5G (2022)', 'Lo Smartphone con pennino più conveniente.', 299, 100, 'Nero', 'Motorola', 'Smartphone', 'Buono'),
('Tcl 20 Se 5G', 'Lo smartphone 5G con display grande più conveniente.', 249, 100, 'Nero Lucido', 'Tcl', 'Smartphone', 'Buono'),
('Oppo A96 5G', 'Lo smartphone 5G più elegante.', 399, 100, 'Nero Cosmico', 'Oppo', 'Smartphone', 'Buono');

INSERT INTO users (name, email, password, address, city, cap, phone) VALUES
('John Doe', 'johndoe@example.com', 'password123', '123 Main St', 'Anytown', '12345', '555-1234'),
('Jane Smith', 'janesmith@example.com', 'password456', '456 Oak St', 'Otherville', '54321', '555-5678'),
('Bob Johnson', 'bobjohnson@example.com', 'password789', '789 Elm St', 'Smalltown', '45678', '555-9012'),
('Alice Lee', 'alicelee@example.com', 'passwordabc', '321 Maple St', 'Bigcity', '67890', '555-3456'),
('David Chen', 'davidchen@example.com', 'passworddef', '654 Pine St', 'Metropolis', '13579', '555-7890');