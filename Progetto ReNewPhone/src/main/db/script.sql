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
  year int not null,
  category varchar(15),
  state char(12)
);

CREATE TABLE users (
  id int primary key AUTO_INCREMENT,
  name varchar(25) not null,
  surname varchar(25) not null,
  email varchar(100) not null unique,
  password varchar(50) not null,
  address varchar(60) not null,
  city varchar(35) not null,
  cap char(7) not null,
  phone varchar(20) not null
);

CREATE TABLE orders (
  id int primary key AUTO_INCREMENT,
  id_user int not null,
  total int not null,
  FOREIGN KEY (id_user) REFERENCES users(id)
);

CREATE TABLE order_items (
  id int primary key AUTO_INCREMENT,
  id_order int not null,
  id_product int not null,
  quantity int not null,
  FOREIGN KEY (id_order) REFERENCES orders(id),
  FOREIGN KEY (id_product) REFERENCES products(id)
);

CREATE TABLE carts (
  id_user int not null,
  id_product int not null,
  quantity int not null,
  primary key (id_user, id_product),
  FOREIGN KEY (id_user) REFERENCES users(id),
  FOREIGN KEY (id_product) REFERENCES products(id)
);


INSERT INTO products (name, description, price, quantity, color, brand, category, state, year) VALUES
('Samsung Galaxy S22 Ultra', 'Il Galaxy S più potente di sempre.', 1199, 100, 'Nero', 'Samsung', 'Smartphone', 'Ottimo',2022),
('Google Pixel 6 Pro', 'Il Pixel più avanzato di sempre.', 899, 100, 'Sorta Sunny', 'Google', 'Smartphone', 'Ottimo',2021),
('Oneplus 10 Pro', 'Il Oneplus più potente di sempre.', 899, 100, 'Nero', 'Oneplus', 'Smartphone', 'Ottimo',2021),
('Xiaomi 12 Pro', 'Lo Xiaomi più avanzato di sempre.', 799, 100, 'Nero', 'Xiaomi', 'Smartphone', 'Buono',2023),
('Realme Gt 2 Pro', 'Il Realme più potente di sempre.', 699, 100, 'Bianco Carta', 'Realme', 'Smartphone', 'Buono', 2022),
('Ipad Pro (2022)', 'Il più potente Ipad di sempre.', 1099, 100, 'Grigio Siderale', 'Apple', 'Tablet', 'Ottimo',2022),
('Samsung Galaxy Tab S8 Ultra', 'Il Galaxy Tab più potente di sempre.', 1199, 100, 'Nero', 'Samsung', 'Tablet', 'Ottimo',2022),
('Microsoft Surface Pro 8', 'Il Surface più potente di sempre.', 999, 100, 'Platino', 'Microsoft', 'Tablet', 'Ottimo',2021),
('Lenovo Yoga Tab 13', 'Il Yoga Tab più potente di sempre.', 799, 100, 'Grigio Tempesta', 'Lenovo', 'Tablet', 'Buono',2021),
('Asus Zenpad 13 8gb', 'Il Zenpad più potente di sempre.', 699, 100, 'Nero Stella', 'Asus', 'Tablet', 'Buono', 2021),
('Huawei Matepad Pro 12.6', 'Il Matepad più potente di sempre.', 899, 100, 'Nero Mezzanotte', 'Huawei', 'Tablet', 'Buono',2022),
('Honor Magicpad 10.4', 'Il Magicpad più potente di sempre.', 599, 100, 'Nero Mezzanotte', 'Honor', 'Tablet', 'Accettabile',2021),
('Nokia Xr20', 'Lo smartphone più resistente di sempre.', 499, 100, 'Blu Mezzanotte', 'Nokia', 'Smartphone', 'Buono',2020),
('Motorola Moto G Power (2022)', 'Lo Smartphone più economico con una batteria a lunga durata.', 199, 100, 'Nero', 'Motorola', 'Smartphone', 'Accettabile',2022),
('Tcl 20 Pro 5G', 'Lo smartphone 5G più economico.', 299, 100, 'Nero Luce Di Luna', 'Tcl', 'Smartphone', 'Buono',2021),
('Oppo Reno 7 5G', 'Lo smartphone 5G più elegante.', 399, 100, 'Nero Cosmico', 'Oppo', 'Smartphone', 'Buono',2022),
('Vivo V23 5G', 'Lo smartphone 5G più incentrato sulla fotocamera.', 499, 100, 'Oro Sunshine', 'Vivo', 'Smartphone', 'Accettabile',2022),
('Iqoo 9 5G', 'Lo smartphone 5G più potente.', 599, 100, 'Edizione Leggendaria', 'Iqoo', 'Smartphone', 'Ottimo',2022),
('Amazon Fire Hd 10 (2021)', 'Il Tablet da 10 pollici più conveniente.', 149, 100, 'Nero', 'Amazon', 'Tablet', 'Accettabile',2021),
('Lenovo Tab M10 Plus (2021)', 'Il Tablet da 10 pollici più potente.', 249, 100, 'Grigio Tempesta', 'Lenovo', 'Tablet', 'Buono',2021),
('Asus Zenpad 10 4gb (2021)', 'Il Tablet da 10 pollici più conveniente.', 199, 100, 'Nero Stella', 'Asus', 'Tablet', 'Buono',2021),
('Huawei Matepad T10s (2021)', 'Il Tablet da 10 pollici più potente.', 199, 100, 'Nero Notte', 'Huawei', 'Tablet', 'Buono',2021),
('Honor Magicpad 10 (2021)', 'Il Tablet da 10 pollici più conveniente.', 149, 100, 'Nero Notte', 'Honor', 'Tablet', 'Accettabile',2021),
('Google Pixel 6a', 'Il Pixel più conveniente di sempre.', 499, 100, 'Sorta Seafoam', 'Google', 'Smartphone', 'Buono',2022),
('Oneplus Nord Ce 2 Lite 5G', 'Lo smartphone 5G più conveniente.', 249, 100, 'Nero', 'Oneplus', 'Smartphone', 'Buono',2022),
('Realme 9 Pro 5G', 'Lo smartphone 5G più potente.', 349, 100, 'Nero Notte', 'Realme', 'Smartphone', 'Buono',2022),
('Motorola Moto G Stylus 5G (2022)', 'Lo Smartphone con pennino più conveniente.', 299, 100, 'Nero', 'Motorola', 'Smartphone', 'Buono',2022),
('Tcl 20 Se 5G', 'Lo smartphone 5G con display grande più conveniente.', 249, 100, 'Nero Lucido', 'Tcl', 'Smartphone', 'Buono',2021),
('Oppo A96 5G', 'Lo smartphone 5G più elegante.', 399, 100, 'Nero Cosmico', 'Oppo', 'Smartphone', 'Buono',2022),
('Iphone 13 Mini', 'iPhone più piccolo e potente di sempre.', 699, 100, 'Bianco', 'Apple', 'Smartphone', 'Buono',2022),
('Samsung Galaxy Z Fold 3', 'Lo smartphone pieghevole più avanzato.', 1799, 100, 'Nero', 'Samsung', 'Smartphone', 'Ottimo',2021),
('Google Pixel 6', 'La nuova generazione di Pixel.', 799, 100, 'Bianco', 'Google', 'Smartphone', 'Buono',2021),
('OnePlus 11', 'Ultimo flagship di OnePlus.', 899, 100, 'Nero', 'OnePlus', 'Smartphone', 'Ottimo',2023),
('Xiaomi 12', 'Un telefono potente a un prezzo conveniente.', 699, 100, 'Verde', 'Xiaomi', 'Smartphone', 'Buono',2022),
('Realme GT Neo 2', 'Un dispositivo gaming ad alte prestazioni.', 599, 100, 'Nero', 'Realme', 'Smartphone', 'Buono',2022),
('iPad Air (2022)', 'Il tablet potente e leggero di Apple.', 899, 100, 'Verde', 'Apple', 'Tablet', 'Ottimo',2022),
('Samsung Galaxy Tab S7 FE', 'Un tablet versatile con funzionalità premium.', 599, 100, 'Nero', 'Samsung', 'Tablet', 'Buono',2021),
('Microsoft Surface Go 3', 'Il tablet compatto e leggero per utilizzo quotidiano.', 499, 100, 'Platino', 'Microsoft', 'Tablet', 'Buono',2022),
('Lenovo Tab P11 Plus', 'Un tablet premium con schermo nitido e audio potente', 349, 100, 'Grisio', 'Lenovo', 'Tablet', 'Buono',2020),
('Asus ROG Phone 5', 'Uno smartphone dedicato al gaming ad alte prestazioni.', 999, 100, 'Nero', 'Asus', 'Smartphone', 'Ottimo',2020),
('Huawei P50 Pro', 'Un dispositivo fotografico con una potente fotocamera.', 1099, 100, 'Argento', 'Huawei', 'Smartphone', 'Ottimo',2022),
('Sony Xperia 1 III', 'Un telefono con funzionalità avanzate per la fotografia.', 1199, 100, 'Nero', 'Sony', 'Smartphone', 'Ottimo',2020),
('Motorola Edge 20 Pro', 'Uno smartphone elegante con caratteristiche di fascia alta.', 899, 100, 'Blu', 'Motorola', 'Smartphone', 'Buono',2021),
('Nokia 8.3 5G', 'Un telefono 5G con un design raffinato.', 599, 100, 'Bianco', 'Nokia', 'Smartphone', 'Buono', 2020),
('Xiaomi Mi 11 Lite', 'Uno smartphone leggero e sottile con ottime prestazioni.', 399, 100, 'Blu', 'Xiaomi', 'Smartphone', 'Buono',2021),
('Xiaomi Redmi 9A', 'Uno smartphone economico con buone prestazioni.', 129, 100, 'Blu', 'Xiaomi', 'Smartphone', 'Accettabile',2022),
('Samsung Galaxy A12', 'Un telefono a basso costo con una grande batteria.', 149, 100, 'Nero', 'Samsung', 'Smartphone', 'Accettabile',2021),
('Nokia 2.4', 'Un telefono economico con un design elegante.', 119, 100, 'Viola', 'Nokia', 'Smartphone', 'Accettabile', 2019),
('Realme C11', 'Uno smartphone economico con una grande batteria.', 99, 100, 'Verde', 'Realme', 'Smartphone', 'Accettabile',2022),
('Xiaomi Mi 11 Lite', 'Uno smartphone leggero e sottile con ottime prestazioni.', 399, 100, 'Nero', 'Xiaomi', 'Smartphone', 'Buono',2021),
('Xiaomi Redmi 9A', 'Uno smartphone economico con buone prestazioni.', 129, 100, 'Bianco', 'Xiaomi', 'Smartphone', 'Accettabile',2021),
('Samsung Galaxy A12', 'Un telefono a basso costo con una grande batteria.', 149, 100, 'Bianco', 'Samsung', 'Smartphone', 'Accettabile',2021),
('Nokia 2.4', 'Un telefono economico con un design elegante.', 119, 100, 'Rosso', 'Nokia', 'Smartphone', 'Accettabile',2019),
('Realme C11', 'Uno smartphone economico con una grande batteria.', 99, 100, 'Nero', 'Realme', 'Smartphone', 'Accettabile',2022),
('iPhone 6', 'Descrizione iPhone 6', 199, 50, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2014),
('iPhone 6', 'Descrizione iPhone 6', 199, 50, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2014),
('iPhone 6s', 'Descrizione iPhone 6s', 249, 50, 'Argento', 'Apple', 'Smartphone', 'Eccellente', 2015),
('iPhone 6s', 'Descrizione iPhone 6s', 249, 50, 'Oro', 'Apple', 'Smartphone', 'Ottimo', 2015),
('iPhone 7', 'Descrizione iPhone 7', 299, 50, 'Nero', 'Apple', 'Smartphone', 'Buono', 2016),
('iPhone 7', 'Descrizione iPhone 7', 299, 50, 'Jet Black', 'Apple', 'Smartphone', 'Eccellente', 2016),
('iPhone 8', 'Descrizione iPhone 8', 399, 50, 'Argento', 'Apple', 'Smartphone', 'Ottimo', 2017),
('iPhone 8', 'Descrizione iPhone 8', 399, 50, 'Oro', 'Apple', 'Smartphone', 'Buono', 2017),
('iPhone X', 'Descrizione iPhone X', 599, 50, 'Spazio grigio', 'Apple', 'Smartphone', 'Eccellente', 2017),
('iPhone X', 'Descrizione iPhone X', 599, 50, 'Argento', 'Apple', 'Smartphone', 'Ottimo', 2017),
('iPhone 11', 'Descrizione iPhone 11', 699, 50, 'Nero', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11', 'Descrizione iPhone 11', 699, 50, 'Verde', 'Apple', 'Smartphone', 'Eccellente', 2019),
('iPhone XS', 'Descrizione iPhone XS', 899, 50, 'Oro', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XS', 'Descrizione iPhone XS', 899, 50, 'Spazio grigio', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone 12', 'Descrizione iPhone 12', 799, 50, 'Blu', 'Apple', 'Smartphone', 'Eccellente', 2020),
('iPhone 12', 'Descrizione iPhone 12', 799, 50, 'Rosso', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 13', 'Descrizione iPhone 13', 999, 50, 'Viola', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13', 'Descrizione iPhone 13', 999, 50, 'Blu', 'Apple', 'Smartphone', 'Eccellente', 2021),
('iPhone SE', 'Descrizione iPhone SE', 399, 50, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone SE', 'Descrizione iPhone SE', 399, 50, 'Nero', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone XR', 'Descrizione iPhone XR', 599, 50, 'Bianco', 'Apple', 'Smartphone', 'Eccellente', 2018),
('iPhone XR', 'Descrizione iPhone XR', 599, 50, 'Rosso', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone 12 Pro', 'Descrizione iPhone 12 Pro', 1099, 50, 'Grafite', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12 Pro', 'Descrizione iPhone 12 Pro', 1099, 50, 'Argento', 'Apple', 'Smartphone', 'Eccellente', 2020),
('iPhone 13 Mini', 'Descrizione iPhone 13 Mini', 799, 50, 'Giallo', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13 Mini', 'Descrizione iPhone 13 Mini', 799, 50, 'Rosa', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 7 Plus', 'Descrizione iPhone 7 Plus', 349, 50, 'Jet Black', 'Apple', 'Smartphone', 'Eccellente', 2016),
('iPhone 7 Plus', 'Descrizione iPhone 7 Plus', 349, 50, 'Oro Rosa', 'Apple', 'Smartphone', 'Ottimo', 2016),
('iPhone 8 Plus', 'Descrizione iPhone 8 Plus', 499, 50, 'Argento', 'Apple', 'Smartphone', 'Buono', 2017),
('iPhone 8 Plus', 'Descrizione iPhone 8 Plus', 499, 50, 'Oro', 'Apple', 'Smartphone', 'Eccellente', 2017),
('iPhone XS Max', 'Descrizione iPhone XS Max', 999, 50, 'Spazio grigio', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XS Max', 'Descrizione iPhone XS Max', 999, 50, 'Oro', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone 11 Pro', 'Descrizione iPhone 11 Pro', 899, 50, 'Verde mezzanotte', 'Apple', 'Smartphone', 'Eccellente', 2019),
('iPhone 11 Pro', 'Descrizione iPhone 11 Pro', 899, 50, 'Argento', 'Apple', 'Smartphone', 'Ottimo', 2019),
('iPhone SE (2020)', 'Descrizione iPhone SE (2020)', 399, 50, 'Rosso', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone SE (2020)', 'Descrizione iPhone SE (2020)', 399, 50, 'Nero', 'Apple', 'Smartphone', 'Eccellente', 2020),
('iPhone 12 Pro Max', 'Descrizione iPhone 12 Pro Max', 1199, 50, 'Pacifi', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 12 Pro Max', 'Descrizione iPhone 12 Pro Max', 1199, 50, 'Oro', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 13 Pro', 'Descrizione iPhone 13 Pro', 1099, 50, 'Graphite', 'Apple', 'Smartphone', 'Eccellente', 2021),
('iPhone 13 Pro', 'Descrizione iPhone 13 Pro', 1099, 50, 'Argento', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13 Pro Max', 'Descrizione iPhone 13 Pro Max', 1199, 50, 'Blu sierra', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13 Pro Max', 'Descrizione iPhone 13 Pro Max', 1199, 50, 'Grafite', 'Apple', 'Smartphone', 'Eccellente', 2021);



INSERT INTO users VALUES
('-10', 'Davide', 'Capricano', 'd@owner.com', 'password_davide', 'Via gambardella 29', ' Torre Annunziata', '80058', '123456789');

INSERT INTO users (name, surname, email, password, address, city, cap, phone) VALUES
('John', 'Doe', 'johndoe@example.com', 'password123', '123 Main St', 'Anytown', '12345', '555-1234'),
('Jane', 'Smith', 'janesmith@example.com', 'password456', '456 Oak St', 'Otherville', '54321', '555-5678'),
('Bob', 'Johnson', 'bobjohnson@example.com', 'password789', '789 Elm St', 'Smalltown', '45678', '555-9012'),
('Alice', 'Lee', 'alicelee@example.com', 'passwordabc', '321 Maple St', 'Bigcity', '67890', '555-3456'),
('David', 'Chen', 'davidchen@example.com', 'passworddef', '654 Pine St', 'Metropolis', '13579', '555-7890');

INSERT INTO carts (id_user, id_product, quantity)
VALUES (1, 10, 1),
       (1, 20, 1),
       (2, 30, 2),
       (3, 40, 1);
