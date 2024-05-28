INSERT INTO public.movies (
    title, orig_title, release_date, genres, actors, directors, script, producers, synopsis, original_version, spanish_version, image, trailer, age_rating, duration
) VALUES (
             'El Origen', 'Inception', '2010-07-16', 'Sci-Fi, Thriller', 'Leonardo DiCaprio, Joseph Gordon-Levitt, Ellen Page', 'Christopher Nolan', 'Christopher Nolan', 'Emma Thomas, Christopher Nolan', 'Un ladrón que roba secretos corporativos a través del uso de tecnología de compartir sueños recibe la tarea inversa de plantar una idea en la mente de un CEO.', true, false, 'inception.jpg', 'https://youtu.be/YoHD9XEInc0', 'PG-13', 148
         ), (
             'Interestelar', 'Interstellar', '2014-11-07', 'Adventure, Drama, Sci-Fi', 'Matthew McConaughey, Anne Hathaway, Jessica Chastain', 'Christopher Nolan', 'Jonathan Nolan, Christopher Nolan', 'Lynda Obst, Christopher Nolan', 'Un grupo de exploradores viaja a través de un agujero de gusano en el espacio en un intento de asegurar la supervivencia de la humanidad.', true, false, 'interstellar.jpg', 'https://youtu.be/zSWdZVtXT7E', 'PG-13', 169
         )
ON CONFLICT (title, orig_title) DO NOTHING;

INSERT INTO public.cinemas (
     address, email, name, phone
) VALUES (
              '123 Calle Falsa', 'contacto@cineMM.com', 'CinemaMM', '666-666-666'
         ) ON CONFLICT (name) DO NOTHING;