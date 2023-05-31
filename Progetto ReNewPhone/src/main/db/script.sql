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
('iPhone 8', 3, 4.7, 128, 169, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2017),
('iPhone 8 Plus', 3,5.5,64, 159, 10, 'Nero', 'Apple', 'Smartphone', 'Buono', 2017),
('iPhone 8 Plus', 3, 5.5, 64, 159, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2017),
('iPhone 8 Plus', 3, 5.5, 128, 175, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2017),
('iPhone 8 Plus', 3, 5.5, 128, 175, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2017),
('iPhone X', 3, 5.8, 64, 289, 10, 'Space Gray', 'Apple', 'Smartphone', 'Buono', 2017),
('iPhone X', 3, 5.8, 64, 269, 10, 'Oro rosa', 'Apple', 'Smartphone', 'Buono', 2017),
('iPhone X', 3, 5.8, 64, 289, 10, 'Argento', 'Apple', 'Smartphone', 'Ottimo', 2017),
('iPhone X', 3, 5.8, 256, 319, 10, 'Oro rosa', 'Apple', 'Smartphone', 'Buono', 2017),
('iPhone X', 3, 5.8, 256, 319, 10, 'Argento', 'Apple', 'Smartphone', 'Ottimo', 2017),
('iPhone X', 3, 5.8, 512, 319, 10, 'Oro rosa', 'Apple', 'Smartphone', 'Buono', 2017),
('iPhone X', 3, 5.8, 512, 319, 10, 'Argento', 'Apple', 'Smartphone', 'Ottimo', 2017),
('iPhone X', 3, 5.8, 64, 289, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2017),
('iPhone X', 3, 5.8, 64, 289, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2017),
('iPhone X', 3, 5.8, 256, 319, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2017),
('iPhone X', 3, 5.8, 256, 329, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2017),
('iPhone X', 3, 5.8, 512, 319, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2017),
('iPhone XS', 4, 5.8, 64, 289, 10, 'Argento', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XS', 4, 5.8, 64, 289, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XS', 4, 5.8, 256, 359, 10, 'Argento', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XS', 4, 5.8, 256, 359, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XS', 4, 5.8, 512, 389, 10, 'Argento', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XS', 4, 5.8, 512, 389, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XS', 4, 5.8, 64, 289, 10, 'Oro rosa', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XS', 4, 5.8, 64, 289, 10, 'Argento', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XS', 4, 5.8, 256, 359, 10, 'Oro rosa', 'Apple', 'Smartphone', 'Buono', 2018),
('iPhone XS', 4, 5.8, 256, 359, 10, 'Argento', 'Apple', 'Smartphone', 'Ottimo', 2018),
('iPhone XS', 4, 5.8, 512, 389, 10, 'Oro rosa', 'Apple', 'Smartphone', 'Buono', 2018),

('iPhone 11', 4, 6.1, 64, 399, 10, 'Nero', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11', 4, 6.1, 64, 399, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2019),
('iPhone 11', 4, 6.1, 128, 449, 10, 'Bianco', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11', 4, 6.1, 128, 449, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2019),
('iPhone 11', 4, 6.1, 256, 549, 10, 'Verde', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11', 4, 6.1, 256, 549, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2019),
('iPhone 11', 4, 6.1, 512, 609, 10, 'Giallo', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11', 4, 6.1, 512, 609, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2019),
('iPhone 11 Pro', 6, 5.8, 64, 599, 10, 'Verde Notte', 'Apple', 'Smartphone', 'Buono', 2019),
('iPhone 11 Pro', 6, 5.8, 64, 629, 10, 'Nero', 'Apple', 'Smartphone', 'Ottimo', 2019),
('iPhone 11 Pro', 6, 5.8, 128, 659, 10, 'Argento', 'Apple', 'Smartphone', 'Buono', 2019);




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
