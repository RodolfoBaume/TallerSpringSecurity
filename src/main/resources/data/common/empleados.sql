-- Insertar usuarios

-- Insertar en la tabla usuarios
INSERT INTO usuarios (username, password)
SELECT 'usuario1@gmail.com', '$2a$10$kkenwzglHNjwVKEfwg/fC.iM1ySyVuAmra65rBQZzk.ue.RkEBgp2'
WHERE NOT EXISTS (SELECT * FROM usuarios WHERE username = 'usuario1@gmail.com');

-- Insertar en la tabla usuarios_roles si la combinación usuario_id y rol_id no existe
INSERT INTO usuarios_roles (usuario_id, rol_id) 
SELECT 1, 1 
WHERE NOT EXISTS (
    SELECT * 
    FROM usuarios_roles 
    WHERE usuario_id = 1
    AND rol_id = 1
);

-- Insertar en la tabla empleados asociando el usuario al empleado correspondiente si no hay duplicados
INSERT INTO empleados (nombre, apellido_paterno, apellido_materno, nss, curp, rfc, puesto, observaciones, usuario_id)
SELECT 'Empleado1', 'ApellidoPaternoEmpleado', 'ApellidoMaternoEmpleado', 1234567890, 'CurpEmpleado', 'RfcEmpleado', 'PuestoEmpleado', 'ObservacionesEmpleado', 1
WHERE NOT EXISTS (
    SELECT *
    FROM empleados
    WHERE usuario_id = 1
);

-- Insertar en la tabla usuarios Empleado 2
INSERT INTO usuarios (username, password)
SELECT 'admin@server.com', '$2a$10$eQP1r4ROrddEIn1y6/mUs.rUEFgda74HyNFyY1xtBQoCLfUlhsqWy'
WHERE NOT EXISTS (SELECT * FROM usuarios WHERE username = 'admin@server.com');

-- Insertar en la tabla usuarios_roles si la combinación usuario_id y rol_id no existe
INSERT INTO usuarios_roles (usuario_id, rol_id) 
SELECT 2, 1 
WHERE NOT EXISTS (
    SELECT * 
    FROM usuarios_roles 
    WHERE usuario_id = 2
    AND rol_id = 1
);

-- Insertar en la tabla empleados asociando el usuario al empleado correspondiente si no hay duplicados
INSERT INTO empleados (nombre, apellido_paterno, apellido_materno, nss, curp, rfc, puesto, observaciones, usuario_id)
SELECT 'Adminstrador', 'ApellidoPaternoEmpleado', 'ApellidoMaternoEmpleado', 1234567890, 'CurpEmpleado', 'RfcEmpleado', 'PuestoEmpleado', 'ObservacionesEmpleado', 2
WHERE NOT EXISTS (
    SELECT *
    FROM empleados
    WHERE usuario_id = 2
);

