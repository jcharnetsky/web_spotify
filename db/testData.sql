use cardinals;

INSERT INTO Users VALUES
    (0,'BASE', 'jack_the_guy@yahoo.com', 'pass', 'salt', 'Jack', 'Strong Island', TRUE, FALSE, NOW(), ''),
    (1,'BASE', 'theJerryShore123@yahoo.com', 'pass', 'salt', 'Jerry', 'The Jersey Shore', TRUE, FALSE, NOW(), '')
;

INSERT INTO Playlists VALUES
	(0, 'The Coolest Tracks ev3r', NOW(), TRUE, FALSE, 0, '', 'This is the coolest playlist ev3r');

INSERT INTO PlaylistFollowers VALUES 
	(0, 0),
	(0, 1);

