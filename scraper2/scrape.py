import requests,json,sys,random,datetime,pdb
from dateutil import parser
import time

def loadJson(filename):
	with open(filename) as f:
		return json.load(f)
	return None

def generateStartingId():
	return random.randrange(1000,10000)

def addAll(collection, subArray):
	for element in subArray:
		collection.append(element)

def pollArtistInformation(token, startingId):
	link = "http://localhost:5000/ws/2/artist/%s?inc=aliases+tags&fmt=json"
	r = requests.get(link%(token))
	artistJson = r.json()
	responseJson = {}
	responseJson["name"] = artistJson["name"]
	responseJson["aliases"] = [alias["name"] for alias in artistJson["aliases"]]
	responseJson["address"] = artistJson["area"]["name"]
	responseJson["id"] = startingId
	dt = parser.parse(artistJson["life-span"]["begin"])
	responseJson["birthday"] = dt.strftime('%Y-%m-%d')
	return [responseJson]

def pollAlbumInformation(token, artistId, startingId):
	link = "http://localhost:5000/ws/2/release-group?artist=%s&inc=tags&fmt=json"
	r = requests.get(link%(token))
	albumJsons = r.json()

	returnAlbums = []
	for albumJson in albumJsons["release-groups"]:
		
		if("first-release-date" not in albumJson or len(albumJson["first-release-date"])==0):
			continue
		if(len(albumJson["tags"])==0):
			continue

		responseAlbum = {}
		responseAlbum["id"] = startingId
		responseAlbum["artistId"] = artistId
		responseAlbum["title"] = albumJson["title"]
		responseAlbum["genre"] = [tag["name"] for tag in albumJson["tags"]]
		dt = parser.parse(albumJson["first-release-date"])
		responseAlbum["release"] = dt.strftime('%Y-%m-%d')
		responseAlbum["music_id"] = albumJson["id"]
		startingId += 1
		returnAlbums.append(responseAlbum)

	return returnAlbums

def pollSongInformation(artistToken, albumToken, artistId, albumId, tags, startingId):
	link = "http://localhost:5000/ws/2/release?release-groups=%s&artist=%s&inc=recordings&fmt=json"
	r = requests.get(link%(albumToken,artistToken))
	songJsons = r.json()

	trackTitle = {}

	songsToReturn = []
	for releaseGroup in songJsons["releases"]:
		if (releaseGroup["status"] != "Official"):
			continue
		if (not releaseGroup["cover-art-archive"]["front"]):
			continue
		for songJson in releaseGroup["media"][0]["tracks"]:

			if(songJson["length"] == None):
				continue
			if(songJson["title"] in trackTitle):
				continue
			
			trackTitle[songJson["title"]] = True

			songResponse = {}
			songResponse["id"] = startingId
			songResponse["artistId"] = artistId
			songResponse["albumId"] = albumId
			songResponse["title"] = songJson["title"]
			songResponse["genre"] = tags
			songResponse["length"] = songJson["length"]

			songsToReturn.append(songResponse)
			startingId += 1


	return songsToReturn

def main (filename):
	artistTokens = loadJson(filename)

	artistId = generateStartingId()
	albumId  = generateStartingId()
	songId   = generateStartingId()

	artists = []
	albums  = []
	songs   = []

	for token in artistTokens:
		artistJsons = pollArtistInformation(token, artistId)
		albumJsons = pollAlbumInformation(token, artistId, albumId)	
		
		for album in albumJsons:						
			songJsons = pollSongInformation(token, album["music_id"], artistId, album["id"], album["genre"], songId)
			addAll(songs, songJsons)
			songId += len(songJsons)

		addAll(albums, albumJsons)
		addAll(artists, artistJsons)
		artistId += len(artistJsons)
		albumId  += len(albumJsons)

	print("Trawling Finished.")
	print("Stats:")
	print("%d Artists" % (len(artists)))
	print("%d Albums" % (len(albums)))
	print("%d Songs" % (len(songs)))

	output = {"artists":artists, "albums":albums, "songs":songs}
	with open('result.json', 'w') as fp:
		json.dump(output, fp)
	

if __name__ == "__main__":
	filename = sys.argv[1]
	main(filename)
