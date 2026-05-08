CREATE DATABASE connect_work;
USE connect_work;


-- Usuarios
CREATE TABLE Usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(100) NOT NULL,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    correo VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    direccion VARCHAR(150),
    cui VARCHAR(20),
    fecha_nacimiento DATE,
    estado BOOLEAN DEFAULT TRUE,
    saldo DECIMAL(10,2) DEFAULT 0,
    rol INT NOT NULL
);


-- Clientes
CREATE TABLE Clientes (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    descripcion TEXT,
    sector VARCHAR(100),
    sitio_web VARCHAR(150),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
);


-- Freelancers
CREATE TABLE Freelancers (
    id_freelancer INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    biografia TEXT,
    nivel_experiencia VARCHAR(50),
    tarifa_hora DECIMAL(10,2),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
);


-- Administradores
CREATE TABLE Administradores (
    id_admin INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
);


-- Categorias
CREATE TABLE Categorias (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    descripcion TEXT,
    estado BOOLEAN DEFAULT TRUE
);


-- Habilidades
CREATE TABLE Habilidades (
    id_habilidad INT AUTO_INCREMENT PRIMARY KEY,
    id_categoria INT,
    nombre VARCHAR(100),
    descripcion TEXT,
    estado BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_categoria) REFERENCES Categorias(id_categoria)
);


-- Freelancer_Habilidad
CREATE TABLE Freelancer_Habilidad (
    id_freelancer INT,
    id_habilidad INT,
    PRIMARY KEY (id_freelancer, id_habilidad),
    FOREIGN KEY (id_freelancer) REFERENCES Freelancers(id_freelancer),
    FOREIGN KEY (id_habilidad) REFERENCES Habilidades(id_habilidad)
);


-- Proyectos
CREATE TABLE Proyectos (
    id_proyecto INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT,
    id_categoria INT,
    titulo VARCHAR(150),
    descripcion TEXT,
    presupuesto_max DECIMAL(10,2),
    fecha_limite DATE,
    estado VARCHAR(50),
    fecha_creacion DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente),
    FOREIGN KEY (id_categoria) REFERENCES Categorias(id_categoria)
);


-- Proyecto_Habilidad
CREATE TABLE Proyecto_Habilidad (
    id_proyecto INT,
    id_habilidad INT,
    PRIMARY KEY (id_proyecto, id_habilidad),
    FOREIGN KEY (id_proyecto) REFERENCES Proyectos(id_proyecto),
    FOREIGN KEY (id_habilidad) REFERENCES Habilidades(id_habilidad)
);


-- Propuestas
CREATE TABLE Propuestas (
    id_propuesta INT AUTO_INCREMENT PRIMARY KEY,
    id_proyecto INT,
    id_freelancer INT,
    monto DECIMAL(10,2),
    plazo_dias INT,
    descripcion TEXT,
    estado VARCHAR(50),
    fecha DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (id_proyecto) REFERENCES Proyectos(id_proyecto),
    FOREIGN KEY (id_freelancer) REFERENCES Freelancers(id_freelancer)
);


-- Contratos
CREATE TABLE Contratos (
    id_contrato INT AUTO_INCREMENT PRIMARY KEY,
    id_propuesta INT,
    id_proyecto INT,
    fecha_inicio DATETIME,
    fecha_fin DATETIME,
    estado VARCHAR(50),
    monto DECIMAL(10,2),
    FOREIGN KEY (id_propuesta) REFERENCES Propuestas(id_propuesta),
    FOREIGN KEY (id_proyecto) REFERENCES Proyectos(id_proyecto)
);


-- Entregas
CREATE TABLE Entregas (
    id_entrega INT AUTO_INCREMENT PRIMARY KEY,
    id_contrato INT,
    descripcion TEXT,
    archivo_url VARCHAR(255),
    fecha DATETIME,
    estado VARCHAR(50),
    FOREIGN KEY (id_contrato) REFERENCES Contratos(id_contrato)
);


-- Calificaciones
CREATE TABLE Calificaciones (
    id_calificacion INT AUTO_INCREMENT PRIMARY KEY,
    id_contrato INT,
    id_cliente INT,
    id_freelancer INT,
    puntuacion INT,
    comentario TEXT,
    fecha DATETIME,
    FOREIGN KEY (id_contrato) REFERENCES Contratos(id_contrato),
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente),
    FOREIGN KEY (id_freelancer) REFERENCES Freelancers(id_freelancer)
);


-- Recargas
CREATE TABLE Recargas (
    id_recarga INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT,
    monto DECIMAL(10,2),
    fecha DATETIME,
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente)
);


-- Comisiones
CREATE TABLE Comisiones (
    id_comision INT AUTO_INCREMENT PRIMARY KEY,
    porcentaje DECIMAL(5,2),
    fecha_inicio DATE DEFAULT (CURRENT_DATE),
    estado BOOLEAN DEFAULT true,
    fecha_fin DATE DEFAULT (CURRENT_DATE)
);


-- Solicitud_Habilidad
CREATE TABLE Solicitud_Habilidad (
    id_solicitud_habilidad INT AUTO_INCREMENT PRIMARY KEY,
    id_freelancer INT,
    nombre VARCHAR(100),
    descripcion TEXT,
    estado VARCHAR(50),
    fecha DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (id_freelancer) REFERENCES Freelancers(id_freelancer)
);

CREATE TABLE Solicitud_Categoria (
    id_solicitud_categoria INT AUTO_INCREMENT PRIMARY KEY,
    id_cliente INT,
    nombre VARCHAR(100),
    descripcion TEXT,
    estado VARCHAR(50),
    fecha DATE DEFAULT (CURRENT_DATE),
    FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente)
);