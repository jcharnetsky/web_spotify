angular.module('web_spotify').controller('LoadPlaylistCtrl', function($compile, $scope, $http, $parse){
  $scope.loadPlaylist = function (id) {
    $http.get(location.origin + "/getPlaylistData", {params:{"playlistId": id}}).then(function(response) {
      if(!response.data.error){
         $parse("playlist").assign($scope, response.data);
         node = loadToDiv("main", "playlist.html");
         $compile(node)($scope);
         return;
      }
      displayErrorPopup(response.data.error, $scope, $parse, $compile);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
});