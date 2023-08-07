# java-filmorate
Template repository for Filmorate project.

Схема связей базы данных:
![DB Scheme](filmorate_db_scheme.png)

1. Получить всех пользователей:
```sql
SELECT * FROM USERS 
```

2. Получить всех друзей пользователя:
```sql
SELECT * FROM USERS, FRIENDS 
WHERE USERS.USER_ID = FRIENDS.FRIEND_ID AND FRIENDS.USER_ID = ?
```

3. Добавить новый фильм:
```sql
INSERT INTO FILMS (FILM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID)
VALUES(?,?,?,?,?)
```

4. Обновить фильм:
```sql
UPDATE FILMS
SET FILM_NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, MPA_ID = ?
WHERE FILM_ID = ?
```

5. Получить самые популярные фильмы:
```sql
SELECT * FROM LIKES
RIGHT JOIN FILMS ON LIKES.FILM_ID = FILMS.FILM_ID 
JOIN MPA ON FILMS.MPA_ID = MPA.MPA_ID
GROUP BY FILMS.FILM_ID
ORDER BY COUNT(USER_ID) DESC
LIMIT ?
```