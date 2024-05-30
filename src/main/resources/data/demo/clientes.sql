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
    FROM empleados
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
SELECT 'pedro', 'martinez', 'lopez', 'desconocido', '6861557788', 3
WHERE NOT EXISTS (
    SELECT *
    FROM empleados
    WHERE usuario_id = 4
);


