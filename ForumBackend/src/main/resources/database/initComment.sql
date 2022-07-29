INSERT INTO comments (id, text, dislikes, likes, articles_id, users_id, date_creation)
VALUES (
        '12f06f38-4ab3-455b-890e-6d16291eb882',
           'Oyyyyy, good articleEntity!!!!',
           0,
           7,
        (select id from articles LIMIT 1),
        (select id from users where username='Vladislav'),
        '2022-02-23 16:25:13'),

       (
        '18f06f38-4ab7-459b-890e-6d16291eb882',
        '!Очень крутая статья))',
        1,
        3,
        (select id from articles LIMIT 1),
        (select id from users where username='Vladislav'),
        '2022-04-11 12:55:13');


select * from comments;
