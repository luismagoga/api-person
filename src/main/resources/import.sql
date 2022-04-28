insert into person (id, dni, nombre, fecha_nacimiento) values (1, '49063286P', 'Luis Manuel', '1990-08-04');
insert into person (id, dni, nombre, fecha_nacimiento) values (2, '68236094P', 'Manuel Luis', '1990-08-05');
insert into vehiculo_person(id, marca, modelo, person_id) values (1, 'Opel', 'Astra', 1);

INSERT INTO user (id, name, password) VALUES (1, 'admin', '$2a$04$GJ85Ihcglhbqac2zc3z3A.C3v55FMmN8.qGQ8FlNBCuyLtQ5/TyMO');
INSERT INTO user (id, name, password) VALUES (2, 'paco', '$2a$04$GJ85Ihcglhbqac2zc3z3A.C3v55FMmN8.qGQ8FlNBCuyLtQ5/TyMO');
INSERT INTO user (id, name, password, person_id) VALUES (3, '49063286P', '$2a$04$GJ85Ihcglhbqac2zc3z3A.C3v55FMmN8.qGQ8FlNBCuyLtQ5/TyMO', 1);
INSERT INTO user (id, name, password, person_id) VALUES (4, '68236094P', '$2a$04$GJ85Ihcglhbqac2zc3z3A.C3v55FMmN8.qGQ8FlNBCuyLtQ5/TyMO', 2);

INSERT INTO role (id, description, name) VALUES (1, 'Admin role', 'ADMIN');
INSERT INTO role (id, description, name) VALUES (2, 'Comercial role', 'COMERCIAL');
INSERT INTO role (id, description, name) VALUES (3, 'Client role', 'CLIENT');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (1, 3);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
INSERT INTO user_roles (user_id, role_id) VALUES (3, 3);
INSERT INTO user_roles (user_id, role_id) VALUES (4, 3);

insert into vehiculo_stock(id, marca, modelo, unidades) values (1, 'Opel', 'Astra', 9);
insert into vehiculo_stock(id, marca, modelo, unidades) values (2, 'Audi', 'A4', 10);
insert into vehiculo_stock(id, marca, modelo, unidades) values (3, 'Renault', 'Megane', 10);
insert into vehiculo_stock(id, marca, modelo, unidades) values (4, 'Seat', 'Ibiza', 10);
insert into vehiculo_stock(id, marca, modelo, unidades) values (5, 'Peugeot', '308', 10);