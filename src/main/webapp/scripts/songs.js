angular.module('web_spotify').controller('LoadSongsCtrl', function($compile, $scope, $http, $parse){
  $scope.dynamic_button = "+";
  $scope.loadSongs = function (div, URL) {
    controllerPath = "getSongsData";

    $http.get(location.origin + "/" + controllerPath, {params:{}}).then(function(response) {
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