INSERT INTO users (username, email, password, )
VALUES ('Vlad','user1@mail.ru','123456'),
       ('Moscow','user2@mail.ru',''),
       ('Samara','user3@mail.ru','');

select * from users;
select * from user_roles;

DELETE from user_roles where user_id =(select id from users where username='Ivan');
DELETE from users where username='Ivan' ;
