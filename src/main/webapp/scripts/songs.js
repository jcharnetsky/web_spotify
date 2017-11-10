angular.module('web_spotify').controller('LoadSongsCtrl', function($compile, $scope, $http, $parse){
  $scope.dynamic_button = "+";
  $scope.loadSongs = function (div, URL) {
    controllerPath = "getSongsData";
    $http.get(location.origin + "/" + controllerPath, {params:{}}).then(function(response) {
      handleJSONResponse(response, div, URL, "getSongsData", $compile, $parse, $scope);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
});