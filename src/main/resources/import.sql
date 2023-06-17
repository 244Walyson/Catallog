INSERT INTO tb_category (name) VALUES ('Eletronicos');
INSERT INTO tb_category (name) VALUES ('Roupas');
INSERT INTO tb_category (name) VALUES ('Acessórios');
INSERT INTO tb_category (name) VALUES ('Alimentos');
INSERT INTO tb_category (name) VALUES ('Cosméticos');

INSERT INTO tb_product (name, price, description, img_url) VALUES ('PC gamer', 2300.00, 'Computador gamer de última geração', 'http://pcimg.com');
INSERT INTO tb_product (name, price, description, img_url) VALUES ('Camiseta', 49.90, 'Camiseta básica de algodão', 'http://camisetaimg.com');
INSERT INTO tb_product (name, price, description, img_url) VALUES ('Colar', 99.90, 'Colar de prata com pingente', 'http://colarimg.com');
INSERT INTO tb_product (name, price, description, img_url) VALUES ('Biscoitos', 5.99, 'Pacote de biscoitos crocantes', 'http://biscoitosimg.com');
INSERT INTO tb_product (name, price, description, img_url) VALUES ('Shampoo', 19.90, 'Shampoo para cabelos hidratados', 'http://shampooimg.com');
INSERT INTO tb_product (name, price, description, img_url) VALUES ('Tênis', 199.90, 'Tênis esportivo para corrida', 'http://tenisimg.com');
INSERT INTO tb_product (name, price, description, img_url) VALUES ('Caneta', 2.50, 'Caneta esferográfica preta', 'http://canetaimg.com');
INSERT INTO tb_product (name, price, description, img_url) VALUES ('Chocolate', 3.99, 'Barra de chocolate ao leite', 'http://chocolateimg.com');
INSERT INTO tb_product (name, price, description, img_url) VALUES ('Batom', 29.90, 'Batom de longa duração', 'http://batomimg.com');
INSERT INTO tb_product (name, price, description, img_url) VALUES ('Calça Jeans', 129.90, 'Calça jeans masculina', 'http://calcaimg.com');

INSERT INTO tb_product_category (product_id, category_id) VALUES (1, 1);
INSERT INTO tb_product_category (product_id, category_id) VALUES (2, 2);
INSERT INTO tb_product_category (product_id, category_id) VALUES (3, 3);
INSERT INTO tb_product_category (product_id, category_id) VALUES (4, 4);
INSERT INTO tb_product_category (product_id, category_id) VALUES (5, 5);
INSERT INTO tb_product_category (product_id, category_id) VALUES (6, 1);
INSERT INTO tb_product_category (product_id, category_id) VALUES (7, 2);
INSERT INTO tb_product_category (product_id, category_id) VALUES (8, 3);
INSERT INTO tb_product_category (product_id, category_id) VALUES (9, 4);
INSERT INTO tb_product_category (product_id, category_id) VALUES (10, 5);
INSERT INTO tb_product_category (product_id, category_id) VALUES (1, 3);
INSERT INTO tb_product_category (product_id, category_id) VALUES (2, 4);

INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN');
INSERT INTO tb_role (authority) VALUES ('ROLE_OPERATOR');

INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('alex', 'braba', 'alex@gmail.com', '');
INSERT INTO tb_user (first_name, last_name, email, password) VALUES ('maria', 'braba', 'maria@gmail.com', '');

INSERT INTO tb_user_role (user_id, role_id) VALUES (1,2);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2,1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2,2);

