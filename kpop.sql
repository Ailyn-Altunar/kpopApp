CREATE DATABASE kpop;
USE kpop;

CREATE TABLE grupo (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE categoria (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE producto (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    grupo_id INTEGER REFERENCES grupo(id) ON DELETE SET NULL,
    categoria_id INTEGER REFERENCES categoria(id) ON DELETE SET NULL,
    precio NUMERIC(10,2) NOT NULL CHECK (precio >= 0),
    stock INTEGER NOT NULL DEFAULT 0 CHECK (stock >= 0),
    descripcion TEXT,
    imagen_url TEXT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO categoria (nombre) VALUES
('Álbum'),
('Lightstick');

INSERT INTO grupo (nombre) VALUES
('BTS'),
('TXT');

INSERT INTO producto (
    nombre, grupo_id, categoria_id, precio, stock, descripcion, imagen_url
) VALUES (
    'Map of the Soul: 7',
    1,
    1,
    320.00,
    15,
    'Álbum completo con photocard incluida',
    'https://ejemplo.com/imagen.jpg'
);

SELECT 
    p.id,
    p.nombre,
    g.nombre AS grupo,
    c.nombre AS categoria,
    p.precio,
    p.stock,
    p.imagen_url
FROM producto p
LEFT JOIN grupo g ON p.grupo_id = g.id
LEFT JOIN categoria c ON p.categoria_id = c.id;