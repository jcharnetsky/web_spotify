angular.module('web_spotify').controller('LoadPlaylistCtrl', function($compile, $scope, $http, $parse){
  $scope.playlist_owner = "Owner";
  $scope.playlist_song_num = "0";
  $scope.playlist_duration = "0 min";
  $scope.loadPlaylist = function (div, URL, id) {
    controllerPath = "getPlaylistData";

    $http.get(location.origin + "/" + controllerPath, {params:{"playlistId": id}}).then(function(response) {
      if(!response.data.error){
         $parse(controllerPath).assign($scope, response.data);
         node = loadToDiv(div, URL);
         $compile(node)($scope);
         return;
      }
      displayErrorPopup(response.data.error, $scope, $parse, $compile);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
});