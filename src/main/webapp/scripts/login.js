angular.module('login_module', []).controller('logInCtrl', function ($http, $scope, $parse, $compile, $location) {
  $scope.logIn = function () {
    $http.get("/api/users/login", {params:{"email":$scope.name, "password":$scope.password}}).
    then(function(response) {
      if (!response.data.error) {
        window.location = location.origin + "/home.html"
        return;
      }
      displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
    }).catch(function(err){
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  };
});
