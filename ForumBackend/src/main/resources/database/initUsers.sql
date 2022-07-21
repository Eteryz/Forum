INSERT INTO users (city, email, avatar, login, name, phone)
VALUES ('Samara','user1@mail.ru','file.img', 'user1','Ivan','+79023458765'),
       ('Moscow','user2@mail.ru','file.img', 'user2','Petr','+78723458765'),
       ('Samara','user3@mail.ru','file.img', 'user3','Diman','+79022758765');

select * from users;
select * from user_roles;

DELETE from users;
