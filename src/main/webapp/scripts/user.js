angular.module("web_spotify").controller("UserCtrl", function($compile, $scope, $http, $parse){
  $scope.loadUser = function (id) {
    controllerPath = "api/users/get/" + id;
    $http.get(location.origin + "/" + controllerPath).then(function(response) {
      handleJSONResponse(response, "main", "user.html", "user", $compile, $parse, $scope);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
});