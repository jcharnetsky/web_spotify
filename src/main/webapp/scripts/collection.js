angular.module("web_spotify").controller("CollectionCtrl", function($compile, $scope, $http, $parse){
  $scope.loadCollection = function (id) {
    $http.get(location.origin + "/api/playlists/" + id + "/get/info").then(function(response) {
      handleJSONResponse(response, "main", "collection.html", "collection", $compile, $parse, $scope);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }

  $scope.openPlaylistDialog = function() {
    node = loadToDiv('modal_dialog', 'createPlaylist.html');
    $compile(node)(scope);
  }
});