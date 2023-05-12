DROP DATABASE IF EXISTS storage;
CREATE DATABASE storage;
USE storage;

DROP TABLE IF EXISTS product;

CREATE TABLE product (	
  code int primary key AUTO_INCREMENT,
  name char(20) not null,
  description char(100),
  price int default 0,
  quantity int default 0
);

INSERT INTO product values (1,"Samsung F8000","TV 48 pollici",550,5);
INSERT INTO product values (2,"Huawei P8","Smartphone",390,13);
INSERT INTO product values (3,"Onkyo SR 646","Receiver",510,4);
INSERT INTO product values (4,"Sony w808c","TV 43 pollici",640,11);
INSERT INTO product values (5,"Dyson 6300","Aspirapolvere",329,3);
INSERT INTO product values (6,"Asus 3200","Router",189,22);
