CREATE TABLE "users" (
  "user_id" integer PRIMARY KEY,
  "email" varchar,
  "login" varchar,
  "name" varchar,
  "birthday" date
);

CREATE TABLE "friends" (
  "user_id" integer PRIMARY KEY,
  "friend_id" integer
);

CREATE TABLE "films" (
  "film_id" integer PRIMARY KEY,
  "name" varchar,
  "description" varchar,
  "release_date" date,
  "duration" integer,
  "mpa_id" integer
);

CREATE TABLE "likes" (
  "film_id" integer PRIMARY KEY,
  "user_id" integer
);

CREATE TABLE "mpa" (
  "mpa_id" integer PRIMARY KEY,
  "name" integer
);

CREATE TABLE "genre" (
  "genre_id" integer PRIMARY KEY,
  "name" integer
);

CREATE TABLE "genre_film" (
  "film_id" integer PRIMARY KEY,
  "genre_id" integer
);

ALTER TABLE "users" ADD FOREIGN KEY ("user_id") REFERENCES "friends" ("user_id");

ALTER TABLE "likes" ADD FOREIGN KEY ("film_id") REFERENCES "films" ("film_id");

ALTER TABLE "likes" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("user_id");

ALTER TABLE "films" ADD FOREIGN KEY ("mpa_id") REFERENCES "mpa" ("mpa_id");

ALTER TABLE "genre_film" ADD FOREIGN KEY ("genre_id") REFERENCES "genre" ("genre_id");

ALTER TABLE "genre_film" ADD FOREIGN KEY ("film_id") REFERENCES "films" ("film_id");
