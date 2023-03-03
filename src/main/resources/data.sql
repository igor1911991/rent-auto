INSERT INTO roles(role_name)
VALUES ('ROLE_ADMIN'), ('ROLE_CUSTOMER');

INSERT INTO users(username, email, password, phone, drivers_license_number)
VALUES
('admin', 'admin@gmail.com', '$2a$12$Pb5YIfGrpIF7VixBh50NRuaZyqHaeML2uTm.KNJRxOvobdjsTmKiS', 89997005058, '11AA111111'),
('user', 'user2000@gmail.com', '$2a$12$51HhybnMdbverOuX46SMCuneXM4HkHgwspVOkuX.Pajk/SD/6rSbi', 89252005757, '22BB222222');

INSERT INTO users_roles (user_id, role_id)
VALUES
(1, 1),
(2, 2);

INSERT INTO autos (model, engine_power, speed, available, order_num)
VALUES
('niva', 120, 140, true, 0),
('bmw', 300, 280, true, 0),
('ford', 160, 220, false, 0),
('hyundai', 123, 170, true, 0),
('audi', 200, 240, false, 0);