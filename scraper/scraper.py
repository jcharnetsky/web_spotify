# The main scraper
import requests, json, pdb, time, sys
import classes

# Main Method, get a list of artists and scrape their information using musicbrainz
def scrape (artistLocation, outputFile):
	artists = []

	with open(artistLocation) as f:
		artists = json.load(f)

	# Create lists of artist/album/song objects that have been scraped
	songList   = []
	albumList  = []
	artistList = []

	# iterate through each artist and scrape for information
	for artist in artists:
		try:
			# Search for the artist and get his/her id
			artistId        = musicBrainzScrape_searchArtist(artist)
			# Scrape the artist's information
			artistObject    = musicBrainzScrape_scrapeArtistInformation(artistId)

			# If artist has no album information, moveon
			if(len(artistObject.getAlbumIdList()) == 0):
				continue

			# Scrape all album information associated with the artist
			albumObjectList = [musicBrainzScrape_scrapeAlbumInformation(albumId) for albumId in artistObject.getAlbumIdList()]
			# Scrape all song information about each album
			songObjectList  = [musicBrainzScrape_scrapeSongInformation(album.getReleaseId()) for album in albumObjectList]
			
			artistList.append(artistObject)
			for albumObject in albumObjectList:
				albumList.append(albumObject)
			for songArray in songObjectList:
				for song in songArray:
					songList.append(song)
		except:
			break	

	with open(outputFile, "w+") as f:
		print("Artists scraped: ")
		for art in artistList:
			print(art.artistName)

		songDicts    = [song.produceDict() for song in songList]
		albumDicts   = [album.produceDict() for album in albumList]
		artistDicts  = [artist.produceDict() for artist in artistList]

		mainDict     = {"artists":artistDicts, "albums":albumDicts, "songs":songDicts}
	
		json.dump(mainDict, f, sort_keys=True, indent=4, separators=(',', ': '))


# A wrapper on requests get method where if response is 503, wait sleep and attempt to procede
def sendGet (url, parameters=None):
	returnVal = None
	correctReturn = False
	while not correctReturn:
		time.sleep(1)
		r = requests.get(url, params=parameters)
		if(r.status_code == 200):
			correctReturn = True
			returnVal = r.json()
		else:
			print("HTTP GET Has status code: %d ... retrying" % (r.status_code))
	return returnVal
	

# Searches for the artist name on musicBrainz. Returns the artist's unique music brainz id.
def musicBrainzScrape_searchArtist (artistName):

	# Set the link to send the HTTP GET and the required parameters to be used
	link = "https://musicbrainz.org/ws/2/artist/"
	parameters = {"query" : "artist:%s" % (artistName), "fmt":"json"}

	# Perform the HTTP GET request and convert it to json
	responseJson = sendGet(link,parameters)
	
	# Take the top result and get the id
	uniqueId = responseJson["artists"][0]["id"]

	print("Searching for artist: %s" % (artistName))

	return uniqueId

# Polls musicbrainz with the given artist ID and returns an artist object 
def musicBrainzScrape_scrapeArtistInformation (artistId):

	# Set the link to send the HTTP GET and the required parameters to be used
	link = "https://musicbrainz.org/ws/2/artist/%s" % (artistId)
	parameters = {"inc":"aliases+release-groups", "fmt":"json"}

	# Perform the HTTP GET request and convert it to json
	responseJson = sendGet(link,parameters)

	# Get the name of the artist
	artistName  = responseJson["name"]

	# Get the aliases the artist goes by
	artistAlias = [alias["name"] for alias in responseJson["aliases"] if alias["name"] != artistName]

	# Get the area that the artist resides
	area        = None
	if ("area" in responseJson and responseJson["area"] != None):
		area    = responseJson["area"]["name"]

	# Get the list of albums plus the album ids
	albumList   = [album["title"] for album in responseJson["release-groups"]]
	albumIdList = [album["id"] for album in responseJson["release-groups"]]
		#albumType = album["primary-type"]
		#if ("secondary-types" in album):
		#	albumType = albumType + "_" + album["secondary-types"][0]

	print("Scraped artist: %s" % (artistName))

	# Create and return an artist object with the information we scraped
	artistObject = classes.artist(artistName, artistAlias, albumList, albumIdList, area)
	return artistObject

# Polls musicbrains with the given album ID and resturns an album object
def musicBrainzScrape_scrapeAlbumInformation (albumId):

	# Set the link to send the HTTP GET and the required parameters to be used
	link = "https://musicbrainz.org/ws/2/release-group/%s" % (albumId)
	parameters = {"inc":"releases+tags", "fmt":"json"}
	
	# Perform the HTTP GET request and convert it to json
	responseJson = sendGet(link,parameters)

	# Get the album's name
	albumName  = responseJson["title"]

	# Get the album's releasedate 
	date       = responseJson["first-release-date"]

	
	# Get the list of songs the album contains plus the id
	releaseId  = responseJson["releases"][0]["id"]
	songList,songIdList = musicBrainzScrape_scrapeReleaseInformation(releaseId)


	# Get the album's type, if the album has a secondary type, we will append as primary_secondary
	albumType = responseJson["primary-type"]
	if (len(responseJson["secondary-types"]) > 0):
		albumType += "_" + responseJson["secondary-types"][0]

	print("Scraped album: %s" % (albumName))

	# Create and return an album object with the information we scraped
	albumObject = classes.album(albumType, albumName, date, songList, releaseId)
	return albumObject

# Polls musicbrains with the given release ID and returns a tuple of list of ([songTitle], [songId])
def musicBrainzScrape_scrapeReleaseInformation(releaseId):

	# Set the link to send the HTTP GET and the required parameters to be used
	link = "https://musicbrainz.org/ws/2/release/%s" % (releaseId)
	parameters = {"inc":"artist-credits+labels+discids+recordings", "fmt":"json"}
	
	# Perform the HTTP GET request and convert it to json
	responseJson = sendGet(link,parameters)
	songName = [song["title"] for song in responseJson["media"][0]["tracks"]]
	songId   = [song["id"] for song in responseJson["media"][0]["tracks"]]

	#print("Scraped Release Information, %d songs found for releaseid: %s" % (len(songName), releaseId))

	return (songName, songId)


# Polls musicbrains with the given release ID and returns a list of song objects
def musicBrainzScrape_scrapeSongInformation (releaseId):

	# Set the link to send the HTTP GET and the required parameters to be used
	link = "https://musicbrainz.org/ws/2/release/%s" % (releaseId)
	parameters = {"inc":"recordings", "fmt":"json"}
	
	# Perform the HTTP GET request and convert it to json
	responseJson = sendGet(link,parameters)
	# Populate and return a list of song objects
	arr = [classes.song(responseJson["title"],s["title"],s["length"],s["position"]) for s in responseJson["media"][0]["tracks"]]	

	print ("Scraped %d songs from release id: %s" % (len(arr), releaseId))	

	return arr



if __name__ == "__main__":
	scrape(sys.argv[1], sys.argv[2])


