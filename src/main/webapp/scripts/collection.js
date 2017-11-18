angular.module("web_spotify").controller("LoadCollectionCtrl", function($compile, $scope, $http, $parse){
  $scope.loadCollection = function (id) {
    $http.get(location.origin + "/api/collections/" + id + "/get/info").then(function(response) {
      handleJSONResponse(response, "main", "playlist.html", "playlist", $compile, $parse, $scope);
      if ($scope.playlist.songTrackLength){
        $scope.playlist.songTrackLength = secondsToMinSec($scope.playlist.songTrackLength);
        $scope.playlist.songs.forEach(function(song){
          song.trackLength = secondsToMinSec(song.trackLength);
        });
      }
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
});