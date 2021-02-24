SET SESSION FOREIGN_KEY_CHECKS=0;
CREATE DATABASE IF NOT EXISTS supermercado;
USE supermercado;

/* Drop Tables */

DROP TABLE IF EXISTS Producto;
DROP TABLE IF EXISTS Compra;
DROP TABLE IF EXISTS Empleado;
DROP TABLE IF EXISTS CantidadProducto;

/* Create Tables */

CREATE TABLE Producto
(
	id int NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,
    nombre_producto varchar(50),
    precio_venta float,
    precio_proveedor float,
    cantidad_stock int
);

CREATE TABLE Compra
(
	id int NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,
    id_empleado int,
    fecha_compra date
);

CREATE TABLE Empleado
(
	id int NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,
    ultima_sesion date,
    fecha_contratacion date
);

CREATE TABLE CantidadProducto
(
	id_compra int NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,
    id_producto int NOT NULL,
    cantidad int NOT NULL
);

/* Create Foreign Keys */

ALTER TABLE CantidadProducto
	ADD FOREIGN KEY (id_compra) REFERENCES Compra (id),
    ADD FOREIGN KEY (id_producto) REFERENCES Producto (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;

ALTER TABLE Compra
	ADD FOREIGN KEY (id_empleado) REFERENCES Empleado (id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;
