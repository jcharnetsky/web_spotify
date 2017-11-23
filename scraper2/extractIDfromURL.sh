echo [ && cat artists_id.json | sed "s/https:\/\/musicbrainz.org\/artist//g" | sed 's/\//\"/' | sed 's/$/\",/' && echo ]
