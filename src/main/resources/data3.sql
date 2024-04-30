-- Insertar usuarios

-- Insertar en la tabla usuarios
INSERT INTO usuarios (email, password)
SELECT 'usuario1@gmail.com', '12345'
WHERE NOT EXISTS (SELECT * FROM usuarios WHERE email = 'usuario1@gmail.com');

-- Insertar en la tabla usuarios_roles si la combinación usuario_id y rol_id no existe
INSERT INTO usuarios_roles (usuario_id, rol_id) 
SELECT currval(pg_get_serial_sequence('usuarios', 'id_usuario')), 1 
WHERE NOT EXISTS (
    SELECT * 
    FROM usuarios_roles 
    WHERE usuario_id = currval(pg_get_serial_sequence('usuarios', 'id_usuario')) 
    AND rol_id = 1
);

-- Insertar en la tabla empleados asociando el usuario al empleado correspondiente si no hay duplicados
INSERT INTO empleados (nombre, apellido_paterno, apellido_materno, nss, curp, rfc, puesto, observaciones, usuario_id)
SELECT 'Empleado1', 'ApellidoPaternoEmpleado', 'ApellidoMaternoEmpleado', 1234567890, 'CurpEmpleado', 'RfcEmpleado', 'PuestoEmpleado', 'ObservacionesEmpleado', currval(pg_get_serial_sequence('usuarios', 'id_usuario'))
WHERE NOT EXISTS (
    SELECT *
    FROM empleados
    WHERE usuario_id = currval(pg_get_serial_sequence('usuarios', 'id_usuario'))
);

