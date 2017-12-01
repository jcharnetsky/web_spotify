angular.module("web_spotify").controller("CollectionCtrl", function ($compile, $scope, $http, $parse, collections) {
  $scope.searchQuery = function(query) {
    $http.get(location.origin+"/api/search/"+query).then(function (response) {
        handleJSONResponse(response, "main", "searchResults.html", "results", $compile, $parse, $scope);
        $scope.getPlaylists();
      }).catch(function (err) {
        displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.loadPlaylist = function (id) {
    $http.get(location.origin + "/api/playlists/" + id + "/get/info").then(function (response) {
      handleJSONResponse(response, "main", "collection.html", "collection", $compile, $parse, $scope);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.loadAlbum = function (id) {
    $http.get(location.origin + "/api/albums/" + id + "/get/info").then(function (response) {
      handleJSONResponse(response, "main", "collection.html", "collection", $compile, $parse, $scope);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.loadBrowse = function () {
    $compile(loadToDiv("main", "navigateContent.html"))($scope);
  }
  $scope.getPlaylists = function () {
    $http.get(location.origin + "/api/playlists/").then(function (response) {
      handleJSONResponse(response, "playlists", "null", "playlists", $compile, $parse, $scope);
      collections.setPlaylists(angular.copy($scope.playlists));
      $scope.playlists = collections.getPlaylists();
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.getAlbums = function () {
    $http.get(location.origin + "/api/albums/").then(function (response) {
      handleJSONResponse(response, "main", "albums.html", "albums", $compile, $parse, $scope);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.openCreatePlaylistDialog = function () {
    $http.get(location.origin + "/api/songs/genres").then(function (response) {
      handleJSONResponse(response, "modal_dialog", "createPlaylist.html", "genres", $compile, $parse, $scope);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.openEditPlaylistDialog = function () {
    $http.get(location.origin + "/api/songs/genres").then(function (response) {
      handleJSONResponse(response, "modal_dialog", "editPlaylist.html", "genres", $compile, $parse, $scope);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.openCreateAlbumDialog = function () {
    $http.get(location.origin + "/api/songs/genres").then(function (response) {
      handleJSONResponse(response, "modal_dialog", "createAlbum.html", "genres", $compile, $parse, $scope);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.openEditAlbumDialog = function () {
    $http.get(location.origin + "/api/songs/genres").then(function (response) {
      handleJSONResponse(response, "modal_dialog", "editAlbum.html", "genres", $compile, $parse, $scope);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }

  $scope.followCollection = function (id) {
    let controllerPath;
    let data;
    if($scope.collection.album){
      controllerPath = "/api/albums/"+id+"/save";
      data = JSON.stringify({"albumId":id});
    } else if ($scope.collection.playlist) {
      controllerPath = "/api/playlists/"+id+"/follow";
      data = JSON.stringify({"playlistId":id});
    }
    $http.post(controllerPath, data, {headers: {"Content-Type": "application/json"}}).
      then(function (response) {
        if (!response.data.error) {
          $scope.collection.followed = true;
          displayErrorPopup("Successfully followed collection", $scope, $parse, $compile);
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }

  $scope.unfollowCollection = function (id) {
    let controllerPath;
    let data;
    if($scope.collection.album){
      controllerPath = "/api/albums/"+id+"/unsave";
      data = JSON.stringify({"albumId":id});
    } else if ($scope.collection.playlist) {
      controllerPath = "/api/playlists/"+id+"/unfollow";
      data = JSON.stringify({"playlistId":id});
    }
    $http.post(controllerPath, data, {headers: {"Content-Type": "application/json"}}).
      then(function (response) {
        if (!response.data.error) {
          $scope.collection.followed = false;
          displayErrorPopup("Successfully unfollowed collection", $scope, $parse, $compile);
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }

  $scope.createAlbum = function (id) {
  }
  $scope.editAlbum = function (id) {
    data = JSON.stringify({
      "title": $scope.edit_title,
      "genre": $scope.edit_genre
    })
    $http.post("/api/albums/" + id + "/edit", data, {headers: {"Content-Type": "application/json"}}).
      then(function (response) {
        if (!response.data.error) {
          if ($scope.edit_title) {
            if ($scope.edit_title.length > 0) {
              $scope.collection.title = $scope.edit_title;
              for(var i = 0;i < $scope.collection.songs.length;i++){
                $scope.collection.songs[i].albumName = $scope.edit_title;
              }
            }
          }
          if ($scope.edit_genre) {
            $scope.collection.genre = $scope.edit_genre;
          }
          $("#editAlbumModal").modal("hide");
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.deleteAlbum = function (id) {
    $http.post("/api/albums/" + id + "/delete").
      then(function (response) {
        if (!response.data.error) {
          displayErrorPopup("Album was successfully deleted", $scope, $parse, $compile);
          $scope.loadBrowse();
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }

  $scope.createPlaylist = function () {
    if (!$scope.new_title) {
      displayErrorPopup("You must enter a playlist title", $scope, $parse, $compile);
      return;
    }
    if (!$scope.new_genre) {
      displayErrorPopup("You must select a Genre", $scope, $parse, $compile);
      return;
    }
    data = JSON.stringify({
      "title": $scope.new_title,
      "description": $scope.new_description,
      "genre": $scope.new_genre
    });
    $http.post("/api/playlists/create", data, {headers: {"Content-Type": "application/json"}}).
      then(function (response) {
        if (!response.data.error) {
          collections.addPlaylist(response.data.content);
          $scope.playlists = collections.getPlaylists();
          $("#createPlaylistModal").modal("hide");
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.editPlaylist = function (id) {
    data = JSON.stringify({
      "title": $scope.edit_title,
      "description": $scope.edit_description,
      "genre": $scope.edit_genre
    })
    $http.post("/api/playlists/" + id + "/edit", data, {headers: {"Content-Type": "application/json"}}).
      then(function (response) {
        if (!response.data.error) {
          if ($scope.edit_title) {
            if ($scope.edit_title.length > 0) {
              $scope.collection.title = $scope.edit_title;
            }
          }
          if ($scope.edit_description) {
            if ($scope.edit_description.length > 0) {
              $scope.collection.description = $scope.edit_description;
            }
          }
          if ($scope.edit_genre) {
            $scope.collection.genre = $scope.edit_genre;
          }
          collections.editPlaylist(id, $scope.edit_title, $scope.edit_description, $scope.edit_genre);
          $scope.playlists = collections.getPlaylists();
          $("#editPlaylistModal").modal("hide");
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.deletePlaylist = function (id) {
    $http.post("/api/playlists/" + id + "/delete").
      then(function (response) {
        if (!response.data.error) {
          collections.removePlaylist(id);
          displayErrorPopup("Playlist was successfully deleted", $scope, $parse, $compile);
          $scope.playlists = collections.getPlaylists();
          $scope.loadBrowse();
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.addSongToPlaylist = function (songId, playlistId) {
    data = JSON.stringify({"playlistId": playlistId, "songId": songId});
    $http.post("/api/playlists/" + playlistId + "/add/song/" + songId, data, {headers: {"Content-Type": "application/json"}}).
      then(function (response) {
        if (!response.data.error) {
          displayErrorPopup("Song successfully added to playlist", $scope, $parse, $compile);
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.removeSongFromPlaylist = function (songId, playlistId) {
    data = JSON.stringify({"playlistId": playlistId, "songId": songId});
    $http.post("/api/playlists/" + playlistId + "/rem/song/" + songId, data, {headers: {"Content-Type": "application/json"}}).
      then(function (response) {
        if (!response.data.error) {
          for (var i = 0; i < $scope.collection.songs.length; i++) {
            if ($scope.collection.songs[i].id === songId) {
              $scope.collection.songs.splice(i, 1);
            }
          }
          displayErrorPopup("Song successfully removed from playlist", $scope, $parse, $compile);
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.getSavedSongs = function () {
    $scope.playlists = collections.getPlaylists();
    $http.get(location.origin + "/api/songs/saved").then(function (response) {
      handleJSONResponse(response, "main", "songs.html", "collection.songs", $compile, $parse, $scope);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  };
  $scope.saveSong = function (songId) {
    $http.get(location.origin + "/api/songs/saved/add/" + songId).
      then(function (response) {
        if (!response.data.error) {
          if($scope.collection.songs){
            song = getArrayElementWithId($scope.collection.songs, songId)
            if(song != null){
              song.saved = true;
            }
          }
          if($scope.queue){
            song = getArrayElementWithId($scope.queue.queue, songId);
            if(song != null){
              song.saved = true;
            }
            song = getArrayElementWithId($scope.queue.history, songId);
            if(song != null){
              song.saved = true;
            }
          }
          displayErrorPopup("Song successfully saved", $scope, $parse, $compile);
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.unsaveSong = function (songId) {
    $http.get(location.origin + "/api/songs/saved/rem/" + songId).
      then(function (response) {
        if (!response.data.error) {
          if($scope.collection.songs){
            song = getArrayElementWithId($scope.collection.songs, songId)
            if(song != null){
              song.saved = false;
            }
          }
          if($scope.queue){
            song = getArrayElementWithId($scope.queue.queue, songId);
            if(song != null){
              song.saved = false;
            }
            song = getArrayElementWithId($scope.queue.history, songId);
            if(song != null){
              song.saved = false;
            }
          }
          displayErrorPopup("Song successfully unsaved", $scope, $parse, $compile);
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
     displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
}).service('collections', function () {
  var playlists;

  getPlaylists = function () {
    return playlists;
  }
  addPlaylist = function (newList) {
    playlists.push(newList);
  }
  setPlaylists = function (newLists) {
    playlists = newLists;
  }
  editPlaylist = function (id, title, description, genre) {
    playlist = getArrayElementWithId(playlists, id);
    if (title) {
      if (title.length > 0) {
        playlist.title = angular.copy(title);
      }
    }
    if (description) {
      if (description.length > 0) {
        playlist.description = angular.copy(description);
      }
    }
    if (genre) {
      playlist.genre = angular.copy(genre);
    }
    return;
  }
  removePlaylist = function (id) {
    for (var i = 0; i < playlists.length; i++) {
      if (playlists[i].id === id) {
        playlists.splice(i, 1);
        return;
      }
    }
  }
  return {
    getPlaylists: getPlaylists,
    setPlaylists: setPlaylists,
    addPlaylist: addPlaylist,
    editPlaylist: editPlaylist,
    removePlaylist: removePlaylist
  }
});