INSERT INTO user_account (id, email, email_verified, image_url, name, password, provider, provider_id) values (1, 'test@test.no', TRUE , NULL, 'test', '$2a$10$HPFFMn5OcUe1bY/zyt.Ov.8JMLr.ga9AkA0OAh5Mb6/gkWd8KeRV6', 'LOCAL', 12);
INSERT INTO user_account (id, email, email_verified, image_url, name, password, provider, provider_id) values (2, 'test2@test.no', TRUE , NULL, 'test2', '$2a$10$HPFFMn5OcUe1bY/zyt.Ov.8JMLr.ga9AkA0OAh5Mb6/gkWd8KeRV6', 'LOCAL', 12);
insert into friend_request (id, accepted, from_id, to_id) values (1, null , 1, 2)
