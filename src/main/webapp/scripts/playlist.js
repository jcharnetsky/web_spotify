angular.module("web_spotify").controller("LoadPlaylistCtrl", function($compile, $scope, $http, $parse){
  $scope.loadPlaylist = function (id) {
    $http.get(location.origin + "/api/playlists/get/specific/" + id).then(function(response) {
      handleJSONResponse(response, "main", "playlist.html", "playlists", $compile, $parse, $scope);
      if ($scope.playlist.duration){
        $scope.playlist.duration = secondsToMinSec($scope.playlist.duration);
      }
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
});