MERGE INTO GENRES(genre_id, genre_name)
VALUES  (1, 'Комедия'),
        (2, 'Драма'),
        (3, 'Мультфильм'),
        (4, 'Триллер'),
        (5, 'Документальный'),
        (6, 'Боевик');

MERGE INTO MPA(mpa_id, mpa_name)
VALUES  (1, 'G'),
        (2, 'PG'),
        (3, 'PG-13'),
        (4, 'R'),
        (5, 'NC-17');

INSERT INTO USERS (email, login, username, birthday)
VALUES  ('test1@mail.com', 'test_login1', 'user_name1', DATE '2021-1-1'),
        ('test2@mail.com','test_login2', 'user_name2', DATE '2022-2-2'),
        ('test3@mail.com','test_login3', 'user_name3', DATE '2023-3-3');

INSERT INTO FILMS (film_name, description, release_date, duration, mpa_id)
VALUES  ('I love Linux', 'Help me',  DATE '1951-1-1', 90, 1),
        ('Qwe', 'asd',  DATE '1952-2-2', 90, 2),
        ('zxc', 'qaz',  DATE '1953-3-3', 90, 3);