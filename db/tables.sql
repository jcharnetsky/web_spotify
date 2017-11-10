use cardinals;

DROP TABLE IF EXISTS PlaylistCollaborators;
DROP TABLE IF EXISTS SongsInPlaylist;
DROP TABLE IF EXISTS Playlists;
DROP TABLE IF EXISTS SongAudio;
DROP TABLE IF EXISTS AdvertisementListens;
DROP TABLE IF EXISTS SongListens;
DROP TABLE IF EXISTS RevenuePerPlay;
DROP TABLE IF EXISTS CostsPerPlay;
DROP TABLE IF EXISTS Songs;
DROP TABLE IF EXISTS Albums;
DROP TABLE IF EXISTS PlayingAtConcerts;
DROP TABLE IF EXISTS Concerts;
DROP TABLE IF EXISTS ArtistAliases;
DROP TABLE IF EXISTS Artists;
DROP TABLE IF EXISTS Reports;
DROP TABLE IF EXISTS Users ;

CREATE TABLE Users (
	ID INTEGER NOT NULL,
	accStatus ENUM('BASE', 'PREM', 'ADMIN', 'ADVER') NOT NULL,
	email VARCHAR(64) NOT NULL,
	password VARCHAR(1024) NOT NULL,
	salt VARCHAR(1024) NOT NULL,
	name VARCHAR(64) NOT NULL,
	address VARCHAR(64) NOT NULL,
	isPublic BOOLEAN NOT NULL,
	isBanned BOOLEAN NOT NULL,
	birthday DATE NOT NULL,
	image VARCHAR(2048) NOT NULL,

	PRIMARY KEY(ID)
);

INSERT INTO Users VALUES 
	(0,'BASE', 'jack_the_guy@yahoo.com', 'pass', 'salt', 'Jack', 'Strong Island', TRUE, FALSE, NOW(), ''),
	(1,'BASE', 'theJerryShore123@yahoo.com', 'pass', 'salt', 'Jerry', 'The Jersey Shore', TRUE, FALSE, NOW(), '')
;

CREATE TABLE Reports (
	creatorID INTEGER NOT NULL,
	subject VARCHAR(64) NOT NULL,
	description VARCHAR(1024) NOT NULL,
	entity ENUM('USER', 'SONG', 'ALBUM', 'PLAYLIST') NOT NULL,
	entityID INTEGER NOT NULL,

	FOREIGN KEY (creatorID) REFERENCES Users(ID)

);

CREATE TABLE Artists (
	artistID INTEGER NOT NULL,
	stageName VARCHAR(64) NOT NULL,
	about VARCHAR(4096) NOT NULL,

	PRIMARY KEY(artistID),
	FOREIGN KEY(artistID) REFERENCES Users(ID)
);

CREATE TABLE ArtistAliases (
	artistID INTEGER NOT NULL,
	alias VARCHAR(64) NOT NULL,
	PRIMARY KEY (artistID, alias),
	FOREIGN KEY (artistID) REFERENCES Artists(artistID)
);

CREATE TABLE Concerts (
	ID INTEGER NOT NULL,
	name VARCHAR(64) NOT NULL,
	time TIMESTAMP NOT NULL,
	purchaseURL VARCHAR(64) NOT NULL,

	PRIMARY KEY (ID)
);

CREATE TABLE PlayingAtConcerts (
	artistID INTEGER NOT NULL,
	concertID INTEGER NOT NULL,

	PRIMARY KEY(artistID, concertID),
	FOREIGN KEY(artistID) REFERENCES Artists(artistID),
	FOREIGN KEY(concertID) REFERENCES Concerts(ID)
);

CREATE TABLE Albums (
	ID INTEGER NOT NULL,
	title VARCHAR(64) NOT NULL,
	artistID INTEGER NOT NULL,
	image VARCHAR(2048) NOT NULL,
	releaseDate DATE NOT NULL,
	genre ENUM('POP', 'ROCK', 'COUNTRY', 'ELECTRONIC', 'RAP', 'METAL', 'INSTRUMENTAL') NOT NULL,
	isBanned BOOLEAN NOT NULL,
	isPublic BOOLEAN NOT NULL,

	PRIMARY KEY (ID),
	FOREIGN KEY(artistID) REFERENCES Artists(artistID)
);

CREATE TABLE Songs (
	ID INTEGER NOT NULL,
	title VARCHAR(64) NOT NULL,
	artistID INTEGER NOT NULL,
	albumID INTEGER NOT NULL,
	genre ENUM('POP', 'ROCK', 'COUNTRY', 'ELECTRONIC', 'RAP', 'METAL', 'INSTRUMENTAL') NOT NULL,
    isBanned BOOLEAN NOT NULL,
    isPublic BOOLEAN NOT NULL,
	trackLength INTEGER NOT NULL,
	trackPos INTEGER NOT NULL,

	PRIMARY KEY(ID),
    FOREIGN KEY(artistID) REFERENCES Artists(artistID),
    FOREIGN KEY(albumID)  REFERENCES Albums(ID),

	CHECK(trackPos >= 1),
	CHECK(trackLength > 0)	

);

CREATE TABLE CostsPerPlay (
	advertID INTEGER NOT NULL,
	costPerClick DECIMAL(12, 2) NOT NULL,

	PRIMARY KEY(advertID),
	FOREIGN KEY(advertID) REFERENCES Songs(ID),

	CHECK(costPerClick >= 0)
);

CREATE TABLE RevenuePerPlay (
    songID INTEGER NOT NULL,
    revPerPlay DECIMAL(12, 2) NOT NULL,

    PRIMARY KEY(songID),
    FOREIGN KEY(songID) REFERENCES Songs(ID),

    CHECK(revPerClick >= 0)
);


CREATE TABLE SongListens (
	songID  INTEGER NOT NULL,
	month   INTEGER NOT NULL,
	year    INTEGER NOT NULL,
	count   INTEGER NOT NULL,
	revenue DECIMAL(12,2) NOT NULL,
	
	PRIMARY KEY(songID),
	FOREIGN KEY(songID) REFERENCES Songs(ID),

	CHECK (month >= 1),
	CHECK (month <= 12),
	CHECK (year > 0),
	CHECK (count > 0),
	CHECK (revenue > 0)

);

CREATE TABLE AdvertisementListens (
	songID  INTEGER NOT NULL,
	month   INTEGER NOT NULL,
	year    INTEGER NOT NULL,
	count   INTEGER NOT NULL,
	revenue DECIMAL(12,2) NOT NULL,
	
	PRIMARY KEY(songID),
	FOREIGN KEY(songID) REFERENCES Songs(ID),

	CHECK (month >= 1),
	CHECK (month <= 12),
	CHECK (year > 0),
	CHECK (count > 0),
	CHECK (revenue > 0)

);

CREATE TABLE SongAudio (
	songID INTEGER NOT NULL,
	audio  BLOB NOT NULL,

    PRIMARY KEY(songID),
    FOREIGN KEY(songID) REFERENCES Songs(ID)
);

CREATE TABLE Playlists (
	ID INTEGER NOT NULL,
	dateCreated DATE NOT NULL,
	isPublic BOOLEAN NOT NULL,
	isDeleted BOOLEAN NOT NULL,
	creator INTEGER NOT NULL,
	image VARCHAR(2048) NOT NULL,
	
	PRIMARY KEY(ID),
	FOREIGN KEY(creator) REFERENCES Users(ID)
);

CREATE TABLE SongsInPlaylist (
	songID INTEGER NOT NULL,
	playlistID INTEGER NOT NULL,
	indexNumber INTEGER NOT NULL,

    PRIMARY KEY(songID, playlistID),
    FOREIGN KEY(songID) REFERENCES Songs(ID),
    FOREIGN KEY(playlistID) REFERENCES Playlists(ID),

	CHECK (indexNumber >= 0)
);

CREATE TABLE PlaylistCollaborators (
    playlistID INTEGER NOT NULL,
	userID INTEGER NOT NULL,

    PRIMARY KEY(userID, playlistID),
    FOREIGN KEY(userID) REFERENCES Users(ID),
    FOREIGN KEY(playlistID) REFERENCES Playlists(ID)
);
