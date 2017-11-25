import requests,json,sys,random,datetime,pdb,re,urllib.request
import lxml.html as HTML
from dateutil import parser
import time

def loadJson(filename):
	with open(filename) as f:
		return json.load(f)
	return None

def convertArtistName(name):
	return name.replace(" ", "_")

def gatherHTML(name):
	time.sleep(1)
	r = requests.get("https://en.wikipedia.org/wiki/%s"%(name))
	return r.text

def extractImageURLFromHTML(html):
	document = HTML.fromstring(html)
	imageLink = document.find_class("image")[0].getchildren()[0].attrib['src']
	imageLink = "https:" + imageLink
	return imageLink

def downloadImage(imageURL, identifier):
	fileDownload = "artistImages/%d.jpg" % (identifier)
	urllib.request.urlretrieve(imageURL, fileDownload)

def main (filename):
	configuration = loadJson(filename)
	
	startingId = configuration["id_start"]

	for artist in configuration["artists"]:
		print("Scraping for: %s" % (artist))
		correctName = convertArtistName(artist)
		html = gatherHTML(correctName)
		imageLocation = extractImageURLFromHTML(html)
		downloadImage(imageLocation, startingId)
		startingId += 1

if __name__ == "__main__":
	filename = sys.argv[1]
	main(filename)
