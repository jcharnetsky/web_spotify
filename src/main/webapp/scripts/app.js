angular.module('web_spotify', []).controller('MainCtrl', function($compile, $scope, $http, $parse){
  $scope.loadTemplateAndJSON = function (div, URL, controllerPath) {
    if(!(controllerPath === 'null')){
      $http.get(location.origin + "/" + controllerPath).then(function(response) {
        handleJSONResponse(response, div, URL, controllerPath, $compile, $parse, $scope);
      }).catch(function (err) {
        displayErrorPopup(err, $scope, $parse, $compile);
      });
    } else {
      $compile(loadToDiv(div, URL))($scope);
    }
  }
});