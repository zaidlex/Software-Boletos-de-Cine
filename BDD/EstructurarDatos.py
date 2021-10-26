import csv

#Crear listas de los datos que están en las tablas

with open("peliculas_info.csv", newline='') as csvfile, open("PeliculasDatos.sql",'w') as sql:
    datos = csv.reader(csvfile, delimiter=':')
    for row in datos:
        sql.write("BEGIN;\n")
        # tabla pelicula
        sql.write("INSERT INTO  Pelicula (Nombre_Pelicula,Sipnosis,Director,Actores,Distribuidora,Duracion) VALUES(")
        sql.write('"'+row[0]+'",') # Nombre pelicula
        sql.write('"'+row[1]+'",') # Sipnosis
        sql.write('"'+row[2]+'",') # Director
        sql.write('"'+row[3]+'",') # Actores
        sql.write('"'+row[4]+'",') # Distribuidora
        sql.write('"'+row[5]+'",') # Duracion
        sql.write('STR_TO_DATE("'+ row[6].replace('/','-') +'","%d-%m-%y"),') # Extreno
        sql.write('STR_TO_DATE("'+ row[7].replace('/','-') +'","%d-%m-%y")') # Fin cartelera
        sql.write(");\n")

        # Guarda el id de la pelicula
        sql.write("SELECT LAST_INSERT_ID() INTO @mysql_id_ultima_pelicula;\n")

        # tabla Poster
        sql.write("INSERT INTO  Poster (IDPelicula,Poster) VALUES(@mysql_id_ultima_pelicula,")
        poster = open(row[8],"rb")
        data = poster.read()
        sql.write('"'+str(data)+'"') # Poster
        sql.write(");\n")

        #tabla Idiomas pelicula
        IDIdiomas={"Español":1,"Inglés":2,"Subtitulado":3}
        for idioma in row[9].split(" "):
            sql.write("INSERT INTO  IdiomasPelicula (IDPelicula,IDIdioma) VALUES(@mysql_id_ultima_pelicula,")
            sql.write(str(IDIdiomas[idioma])) # Idiomas pelicula
            sql.write(");\n")

        # tabla Clasificación pelicula
        sql.write("INSERT INTO  ClasificacionPelicula (IDPelicula,IDClasificacion) VALUES(@mysql_id_ultima_pelicula,")
        sql.write('"'+row[10]+'"') # Clasificacion pelicula
        sql.write(");\n")

        # tabla Generos pelicula
        IDGeneros ={"Comedia":1,"Animación":2,"Suspenso":3,"Acción":4,"Familiar":5,"Aventura":6,"Terror":7,"Drama":8}
        for genero in row[11].split(" "):
            sql.write("INSERT INTO  GenerosPelicula (IDPelicula,IDGenero) VALUES(@mysql_id_ultima_pelicula,")
            sql.write(str(IDGeneros[genero])) # Genero pelicula
            sql.write(");\n")

        #Pares, sala-Horarios hasta terminar la fila
        #desde 12 sala 13 hoario
        for sala in range(12,16,2):
            for horario in row[sala+1].split(","):
                # tabla Sala
                sql.write("INSERT INTO  Sala (IDPelicula,Nombre_Sala,Hora) VALUES(@mysql_id_ultima_pelicula,")
                # split ,
                sql.write('"'+row[sala]+'",') # Nombre sala
                sql.write('"'+horario+'"') # Nombre sala
                sql.write(");\n")

        sql.write("COMMIT;\n")
