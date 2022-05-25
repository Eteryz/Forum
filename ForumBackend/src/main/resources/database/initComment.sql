INSERT INTO comments (text, dislikes, likes, articles_id, users_id)
VALUES (
           'Oyyyyy, good articleEntity!!!!',
           0,
           7,
           (select id from articles where id=1),
           (select id from users where name='Ivan')),

       ('!Очень крутая статья))',
        1,
        3,
        (select id from articles where id=2),
        (select id from users where name='Diman'));


select * from comments;
