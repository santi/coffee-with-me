
-- Create user accounts
INSERT INTO user_account    (id,    email,          email_verified, image_url,  name,   password,                                                       provider,   provider_id, created)
            values          (1,     'test@test.no', true,           null,       'test', '$2a$10$HPFFMn5OcUe1bY/zyt.Ov.8JMLr.ga9AkA0OAh5Mb6/gkWd8KeRV6', 'LOCAL',    12,          now());
INSERT INTO user_account    (id,    email,          email_verified, image_url,  name,   password,                                                       provider,   provider_id, created)
            values          (2,     'test2@test.no',true,           null,       'test2','$2a$10$HPFFMn5OcUe1bY/zyt.Ov.8JMLr.ga9AkA0OAh5Mb6/gkWd8KeRV6', 'LOCAL',    12,          now());
INSERT INTO user_account    (id,    email,          email_verified, image_url,  name,   password,                                                       provider,   provider_id, created)
            values          (3,     'test3@test.no',true,           null,       'test3','$2a$10$HPFFMn5OcUe1bY/zyt.Ov.8JMLr.ga9AkA0OAh5Mb6/gkWd8KeRV6', 'LOCAL',    12,          now());
INSERT INTO user_account    (id,    email,          email_verified, image_url,  name,   password,                                                       provider,   provider_id, created)
            values          (4,     'test4@test.no',true,           null,       'test4','$2a$10$HPFFMn5OcUe1bY/zyt.Ov.8JMLr.ga9AkA0OAh5Mb6/gkWd8KeRV6', 'LOCAL',    12,          now());

-- Create friend request
insert into friend_request  (id,    created, user_id_from,  user_id_to, status)
            values          (1,     now(),   1,             2,          'PENDING')

-- Create existing friends
INSERT INTO user_friend (user_id_from,  user_id_to)
            values      (3,             4)
INSERT INTO user_friend (user_id_from,  user_id_to)
            values      (4,             3)
