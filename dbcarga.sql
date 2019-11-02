-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 20-06-2016 a las 06:25:03
-- Versión del servidor: 10.1.10-MariaDB
-- Versión de PHP: 7.0.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `dbcarga`
--
CREATE DATABASE IF NOT EXISTS `dbcarga` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `dbcarga`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `camion`
--

DROP TABLE IF EXISTS `camion`;
CREATE TABLE `camion` (
  `id_camion` bigint(20) NOT NULL,
  `placa` varchar(10) DEFAULT NULL,
  `disponible` tinyint(1) DEFAULT NULL,
  `id_ciudad_actual` bigint(20) DEFAULT NULL,
  `modelo` text,
  `peso_capacidad` bigint(20) DEFAULT NULL,
  `volumen_capacidad` bigint(20) DEFAULT NULL,
  `id_empleado` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `camion`
--

INSERT INTO `camion` (`id_camion`, `placa`, `disponible`, `id_ciudad_actual`, `modelo`, `peso_capacidad`, `volumen_capacidad`, `id_empleado`) VALUES
(1, '1213', 0, 3, '123', 123, 123, 1),
(2, '123', 0, 3, '213', 123, 123, 1),
(3, '123', 0, 3, '213', 123, 123, 1),
(4, '123', 1, 3, '213', 123, 123, 1),
(5, '123', 1, 3, '213', 123, 123, 1),
(6, '123123', 0, 2, '123', 123, 123, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cargo`
--

DROP TABLE IF EXISTS `cargo`;
CREATE TABLE `cargo` (
  `id_cargo` bigint(20) NOT NULL,
  `nombre` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cargo`
--

INSERT INTO `cargo` (`id_cargo`, `nombre`) VALUES
(1, 'ADMINISTRADOR');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ciudad`
--

DROP TABLE IF EXISTS `ciudad`;
CREATE TABLE `ciudad` (
  `ID` bigint(20) NOT NULL,
  `Nombre` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `ciudad`
--

INSERT INTO `ciudad` (`ID`, `Nombre`) VALUES
(2, 'ARJONA'),
(3, 'BARRANQUILLA'),
(5, 'BOGOTA'),
(1, 'CARTAGENA'),
(6, 'MEDELLIN'),
(4, 'PASTO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

DROP TABLE IF EXISTS `cliente`;
CREATE TABLE `cliente` (
  `id_cliente` bigint(20) NOT NULL,
  `id_tipoidentificacion` bigint(20) DEFAULT NULL,
  `identificacion` varchar(100) DEFAULT NULL,
  `nombre` text,
  `telefono` text,
  `id_ciudad` bigint(20) DEFAULT NULL,
  `direccion` text,
  `id_login` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`id_cliente`, `id_tipoidentificacion`, `identificacion`, `nombre`, `telefono`, `id_ciudad`, `direccion`, `id_login`) VALUES
(1, 1, '1143370194', 'pedro manjarrez', '3005116563', 1, 'Calle 31a', 1),
(2, 1, '123123123', 'Pedro David Manjarrez Serrano', '3005116563', 1, 'Barrio 13 de junio #64b11 - Calle31a', 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `conexcion_ciudad`
--

DROP TABLE IF EXISTS `conexcion_ciudad`;
CREATE TABLE `conexcion_ciudad` (
  `id_conexion_ciudad` bigint(20) NOT NULL,
  `id_ciudad_a` bigint(20) DEFAULT NULL,
  `id_ciudad_b` bigint(20) DEFAULT NULL,
  `distancia` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `conexcion_ciudad`
--

INSERT INTO `conexcion_ciudad` (`id_conexion_ciudad`, `id_ciudad_a`, `id_ciudad_b`, `distancia`) VALUES
(1, 2, 3, 123),
(2, 2, 2, 123123);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleado`
--

DROP TABLE IF EXISTS `empleado`;
CREATE TABLE `empleado` (
  `id_empleado` bigint(20) NOT NULL,
  `nombre` text,
  `id_cargo` bigint(20) DEFAULT NULL,
  `id_login` bigint(20) DEFAULT NULL,
  `id_tipodeidentificacion` bigint(20) DEFAULT NULL,
  `identificacion` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `empleado`
--

INSERT INTO `empleado` (`id_empleado`, `nombre`, `id_cargo`, `id_login`, `id_tipodeidentificacion`, `identificacion`) VALUES
(1, '1', 1, 4, 1, '1'),
(3, '', 1, 5, 1, ''),
(4, 'Pedro David Manjarrez Serrano', 1, 6, 1, '1234'),
(5, 'root', 1, 7, 1, '12345'),
(6, 'root', 1, 8, 1, '321');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `envio`
--

DROP TABLE IF EXISTS `envio`;
CREATE TABLE `envio` (
  `id_envio` bigint(20) NOT NULL,
  `id_camion` bigint(20) NOT NULL,
  `fecha_recogida` datetime DEFAULT NULL,
  `fecha_entrega` datetime DEFAULT NULL,
  `id_solicitud` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `envio`
--

INSERT INTO `envio` (`id_envio`, `id_camion`, `fecha_recogida`, `fecha_entrega`, `id_solicitud`) VALUES
(1, 2, '2016-06-14 00:00:00', '2016-06-14 21:11:41', 2),
(2, 6, '2016-06-14 22:04:56', NULL, 3),
(3, 3, '2016-06-14 22:05:39', NULL, 4),
(4, 1, '2016-06-14 22:17:37', NULL, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estado`
--

DROP TABLE IF EXISTS `estado`;
CREATE TABLE `estado` (
  `id_estado` bigint(20) NOT NULL,
  `nombre` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `estado`
--

INSERT INTO `estado` (`id_estado`, `nombre`) VALUES
(1, 'recogido'),
(2, 'EN CAMINO'),
(3, 'ENTREGADO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estados_envio`
--

DROP TABLE IF EXISTS `estados_envio`;
CREATE TABLE `estados_envio` (
  `id_estado_envio` bigint(20) NOT NULL,
  `id_estado` bigint(20) DEFAULT NULL,
  `id_envio` bigint(20) DEFAULT NULL,
  `id_empleado` bigint(20) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `estados_envio`
--

INSERT INTO `estados_envio` (`id_estado_envio`, `id_estado`, `id_envio`, `id_empleado`, `fecha`) VALUES
(1, 1, 1, 1, '2016-06-19 15:40:59'),
(2, 2, 1, 1, '2016-06-19 15:41:35');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `login`
--

DROP TABLE IF EXISTS `login`;
CREATE TABLE `login` (
  `id_login` bigint(20) NOT NULL,
  `user` text,
  `pass` text,
  `email` text,
  `permiso` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `login`
--

INSERT INTO `login` (`id_login`, `user`, `pass`, `email`, `permiso`) VALUES
(1, '', '', '', 'CLIENTE'),
(2, 'admin', 'admin', 'admin@admin', 'CLIENTE'),
(3, 'a', 'aa', 'aa', 'ADMINISTRADOR'),
(4, '1', '1', '1', 'EMPLEADO'),
(5, '1123', '213', '123123', 'ADMINISTRADOR'),
(6, 'admin1', '1234', 'admim@admin.com', 'ADMINISTRADOR'),
(7, 'root', 'root', 'root@root.com', 'ADMINISTRADOR'),
(8, 'empleado', '1234', 'empleado@root.com', 'EMPLEADO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `solicitud`
--

DROP TABLE IF EXISTS `solicitud`;
CREATE TABLE `solicitud` (
  `id_solicitud` bigint(20) NOT NULL,
  `id_ciudad_origen` bigint(20) DEFAULT NULL,
  `id_ciudad_destino` bigint(20) DEFAULT NULL,
  `id_cliente` bigint(20) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `peso_mercancia` text,
  `volumen_mercancia` text,
  `aceptada` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `solicitud`
--

INSERT INTO `solicitud` (`id_solicitud`, `id_ciudad_origen`, `id_ciudad_destino`, `id_cliente`, `fecha`, `peso_mercancia`, `volumen_mercancia`, `aceptada`) VALUES
(1, 3, 2, 1, '2016-06-14 00:00:00', '123', '123', 1),
(2, 2, 2, 1, '2016-06-14 00:00:00', '123112', '123', 1),
(3, 2, 2, 1, '2016-06-14 22:04:56', '123', '123', 1),
(4, 2, 3, 1, '2016-06-14 22:05:39', '123', '123', 1),
(5, 2, 2, 1, '2016-06-14 22:17:37', '213', '123', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tipoidentificacion`
--

DROP TABLE IF EXISTS `tipoidentificacion`;
CREATE TABLE `tipoidentificacion` (
  `id_tipoidentificacion` bigint(20) NOT NULL,
  `nombre_tipo_identificacion` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tipoidentificacion`
--

INSERT INTO `tipoidentificacion` (`id_tipoidentificacion`, `nombre_tipo_identificacion`) VALUES
(1, 'CC');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `camion`
--
ALTER TABLE `camion`
  ADD PRIMARY KEY (`id_camion`),
  ADD KEY `IXFK_Camion_Empleado` (`id_empleado`),
  ADD KEY `Index_Placa` (`placa`),
  ADD KEY `FK_Camion_Ciudad` (`id_ciudad_actual`);

--
-- Indices de la tabla `cargo`
--
ALTER TABLE `cargo`
  ADD PRIMARY KEY (`id_cargo`);

--
-- Indices de la tabla `ciudad`
--
ALTER TABLE `ciudad`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `Index_Ciudad` (`Nombre`);

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id_cliente`),
  ADD KEY `IXFK_Cliente_Ciudad` (`id_ciudad`),
  ADD KEY `IXFK_Cliente_Login` (`id_login`),
  ADD KEY `PK_identificacion` (`identificacion`),
  ADD KEY `FK_Cliente_TipoIdentificacion` (`id_tipoidentificacion`);

--
-- Indices de la tabla `conexcion_ciudad`
--
ALTER TABLE `conexcion_ciudad`
  ADD PRIMARY KEY (`id_conexion_ciudad`),
  ADD KEY `IXFK_Conexcion_Ciudad_Ciudad` (`id_ciudad_a`),
  ADD KEY `IXFK_Conexcion_Ciudad_Ciudad_02` (`id_ciudad_b`);

--
-- Indices de la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD PRIMARY KEY (`id_empleado`),
  ADD KEY `IXFK_Empleado_Cargo` (`id_cargo`),
  ADD KEY `IXFK_Empleado_Login` (`id_login`),
  ADD KEY `IXFK_Empleado_TipoIdentificacion` (`id_tipodeidentificacion`);

--
-- Indices de la tabla `envio`
--
ALTER TABLE `envio`
  ADD PRIMARY KEY (`id_envio`),
  ADD KEY `IXFK_Envio_Solicitud` (`id_solicitud`),
  ADD KEY `FK_Envio_Camion` (`id_camion`);

--
-- Indices de la tabla `estado`
--
ALTER TABLE `estado`
  ADD PRIMARY KEY (`id_estado`);

--
-- Indices de la tabla `estados_envio`
--
ALTER TABLE `estados_envio`
  ADD PRIMARY KEY (`id_estado_envio`),
  ADD KEY `IXFK_Estados_Envio_Empleado` (`id_empleado`),
  ADD KEY `IXFK_Estados_Envio_Envio` (`id_envio`),
  ADD KEY `IXFK_Estados_Envio_Estado` (`id_estado`);

--
-- Indices de la tabla `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`id_login`);

--
-- Indices de la tabla `solicitud`
--
ALTER TABLE `solicitud`
  ADD PRIMARY KEY (`id_solicitud`),
  ADD KEY `IXFK_Solicitud_Ciudad` (`id_ciudad_origen`),
  ADD KEY `IXFK_Solicitud_Ciudad_02` (`id_ciudad_destino`),
  ADD KEY `IXFK_Solicitud_Cliente` (`id_cliente`);

--
-- Indices de la tabla `tipoidentificacion`
--
ALTER TABLE `tipoidentificacion`
  ADD PRIMARY KEY (`id_tipoidentificacion`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `camion`
--
ALTER TABLE `camion`
  MODIFY `id_camion` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT de la tabla `cargo`
--
ALTER TABLE `cargo`
  MODIFY `id_cargo` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT de la tabla `ciudad`
--
ALTER TABLE `ciudad`
  MODIFY `ID` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id_cliente` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `conexcion_ciudad`
--
ALTER TABLE `conexcion_ciudad`
  MODIFY `id_conexion_ciudad` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `empleado`
--
ALTER TABLE `empleado`
  MODIFY `id_empleado` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT de la tabla `envio`
--
ALTER TABLE `envio`
  MODIFY `id_envio` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT de la tabla `estado`
--
ALTER TABLE `estado`
  MODIFY `id_estado` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT de la tabla `estados_envio`
--
ALTER TABLE `estados_envio`
  MODIFY `id_estado_envio` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT de la tabla `login`
--
ALTER TABLE `login`
  MODIFY `id_login` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
--
-- AUTO_INCREMENT de la tabla `solicitud`
--
ALTER TABLE `solicitud`
  MODIFY `id_solicitud` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT de la tabla `tipoidentificacion`
--
ALTER TABLE `tipoidentificacion`
  MODIFY `id_tipoidentificacion` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `camion`
--
ALTER TABLE `camion`
  ADD CONSTRAINT `FK_Camion_Ciudad` FOREIGN KEY (`id_ciudad_actual`) REFERENCES `ciudad` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_Camion_Empleado` FOREIGN KEY (`id_empleado`) REFERENCES `empleado` (`id_empleado`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD CONSTRAINT `FK_Cliente_Ciudad` FOREIGN KEY (`id_ciudad`) REFERENCES `ciudad` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_Cliente_Login` FOREIGN KEY (`id_login`) REFERENCES `login` (`id_login`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_Cliente_TipoIdentificacion` FOREIGN KEY (`id_tipoidentificacion`) REFERENCES `tipoidentificacion` (`id_tipoidentificacion`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `conexcion_ciudad`
--
ALTER TABLE `conexcion_ciudad`
  ADD CONSTRAINT `FK_Conexcion_Ciudad_Ciudad` FOREIGN KEY (`id_ciudad_a`) REFERENCES `ciudad` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_Conexcion_Ciudad_Ciudad_02` FOREIGN KEY (`id_ciudad_b`) REFERENCES `ciudad` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD CONSTRAINT `FK_Empleado_Cargo` FOREIGN KEY (`id_cargo`) REFERENCES `cargo` (`id_cargo`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_Empleado_Login` FOREIGN KEY (`id_login`) REFERENCES `login` (`id_login`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_Empleado_TipoIdentificacion` FOREIGN KEY (`id_tipodeidentificacion`) REFERENCES `tipoidentificacion` (`id_tipoidentificacion`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `envio`
--
ALTER TABLE `envio`
  ADD CONSTRAINT `FK_Envio_Camion` FOREIGN KEY (`id_camion`) REFERENCES `camion` (`id_camion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_Envio_Solicitud` FOREIGN KEY (`id_solicitud`) REFERENCES `solicitud` (`id_solicitud`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `estados_envio`
--
ALTER TABLE `estados_envio`
  ADD CONSTRAINT `FK_Estados_Envio_Empleado` FOREIGN KEY (`id_empleado`) REFERENCES `empleado` (`id_empleado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_Estados_Envio_Envio` FOREIGN KEY (`id_envio`) REFERENCES `envio` (`id_envio`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_Estados_Envio_Estado` FOREIGN KEY (`id_estado`) REFERENCES `estado` (`id_estado`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Filtros para la tabla `solicitud`
--
ALTER TABLE `solicitud`
  ADD CONSTRAINT `FK_Solicitud_Ciudad` FOREIGN KEY (`id_ciudad_origen`) REFERENCES `ciudad` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_Solicitud_Ciudad_02` FOREIGN KEY (`id_ciudad_destino`) REFERENCES `ciudad` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_Solicitud_Cliente` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`) ON DELETE NO ACTION ON UPDATE NO ACTION;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
