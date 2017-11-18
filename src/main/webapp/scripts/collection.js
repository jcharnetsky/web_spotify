angular.module("web_spotify").controller("LoadCollectionCtrl", function($compile, $scope, $http, $parse){
  $scope.loadCollection = function (id) {
    $http.get(location.origin + "/api/collections/" + id + "/get/info").then(function(response) {
      handleJSONResponse(response, "main", "playlist.html", "playlist", $compile, $parse, $scope);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
});