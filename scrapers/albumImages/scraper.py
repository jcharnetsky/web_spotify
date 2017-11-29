import requests,json,sys,random,datetime,pdb,re,urllib.request
import lxml.html as HTML
from dateutil import parser
import time

def loadJson(filename):
	with open(filename) as f:
		return json.load(f)
	return None

def getGroupURLFromID(id):
	return "http://coverartarchive.org/release-group/" + id + "/front"

def downloadImage(imageURL, identifier):
	print("Found image %s" % (identifier))
	fileDownload = "images/%d.jpg" % (identifier)
	try:
		urllib.request.urlretrieve(imageURL, fileDownload)
	except urllib.error.HTTPError:
		return

def main (filename):
	result = loadJson(filename)
	for album in result["albums"]:
		print("Scraping for: %s" % (album["id"]))
		GroupURL = getGroupURLFromID(album["music_id"])
		downloadImage(GroupURL, album["id"])

if __name__ == "__main__":
	filename = sys.argv[1]
	main(filename)