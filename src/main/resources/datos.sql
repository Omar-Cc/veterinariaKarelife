-- Crear la base de datos
DROP DATABASE IF EXISTS clinicaveterinaria;

CREATE DATABASE IF NOT EXISTS clinicaveterinaria;
USE clinicaveterinaria;

-- Crear tablas
CREATE TABLE Categorias (
    categoria_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE Estados (
    estado_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_estado VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE EstadoCategoria (
    estado_categoria_id INT AUTO_INCREMENT PRIMARY KEY,
    estado_id INT,
    categoria_id INT,
    FOREIGN KEY (estado_id) REFERENCES Estados(estado_id),
    FOREIGN KEY (categoria_id) REFERENCES Categorias(categoria_id)
);

CREATE TABLE Especies (
    especie_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_especie VARCHAR(50) NOT NULL UNIQUE,
    estado_id INT DEFAULT 1,
    FOREIGN KEY (estado_id) REFERENCES Estados(estado_id)
);

CREATE TABLE Razas (
    raza_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_raza VARCHAR(50) NOT NULL UNIQUE,
    especie_id INT,
    estado_id INT DEFAULT 1,
    FOREIGN KEY (especie_id) REFERENCES Especies(especie_id),
    FOREIGN KEY (estado_id) REFERENCES Estados(estado_id)
);

CREATE TABLE Clientes (
    cliente_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    telefono VARCHAR(20) NOT NULL,
    direccion VARCHAR(255),
    fecha_registro DATE,
    estado_id INT DEFAULT 1,
    FOREIGN KEY (estado_id) REFERENCES Estados(estado_id)
);

CREATE TABLE Mascotas (
    mascota_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    especie_id INT,
    raza_id INT,
    fecha_nacimiento DATE,
    cliente_id INT,
    estado_id INT DEFAULT 1,
    FOREIGN KEY (especie_id) REFERENCES Especies(especie_id),
    FOREIGN KEY (raza_id) REFERENCES Razas(raza_id),
    FOREIGN KEY (cliente_id) REFERENCES Clientes(cliente_id),
    FOREIGN KEY (estado_id) REFERENCES Estados(estado_id)
);

CREATE TABLE Servicios (
    servicio_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL,
    estado_id INT DEFAULT 1,
    FOREIGN KEY (estado_id) REFERENCES Estados(estado_id)
);

CREATE TABLE Citas (
    cita_id INT AUTO_INCREMENT PRIMARY KEY,
    fecha_cita DATETIME NOT NULL,
    mascota_id INT,
    servicio_id INT,
    cliente_id INT,
    estado_id INT DEFAULT 3,
    FOREIGN KEY (mascota_id) REFERENCES Mascotas(mascota_id),
    FOREIGN KEY (servicio_id) REFERENCES Servicios(servicio_id),
    FOREIGN KEY (cliente_id) REFERENCES Clientes(cliente_id),
    FOREIGN KEY (estado_id) REFERENCES Estados(estado_id)
);

CREATE TABLE Productos (
    producto_id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DECIMAL(10, 2) NOT NULL,
    stock INT DEFAULT 0,
    estado_id INT DEFAULT 8,
    FOREIGN KEY (estado_id) REFERENCES Estados(estado_id)
);

CREATE TABLE Pedidos (
    pedido_id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT,
    fecha_pedido DATE,
    total DECIMAL(10, 2),
    estado_id INT DEFAULT 3,
    FOREIGN KEY (cliente_id) REFERENCES Clientes(cliente_id),
    FOREIGN KEY (estado_id) REFERENCES Estados(estado_id)
);

CREATE TABLE DetallesPedidos (
    detalle_id INT AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT,
    producto_id INT,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES Pedidos(pedido_id),
    FOREIGN KEY (producto_id) REFERENCES Productos(producto_id)
);

CREATE TABLE Consejos (
    consejo_id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    contenido TEXT,
    especie_id INT,
    raza_id INT,
    fecha_publicacion DATE,
    estado_id INT DEFAULT 1,
    FOREIGN KEY (especie_id) REFERENCES Especies(especie_id),
    FOREIGN KEY (raza_id) REFERENCES Razas(raza_id),
    FOREIGN KEY (estado_id) REFERENCES Estados(estado_id)
);

CREATE TABLE Adopciones (
    adopcion_id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT,
    mascota_id INT,
    estado_id INT DEFAULT 11,
    fecha_publicacion DATE,
    fecha_adopcion DATE,
    FOREIGN KEY (cliente_id) REFERENCES Clientes(cliente_id),
    FOREIGN KEY (mascota_id) REFERENCES Mascotas(mascota_id),
    FOREIGN KEY (estado_id) REFERENCES Estados(estado_id)
);

CREATE TABLE Cuidados (
    cuidado_id INT AUTO_INCREMENT PRIMARY KEY,
    especie_id INT NOT NULL,
    raza_id INT,
    consejos TEXT,
    recomendaciones_especificas TEXT,
    estado_id INT DEFAULT 1,
    FOREIGN KEY (especie_id) REFERENCES Especies(especie_id),
    FOREIGN KEY (raza_id) REFERENCES Razas(raza_id),
    FOREIGN KEY (estado_id) REFERENCES Estados(estado_id)
);

-- Procedimientos almacenados
DELIMITER $$

-- Especies
CREATE PROCEDURE usp_Especies(
    IN Accion VARCHAR(10),
    IN especie_id INT,
    IN nombre_especie VARCHAR(50),
    IN estado_id INT
)
BEGIN
    IF Accion = 'Insert' THEN
        INSERT INTO Especies (nombre_especie, estado_id)
        VALUES (nombre_especie, IFNULL(estado_id, 1));
    ELSEIF Accion = 'Select' THEN
        SELECT * FROM Especies;
    ELSEIF Accion = 'Update' THEN
        UPDATE Especies
        SET nombre_especie = nombre_especie,
            estado_id = IFNULL(estado_id, estado_id)
        WHERE especie_id = especie_id;
    ELSEIF Accion = 'Status' THEN
        UPDATE Especies
        SET estado_id = estado_id
        WHERE especie_id = especie_id;
    END IF;
END $$

-- Razas
CREATE PROCEDURE usp_Razas(
    IN Accion VARCHAR(10),
    IN raza_id INT,
    IN nombre_raza VARCHAR(50),
    IN especie_id INT,
    IN estado_id INT
)
BEGIN
    IF Accion = 'Insert' THEN
        INSERT INTO Razas (nombre_raza, especie_id, estado_id)
        VALUES (nombre_raza, especie_id, IFNULL(estado_id, 1));
    ELSEIF Accion = 'Update' THEN
        UPDATE Razas
        SET nombre_raza = nombre_raza,
            especie_id = especie_id,
            estado_id = IFNULL(estado_id, estado_id)
        WHERE raza_id = raza_id;
    ELSEIF Accion = 'Select' THEN
        SELECT * FROM Razas;
    ELSEIF Accion = 'Status' THEN
        UPDATE Razas
        SET estado_id = estado_id
        WHERE raza_id = raza_id;
    END IF;
END $$

-- Stored Procedure de Clientes
CREATE PROCEDURE usp_Clientes(
    IN Accion VARCHAR(10),
    IN cliente_id INT,
    IN nombre VARCHAR(100),
    IN email VARCHAR(100),
    IN telefono VARCHAR(20),
    IN direccion VARCHAR(255),
    IN estado_id INT
)
BEGIN
    IF Accion = 'Insert' THEN
        INSERT INTO Clientes (nombre, email, telefono, direccion, estado_id)
        VALUES (nombre, email, telefono, direccion, COALESCE(estado_id, 1)); -- Por defecto, estado "Habilitado"
    ELSEIF Accion = 'Update' THEN
        UPDATE Clientes
        SET nombre = nombre,
            email = email,
            telefono = telefono,
            direccion = direccion,
            estado_id = COALESCE(estado_id, estado_id)
        WHERE cliente_id = cliente_id;
    ELSEIF Accion = 'Select' THEN
        SELECT * FROM Clientes;
    ELSEIF Accion = 'Status' THEN
        UPDATE Clientes
        SET estado_id = estado_id
        WHERE cliente_id = cliente_id;
    END IF;
END $$

-- Stored Procedure de Mascotas
CREATE PROCEDURE usp_Mascotas(
    IN Accion VARCHAR(10),
    IN mascota_id INT,
    IN nombre VARCHAR(100),
    IN especie_id INT,
    IN raza_id INT,
    IN fecha_nacimiento DATE,
    IN cliente_id INT,
    IN estado_id INT
)
BEGIN
    IF Accion = 'Insert' THEN
        INSERT INTO Mascotas (nombre, especie_id, raza_id, fecha_nacimiento, cliente_id, estado_id)
        VALUES (nombre, especie_id, raza_id, fecha_nacimiento, cliente_id, COALESCE(estado_id, 1)); -- Por defecto, estado "Habilitado"
    ELSEIF Accion = 'Update' THEN
        UPDATE Mascotas
        SET nombre = nombre,
            especie_id = especie_id,
            raza_id = raza_id,
            fecha_nacimiento = fecha_nacimiento,
            cliente_id = cliente_id,
            estado_id = COALESCE(estado_id, estado_id)
        WHERE mascota_id = mascota_id;
    ELSEIF Accion = 'Select' THEN
        SELECT * FROM Mascotas;
    ELSEIF Accion = 'Status' THEN
        UPDATE Mascotas
        SET estado_id = estado_id
        WHERE mascota_id = mascota_id;
    END IF;
END $$

-- Stored Procedure de Servicios
CREATE PROCEDURE usp_Servicios(
    IN Accion VARCHAR(10),
    IN servicio_id INT,
    IN nombre VARCHAR(100),
    IN descripcion TEXT,
    IN precio DECIMAL(10, 2),
    IN estado_id INT
)
BEGIN
    IF Accion = 'Insert' THEN
        INSERT INTO Servicios (nombre, descripcion, precio, estado_id)
        VALUES (nombre, descripcion, precio, COALESCE(estado_id, 1)); -- Por defecto, estado "Habilitado"
    ELSEIF Accion = 'Update' THEN
        UPDATE Servicios
        SET nombre = nombre,
            descripcion = descripcion,
            precio = precio,
            estado_id = COALESCE(estado_id, estado_id)
        WHERE servicio_id = servicio_id;
    ELSEIF Accion = 'Select' THEN
        SELECT s.servicio_id, s.nombre, s.descripcion, s.precio, e.estado_id, e.nombre_estado 
        FROM servicios s
        JOIN Estados e ON s.estado_id = e.estado_id;
    ELSEIF Accion = 'Status' THEN
        UPDATE Servicios
        SET estado_id = estado_id
        WHERE servicio_id = servicio_id;
    END IF;
END $$

-- Stored Procedure de Citas
CREATE PROCEDURE usp_Citas(
    IN Accion VARCHAR(10),
    IN cita_id INT,
    IN fecha_cita DATETIME,
    IN mascota_id INT,
    IN servicio_id INT,
    IN cliente_id INT,
    IN estado_id INT
)
BEGIN
    IF Accion = 'Insert' THEN
        INSERT INTO Citas (fecha_cita, mascota_id, servicio_id, cliente_id, estado_id)
        VALUES (fecha_cita, mascota_id, servicio_id, cliente_id, COALESCE(estado_id, 3)); -- Por defecto, estado "Pendiente"
    ELSEIF Accion = 'Update' THEN
        UPDATE Citas
        SET fecha_cita = fecha_cita,
            mascota_id = mascota_id,
            servicio_id = servicio_id,
            cliente_id = cliente_id,
            estado_id = COALESCE(estado_id, estado_id)
        WHERE cita_id = cita_id;
    ELSEIF Accion = 'Select' THEN
        SELECT * FROM Citas;
    ELSEIF Accion = 'Status' THEN
        UPDATE Citas
        SET estado_id = estado_id
        WHERE cita_id = cita_id;
    END IF;
END $$

-- Stored Procedure de Productos
CREATE PROCEDURE usp_Productos(
    IN Accion VARCHAR(10),
    IN producto_id INT,
    IN nombre VARCHAR(100),
    IN descripcion TEXT,
    IN precio DECIMAL(10, 2),
    IN stock INT,
    IN estado_id INT
)
BEGIN
    IF Accion = 'Insert' THEN
        INSERT INTO Productos (nombre, descripcion, precio, stock, estado_id)
        VALUES (nombre, descripcion, precio, COALESCE(stock, 0), COALESCE(estado_id, 1)); -- Por defecto, estado "Habilitado"
    ELSEIF Accion = 'Update' THEN
        UPDATE Productos
        SET nombre = nombre,
            descripcion = descripcion,
            precio = precio,
            stock = stock,
            estado_id = COALESCE(estado_id, estado_id)
        WHERE producto_id = producto_id;
    ELSEIF Accion = 'Select' THEN
        SELECT * FROM Productos;
    ELSEIF Accion = 'Status' THEN
        UPDATE Productos
        SET estado_id = estado_id
        WHERE producto_id = producto_id;
    END IF;
END $$

-- Stored Procedure de Pedidos
CREATE PROCEDURE usp_Pedidos(
    IN Accion VARCHAR(10),
    IN pedido_id INT,
    IN cliente_id INT,
    IN fecha_pedido DATETIME,
    IN total DECIMAL(10, 2),
    IN estado_id INT
)
BEGIN
    IF Accion = 'Insert' THEN
        INSERT INTO Pedidos (cliente_id, fecha_pedido, total, estado_id)
        VALUES (cliente_id, fecha_pedido, total, COALESCE(estado_id, 1)); -- Estado "Habilitado" por defecto
    ELSEIF Accion = 'Update' THEN
        UPDATE Pedidos
        SET cliente_id = cliente_id,
            fecha_pedido = fecha_pedido,
            total = total,
            estado_id = COALESCE(estado_id, estado_id)
        WHERE pedido_id = pedido_id;
    ELSEIF Accion = 'Select' THEN
        SELECT * FROM Pedidos;
    ELSEIF Accion = 'Status' THEN
        UPDATE Pedidos
        SET estado_id = estado_id
        WHERE pedido_id = pedido_id;
    END IF;
END $$

-- Stored Procedure de DetallesPedidos
CREATE PROCEDURE usp_DetallesPedidos(
    IN Accion VARCHAR(10),
    IN detalle_id INT,
    IN pedido_id INT,
    IN producto_id INT,
    IN cantidad INT,
    IN precio DECIMAL(10, 2)
)
BEGIN
    IF Accion = 'Insert' THEN
        INSERT INTO DetallesPedidos (pedido_id, producto_id, cantidad, precio_unitario)
        VALUES (pedido_id, producto_id, cantidad, precio);
    ELSEIF Accion = 'Update' THEN
        UPDATE DetallesPedidos
        SET pedido_id = pedido_id,
            producto_id = producto_id,
            cantidad = cantidad,
            precio_unitario = precio
        WHERE detalle_id = detalle_id;
    ELSEIF Accion = 'Select' THEN
        SELECT * FROM DetallesPedidos;
    END IF;
END $$

-- Stored Procedure de Consejos
CREATE PROCEDURE usp_Consejos(
    IN Accion VARCHAR(10),
    IN consejo_id INT,
    IN titulo VARCHAR(100),
    IN contenido TEXT,
    IN especie_id INT,
    IN raza_id INT,
    IN estado_id INT
)
BEGIN
    IF Accion = 'Insert' THEN
        INSERT INTO Consejos (titulo, contenido, especie_id, raza_id, fecha_publicacion, estado_id)
        VALUES (titulo, contenido, especie_id, raza_id, now(), COALESCE(estado_id, 1)); -- Estado "Habilitado" por defecto
    ELSEIF Accion = 'Update' THEN
        UPDATE Consejos
        SET titulo = titulo,
            contenido = contenido,
            estado_id = COALESCE(estado_id, estado_id)
        WHERE consejo_id = consejo_id;
    ELSEIF Accion = 'Select' THEN
        SELECT * FROM Consejos;
    ELSEIF Accion = 'Status' THEN
        UPDATE Consejos
        SET estado_id = estado_id
        WHERE consejo_id = consejo_id;
    END IF;
END $$

-- Stored Procedure de Adopciones
CREATE PROCEDURE usp_Adopciones(
    IN Accion VARCHAR(10),
    IN adopcion_id INT,
    IN mascota_id INT,
    IN fecha_adopcion DATE,
    IN cliente_id INT,
    IN estado_id INT
)
BEGIN
    IF Accion = 'Insert' THEN
        INSERT INTO Adopciones (mascota_id, estado_id, fecha_adopcion, cliente_id, estado_id)
        VALUES (mascota_id, COALESCE(estado_id, 11), fecha_adopcion, cliente_id, estado_id); -- Estado "En Adopción" por defecto
    ELSEIF Accion = 'Update' THEN
        UPDATE Adopciones
        SET mascota_id = mascota_id,
            fecha_adopcion = fecha_adopcion,
            cliente_id = cliente_id,
            estado_id = COALESCE(estado_id, estado_id)
        WHERE adopcion_id = adopcion_id;
    ELSEIF Accion = 'Select' THEN
        SELECT * FROM Adopciones;
    ELSEIF Accion = 'Status' THEN
        UPDATE Adopciones
        SET estado_id = estado_id
        WHERE adopcion_id = adopcion_id;
    END IF;
END $$

-- Stored Procedure de Cuidados
CREATE PROCEDURE usp_Cuidados(
    IN accion VARCHAR(10),						
    IN cuidado_id INT,						
    IN especie_id INT,						
    IN raza_id INT,						
    IN consejos TEXT,						
    IN recomendaciones_especificas TEXT,	
    IN estado_id INT						
)
BEGIN
    IF accion = 'INSERT' THEN
        INSERT INTO Cuidados (especie_id, raza_id, consejos, recomendaciones_especificas, estado_id)
        VALUES (especie_id, raza_id, consejos, recomendaciones_especificas, 1); -- Estado habilitado por defecto
    ELSEIF accion = 'UPDATE' THEN
        UPDATE Cuidados
        SET especie_id = especie_id,
            raza_id = raza_id,
            consejos = consejos,
            recomendaciones_especificas = recomendaciones_especificas,
            estado_id = COALESCE(estado_id, estado_id)
        WHERE cuidado_id = cuidado_id;
    ELSEIF accion = 'SELECT' THEN
        SELECT * FROM Cuidados;
    ELSEIF accion = 'STATUS' THEN
        UPDATE Cuidados
        SET estado_id = 2 -- Estado "Deshabilitado"
        WHERE cuidado_id = cuidado_id;
    END IF;
END $$

-- Stored Procedure de Estados
CREATE PROCEDURE usp_Estados(
    IN accion VARCHAR(10),						
    IN estado_id INT,						
    IN nombre_estado VARCHAR(50)				
)
BEGIN
    IF accion = 'Update' THEN
        UPDATE Cuidados
        SET nombre_estado = nombre_estado
        WHERE estado_id = estado_id;
    ELSEIF accion = 'Select' THEN
        SELECT * FROM Estados;
    ELSEIF accion = 'Status' THEN
        UPDATE Estados
        SET estado_id = 2 -- Estado "Deshabilitado"
        WHERE estado_id = estado_id;
	ELSEIF accion = 'SelectById' THEN
		SELECT * FROM Estados
        WHERE estado_id = estado_id;
    END IF;
END $$

-- Restaurar el delimitador estándar
DELIMITER ;

-- Insertar categorías de estado
INSERT INTO Categorias (nombre_categoria) VALUES 
('Cliente'),
('Mascota'),
('Servicio'),
('Cita'),
('Producto'),
('Pedido'),
('Consejo'),
('Adopciones');

-- Insertar nuevos estados
INSERT INTO Estados (nombre_estado) VALUES 
('Habilitado'),
('Deshabilitado'),
('Pendiente'),
('Confirmada'),
('Cancelada'),
('Realizada'),
('Entregado'),
('Disponible'),
('No disponible'),
('Descontinuado'),
('En Adopción'),
('Adoptado');

-- Insertar relaciones entre estados y categorías
INSERT INTO EstadoCategoria (estado_id, categoria_id) VALUES 
(1, 1),
(2, 1),
(1, 2),
(2, 2),
(11, 8),
(12, 8),
(1, 3),
(2, 3),
(3, 4),
(4, 4),
(5, 4),
(6, 4),
(8, 5),
(9, 5),
(10, 5),
(3, 6),
(4, 6),
(7, 6),
(5, 6),
(1, 7),
(2, 7);

-- Insertar Especies
INSERT INTO Especies (nombre_especie) VALUES 
('Gato'),
('Perro'),
('Conejo'),
('Loro'),
('Pez'),
('Tortuga'),
('Lagartija'),
('Hamster'),
('Caballo'),
('Erizo');

-- Insertar Razas para cada Especie
INSERT INTO Razas (nombre_raza, especie_id) VALUES 
('Siamés', 1),                      -- Gato
('Persa', 1),                       -- Gato
('Maine Coon', 1),                  -- Gato
('Labrador Retriever', 2),          -- Perro
('Bulldog', 2),                     -- Perro
('Beagle', 2),                      -- Perro
('Holandés Enano', 3),              -- Conejo
('Rex', 3),                         -- Conejo
('Cabeza de León', 3),              -- Conejo
('Guacamayo', 4),                   -- Loro
('Cacatúa', 4),                     -- Loro
('Periquito', 4),                   -- Loro
('Goldfish', 5),                    -- Pez
('Betta', 5),                       -- Pez
('Guppy', 5),                       -- Pez
('Tortuga de Orejas Rojas', 6),     -- Tortuga
('Tortuga Rusa', 6),                -- Tortuga
('Tortuga de Caja', 6),             -- Tortuga
('Gecko Leopardo', 7),              -- Lagartija
('Iguana Verde', 7),                -- Lagartija
('Camaleón', 7),                    -- Lagartija
('Sirio', 8),                       -- Hamster
('Ruso', 8),                        -- Hamster
('Chino', 8),                       -- Hamster
('Pura Sangre', 9),                 -- Caballo
('Árabe', 9),                       -- Caballo
('Percherón', 9),                   -- Caballo
('Erizo Pigmeo Africano', 10),      -- Erizo
('Erizo Europeo', 10),              -- Erizo
('Erizo de Vientre Blanco', 10);    -- Erizo

-- Insertar Clientes
INSERT INTO Clientes (nombre, email, telefono, direccion, fecha_registro) VALUES 
('Juan Pérez', 'juan.perez@example.com', '555-1234', 'Calle Falsa 123', '2023-01-01'),
('María López', 'maria.lopez@example.com', '555-5678', 'Avenida Siempre Viva 742', '2023-02-01'),
('Carlos García', 'carlos.garcia@example.com', '555-8765', 'Calle Luna 456', '2023-03-01'),
('Ana Martínez', 'ana.martinez@example.com', '555-4321', 'Calle Sol 789', '2023-04-01'),
('Luis Rodríguez', 'luis.rodriguez@example.com', '555-6789', 'Calle Estrella 101', '2023-05-01'),
('Laura Fernández', 'laura.fernandez@example.com', '555-9876', 'Calle Nube 202', '2023-06-01'),
('Jorge Sánchez', 'jorge.sanchez@example.com', '555-5432', 'Calle Viento 303', '2023-07-01'),
('Marta Gómez', 'marta.gomez@example.com', '555-6543', 'Calle Mar 404', '2023-08-01'),
('Pedro Díaz', 'pedro.diaz@example.com', '555-7654', 'Calle Tierra 505', '2023-09-01'),
('Sofía Ramírez', 'sofia.ramirez@example.com', '555-8765', 'Calle Fuego 606', '2023-10-01');

-- Insertar Mascotas
INSERT INTO Mascotas (nombre, especie_id, raza_id, fecha_nacimiento, cliente_id) VALUES 
('Michi', 1, 1, '2022-01-01', 1),
('Firulais', 2, 4, '2021-02-01', 2),
('Bunny', 3, 7, '2020-03-01', 3),
('Loro', 4, 10, '2019-04-01', 4),
('Nemo', 5, 13, '2018-05-01', 5),
('Tuga', 6, 16, '2017-06-01', 6),
('Gecko', 7, 19, '2016-07-01', 7),
('Hammy', 8, 22, '2015-08-01', 8),
('Spirit', 9, 25, '2014-09-01', 9),
('Spike', 10, 28, '2013-10-01', 10);

-- Insertar Servicios
INSERT INTO Servicios (nombre, descripcion, precio) VALUES
('Hospitalizacion', 'Servicio de hospitalización para mascotas', 100.00),
('Lavado de mascotas', 'Servicio de lavado y aseo para mascotas', 30.00),
('Cirugia', 'Servicio de cirugía para mascotas', 500.00),
('Traumatologia', 'Servicio de traumatología para mascotas', 200.00),
('Neurologia', 'Servicio de neurología para mascotas', 300.00),
('Oftamologia', 'Servicio de oftalmología para mascotas', 150.00),
('Dermatologia', 'Servicio de dermatología para mascotas', 120.00),
('Odontologia', 'Servicio de odontología para mascotas', 80.00),
('Laboratorio', 'Servicio de laboratorio para mascotas', 70.00),
('Radiologia', 'Servicio de radiología para mascotas', 250.00),
('Endoscopia', 'Servicio de endoscopia para mascotas', 400.00),
('Medicion de la presion arterial', 'Servicio de medición de la presión arterial para mascotas', 50.00);

-- Insertar Citas
INSERT INTO Citas (fecha_cita, mascota_id, servicio_id, cliente_id) VALUES 
('2023-11-01 10:00:00', 1, 1, 1),
('2023-11-02 11:00:00', 2, 2, 2),
('2023-11-03 12:00:00', 3, 3, 3),
('2023-11-04 13:00:00', 4, 4, 4),
('2023-11-05 14:00:00', 5, 5, 5),
('2023-11-06 15:00:00', 6, 6, 6),
('2023-11-07 16:00:00', 7, 7, 7),
('2023-11-08 17:00:00', 8, 8, 8),
('2023-11-09 18:00:00', 9, 9, 9),
('2023-11-10 19:00:00', 10, 10, 10);

-- Insertar Productos
INSERT INTO Productos (nombre, descripcion, precio, stock) VALUES 
('Alimento para gatos', 'Alimento balanceado para gatos adultos', 20.00, 50),
('Alimento para perros', 'Alimento balanceado para perros adultos', 25.00, 40),
('Juguete para gatos', 'Juguete interactivo para gatos', 10.00, 30),
('Juguete para perros', 'Juguete resistente para perros', 15.00, 20),
('Cama para mascotas', 'Cama cómoda para mascotas', 35.00, 10),
('Collar para gatos', 'Collar ajustable para gatos', 5.00, 60),
('Collar para perros', 'Collar ajustable para perros', 7.00, 50),
('Transportadora para mascotas', 'Transportadora segura para mascotas', 40.00, 15),
('Arena para gatos', 'Arena absorbente para gatos', 12.00, 70),
('Cepillo para mascotas', 'Cepillo para el cuidado del pelaje de las mascotas', 8.00, 25);

-- Insertar Pedidos
INSERT INTO Pedidos (cliente_id, fecha_pedido, total) VALUES 
(1, '2023-11-01', 100.00),
(2, '2023-11-02', 150.00),
(3, '2023-11-03', 200.00),
(4, '2023-11-04', 250.00),
(5, '2023-11-05', 300.00),
(6, '2023-11-06', 350.00),
(7, '2023-11-07', 400.00),
(8, '2023-11-08', 450.00),
(9, '2023-11-09', 500.00),
(10, '2023-11-10', 550.00);

-- Insertar DetallesPedidos
INSERT INTO DetallesPedidos (pedido_id, producto_id, cantidad, precio_unitario) VALUES 
(1, 1, 2, 20.00),
(1, 2, 1, 25.00),
(2, 3, 3, 10.00),
(2, 4, 2, 15.00),
(3, 5, 1, 35.00),
(3, 6, 4, 5.00),
(4, 7, 3, 7.00),
(4, 8, 1, 40.00),
(5, 9, 5, 12.00),
(5, 10, 2, 8.00),
(6, 1, 3, 20.00),
(6, 2, 2, 25.00),
(7, 3, 4, 10.00),
(7, 4, 3, 15.00),
(8, 5, 2, 35.00),
(8, 6, 5, 5.00),
(9, 7, 4, 7.00),
(9, 8, 2, 40.00),
(10, 9, 6, 12.00),
(10, 10, 3, 8.00);

-- Insertar Consejos
INSERT INTO Consejos (titulo, contenido, especie_id, raza_id, fecha_publicacion) VALUES 
('Cuidado del pelaje del gato', 'Es importante cepillar el pelaje de tu gato regularmente para evitar nudos y mantenerlo limpio.', 1, 1, '2023-11-01'),
('Alimentación adecuada para perros', 'Proporciona a tu perro una dieta balanceada y adecuada a su tamaño y edad.', 2, 4, '2023-11-02'),
('Cómo cuidar a tu conejo', 'Asegúrate de que tu conejo tenga suficiente espacio para moverse y una dieta rica en fibra.', 3, 7, '2023-11-03'),
('Entrenamiento de loros', 'Los loros son aves inteligentes que pueden aprender trucos y palabras con el entrenamiento adecuado.', 4, 10, '2023-11-04'),
('Mantenimiento del acuario', 'Mantén el agua de tu acuario limpia y a la temperatura adecuada para la salud de tus peces.', 5, 13, '2023-11-05'),
('Cuidado de la tortuga', 'Proporciona a tu tortuga un hábitat adecuado con acceso a agua y tierra.', 6, 16, '2023-11-06'),
('Hábitat para lagartijas', 'Asegúrate de que tu lagartija tenga un terrario con la temperatura y humedad adecuadas.', 7, 19, '2023-11-07'),
('Cuidado de los hamsters', 'Proporciona a tu hamster una jaula espaciosa y una rueda para ejercitarse.', 8, 22, '2023-11-08'),
('Cuidado del caballo', 'Cepilla a tu caballo regularmente y asegúrate de que tenga suficiente ejercicio.', 9, 25, '2023-11-09'),
('Cuidado del erizo', 'Proporciona a tu erizo un ambiente cálido y una dieta rica en proteínas.', 10, 28, '2023-11-10'),
('Cómo bañar a tu perro', 'Utiliza un champú adecuado para perros y asegúrate de enjuagar bien.', 2, 5, '2023-11-11'),
('Juguetes para gatos', 'Proporciona a tu gato juguetes interactivos para mantenerlo activo y entretenido.', 1, 2, '2023-11-12'),
('Alimentación de loros', 'Proporciona a tu loro una dieta variada que incluya frutas, verduras y semillas.', 4, 11, '2023-11-13'),
('Cuidado dental para perros', 'Cepilla los dientes de tu perro regularmente para prevenir enfermedades dentales.', 2, 6, '2023-11-14'),
('Ejercicio para conejos', 'Proporciona a tu conejo suficiente espacio y juguetes para ejercitarse.', 3, 8, '2023-11-15');

-- Insertar Cliente
INSERT INTO Clientes (nombre, email, telefono, direccion, fecha_registro) VALUES 
('Gabriela Torres', 'gabriela.torres@example.com', '555-1122', 'Calle Primavera 123', '2023-11-01');

-- Insertar Mascota
INSERT INTO Mascotas (nombre, especie_id, raza_id, fecha_nacimiento, cliente_id) VALUES 
('Lucky', 2, 5, '2022-05-01', NULL);

-- Insertar Adopción
INSERT INTO Adopciones (cliente_id, mascota_id, fecha_publicacion, fecha_adopcion) VALUES 
((SELECT cliente_id FROM Clientes WHERE email = 'gabriela.torres@example.com'), 
 (SELECT mascota_id FROM Mascotas WHERE nombre = 'Lucky'), 
 '2023-11-01', 
 '2023-11-02');

-- Insertar Cuidados
INSERT INTO Cuidados (especie_id, raza_id, consejos, recomendaciones_especificas) VALUES 
(1, 1, 'Cepillar el pelaje regularmente', 'Usar un cepillo adecuado para gatos de pelo corto'),
(1, 2, 'Proporcionar juguetes interactivos', 'Evitar juguetes pequeños que puedan ser tragados'),
(2, 4, 'Alimentar con comida balanceada', 'Evitar alimentos con alto contenido de grasa'),
(2, 5, 'Ejercicio diario', 'Sacar a pasear al perro al menos dos veces al día'),
(3, 7, 'Proporcionar heno fresco', 'Evitar alimentos con alto contenido de azúcar'),
(3, 8, 'Limpieza regular de la jaula', 'Usar productos de limpieza no tóxicos'),
(4, 10, 'Entrenamiento con refuerzo positivo', 'Evitar castigos físicos'),
(4, 11, 'Proporcionar una dieta variada', 'Incluir frutas y verduras frescas'),
(5, 13, 'Mantener el acuario limpio', 'Cambiar el agua regularmente'),
(5, 14, 'Proporcionar un filtro adecuado', 'Evitar cambios bruscos de temperatura'),
(6, 16, 'Proporcionar un hábitat adecuado', 'Incluir una zona de agua y una zona seca'),
(6, 17, 'Alimentar con una dieta balanceada', 'Incluir suplementos de calcio'),
(7, 19, 'Mantener la temperatura y humedad adecuadas', 'Usar un termómetro y un higrómetro'),
(7, 20, 'Proporcionar escondites en el terrario', 'Evitar el uso de sustratos tóxicos'),
(8, 22, 'Proporcionar una rueda para ejercicio', 'Evitar jaulas pequeñas y sin ventilación');

DELIMITER ;

