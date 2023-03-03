DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS autos;
DROP TABLE IF EXISTS users_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;


CREATE TABLE autos (
    id SERIAL PRIMARY KEY,
	model VARCHAR(50) NOT NULL,
	engine_power INTEGER NOT NULL,
	speed INTEGER NOT NULL,
	available BOOLEAN,
	order_num BIGINT
);

CREATE TABLE users (
	id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
	email VARCHAR(30) NOT NULL,
    password VARCHAR(100) NOT NULL,
	phone VARCHAR(20) NOT NULL,
	drivers_license_number VARCHAR(10)
);

CREATE TABLE roles (
    id SERIAL PRIMARY KEY,
    role_name VARCHAR(50)
);

CREATE TABLE  users_roles (
    user_id BIGINT,
    role_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id),
    UNIQUE (user_id, role_id)
);

CREATE TABLE orders (
	id SERIAL PRIMARY KEY,
	users BIGINT NOT NULL,
	auto BIGINT NOT NULL,
	order_date TIMESTAMP NOT NULL,
	status_order VARCHAR(15) NOT NULL,
	FOREIGN KEY (users) REFERENCES users(id) ON DELETE RESTRICT,
	FOREIGN KEY (auto) REFERENCES autos(id) ON DELETE RESTRICT
);