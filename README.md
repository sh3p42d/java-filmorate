# java-filmorate
Template repository for Filmorate project.

Схема связей базы данных:
![DB Scheme](filmorate_db_scheme.png)

Примеры запросов: 
1. Получить всех пользователей:
SELECT *
FROM users

2. Получить всех друзей пользователя:
SELECT *
FROM users AS u
JOIN friends AS f ON u.user_id = f.user_id

3. Получить все фильмы:
SELECT *
FROM films

4. Получить всех пользователей лайкнувших фильм:
SELECT *
FROM user AS u
JOIN likes AS l ON u.user_id = l.user_id
WHERE l.film_id={id}

5. Получить жанры фильма:
SELECT name
FROM genre AS g
JOIN genre_film AS gf g.genre_id = gf.genre_id
WHERE gf.film_id = {id}