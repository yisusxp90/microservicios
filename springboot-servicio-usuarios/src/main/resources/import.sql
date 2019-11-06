insert into usuarios (username, password, enabled, nombre, email) values ('jesus', '$2a$10$PXHtnrMExi5ku69jDzmq6OaOHRja14NHLE.9oRqM9UM.Z9qiamnJq', true, 'jesus', 'yisusxp90@gmail.com');
insert into usuarios (username, password, enabled, nombre, email) values ('sebas', '$2a$10$cprmn.oXNA7pSqMfCqmdD.XjcBbo7Yzwua3pwHryE1cLUG2te/jUq', true, 'sebas', 'sebas@gmail.com');

insert into roles (nombre) values ('ROLE_USER');
insert into roles (nombre) values ('ROLE_ADMIN');

insert into usuarios_roles (usuario_id, role_id) values (1,1);
insert into usuarios_roles (usuario_id, role_id) values (2,2);
insert into usuarios_roles (usuario_id, role_id) values (2,1);
