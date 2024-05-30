-- estatus servicios


-- INSERT INTO estatus_servicio(estatus_servicio)
-- SELECT 'nuevo' WHERE NOT EXISTS (SELECT * FROM estatus_servicio WHERE estatus_servicio = 'nuevo');

-- INSERT INTO estatus_servicio(estatus_servicio)
-- SELECT 'llamar' WHERE NOT EXISTS (SELECT * FROM estatus_servicio WHERE estatus_servicio = 'llamar');

-- INSERT INTO estatus_servicio(estatus_servicio)
-- SELECT 'para entregar' WHERE NOT EXISTS (SELECT * FROM estatus_servicio WHERE estatus_servicio = 'para entregar');

-- INSERT INTO estatus_servicio(estatus_servicio)
-- SELECT 'mas de 1 mes' WHERE NOT EXISTS (SELECT * FROM estatus_servicio WHERE estatus_servicio = 'mas de 1 mes');

-- INSERT INTO estatus_servicio(estatus_servicio)
-- SELECT 'entregado' WHERE NOT EXISTS (SELECT * FROM estatus_servicio WHERE estatus_servicio = 'entregado');




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

