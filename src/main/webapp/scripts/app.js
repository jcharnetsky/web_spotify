angular.module('web_spotify', []).controller('MainCtrl', function($compile, $scope, $http, $parse){
  $scope.loadTemplateAndJSON = function (div, URL, controllerPath) {
    if(!(controllerPath === 'null')){
      $http.get(location.origin + "/" + controllerPath).then(function(response) {
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
    } else {
      node = loadToDiv(div, URL);
      $compile(node)($scope);
    }
  }
});