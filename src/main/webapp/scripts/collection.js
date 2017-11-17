angular.module("web_spotify").controller("LoadCollectionCtrl", function($compile, $scope, $http, $parse){
  $scope.loadCollection = function (id) {
    $http.get(location.origin + "/api/playlists/get/specific/" + id).then(function(response) {
      handleJSONResponse(response, "main", "playlist.html", "playlists", $compile, $parse, $scope);
      if ($scope.playlists[0].duration){
        $scope.playlists[0].duration = secondsToMinSec($scope.playlists[0].duration);
      }
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
});