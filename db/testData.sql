use cardinals;

INSERT INTO Users(accStatus,email,password,salt,name,address,isPublic,isBanned,birthday,image) VALUES
    ('BASE', 'jack_the_guy@yahoo.com', 'pass', 'salt', 'Jack', 'Strong Island', TRUE, FALSE, NOW(), ''),
    ('BASE', 'theJerryShore123@yahoo.com', 'pass', 'salt', 'Jerry', 'The Jersey Shore', TRUE, FALSE, NOW(), '')
;

INSERT INTO Playlists VALUES
	(1, 'The Coolest Tracks ev3r', NOW(), TRUE, FALSE, 1, '', 'This is the coolest playlist ev3r');

INSERT INTO PlaylistFollowers VALUES 
	(1, 1),
	(1, 2);

