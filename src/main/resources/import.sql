INSERT INTO pizzas (`name`, description, photo, price) VALUES('Margherita', 'Polpa di pomodoro 500g, Fiordilatte 400g, Origano secco 1 cucchiaio, Sale fino 1 cucchiaino, Basilico q.b., Olio extravergine q.b.', 'https://th.bing.com/th/id/OIP.PKwqaxn-zpJpjHorZJv2aQAAAA?rs=1&pid=ImgDetMain', '5');
INSERT INTO pizzas (`name`, description, photo, price) VALUES('Capricciosa', 'Polpa di pomodoro 300g, 1 Mozzarella, Carciofini, Funghetti, Olive nere, Prosciutto cotto 100g', 'https://primochef.it/wp-content/uploads/2018/05/SH_pizza_capricciosa-768x512.jpg', '4');


INSERT INTO offers (pizza_id, start_date, expire_date, title) VALUES(1, '2023-01-20', '2024-12-20', 'Margherita');
INSERT INTO offers (pizza_id, start_date, expire_date, title) VALUES(1, '2023-11-10', '2024-10-10', 'Marinara');