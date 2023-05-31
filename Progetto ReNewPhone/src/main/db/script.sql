DROP DATABASE IF EXISTS renewphonedb;
CREATE DATABASE renewphonedb;
USE renewphonedb;

DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS buys;

CREATE TABLE products (
  id int primary key AUTO_INCREMENT,
  name varchar(150) not null,
  ram int not null,
  display_size float not null,
  storage int not null,
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


INSERT INTO products (name, ram, display_size, storage, price, quantity, color, brand, category, state, year) VALUES
('iPhone 8', 3,4.7,64, 149, 10, 'Nero', 'Apple', 'Smartphone', 'Buono', 2017),
('iPhone 8', 3, 4.7, 64, 149, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2017),
('iPhone 8', 3, 4.7, 128, 169, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2017),
('iPhone 8', 3, 4.7, 32, 169, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2017),
('iPhone 8', 3,4.7,128, 149, 10, 'Nero', 'Apple', 'Smartphone', 'Buono', 2017),
('iPhone 8', 3, 4.7, 32, 149, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2017),
('iPhone 8', 3, 4.7, 64, 169, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2017),
('iPhone 8', 3, 4.7, 256, 199, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2017),

('iPhone 8 Plus', 3,5.5,64, 159, 10, 'Nero', 'Apple', 'Smartphone', 'Buono', 2017),
('iPhone 8 Plus', 3, 5.5, 64, 159, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2017),
('iPhone 8 Plus', 3, 5.5, 128, 175, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2017),
('iPhone 8 Plus', 3, 5.5, 128, 175, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2017),

('iPhone X', 3, 5.8, 64, 289, 10, 'Space Gray', 'Apple', 'Smartphone', 'Buono', 2017),
('iPhone X', 3, 5.8, 64, 269, 10, 'Oro ', 'Apple', 'Smartphone', 'Buono', 2017),
('iPhone X', 3, 5.8, 64, 289, 10, 'Argento', 'Apple', 'Smartphone', 'Ottimo', 2017),
('iPhone X', 3, 5.8, 256, 319, 10, 'Oro ', 'Apple', 'Smartphone', 'Buono', 2017),
('iPhone X', 3, 5.8, 256, 319, 10, 'Argento', 'Apple', 'Smartphone', 'Ottimo', 2017),
('iPhone X', 3, 5.8, 512, 319, 10, 'Oro ', 'Apple', 'Smartphone', 'Buono', 2017),
('iPhone X', 3, 5.8, 512, 319, 10, 'Argento', 'Apple', 'Smartphone', 'Ottimo', 2017),
('iPhone X', 3, 5.8, 64, 289, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2017),
('iPhone X', 3, 5.8, 64, 289, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2017),
('iPhone X', 3, 5.8, 256, 319, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2017),
('iPhone X', 3, 5.8, 256, 329, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2017),
('iPhone X', 3, 5.8, 512, 319, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2017),

('iPhone XR', 4, 6.1, 64, 339, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XR', 4, 6.1, 64, 339, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XR', 4, 6.1, 128, 369, 10, 'Giallo', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XR', 4, 6.1, 128, 379, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XR', 4, 6.1, 256, 449, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XR', 4, 6.1, 256, 449, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XR', 4, 6.1, 64, 339, 10, 'Giallo', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XR', 4, 6.1, 64, 339, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XR', 4, 6.1, 128, 399, 10, 'Rosso', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XR', 4, 6.1, 128, 399, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XR', 4, 6.1, 128, 449, 10, 'Rosso', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XR', 4, 6.1, 64, 339, 10, 'Azzurro', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XR', 4, 6.1, 64, 339, 10, 'Corallo', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XR', 4, 6.1, 256, 399, 10, 'Blu', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XR', 4, 6.1, 256, 399, 10, 'Rosso', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XR', 4, 6.1, 128, 449, 10, 'Nero', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XR', 4, 6.1, 128, 449, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XR', 4, 6.1, 64, 339, 10, 'Nero', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XR', 4, 6.1, 64, 339, 10, 'Azzurro', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XR', 4, 6.1, 256, 399, 10, 'Azzurro', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XR', 4, 6.1, 256, 419, 10, 'Corallo', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XR', 4, 6.1, 128, 389, 10, 'Giallo', 'Apple', 'Smartphone', 'Buono', 2018),

('iPhone XS', 4, 5.8, 64, 289, 10, 'Argento', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XS', 4, 5.8, 64, 289, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XS', 4, 5.8, 256, 359, 10, 'Argento', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XS', 4, 5.8, 256, 359, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XS', 4, 5.8, 512, 389, 10, 'Argento', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XS', 4, 5.8, 512, 389, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XS', 4, 5.8, 64, 289, 10, 'Oro ', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XS', 4, 5.8, 64, 289, 10, 'Argento', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XS', 4, 5.8, 256, 359, 10, 'Oro ', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XS', 4, 5.8, 256, 359, 10, 'Argento', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XS', 4, 5.8, 512, 389, 10, 'Oro ', 'Apple', 'Smartphone', 'Buono', 2018),

('iPhone XS Max', 4, 6.4, 64, 314, 10, 'Argento', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XS Max', 4, 6.4, 64, 314, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XS Max', 4, 6.4, 256, 384, 10, 'Argento', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XS Max', 4, 6.4, 256, 384, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XS Max', 4, 6.4, 512, 414, 10, 'Argento', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XS Max', 4, 6.4, 512, 414, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XS Max', 4, 6.4, 64, 314, 10, 'Oro ', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XS Max', 4, 6.4, 64, 314, 10, 'Argento', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XS Max', 4, 6.4, 256, 384, 10, 'Oro ', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XS Max', 4, 6.4, 256, 384, 10, 'Argento', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XS Max', 4, 6.4, 512, 414, 10, 'Oro ', 'Apple', 'Smartphone', 'Buono', 2018),

('iPhone 11', 4, 6.1, 64, 359, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11', 4, 6.1, 64, 359, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2019),
('iPhone 11', 4, 6.1, 128, 379, 10, 'Giallo', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11', 4, 6.1, 128, 389, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2019),
('iPhone 11', 4, 6.1, 256, 469, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11', 4, 6.1, 256, 469, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2019),
('iPhone 11', 4, 6.1, 64, 359, 10, 'Giallo', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11', 4, 6.1, 64, 359, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2019),
('iPhone 11', 4, 6.1, 128, 399, 10, 'Rosso', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11', 4, 6.1, 128, 399, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2019),
('iPhone 11', 4, 6.1, 128, 469, 10, 'Rosso', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11', 4, 6.1, 64, 359, 10, 'Azzurro', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11', 4, 6.1, 64, 359, 10, 'Verde', 'Apple', 'Smartphone', 'Ottimo', 2019),
('iPhone 11', 4, 6.1, 256, 469, 10, 'Blu', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11', 4, 6.1, 256, 469, 10, 'Rosso', 'Apple', 'Smartphone', 'Ottimo', 2019),


('iPhone 11 Pro', 4, 5.8, 64, 499, 10, 'Nero', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11 Pro', 4, 5.8, 64, 499, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2019),
('iPhone 11 Pro', 4, 5.8, 128, 599, 10, 'Verde notte', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11 Pro', 4, 5.8, 128, 609, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2019),
('iPhone 11 Pro', 4, 5.8, 256, 669, 10, 'Nero', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11 Pro', 4, 5.8, 256, 669, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2019),
('iPhone 11 Pro', 4, 5.8, 64, 499, 10, 'Verde notte', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11 Pro', 4, 5.8, 64, 499, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2019),
('iPhone 11 Pro', 4, 5.8, 128, 599, 10, 'Verde Notte', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11 Pro', 4, 5.8, 128, 599, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2019),
('iPhone 11 Pro', 4, 5.8, 128, 649, 10, 'Oro', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11 Pro', 4, 5.8, 64, 499, 10, 'Oro', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11 Pro', 4, 5.8, 64, 499, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2019),
('iPhone 11 Pro', 4, 5.8, 256, 599, 10, 'Verde Notte', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11 Pro', 4, 5.8, 256, 599, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2019),

('iPhone 11 Pro Max', 4, 6.4, 64, 469, 10, 'Nero', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11 Pro Max', 4, 6.4, 64, 499, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2019),
('iPhone 11 Pro Max', 4, 6.4, 128, 569, 10, 'Verde notte', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11 Pro Max', 4, 6.4, 128, 599, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2019),
('iPhone 11 Pro Max', 4, 6.4, 256, 649, 10, 'Nero', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11 Pro Max', 4, 6.4, 256, 669, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2019),
('iPhone 11 Pro Max', 4, 6.4, 64, 469, 10, 'Verde notte', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11 Pro Max', 4, 6.4, 64, 499, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2019),
('iPhone 11 Pro Max', 4, 6.4, 128, 569, 10, 'Verde Notte', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11 Pro Max', 4, 6.4, 128, 599, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2019),
('iPhone 11 Pro Max', 4, 6.4, 128, 649, 10, 'Oro', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11 Pro Max', 4, 6.4, 64, 469, 10, 'Oro', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11 Pro Max', 4, 6.4, 64, 499, 10, 'Verde Notte', 'Apple', 'Smartphone', 'Ottimo', 2019),
('iPhone 11 Pro Max', 4, 6.4, 256, 569, 10, 'Verde Notte', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11 Pro Max', 4, 6.4, 256, 599, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2019),

('iPhone 12', 4, 6.1, 64, 454, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12', 4, 6.1, 64, 454, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 12', 4, 6.1, 64, 454, 10, 'Azzurro', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12', 4, 6.1, 64, 454, 10, 'Verde', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 12', 4, 6.1, 64, 454, 10, 'Giallo', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12', 4, 6.1, 64, 454, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 12', 4, 6.1, 128, 474, 10, 'Giallo', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12', 4, 6.1, 128, 484, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 12', 4, 6.1, 128, 494, 10, 'Rosso', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12', 4, 6.1, 128, 494, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 12', 4, 6.1, 128, 564, 10, 'Rosso', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12', 4, 6.1, 256, 564, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12', 4, 6.1, 256, 564, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 12', 4, 6.1, 256, 564, 10, 'Blu', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12', 4, 6.1, 256, 564, 10, 'Rosso', 'Apple', 'Smartphone', 'Ottimo', 2020),

('iPhone 12 mini', 4, 5.4, 64, 414, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12 mini', 4, 5.4, 64, 414, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 12 mini', 4, 5.4, 64, 414, 10, 'Azzurro', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12 mini', 4, 5.4, 64, 414, 10, 'Verde', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 12 mini', 4, 5.4, 64, 414, 10, 'Giallo', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12 mini', 4, 5.4, 64, 414, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 12 mini', 4, 5.4, 128, 424, 10, 'Giallo', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12 mini', 4, 5.4, 128, 424, 10, 'Rosso', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12 mini', 4, 5.4, 128, 434, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 12 mini', 4, 5.4, 128, 424, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 12 mini', 4, 5.4, 128, 494, 10, 'Rosso', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12 mini', 4, 5.4, 256, 494, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12 mini', 4, 5.4, 256, 494, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 12 mini', 4, 5.4, 256, 494, 10, 'Blu', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12 mini', 4, 5.4, 256, 494, 10, 'Rosso', 'Apple', 'Smartphone', 'Ottimo', 2020),

('iPhone 12 Pro', 6, 6.1, 64, 654, 10, 'Blu', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12 Pro', 6, 6.1, 64, 654, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 12 Pro', 6, 6.1, 64, 654, 10, 'Oro', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12 Pro', 6, 6.1, 64, 654, 10, 'Oro', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 12 Pro', 6, 6.1, 64, 654, 10, 'Blu', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12 Pro', 6, 6.1, 64, 654, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 12 Pro', 6, 6.1, 128, 664, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12 Pro', 6, 6.1, 128, 674, 10, 'Oro', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 12 Pro', 6, 6.1, 128, 684, 10, 'Nero', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12 Pro', 6, 6.1, 128, 684, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 12 Pro', 6, 6.1, 128, 704, 10, 'Blu', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12 Pro', 6, 6.1, 256, 704, 10, 'Blu', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12 Pro', 6, 6.1, 256, 704, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 12 Pro', 6, 6.1, 256, 724, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2020),

('iPhone 12 Pro Max', 6, 6.7, 64, 744, 10, 'Blu', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12 Pro Max', 6, 6.7, 64, 744, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 12 Pro Max', 6, 6.7, 64, 744, 10, 'Oro', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12 Pro Max', 6, 6.7, 64, 744, 10, 'Oro', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 12 Pro Max', 6, 6.7, 64, 744, 10, 'Blu', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12 Pro Max', 6, 6.7, 64, 744, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 12 Pro Max', 6, 6.7, 128, 754, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12 Pro Max', 6, 6.7, 128, 764, 10, 'Oro', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 12 Pro Max', 6, 6.7, 128, 774, 10, 'Nero', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12 Pro Max', 6, 6.7, 128, 774, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 12 Pro Max', 6, 6.7, 128, 794, 10, 'Blu', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12 Pro Max', 6, 6.7, 256, 794, 10, 'Blu', 'Apple', 'Smartphone', 'Buono', 2020),
('iPhone 12 Pro Max', 6, 6.7, 256, 794, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2020),
('iPhone 12 Pro Max', 6, 6.7, 256, 814, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2020),

('iPhone 13', 4, 6.1, 64, 654, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13', 4, 6.1, 64, 654, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13', 4, 6.1, 64, 654, 10, 'Azzurro', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13', 4, 6.1, 64, 654, 10, 'Verde', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13', 4, 6.1, 64, 654, 10, 'Verde', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13', 4, 6.1, 64, 654, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13', 4, 6.1, 128, 664, 10, 'Verde', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13', 4, 6.1, 128, 674, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13', 4, 6.1, 128, 684, 10, 'Rosso', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13', 4, 6.1, 128, 684, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13', 4, 6.1, 128, 764, 10, 'Rosso', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13', 4, 6.1, 256, 764, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13', 4, 6.1, 256, 764, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13', 4, 6.1, 256, 764, 10, 'Blu', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13', 4, 6.1, 256, 764, 10, 'Rosso', 'Apple', 'Smartphone', 'Ottimo', 2021),

('iPhone 13 mini', 4, 5.4, 64, 514, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13 mini', 4, 5.4, 64, 514, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13 mini', 4, 5.4, 64, 514, 10, 'Azzurro', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13 mini', 4, 5.4, 64, 514, 10, 'Verde', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13 mini', 4, 5.4, 64, 514, 10, 'Verde', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13 mini', 4, 5.4, 64, 514, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13 mini', 4, 5.4, 128, 524, 10, 'Verde', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13 mini', 4, 5.4, 128, 524, 10, 'Rosso', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13 mini', 4, 5.4, 128, 534, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13 mini', 4, 5.4, 128, 524, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13 mini', 4, 5.4, 128, 594, 10, 'Rosso', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13 mini', 4, 5.4, 256, 594, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13 mini', 4, 5.4, 256, 594, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13 mini', 4, 5.4, 256, 594, 10, 'Blu', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13 mini', 4, 5.4, 256, 594, 10, 'Rosso', 'Apple', 'Smartphone', 'Ottimo', 2021),

('iPhone 13 Pro', 6, 6.1, 64, 764, 10, 'Blu', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13 Pro', 6, 6.1, 64, 764, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13 Pro', 6, 6.1, 64, 764, 10, 'Oro', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13 Pro', 6, 6.1, 64, 764, 10, 'Oro', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13 Pro', 6, 6.1, 64, 764, 10, 'Blu', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13 Pro', 6, 6.1, 64, 764, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13 Pro', 6, 6.1, 128, 764, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13 Pro', 6, 6.1, 128, 784, 10, 'Oro', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13 Pro', 6, 6.1, 128, 784, 10, 'Nero', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13 Pro', 6, 6.1, 128, 784, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13 Pro', 6, 6.1, 128, 809, 10, 'Blu', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13 Pro', 6, 6.1, 256, 809, 10, 'Blu', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13 Pro', 6, 6.1, 256, 809, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13 Pro', 6, 6.1, 256, 809, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2021),

('iPhone 13 Pro Max', 6, 6.7, 64, 850, 10, 'Blu', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13 Pro Max', 6, 6.7, 64, 850, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13 Pro Max', 6, 6.7, 64, 850, 10, 'Oro', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13 Pro Max', 6, 6.7, 64, 850, 10, 'Oro', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13 Pro Max', 6, 6.7, 64, 850, 10, 'Blu', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13 Pro Max', 6, 6.7, 64, 850, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13 Pro Max', 6, 6.7, 128, 865, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13 Pro Max', 6, 6.7, 128, 865, 10, 'Oro', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13 Pro Max', 6, 6.7, 128, 885, 10, 'Nero', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13 Pro Max', 6, 6.7, 128, 885, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13 Pro Max', 6, 6.7, 128, 895, 10, 'Blu', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13 Pro Max', 6, 6.7, 256, 895, 10, 'Blu', 'Apple', 'Smartphone', 'Buono', 2021),
('iPhone 13 Pro Max', 6, 6.7, 256, 895, 10, 'Bianco', 'Apple', 'Smartphone', 'Ottimo', 2021),
('iPhone 13 Pro Max', 6, 6.7, 256, 914, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2021),

('Galaxy S 10', 8, 6.1, 64, 455, 10, 'Blu', 'Samsung', 'Smartphone', 'Buono', 2019),
('Galaxy S 10', 8, 6.1, 64, 455, 10, 'Nero', 'Samsung', 'Smartphone', 'Ottimo', 2019),
('Galaxy S 10', 8, 6.1, 64, 455, 10, 'Viola', 'Samsung', 'Smartphone', 'Buono', 2019),
('Galaxy S 10', 8, 6.1, 64, 455, 10, 'Viola', 'Samsung', 'Smartphone', 'Ottimo', 2019),
('Galaxy S 10', 8, 6.1, 64, 455, 10, 'Blu', 'Samsung', 'Smartphone', 'Buono', 2019),
('Galaxy S 10', 8, 6.1, 64, 455, 10, 'Bianco', 'Samsung', 'Smartphone', 'Ottimo', 2019),
('Galaxy S 10', 8, 6.1, 126, 475, 10, 'Bianco', 'Samsung', 'Smartphone', 'Buono', 2019),
('Galaxy S 10', 8, 6.1, 126, 475, 10, 'Viola', 'Samsung', 'Smartphone', 'Ottimo', 2019),
('Galaxy S 10', 8, 6.1, 126, 475, 10, 'Nero', 'Samsung', 'Smartphone', 'Buono', 2019),
('Galaxy S 10', 8, 6.1, 126, 475, 10, 'Bianco', 'Samsung', 'Smartphone', 'Ottimo', 2019),
('Galaxy S 10', 8, 6.1, 126, 530, 10, 'Blu', 'Samsung', 'Smartphone', 'Buono', 2019),
('Galaxy S 10', 8, 6.1, 256, 530, 10, 'Blu', 'Samsung', 'Smartphone', 'Buono', 2019),
('Galaxy S 10', 8, 6.1, 256, 530, 10, 'Bianco', 'Samsung', 'Smartphone', 'Ottimo', 2019),
('Galaxy S 10', 8, 6.1, 256, 530, 10, 'Nero', 'Samsung', 'Smartphone', 'Ottimo', 2019),

('Galaxy S 10 Plus', 8, 6.4, 64, 465, 10, 'Blu', 'Samsung', 'Smartphone', 'Buono', 2019),
('Galaxy S 10 Plus', 8, 6.4, 64, 465, 10, 'Nero', 'Samsung', 'Smartphone', 'Ottimo', 2019),
('Galaxy S 10 Plus', 8, 6.4, 64, 465, 10, 'Viola', 'Samsung', 'Smartphone', 'Buono', 2019),
('Galaxy S 10 Plus', 8, 6.4, 64, 465, 10, 'Viola', 'Samsung', 'Smartphone', 'Ottimo', 2019),
('Galaxy S 10 Plus', 8, 6.4, 64, 465, 10, 'Blu', 'Samsung', 'Smartphone', 'Buono', 2019),
('Galaxy S 10 Plus', 8, 6.4, 64, 465, 10, 'Bianco', 'Samsung', 'Smartphone', 'Ottimo', 2019),
('Galaxy S 10 Plus', 8, 6.4, 126, 485, 10, 'Bianco', 'Samsung', 'Smartphone', 'Buono', 2019),
('Galaxy S 10 Plus', 8, 6.4, 126, 485, 10, 'Viola', 'Samsung', 'Smartphone', 'Ottimo', 2019),
('Galaxy S 10 Plus', 8, 6.4, 126, 485, 10, 'Nero', 'Samsung', 'Smartphone', 'Buono', 2019),
('Galaxy S 10 Plus', 8, 6.4, 126, 485, 10, 'Bianco', 'Samsung', 'Smartphone', 'Ottimo', 2019),
('Galaxy S 10 Plus', 8, 6.4, 126, 540, 10, 'Blu', 'Samsung', 'Smartphone', 'Buono', 2019),
('Galaxy S 10 Plus', 8, 6.4, 256, 540, 10, 'Blu', 'Samsung', 'Smartphone', 'Buono', 2019),
('Galaxy S 10 Plus', 8, 6.4, 256, 540, 10, 'Bianco', 'Samsung', 'Smartphone', 'Ottimo', 2019),
('Galaxy S 10 Plus', 8, 6.4, 256, 540, 10, 'Nero', 'Samsung', 'Smartphone', 'Ottimo', 2019),

('Galaxy S 20', 8, 6.2, 64, 555, 10, 'Blu', 'Samsung', 'Smartphone', 'Buono', 2020),
('Galaxy S 20', 8, 6.2, 64, 555, 10, 'Nero', 'Samsung', 'Smartphone', 'Ottimo', 2020),
('Galaxy S 20', 8, 6.2, 64, 555, 10, 'Viola', 'Samsung', 'Smartphone', 'Buono', 2020),
('Galaxy S 20', 8, 6.2, 64, 555, 10, 'Viola', 'Samsung', 'Smartphone', 'Ottimo', 2020),
('Galaxy S 20', 8, 6.2, 64, 555, 10, 'Blu', 'Samsung', 'Smartphone', 'Buono', 2020),
('Galaxy S 20', 8, 6.2, 64, 555, 10, 'Bianco', 'Samsung', 'Smartphone', 'Ottimo', 2020),
('Galaxy S 20', 8, 6.2, 126, 575, 10, 'Bianco', 'Samsung', 'Smartphone', 'Buono', 2020),
('Galaxy S 20', 8, 6.2, 126, 575, 10, 'Viola', 'Samsung', 'Smartphone', 'Ottimo', 2020),
('Galaxy S 20', 8, 6.2, 126, 575, 10, 'Nero', 'Samsung', 'Smartphone', 'Buono', 2020),
('Galaxy S 20', 8, 6.2, 126, 575, 10, 'Bianco', 'Samsung', 'Smartphone', 'Ottimo', 2020),
('Galaxy S 20', 8, 6.2, 126, 630, 10, 'Blu', 'Samsung', 'Smartphone', 'Buono', 2020),
('Galaxy S 20', 8, 6.2, 256, 630, 10, 'Blu', 'Samsung', 'Smartphone', 'Buono', 2020),
('Galaxy S 20', 8, 6.2, 256, 630, 10, 'Bianco', 'Samsung', 'Smartphone', 'Ottimo', 2020),
('Galaxy S 20', 8, 6.2, 256, 630, 10, 'Nero', 'Samsung', 'Smartphone', 'Ottimo', 2020),

('Galaxy S 20', 8, 6.2, 64, 555, 10, 'Blu', 'Samsung', 'Smartphone', 'Buono', 2020),
('Galaxy S 20', 8, 6.2, 64, 555, 10, 'Nero', 'Samsung', 'Smartphone', 'Ottimo', 2020),
('Galaxy S 20', 8, 6.2, 64, 555, 10, 'Viola', 'Samsung', 'Smartphone', 'Buono', 2020),
('Galaxy S 20', 8, 6.2, 64, 555, 10, 'Viola', 'Samsung', 'Smartphone', 'Ottimo', 2020),
('Galaxy S 20', 8, 6.2, 64, 555, 10, 'Blu', 'Samsung', 'Smartphone', 'Buono', 2020),
('Galaxy S 20', 8, 6.2, 64, 555, 10, 'Bianco', 'Samsung', 'Smartphone', 'Ottimo', 2020),
('Galaxy S 20', 8, 6.2, 126, 575, 10, 'Bianco', 'Samsung', 'Smartphone', 'Buono', 2020),
('Galaxy S 20', 8, 6.2, 126, 575, 10, 'Viola', 'Samsung', 'Smartphone', 'Ottimo', 2020),
('Galaxy S 20', 8, 6.2, 126, 575, 10, 'Nero', 'Samsung', 'Smartphone', 'Buono', 2020),
('Galaxy S 20', 8, 6.2, 126, 575, 10, 'Bianco', 'Samsung', 'Smartphone', 'Ottimo', 2020),
('Galaxy S 20', 8, 6.2, 126, 630, 10, 'Blu', 'Samsung', 'Smartphone', 'Buono', 2020),
('Galaxy S 20', 8, 6.2, 256, 630, 10, 'Blu', 'Samsung', 'Smartphone', 'Buono', 2020),
('Galaxy S 20', 8, 6.2, 256, 630, 10, 'Bianco', 'Samsung', 'Smartphone', 'Ottimo', 2020),
('Galaxy S 20', 8, 6.2, 256, 630, 10, 'Nero', 'Samsung', 'Smartphone', 'Ottimo', 2020);


INSERT INTO users (name, surname, email, password, address, city, cap, phone) VALUES
('John', 'Doe', 'johndoe@example.com', 'password123', '123 Main St', 'Anytown', '12345', '555-1234'),
('Jane', 'Smith', 'janesmith@example.com', 'password456', '456 Oak St', 'Otherville', '54321', '555-5678'),
('Bob', 'Johnson', 'bobjohnson@example.com', 'password789', '789 Elm St', 'Smalltown', '45678', '555-9012'),
('Alice', 'Lee', 'alicelee@example.com', 'passwordabc', '321 Maple St', 'Bigcity', '67890', '555-3456'),
('David', 'Chen', 'davidchen@example.com', 'passworddef', '654 Pine St', 'Metropolis', '13579', '555-7890'),
('Davide', 'Capricano', 'd@owner.com', 'password _davide', 'Via gambardella 29', ' Torre Annunziata', '80058', '123456789');

INSERT INTO carts (id_user, id_product, quantity)
VALUES (1, 10, 1),
       (1, 20, 1),
       (2, 30, 2),
       (3, 40, 1);
