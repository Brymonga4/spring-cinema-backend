SELECT setval('public.bookings_booking_id_seq', 1, false);
SELECT setval('public.cinemas_id_seq', 1, false);
SELECT setval('public.movies_movie_id_seq', 1, false);
SELECT setval('public.reviews_review_id_seq', 1, false);
SELECT setval('public.screen_rows_row_id_seq', 1, false);
SELECT setval('public.screenings_screening_id_seq', 1, false);
SELECT setval('public.screens_id_screen_seq', 1, false);
SELECT setval('public.seats_seat_id_seq', 1, false);
SELECT setval('public.tickets_ticket_id_seq', 1, false);
SELECT setval('public.users_user_id_seq', 1, false);


INSERT INTO public.movies (
    title, orig_title, release_date, genres, actors, directors, script, producers, synopsis, original_version, spanish_version, image, trailer, age_rating, duration
) VALUES (
             'Hit Man. Asesino por casualidad', 'Hit Man',
             '2024-06-07', 'Acción, Comedia',
             'Adria Arjona, Austin Amelio, Sanjay Rao, Mike Markoff, Molly Bernard, Gralen Bryant Banks, Retta , Evan Holtzman, Glen Powell',
             'Richard Linklater',
             'Richard Linklater, Glen Powell, Skip Hollandsworth',
             'Jason Bateman, Mike Blizzard, Michael Costigan, Richard Linklater, Glen Powell',
             'Gary Johnson (Glen Powell) es el asesino profesional más buscado de Nueva Orleans. Un misterioso pistolero a sueldo… que trabaja para la policía. Cuando rompe el protocolo para ayudar a una mujer desesperada (Adria Arjona) que intenta huir de un marido maltratador, acaba convirtiéndose en uno de sus falsos personajes y coqueteará con transformarse en un verdadero criminal.',
             true, true, 'hitman.jpg',
             'https://www.youtube.com/embed/dm4KP55jdqI', 'PG-16', 113

         ),

         (
             'Bad Boys: Ride or die', 'Bad Boys: Ride or die',
             '2024-06-07', 'Acción, Comedia, Aventura',
             'Tiffany Haddish, Will Smith, Jacob Scipio, Vanessa Hudgens, Martin Lawrence, Joe Pantoliano, Alexander Ludwig, Paola Nunez, Ioan Gruffudd, Eric Dane, Tasha Smith, Rhea Seehorn, Melanie Liburd',
             'Adil El Arbi, Bilall Fallah',
             'Chris Bremner',
             'Jerry Bruckheimer, Will Smith, Chad Oman, Doug Belgrad',
             'Este verano, los policías más famosos del mundo regresan con su icónica mezcla de acción al límite y comedia escandalosa, pero esta vez con un giro inesperado: ¡Los mejores de Miami se dan a la fuga!.',
             true, true, 'badboys.jpg',
             'https://www.youtube.com/embed/LOZqqEXURzg', 'PG-16', 117
         ),
         (
             'El reino del planeta de los simios', 'Kingdom of the Planet of the Apes',
             '2024-05-10', 'Acción, Ciencia ficción',
             'Kevin Durand, Freya Allan, Peter Macon, Owen Teague, William H. Macy',
             'Wes Ball',
             'Josh Friedman',
             'Wes Ball, Joe Hartwick Jr., Rick Jaffa, Amanda Silver, Jason Reed',
             'Ambientada varias generaciones en el futuro tras el reinado de César, en la que los simios son la especie dominante que vive en armonía y los humanos se han visto reducidos a vivir en la sombra. Mientras un nuevo y tiránico líder simio construye su imperio, un joven simio emprende un angustioso viaje que le llevará a cuestionarse todo lo que sabe sobre el pasado y a tomar decisiones que definirán el futuro de simios y humanos por igual.',
             true, true, 'monki.jpg',
             'https://www.youtube.com/embed/OcBktw-5QlE', 'PG-12', 145
         ),

         (
             'Furiosa: de la saga Mad Max', 'Hit Man',
             '2024-05-24', 'Acción',
             'Anya Taylor-Joy, Chris Hemsworth, Tom Burke, Alyla Browne',
             'George Miller',
             'George Miller, Nick Lathouris',
             'Doug Mitchell, George Miller',
             'Al caer el mundo, la joven Furiosa (Anya Taylor-Joy) es arrebatada del Lugar Verde de Muchas Madres y cae en manos de una gran Horda de Motoristas liderada por el Señor de la Guerra Dementus. Arrasando el Páramo, se topan con la Ciudadela presidida por El Inmortan Joe. Mientras los dos Tiranos luchan por el dominio, Furiosa debe sobrevivir a muchas pruebas mientras reúne los medios para encontrar el camino de vuelta a casa.',
             true, true, 'hitman.jpg',
             'https://www.youtube.com/embed/GgerHF86Rv4', 'PG-16', 148
         )
ON CONFLICT (title, orig_title) DO NOTHING;

INSERT INTO public.cinemas (
     address, email, name, phone
) VALUES (
              '123 Calle Falsa', 'contacto@cineMM.com', 'FilMM', '666-666-666'
         ) ON CONFLICT (name) DO NOTHING;


INSERT INTO public.users (
    user_id, nickname, password, name, surname, email, phone, points, premium, admin, token, recover_code
) VALUES
      (
          1, 'bryan', '$2a$12$pj4F7oCj6gV8T675.IXpq.Dn1/Q7hBjph54Oco4Zn3Pd75vedOYGO', 'Fulano', 'García', 'nostaln44@gmail.com', '123-456-7891', 100, false, false, 'dummyToken1', 'dummyRecoverCode1'
      ),
      (
          2, 'premium', '$2a$12$VmC9dzDJU1uVtQU7nVi.ZO5b88cEWh7by6Gr6YSNUEfqzxquZ5xf.', 'Fulanazo', 'Pichón', 'janesmith@example.com', '123-456-7892', 200, true, false, 'dummyToken2', 'dummyRecoverCode2'
      ),
      (
          3, 'admin', '$2a$12$AxiZIgsRdefj/pQEGbgOfulSqfRPhxty/egt/u.8g0R1X2pTI8P3G', 'Adminazo', 'Admin', 'bryan.montesdeoca1@educa.madrid.org', '123-456-7893', 300, false, true, 'dummyToken3', 'dummyRecoverCode3'
      )ON CONFLICT (user_id) DO NOTHING;

