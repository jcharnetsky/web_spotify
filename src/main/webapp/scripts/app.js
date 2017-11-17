angular.module('web_spotify', []).controller('MainCtrl', function($compile, $scope, $location, $http, $parse){

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

  $scope.logout = function(){
    $http.get(location.origin + "/api/users/logout").then(function(response){
      if (response.data.error) {
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }
      window.location = location.origin + "/logIn.html";
    }).catch(function(err){
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
});
