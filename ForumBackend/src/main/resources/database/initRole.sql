INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');

select * from roles;

Insert into user_roles(user_id, role_id)
VALUES ((select  id from users where username='Ivan'),'3');

delete from user_roles where role_id='3'
