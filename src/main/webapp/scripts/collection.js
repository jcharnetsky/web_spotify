angular.module("web_spotify").controller("CollectionCtrl", function($compile, $scope, $http, $parse, collections){
  $scope.loadCollection = function(id) {
    $http.get(location.origin + "/api/playlists/" + id + "/get/info").then(function(response) {
      handleJSONResponse(response, "main", "collection.html", "collection", $compile, $parse, $scope);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }

  $scope.getPlaylists = function() {
    controllerPath = "/api/playlists/";
    $http.get(location.origin + controllerPath).then(function(response) {
      handleJSONResponse(response, "playlists", "null", "playlists", $compile, $parse, $scope);
      collections.setPlaylists(angular.copy($scope.playlists));
      $scope.playlists = collections.getPlaylists();
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }

  $scope.openPlaylistDialog = function() {
    $http.get(location.origin + "/api/songs/genres").then(function(response) {
      handleJSONResponse(response, "modal_dialog", "createPlaylist.html", "genres", $compile, $parse, $scope);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }

  $scope.createPlaylist = function() {
    if(!$scope.new_title) {
      displayErrorPopup("You must enter a playlist title", $scope, $parse, $compile);
      return;
    }
    if(!$scope.new_genre) {
      displayErrorPopup("You must select a Genre", $scope, $parse, $compile);
      return;
    }
    data = JSON.stringify({
      "title":$scope.new_title,
      "description":$scope.new_description,
      "genre": $scope.new_genre
    })
    $http.post("/api/playlists/create", data, {headers: {"Content-Type":"application/json"}}).
      then(function(response) {
        if (!response.data.error) {
          collections.addPlaylist(response.data.content);
          $("#createPlaylistModal").modal("hide");
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function(err){
        displayErrorPopup(err, $scope, $parse, $compile);
      });
  }

  $scope.deletePlaylist = function(id) {
    data = JSON.stringify({"playlistId" : id});
    $http.post("/api/playlists/"+id+"/delete", data, {headers: {"Content-Type":"application/json"}}).
      then(function(response) {
        if (!response.data.error) {
          collections.removePlaylist(id);
          displayErrorPopup("Playlist was successfully deleted", $scope, $parse, $compile);
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function(err){
        displayErrorPopup(err, $scope, $parse, $compile);
      });
  }

}).service('collections', function() {
  var playlists;
  var albums;

  getPlaylists = function() { return playlists; }
  addPlaylist = function(newList) { playlists.push(newList); }
  setPlaylists = function(newLists) { playlists = newLists; }
  removePlaylist = function(id){
    for(var i = 0; i < playlists.length; i++){
      if(playlists[i].id === id){
        playlists.splice(i, 1);
      }
    }
   }

  return {
    getPlaylists : getPlaylists,
    addPlaylist : addPlaylist,
    setPlaylists : setPlaylists,
    removePlaylist : removePlaylist
  }
});