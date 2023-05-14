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
  email varchar(100) not null,
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
('Iphone 13 Pro', 'Il Più Avanzato Iphone Di Sempre.', 999, 100, 'Argento', 'Apple', 'Smartphone', 'Ottimo'),
('Samsung Galaxy S22 Ultra', 'Il Galaxy S Più Potente Di Sempre.', 1199, 100, 'Nero', 'Samsung', 'Smartphone', 'Ottimo'),
('Google Pixel 6 Pro', 'Il Pixel Più Avanzato Di Sempre.', 899, 100, 'Sorta Sunny', 'Google', 'Smartphone', 'Ottimo'),
('Oneplus 10 Pro', 'Il Oneplus Più Potente Di Sempre.', 899, 100, 'Nero', 'Oneplus', 'Smartphone', 'Ottimo'),
('Xiaomi 12 Pro', 'Il Xiaomi Più Avanzato Di Sempre.', 799, 100, 'Nero', 'Xiaomi', 'Smartphone', 'Buono'),
('Realme Gt 2 Pro', 'Il Realme Più Potente Di Sempre.', 699, 100, 'Bianco Carta', 'Realme', 'Smartphone', 'Buono'),
('Ipad Pro (2022)', 'Il Più Potente Ipad Di Sempre.', 1099, 100, 'Grigio Siderale', 'Apple', 'Tablet', 'Ottimo'),
('Samsung Galaxy Tab S8 Ultra', 'Il Galaxy Tab Più Potente Di Sempre.', 1199, 100, 'Nero', 'Samsung', 'Tablet', 'Ottimo'),
('Microsoft Surface Pro 8', 'Il Surface Più Potente Di Sempre.', 999, 100, 'Platino', 'Microsoft', 'Tablet', 'Ottimo'),
('Lenovo Yoga Tab 13', 'Il Yoga Tab Più Potente Di Sempre.', 799, 100, 'Grigio Tempesta', 'Lenovo', 'Tablet', 'Buono'),
('Asus Zenpad 13 8gb', 'Il Zenpad Più Potente Di Sempre.', 699, 100, 'Nero Stella', 'Asus', 'Tablet', 'Buono'),
('Huawei Matepad Pro 12.6', 'Il Matepad Più Potente Di Sempre.', 899, 100, 'Nero Mezzanotte', 'Huawei', 'Tablet', 'Buono'),
('Honor Magicpad 10.4', 'Il Magicpad Più Potente Di Sempre.', 599, 100, 'Nero Mezzanotte', 'Honor', 'Tablet', 'Accettabile'),
('Nokia Xr20', 'Lo Smartphone Più Resistente Di Sempre.', 499, 100, 'Blu Mezzanotte', 'Nokia', 'Smartphone', 'Buono'),
('Motorola Moto G Power (2022)', 'Lo Smartphone Più Economico Con Una Batteria A Lunga Durata.', 199, 100, 'Nero', 'Motorola', 'Smartphone', 'Accettabile'),
('Tcl 20 Pro 5g', 'Lo Smartphone 5g Più Economico.', 299, 100, 'Nero Luce Di Luna', 'Tcl', 'Smartphone', 'Buono'),
('Oppo Reno 7 5g', 'Lo Smartphone 5g Più Elegante.', 399, 100, 'Nero Cosmico', 'Oppo', 'Smartphone', 'Buono'),
('Vivo V23 5g', 'Lo Smartphone 5g Più Incentrato Sulla Fotocamera.', 499, 100, 'Oro Sunshine', 'Vivo', 'Smartphone', 'Accettabile'),
('Iqoo 9 5g', 'Lo Smartphone 5g Più Potente.', 599, 100, 'Edizione Leggendaria', 'Iqoo', 'Smartphone', 'Ottimo'),
('Amazon Fire Hd 10 (2021)', 'Il Tablet Da 10 Pollici Più Conveniente.', 149, 100, 'Nero', 'Amazon', 'Tablet', 'Accettabile'),
('Lenovo Tab M10 Plus (2021)', 'Il Tablet Da 10 Pollici Più Potente.', 249, 100, 'Grigio Tempesta', 'Lenovo', 'Tablet', 'Buono'),
('Asus Zenpad 10 4gb (2021)', 'Il Tablet Da 10 Pollici Più Conveniente.', 199, 100, 'Nero Stella', 'Asus', 'Tablet', 'Buono'),
('Huawei Matepad T10s (2021)', 'Il Tablet Da 10 Pollici Più Potente.', 199, 100, 'Nero Notte', 'Huawei', 'Tablet', 'Buono'),
('Honor Magicpad 10 (2021)', 'Il Tablet Da 10 Pollici Più Conveniente.', 149, 100, 'Nero Notte', 'Honor', 'Tablet', 'Accettabile'),
('Google Pixel 6a', 'Il Pixel Più Conveniente Di Sempre.', 499, 100, 'Sorta Seafoam', 'Google', 'Smartphone', 'Buono'),
('Oneplus Nord Ce 2 Lite 5g', 'Lo Smartphone 5g Più Conveniente.', 249, 100, 'Nero', 'Oneplus', 'Smartphone', 'Buono'),
('Realme 9 Pro 5g', 'Lo Smartphone 5g Più Potente.', 349, 100, 'Nero Notte', 'Realme', 'Smartphone', 'Buono'),
('Motorola Moto G Stylus 5g (2022)', 'Lo Smartphone Con Pennino Più Conveniente.', 299, 100, 'Nero', 'Motorola', 'Smartphone', 'Buono'),
('Tcl 20 Se 5g', 'Lo Smartphone 5g Con Display Grande Più Conveniente.', 249, 100, 'Nero Lucido', 'Tcl', 'Smartphone', 'Buono'),
('Oppo A96 5g', 'Lo Smartphone 5g Più Elegante.', 399, 100, 'Nero Cosmico', 'Oppo', 'Smartphone', 'Buono');
