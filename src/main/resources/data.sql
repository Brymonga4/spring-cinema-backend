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


INSERT INTO public.users (
    user_id, nickname, password, name, surname, email, phone, points, premium, admin, token, recover_code
) VALUES
      (
          1, 'bryan', '$2a$12$pj4F7oCj6gV8T675.IXpq.Dn1/Q7hBjph54Oco4Zn3Pd75vedOYGO', 'John', 'Doe', 'johndoe1@example.com', '123-456-7891', 100, false, false, 'dummyToken1', 'dummyRecoverCode1'
      ),
      (
          2, 'premium', '$2a$12$VmC9dzDJU1uVtQU7nVi.ZO5b88cEWh7by6Gr6YSNUEfqzxquZ5xf.', 'Jane', 'Smith', 'janesmith@example.com', '123-456-7892', 200, true, false, 'dummyToken2', 'dummyRecoverCode2'
      ),
      (
          3, 'admin', '$2a$12$AxiZIgsRdefj/pQEGbgOfulSqfRPhxty/egt/u.8g0R1X2pTI8P3G', 'Alice', 'Johnson', 'alicejohnson@example.com', '123-456-7893', 300, false, true, 'dummyToken3', 'dummyRecoverCode3'
      )ON CONFLICT (user_id) DO NOTHING;
