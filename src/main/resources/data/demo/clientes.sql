-- Insertar Clientes

-- Insertar en la tabla usuarios Cliente 1--------------------------
INSERT INTO usuarios (username, password)
SELECT 'customer@server.com', '$2a$10$eQP1r4ROrddEIn1y6/mUs.rUEFgda74HyNFyY1xtBQoCLfUlhsqWy'
WHERE NOT EXISTS (SELECT * FROM usuarios WHERE username = 'customer@server.com');

-- Insertar en la tabla usuarios_roles si la combinación usuario_id y rol_id no existe
INSERT INTO usuarios_roles (usuario_id, rol_id) 
SELECT 3, 3 
WHERE NOT EXISTS (
    SELECT * 
    FROM usuarios_roles 
    WHERE usuario_id = 3
    AND rol_id = 3
);

-- Insertar en la tabla empleados asociando el usuario al empleado correspondiente si no hay duplicados
INSERT INTO clientes (nombre, apellido_paterno, apellido_materno, domicilio, telefono, usuario_id)
SELECT 'juan', 'perez', 'lopez', 'desconocido', '6861554433', 3
WHERE NOT EXISTS (
    SELECT *
    FROM clientes
    WHERE usuario_id = 3
);



-- Insertar en la tabla usuarios Cliente 2--------------------------
INSERT INTO usuarios (username, password)
SELECT 'pedro@server.com',  '$2a$10$eQP1r4ROrddEIn1y6/mUs.rUEFgda74HyNFyY1xtBQoCLfUlhsqWy'
WHERE NOT EXISTS (SELECT * FROM usuarios WHERE username = 'pedro@server.com');

-- Insertar en la tabla usuarios_roles si la combinación usuario_id y rol_id no existe
INSERT INTO usuarios_roles (usuario_id, rol_id) 
SELECT 4, 3 
WHERE NOT EXISTS (
    SELECT * 
    FROM usuarios_roles 
    WHERE usuario_id = 4
    AND rol_id = 3
);

-- Insertar en la tabla empleados asociando el usuario al empleado correspondiente si no hay duplicados
INSERT INTO clientes (nombre, apellido_paterno, apellido_materno, domicilio, telefono, usuario_id)
SELECT 'pedro', 'martinez', 'lopez', 'desconocido', '6861557788', 4
WHERE NOT EXISTS (
    SELECT *
    FROM clientes
    WHERE usuario_id = 4
);



-- Insertar en la tabla usuarios Cliente 3--------------------------
INSERT INTO usuarios (username, password)
SELECT 'tom@server.com',  '$2a$10$eQP1r4ROrddEIn1y6/mUs.rUEFgda74HyNFyY1xtBQoCLfUlhsqWy'
WHERE NOT EXISTS (SELECT * FROM usuarios WHERE username = 'tom@server.com');

-- Insertar en la tabla empleados asociando el usuario al empleado correspondiente si no hay duplicados
INSERT INTO clientes (nombre, apellido_paterno, apellido_materno, domicilio, telefono, usuario_id)
SELECT 'tomas', 'alvarez', 'reyes', 'av la joya 2291', '6648908473', 5
WHERE NOT EXISTS (
    SELECT *
    FROM clientes
    WHERE usuario_id = 5
);


-- Insertar en la tabla usuarios_roles si la combinación usuario_id y rol_id no existe
INSERT INTO usuarios_roles (usuario_id, rol_id) 
SELECT 5, 3 
WHERE NOT EXISTS (
    SELECT * 
    FROM usuarios_roles 
    WHERE usuario_id = 5
    AND rol_id = 3
);


-- Insertar en la tabla usuarios Cliente 4--------------------------
INSERT INTO usuarios (username, password)
SELECT 'ignacio@server.com',  '$2a$10$eQP1r4ROrddEIn1y6/mUs.rUEFgda74HyNFyY1xtBQoCLfUlhsqWy'
WHERE NOT EXISTS (SELECT * FROM usuarios WHERE username = 'ignacio@server.com');

-- Insertar en la tabla empleados asociando el usuario al empleado correspondiente si no hay duplicados
INSERT INTO clientes (nombre, apellido_paterno, apellido_materno, domicilio, telefono, usuario_id)
SELECT 'ignacio', 'lopez', 'tarzo', 'polanco', '6642658899', 6
WHERE NOT EXISTS (
    SELECT *
    FROM clientes
    WHERE usuario_id = 6
);


-- Insertar en la tabla usuarios_roles si la combinación usuario_id y rol_id no existe
INSERT INTO usuarios_roles (usuario_id, rol_id) 
SELECT 6, 3 
WHERE NOT EXISTS (
    SELECT * 
    FROM usuarios_roles 
    WHERE usuario_id = 6
    AND rol_id = 3
);


-- Insertar en la tabla usuarios Cliente 5--------------------------
INSERT INTO usuarios (username, password)
SELECT 'carolg@server.com',  '$2a$10$eQP1r4ROrddEIn1y6/mUs.rUEFgda74HyNFyY1xtBQoCLfUlhsqWy'
WHERE NOT EXISTS (SELECT * FROM usuarios WHERE username = 'carolg@server.com');

-- Insertar en la tabla usuarios_roles si la combinación usuario_id y rol_id no existe
INSERT INTO usuarios_roles (usuario_id, rol_id) 
SELECT 7, 3 
WHERE NOT EXISTS (
    SELECT * 
    FROM usuarios_roles 
    WHERE usuario_id = 7
    AND rol_id = 3
);

-- Insertar en la tabla empleados asociando el usuario al empleado correspondiente si no hay duplicados
INSERT INTO clientes (nombre, apellido_paterno, apellido_materno, domicilio, telefono, usuario_id)
SELECT 'carolina', 'giraldo', 'navarro', 'pedergal', '6643459204', 7
WHERE NOT EXISTS (
    SELECT *
    FROM clientes
    WHERE usuario_id = 7
);


-- Insertar en la tabla usuarios Cliente 6--------------------------
INSERT INTO usuarios (username, password)
SELECT 'jose@server.com',  '$2a$10$eQP1r4ROrddEIn1y6/mUs.rUEFgda74HyNFyY1xtBQoCLfUlhsqWy'
WHERE NOT EXISTS (SELECT * FROM usuarios WHERE username = 'jose@server.com');

-- Insertar en la tabla usuarios_roles si la combinación usuario_id y rol_id no existe
INSERT INTO usuarios_roles (usuario_id, rol_id) 
SELECT 8, 3 
WHERE NOT EXISTS (
    SELECT * 
    FROM usuarios_roles 
    WHERE usuario_id = 8
    AND rol_id = 3
);

-- Insertar en la tabla empleados asociando el usuario al empleado correspondiente si no hay duplicados
INSERT INTO clientes (nombre, apellido_paterno, apellido_materno, domicilio, telefono, usuario_id)
SELECT 'jose', 'fernandez', 'librado', 'el pipila #1234', '6648762320', 8
WHERE NOT EXISTS (
    SELECT *
    FROM clientes
    WHERE usuario_id = 8
);


