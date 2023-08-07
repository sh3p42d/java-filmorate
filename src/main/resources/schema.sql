CREATE TABLE IF NOT EXISTS USERS
(
    USER_ID INTEGER  auto_increment,
    EMAIL    CHARACTER VARYING not null,
    LOGIN    CHARACTER VARYING not null,
    BIRTHDAY TIMESTAMP         not null,
    USERNAME CHARACTER VARYING not null,
    constraint USER_PK
        PRIMARY KEY  (USER_ID)
);

CREATE TABLE IF NOT EXISTS FRIENDS
(
    FRIENDSHIP_ID INTEGER auto_increment,
    USER_ID   INTEGER not null,
    FRIEND_ID INTEGER not null,
    constraint FRIENDS_PK
        PRIMARY KEY (FRIENDSHIP_ID),
    constraint FRIENDS_USERS_FRIENDSHIP_ID_FK
        foreign key (USER_ID) references USERS,
    constraint FRIENDS_FRIEND_ID_FRIENDSHIP_ID_FK
        foreign key (FRIEND_ID) references USERS
);

CREATE TABLE IF NOT EXISTS MPA
(
    MPA_ID   INTEGER primary key,
    MPA_NAME CHARACTER VARYING not null
);

CREATE TABLE IF NOT EXISTS GENRE
(
    GENRE_ID   INTEGER           not null primary key,
    GENRE_NAME CHARACTER VARYING not null
);

CREATE TABLE IF NOT EXISTS FILMS
(
    FILM_ID      INTEGER auto_increment,
    FILM_NAME    CHARACTER VARYING not null,
    DESCRIPTION  CHARACTER VARYING not null,
    RELEASE_DATE TIMESTAMP         not null,
    DURATION     INTEGER           not null,
    MPA_ID       INTEGER,
    constraint FILMS_PK
        PRIMARY KEY  (FILM_ID),
    constraint FILMS_RATING_MPA_ID_FK
        foreign key (MPA_ID) references MPA(MPA_ID)
);

CREATE TABLE IF NOT EXISTS GENRE_FILM
(
    GENRE_FILM_ID INTEGER auto_increment,
    FILM_ID  INTEGER not null,
    GENRE_ID INTEGER not null,
    constraint GENRE_FILM_PK
        PRIMARY KEY (GENRE_FILM_ID),
    constraint GENRE_FILM_FILMS_FILM_ID_FK
        foreign key (FILM_ID) references FILMS(FILM_ID),
    constraint GENRE_GENRE_FILM_GENRE_ID_FK
        foreign key (GENRE_ID) references GENRE(GENRE_ID)
);

CREATE TABLE IF NOT EXISTS LIKES
(
    FILM_ID INTEGER not null,
    USER_ID INTEGER not null,
    constraint LIKES_PK
        PRIMARY KEY (USER_ID, FILM_ID),
    constraint LIKES_FILMS_FILM_ID_FK
        foreign key (FILM_ID) references FILMS(FILM_ID),
    constraint LIKES_USERS_USER_ID_FK
        foreign key (USER_ID) references USERS
);

CREATE UNIQUE INDEX IF NOT EXISTS USER_EMAIL_UINDEX on USERS (email);
CREATE UNIQUE INDEX IF NOT EXISTS USER_LOGIN_UINDEX on USERS (login);
