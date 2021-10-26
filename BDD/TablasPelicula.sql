ALTER DATABASE brvqbhfwy3arpy8r6ohh DEFAULT CHARACTER SET latin1 COLLATE latin1_spanish_ci;/*para reconocer ñ y acentos*/

CREATE TABLE IF NOT EXISTS Pelicula (
	IDPelicula INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	IDClasificacion TINYINT UNSIGNED NOT NULL,
	Nombre_Pelicula varchar(255) NOT NULL,
	Sipnosis TEXT NOT NULL,
	Director varchar(100) NOT NULL,
	Actores TEXT NOT NULL,
	Distribuidora varchar(50) NOT NULL,
	Duracion varchar(15) NOT NULL,
	Extreno DATE NOT NULL, /*dia/mes/año*/
	FinCartelera DATE NOT NULL /*dia/mes/año*/
);

CREATE TABLE IF NOT EXISTS Poster (
	IDPelicula INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	Poster MEDIUMBLOB NOT NULL,
	FOREIGN KEY (IDPelicula)
	    REFERENCES Pelicula (IDPelicula)
);

CREATE TABLE IF NOT EXISTS Sala (
    IDSala INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	IDPelicula INT UNSIGNED,
	Nombre_Sala ENUM('s1', 's2', 's3', 's4', 's5') NOT NULL,
	Hora TIME NOT NULL, /*hora:minutos*/
	FOREIGN KEY (IDPelicula)
	    REFERENCES Pelicula (IDPelicula)
);

CREATE TABLE IF NOT EXISTS Silla (
	IDSala INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	Letra ENUM('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K') NOT NULL,
	Numero ENUM('1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11') NOT NULL,
	FOREIGN KEY (IDSala)
	    REFERENCES Sala (IDSala)
);

CREATE TABLE IF NOT EXISTS Genero (
	IDGenero TINYINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	Genero varchar(20) NOT NULL,
	Descripcion varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS GenerosPeliculas(
	IDPelicula INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	IDGenero TINYINT UNSIGNED,
    FOREIGN KEY (IDPelicula)
	    REFERENCES Pelicula (IDPelicula),
    FOREIGN KEY (IDGenero)
	    REFERENCES Genero(IDGenero)
);

CREATE TABLE IF NOT EXISTS Idioma (
	IDIdioma TINYINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	Idioma VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS IdiomasPelicula (
	IDPelicula INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    IDIdioma TINYINT UNSIGNED,
    FOREIGN KEY (IDPelicula)
	    REFERENCES Pelicula (IDPelicula),
    FOREIGN KEY (IDIdioma)
	    REFERENCES Idioma (IDIdioma)
);

CREATE TABLE IF NOT EXISTS Clasificacion (
	IDClasificacion TINYINT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	Clasificacion ENUM('A', 'B', 'B15', 'C') NOT NULL,
	Descripcion varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS ClasificacionPelicula (
    IDPelicula INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
	IDClasificacion TINYINT UNSIGNED,
	FOREIGN KEY (IDPelicula)
	    REFERENCES Pelicula (IDPelicula),
    FOREIGN KEY (IDClasificacion)
	    REFERENCES Clasificacion (IDClasificacion)
);

SHOW TABLES;

# Contenido de los generos
INSERT INTO Genero (Genero,Descripcion) VALUES ('Comedia','Diseñadas específicamente para provocar la risa o la alegría entre los espectadores.');
INSERT INTO Genero (Genero,Descripcion) VALUES ('Animación','Películas que se componen de fotogramas hechos a mano y que, pasados rápidamente uno detrás de otro, producen la ilusión de movimiento o vídeo. Pueden ser hechas a mano (tradicionalmente) o mediante ordenador.');
INSERT INTO Genero (Genero,Descripcion) VALUES ('Suspenso','Conocido también como intriga, estas películas se desarrollan rápidamente, y todos sus elementos giran entorno un mismo elemento intrigante.');
INSERT INTO Genero (Genero,Descripcion) VALUES ('Acción','En este género prevalecen altas dosis de adrenalina con una buena carga de movimiento, fugas, acrobacias, peleas, guerras, persecuciones y una lucha contra el mal.');
INSERT INTO Genero (Genero,Descripcion) VALUES ('Familiar','Películas para toda la familia, en general de contenido "amable" y para todos los públicos.');
INSERT INTO Genero (Genero,Descripcion) VALUES ('Aventura','Similares a las de acción, predominan las nuevas experiencias y situaciones.');
INSERT INTO Genero (Genero,Descripcion) VALUES ('Terror','Su principal objetivo es causar miedo, horror, incomodidad o preocupación.');
INSERT INTO Genero (Genero,Descripcion) VALUES ('Drama','Los dramas se centran en desarrollar el problema o problemas entre los diferentes protagonistas.');

SELECT * FROM Genero;

# Idiomas
INSERT INTO Idioma (Idioma) VALUES ('Español');
INSERT INTO Idioma (Idioma) VALUES ('Inglés');
INSERT INTO Idioma (Idioma) VALUES ('Subtitulado');

SELECT * FROM Idioma;

# Clasificación
INSERT INTO Clasificacion (Clasificacion,Descripcion) VALUES('A','Para todo público.');
INSERT INTO Clasificacion (Clasificacion,Descripcion) VALUES('B','Adolescentes y adultos.');
INSERT INTO Clasificacion (Clasificacion,Descripcion) VALUES('B15','Adolescentes mayores de 15 años y adultos.');
INSERT INTO Clasificacion (Clasificacion,Descripcion) VALUES('C','Adultos.');

SELECT * FROM Clasificacion;
