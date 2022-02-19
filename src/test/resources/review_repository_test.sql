INSERT INTO USER(id, user_type, email, password, pseudonym, phone_number) VALUES (1, 'Moderator' ,'a@a', 'pwd', 'moderator', '000000');
INSERT INTO user(id, user_type, email, password, pseudonym, birth_date) VALUES (2, 'Player', 'b@b', 'pwd', 'player', '2010-10-10');

INSERT INTO game(id, description, name, picture, release_date) VALUES (1, 'description', 'name', 'picture', '2020-10-10');
INSERT INTO game(id, description, name, picture, release_date) VALUES (2, 'description2', 'name2', 'picture2', '2020-10-11');

INSERT INTO review(id, description, moderation_date_time, publication_date_time, score, game_id, moderator_id, player_id) VALUES (1, 'accepted',now(), now() - 10, 15, 1, 1, 2);
INSERT INTO review(id, description, publication_date_time, score, game_id, player_id) VALUES (2, 'pending', now() - 5, 13, 2, 2);