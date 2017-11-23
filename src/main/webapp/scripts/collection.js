angular.module("web_spotify").controller("CollectionCtrl", function($compile, $scope, $http, $parse){
  $scope.loadCollection = function (id) {
    $http.get(location.origin + "/api/playlists/" + id + "/get/info").then(function(response) {
      handleJSONResponse(response, "main", "collection.html", "collection", $compile, $parse, $scope);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }

  $scope.getPlaylists = function () {
    controllerPath = "/api/playlists/"
    $http.get(location.origin + controllerPath).then(function(response) {
      handleJSONResponse(response, "playlists", "null", controllerPath, $compile, $parse, $scope);
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
    data = JSON.stringify({
      "title":$scope.new_title,
      "description":$scope.new_description,
      "genre": $scope.new_genre
    })
    $http.post("/api/playlists/create", data, {headers: {'Content-Type':'application/json'}}).
      then(function(response) {
        if (!response.data.error) {
          $scope.getPlaylists();
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function(err){
        displayErrorPopup(err, $scope, $parse, $compile);
      });
  }
});