INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

select * from roles;

Insert into user_roles(user_id, role_id)
VALUES ((select  id from users where username='Vladislav'),'3');
