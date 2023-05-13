DROP DATABASE IF EXISTS storage;
DROP DATABASE IF EXISTS renewphonedb;
CREATE DATABASE renewphonedb;
USE renewphonedb;

DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS buys;

CREATE TABLE products (	
  id int primary key AUTO_INCREMENT,
  name char(50) not null,
  description char(200),
  price int default 0,
  quantity int default 0,
  color char(20),
  brand char(20),
  category char(20),
  state char(20)
);

CREATE TABLE users (
    id int primary key AUTO_INCREMENT,
    name char(20) not null,
    email char(40) not null,
    password char(40) not null,
    address char(40) not null,
    city char(20) not null,
    cap int not null,
    phone int not null
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
('iPhone 13 Pro', 'Il più avanzato iPhone di sempre.', 999, 100, 'Argento', 'Apple', 'Smartphone', 'ottimo'),
('Samsung Galaxy S22 Ultra', 'Il Galaxy S più potente di sempre.', 1199, 100, 'Nero', 'Samsung', 'Smartphone', 'ottimo'),
('Google Pixel 6 Pro', 'Il Pixel più avanzato di sempre.', 899, 100, 'Sorta Sunny', 'Google', 'Smartphone', 'ottimo'),
('OnePlus 10 Pro', 'Il OnePlus più potente di sempre.', 899, 100, 'Nero', 'OnePlus', 'Smartphone', 'ottimo'),
('Xiaomi 12 Pro', 'Il Xiaomi più avanzato di sempre.', 799, 100, 'Nero', 'Xiaomi', 'Smartphone', 'buono'),
('Realme GT 2 Pro', 'Il Realme più potente di sempre.', 699, 100, 'Bianco Carta', 'Realme', 'Smartphone', 'buono'),
('iPad Pro (2022)', 'Il più potente iPad di sempre.', 1099, 100, 'Grigio siderale', 'Apple', 'Tablet', 'ottimo'),
('Samsung Galaxy Tab S8 Ultra', 'Il Galaxy Tab più potente di sempre.', 1199, 100, 'Nero', 'Samsung', 'Tablet', 'ottimo'),
('Microsoft Surface Pro 8', 'Il Surface più potente di sempre.', 999, 100, 'Platino', 'Microsoft', 'Tablet', 'ottimo'),
('Lenovo Yoga Tab 13', 'Il Yoga Tab più potente di sempre.', 799, 100, 'Grigio tempesta', 'Lenovo', 'Tablet', 'buono'),
('Asus ZenPad 13 8GB', 'Il ZenPad più potente di sempre.', 699, 100, 'Nero Stella', 'Asus', 'Tablet', 'buono'),
('Huawei MatePad Pro 12.6', 'Il MatePad più potente di sempre.', 899, 100, 'Nero mezzanotte', 'Huawei', 'Tablet', 'buono'),
('Honor MagicPad 10.4', 'Il MagicPad più potente di sempre.', 599, 100, 'Nero mezzanotte', 'Honor', 'Tablet', 'accettabile'),
('Nokia XR20', 'Lo smartphone più resistente di sempre.', 499, 100, 'Blu mezzanotte', 'Nokia', 'Smartphone', 'buono'),
('Motorola Moto G Power (2022)', 'Lo smartphone più economico con una batteria a lunga durata.', 199, 100, 'Nero', 'Motorola', 'Smartphone', 'accettabile'),
('TCL 20 Pro 5G', 'Lo smartphone 5G più economico.', 299, 100, 'Nero luce di luna', 'TCL', 'Smartphone', 'buono'),
('Oppo Reno 7 5G', 'Lo smartphone 5G più elegante.', 399, 100, 'Nero cosmico', 'Oppo', 'Smartphone', 'buono'),
('Vivo V23 5G', 'Lo smartphone 5G più incentrato sulla fotocamera.', 499, 100, 'Oro sunshine', 'Vivo', 'Smartphone', 'accettabile'),
('iQOO 9 5G', 'Lo smartphone 5G più potente.', 599, 100, 'Edizione leggendaria', 'iQOO', 'Smartphone', 'ottimo'),
('Amazon Fire HD 10 (2021)', 'Il tablet da 10 pollici più conveniente.', 149, 100, 'Nero', 'Amazon', 'Tablet', 'accettabile'),
('Lenovo Tab M10 Plus (2021)', 'Il tablet da 10 pollici più potente.', 249, 100, 'Grigio Tempesta', 'Lenovo', 'Tablet', 'buono'),
('Asus ZenPad 10 4GB (2021)', 'Il tablet da 10 pollici più conveniente.', 199, 100, 'Nero Stella', 'Asus', 'Tablet', 'buono'),
('Huawei MatePad T10s (2021)', 'Il tablet da 10 pollici più potente.', 199, 100, 'Nero Notte', 'Huawei', 'Tablet', 'buono'),
('Honor MagicPad 10 (2021)', 'Il tablet da 10 pollici più conveniente.', 149, 100, 'Nero Notte', 'Honor', 'Tablet', 'accettabile'),
('Google Pixel 6a', 'Il Pixel più conveniente di sempre.', 499, 100, 'Sorta Seafoam', 'Google', 'Smartphone', 'buono'),
('OnePlus Nord CE 2 Lite 5G', 'Lo smartphone 5G più conveniente.', 249, 100, 'Nero', 'OnePlus', 'Smartphone', 'buono'),
('Realme 9 Pro 5G', 'Lo smartphone 5G più potente.', 349, 100, 'Nero Notte', 'Realme', 'Smartphone', 'buono'),
('Motorola Moto G Stylus 5G (2022)', 'Lo smartphone con pennino più conveniente.', 299, 100, 'Nero', 'Motorola', 'Smartphone', 'buono'),
('TCL 20 SE 5G', 'Lo smartphone 5G con display grande più conveniente.', 249, 100, 'Nero Lucido', 'TCL', 'Smartphone', 'buono'),
('Oppo A96 5G', 'Lo smartphone 5G più elegante.', 399, 100, 'Nero Cosmico', 'Oppo', 'Smartphone', 'buono');
