CREATE TABLE IF NOT EXISTS USERS
(
    user_id  INTEGER AUTO_INCREMENT NOT NULL,
    email    VARCHAR(40) NOT NULL,
    login    VARCHAR(20) NOT NULL,
    birthday TIMESTAMP NOT NULL,
    username VARCHAR(50) NOT NULL,
    constraint user_pk
        PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS FRIENDS
(
    friendship_id INTEGER AUTO_INCREMENT NOT NULL,
    user_id   INTEGER NOT NULL,
    friend_id INTEGER NOT NULL,
    constraint friends_pk
        PRIMARY KEY (friendship_id),
    constraint friends_users_friendship_id_fk
        foreign key (user_id) references USERS,
    constraint friends_friend_id_friendship_id_fk
        foreign key (friend_id) references USERS
);

CREATE TABLE IF NOT EXISTS MPA
(
    mpa_id   INTEGER PRIMARY KEY NOT NULL,
    mpa_name VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS GENRES
(
    genre_id   INTEGER PRIMARY KEY NOT NULL,
    genre_name VARCHAR(25) NOT NULL
);

CREATE TABLE IF NOT EXISTS FILMS
(
    film_id      INTEGER AUTO_INCREMENT NOT NULL,
    film_name    VARCHAR(40) NOT NULL,
    description  VARCHAR(200) NOT NULL,
    release_date TIMESTAMP NOT NULL,
    duration     INTEGER NOT NULL,
    mpa_id       INTEGER,
    constraint films_pk
        PRIMARY KEY  (film_id),
    constraint films_rating_mpa_id_fk
        foreign key (mpa_id) references MPA(mpa_id)
);

CREATE TABLE IF NOT EXISTS GENRE_FILM
(
    genre_film_id INTEGER AUTO_INCREMENT NOT NULL,
    film_id  INTEGER NOT NULL,
    genre_id INTEGER NOT NULL,
    constraint genre_film_pk
        PRIMARY KEY (genre_film_id),
    constraint genre_film_films_film_id_fk
        foreign key (film_id) references FILMS(film_id),
    constraint genre_genre_film_genre_id_fk
        foreign key (genre_id) references GENRES(genre_id)
);

CREATE TABLE IF NOT EXISTS LIKES
(
    film_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    constraint likes_pk
        PRIMARY KEY (user_id, film_id),
    constraint likes_films_film_id_fk
        foreign key (film_id) references FILMS(film_id),
    constraint likes_users_user_id_fk
        foreign key (user_id) references USERS
);

CREATE UNIQUE INDEX IF NOT EXISTS user_email_uindex on USERS (email);
CREATE UNIQUE INDEX IF NOT EXISTS user_login_uindex on USERS (login);
