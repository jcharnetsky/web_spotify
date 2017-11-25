

#Contains the artist name and list of albums for which they possess
class artist (object):
	
	def __init__(self, artistName, aliasList, albumList, albumIdList, area):
		self.artistName = artistName
		self.area = area
		self.albumList = albumList
		self.albumIdList = albumIdList
		self.aliasList = aliasList

	def getAlbumIdList (self):
		return self.albumIdList

	def produceDict (self):
		artistDict = {
			"name":self.artistName,
			"area":self.area,
			"albumList":self.albumList,
			"aliasList":self.aliasList
		}
		return artistDict

#Contains the album name, type (live/album/single/ep), the date it came out, and a list of songs
class album (object):
	
	def __init__(self, albumType, albumName, date, songList, releaseId):
		self.albumName = albumName
		self.date = date
		self.songList = songList
		self.releaseId = releaseId
		self.albumType = albumType

	def getReleaseId (self):
		return self.releaseId

	def produceDict (self):
		albumDict = {
			"name":self.albumName,
			"date":self.date,
			"songList": self.songList,
			"type":self.albumType
		}
		return albumDict

class song (object):
	
	def __init__ (self, albumName, songName, length, trackPosition):
		self.albumName     = albumName
		self.songName      = songName
		self.length        = length
		self.trackPosition = trackPosition

	def produceDict (self):
		songDict = {
			"name"          : self.songName,
			"album"         : self.albumName,
			"length"        : self.length,
			"trackPosition" : self.trackPosition
		}
		return songDict
