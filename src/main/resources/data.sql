INSERT INTO public.movies
    (id_movie, title, orig_title, release, genres, actors, directors, script, producers, synopsis, original_version, spanish_version, image, trailer, age_rating, duration)
VALUES
  (1, 'Inception', 'Inception', '2010-07-16', 'Sci-Fi, Thriller', 'Leonardo DiCaprio, Joseph Gordon-Levitt', 'Christopher Nolan', 'Christopher Nolan', 'Emma Thomas, Christopher Nolan', 'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO.', true, false, 'inception.jpg', 'https://youtu.be/YoHD9XEInc0', 'PG-13', 148),
  (2, 'The Matrix', 'The Matrix', '1999-03-31', 'Action, Sci-Fi', 'Keanu Reeves, Laurence Fishburne', 'Lana Wachowski, Lilly Wachowski', 'Lana Wachowski, Lilly Wachowski', 'Joel Silver', 'A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.', true, true, 'matrix.jpg', 'https://youtu.be/m8e-FF8MsqU', 'R', 136),
  (3, 'Interstellar', 'Interstellar', '2014-11-07', 'Adventure, Drama, Sci-Fi', 'Matthew McConaughey, Anne Hathaway', 'Christopher Nolan', 'Jonathan Nolan, Christopher Nolan', 'Lynda Obst, Christopher Nolan', 'A team of explorers travel through a wormhole in space in an attempt to ensure humanity''s survival.', true, false, 'interstellar.jpg', 'https://youtu.be/zSWdZVtXT7E', 'PG-13', 169),
  (4, 'The Dark Knight', 'The Dark Knight', '2008-07-18', 'Action, Crime, Drama', 'Christian Bale, Heath Ledger', 'Christopher Nolan', 'Jonathan Nolan, Christopher Nolan', 'Christopher Nolan, Charles Roven', 'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.', true, true, 'dark_knight.jpg', 'https://youtu.be/EXeTwQWrcwY', 'PG-13', 152)
    ON CONFLICT (id_movie) DO NOTHING;
INSERT INTO public.cinemas (id, name, address, phone, email)
VALUES
    (1, 'Cinema MM', 'Calle falsa 123', '666666666', 'cinemm@gmail.com')
    ON CONFLICT (id) DO NOTHING;
INSERT INTO public.screens (id_screen, id_cinema, distribution, supports)
VALUES
    (1, 1, 'NNNNNN-NNNNNNNNNN-NNNNNN#NNNNNN-NNNNNNNNNN-NNNNNN#NNNNNN-NNNNNNNNNN-NNNNNN#NNNNNN-NNNNNNNNNN-NNNNNN#', 'Digital, 3D'),
    (2, 1, 'NNNNNN-PPPPPPPPPP-NNNNNN#NNNNNN-PPPPPPPPPP-NNNNNN#NNNNNN-PPPPPPPPPP-NNNNNN#', 'Digital, IMAX'),
    (3, 1, 'NNNNNN-NNNNNNNNNN-NNNNNN#NNNNNN-NNNNNNNNNN-NNNNNN#NNNNNN-NNNNNNNNNN-NNNNNN#NNNNNN-NNNNNNNNNN-NNNNNN#', '4K, Atmos'),
    (4, 1, 'EEE-EEEEE-EEE#', 'VIP, 4K')
    ON CONFLICT (id_screen) DO NOTHING;