import csv

#Crear listas de los datos que están en las tablas

with open("PeliculasDatos.csv", newline='') as csvfile, open("PeliculasDatos.sql",'w') as sql:
	datos = csv.reader(csvfile, delimiter=':')
	for row in datos:
        """
        row

        """
        sql.write("BEGIN;\n")
        # tabla pelicula
		sql.write("INSERT INTO  Pelicula (Nombre_Pelicula,Sipnosis,director,Actores,Distribuidora,Duracion) VALUES(")
		sql.write('"'+row[0]+'",') # Nombre pelicula
		sql.write('"'+row[1]+'",') # Sipnosis
        sql.write('"'+row[2]+'",') # Director
        sql.write('"'+row[3]+'",') # Actores
        sql.write('"'+row[4]+'",') # Distribuidora
        sql.write('"'+row[5]+'"') # Duracion
        sql.write('"'+row[6]+'"') # Extreno **insert?
        sql.write('"'+row[7]+'"') # Fin cartelera **insert?
		sql.write(");\n")
        # Guarda el id de la pelicula
		sql.write("SELECT LAST_INSERT_ID() INTO @mysql_id_ultima_pelicula;\n")

		# tabla Poster
		sql.write("INSERT INTO  Poster (IDPelicula,Poster) VALUES(@mysql_id_ultima_pelicula,")
        sql.write('"'+row[8]+'"') # Poster **insert?
		sql.write(");\n")

        #tabla Idiomas pelicula
		sql.write("INSERT INTO  IdiomasPelicula (IDPelicula,IDIdioma) VALUES(@mysql_id_ultima_pelicula,")
		#split espacio
        sql.write('"'+row[9]+'",') # Idiomas pelicula
		sql.write(");\n")

        # tabla Clasificación pelicula
		sql.write("INSERT INTO  ClasificacionPelicula (IDPelicula,IDClasificacion) VALUES(@mysql_id_ultima_pelicula,")
        sql.write('"'+row[10]+'"') # Clasificacion pelicula
		sql.write(");\n")

		# tabla Generos pelicula
		sql.write("INSERT INTO  GenerosPelicula (IDPelicula,IDGenero) VALUES(@mysql_id_ultima_pelicula,")
		#split espacio
        sql.write('"'+row[11]+'"') # Genero pelicula
		sql.write(");\n")

        #Pares, sala-Horarios hasta terminar la fila
        #desde 12 sala 13 hoario
        # tabla Sala
		sql.write("INSERT INTO  Sala (IDPelicula,Nombre_Sala,Hora) VALUES(@mysql_id_ultima_pelicula,")
		# split ,
        sql.write('"'+row[]+'",') # Nombre sala
		sql.write(");\n")

		sql.write("COMMIT;\n")
		"""
		for numItem in range(len(row)):
			sql.write('"'+row[numItem]+'"')
			if not (numItem == (len(row)-1)):
				sql.write(",")
		"""

