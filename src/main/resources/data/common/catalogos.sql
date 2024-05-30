
-- Tipo Motor
INSERT INTO tipo_motor(tipo_motor)
SELECT 'Gasolina' WHERE NOT EXISTS (SELECT * FROM tipo_motor WHERE tipo_motor = 'Gasolina');

INSERT INTO tipo_motor(tipo_motor)
SELECT 'Diesel' WHERE NOT EXISTS (SELECT * FROM tipo_motor WHERE tipo_motor = 'Diesel');

INSERT INTO tipo_motor(tipo_motor)
SELECT 'Eléctrico' WHERE NOT EXISTS (SELECT * FROM tipo_motor WHERE tipo_motor = 'Eléctrico');

INSERT INTO tipo_motor(tipo_motor)
SELECT 'Hibrido' WHERE NOT EXISTS (SELECT * FROM tipo_motor WHERE tipo_motor = 'Hibrido');


-- tabla roles
INSERT INTO roles(nombre)
SELECT 'ADMIN' WHERE NOT EXISTS (SELECT * FROM roles WHERE nombre = 'ADMIN');

INSERT INTO roles(nombre)
SELECT 'EMPLEADO' WHERE NOT EXISTS (SELECT * FROM roles WHERE nombre = 'EMPLEADO');

INSERT INTO roles(nombre)
SELECT 'CLIENTE' WHERE NOT EXISTS (SELECT * FROM roles WHERE nombre = 'CLIENTE');

