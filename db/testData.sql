use cardinals;

INSERT INTO Users(userType,email,password,salt,name,address,isPublic,isBanned,isPremium,birthdate,image) VALUES
    ('BASE','jack_the_guy@yahoo.com', 'pass', 'salt', 'Jack', 'Strong Island', TRUE, FALSE, TRUE, NOW(), ''),
    ('BASE','theJerryShore123@yahoo.com', 'pass', 'salt', 'Jerry', 'The Jersey Shore', TRUE, FALSE, TRUE, NOW(), '')
;

insert into reports(creatorId, subject, description, entity, entityID) values (1, 'Subject', 'description', 'USER', 1);
insert into reports(creatorId, subject, description, entity, entityID) values (1, 'Subject', 'description', 'USER', 1);
insert into reports(creatorId, subject, description, entity, entityID) values (1, 'Subject', 'description', 'USER', 1);

INSERT INTO Playlists VALUES
	(1, 'The Coolest Tracks ev3r', NOW(), TRUE, FALSE, 1, '', 'This is the coolest playlist ev3r');

INSERT INTO PlaylistFollowers VALUES 
	(1, 1),
	(1, 2);

