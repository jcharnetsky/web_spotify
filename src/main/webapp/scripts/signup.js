angular.module('signup_module', []).controller('signUpCtrl', function ($http, $scope, $parse, $compile) {s
  $scope.createAccount = function () {
    $http.get(location.origin + "/registerUser", {params:{"name":$scope.name,"email":$scope.email, "birthdate":$scope.birthdate,"password":$scope.password}}).
    then(function(response) {
      if(!response.data.error) {
        window.location = location.origin + "/home.html";
        return;
      }
      displayErrorPopup(response.data.error, $scope, $parse, $compile);
    }).catch(function(err){
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  };
});