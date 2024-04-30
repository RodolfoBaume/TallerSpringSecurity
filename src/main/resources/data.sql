-- estatus servicios
INSERT INTO estatus_servicio(estatus_servicio)
SELECT 'Nuevo' WHERE NOT EXISTS (SELECT * FROM estatus_servicio WHERE estatus_servicio = 'Nuevo');

INSERT INTO estatus_servicio(estatus_servicio)
SELECT 'Pendiente' WHERE NOT EXISTS (SELECT * FROM estatus_servicio WHERE estatus_servicio = 'Pendiente');

INSERT INTO estatus_servicio(estatus_servicio)
SELECT 'Progreso' WHERE NOT EXISTS (SELECT * FROM estatus_servicio WHERE estatus_servicio = 'Progreso');

INSERT INTO estatus_servicio(estatus_servicio)
SELECT 'Terminado' WHERE NOT EXISTS (SELECT * FROM estatus_servicio WHERE estatus_servicio = 'Terminado');

INSERT INTO estatus_servicio(estatus_servicio)
SELECT 'Entregado' WHERE NOT EXISTS (SELECT * FROM estatus_servicio WHERE estatus_servicio = 'Entregado');

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

