-- GLI STATEMENT NON DEVONO ANDARE
-- INSERT PIZZA
INSERT INTO pizzas (`name`, description, photo, price) VALUES('Margherita', 'Polpa di pomodoro 500g, Fiordilatte 400g, Origano secco 1 cucchiaio, Sale fino 1 cucchiaino, Basilico q.b., Olio extravergine q.b.', 'https://th.bing.com/th/id/R.5151e06852a675b1e3db05458506356e?rik=%2bV%2fHNBlRXomGbw&pid=ImgRaw&r=0', '5');
INSERT INTO pizzas (`name`, description, photo, price) VALUES('Capricciosa', 'Polpa di pomodoro 300g, 1 Mozzarella, Carciofini, Funghetti, Olive nere, Prosciutto cotto 100g', 'https://primochef.it/wp-content/uploads/2018/05/SH_pizza_capricciosa-768x512.jpg', '4');

-- INSERT OFFERTA
INSERT INTO offers (pizza_id, start_date, expire_date, title) VALUES(1, '2023-01-20', '2024-12-20', 'Offerta Estate');
INSERT INTO offers (pizza_id, start_date, expire_date, title) VALUES(1, '2023-11-10', '2024-10-10', 'Offerta Inverno');

-- INSERT INGREDIENTI
INSERT INTO ingredients (name) VALUES ('Mozzarella');
INSERT INTO ingredients (name) VALUES ('Prosciutto Cotto');