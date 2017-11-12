angular.module('signup_module', []).controller('signUpCtrl', function ($http, $scope, $parse, $compile) {
  $scope.createAccount = function () {
    const toPass = JSON.stringify({"name":$scope.name,"email":$scope.email, "birthdate":$scope.birthdate,"password":$scope.password});
    $http.post(location.origin + "/registerUser", {data:toPass}).
    then(function(response) {
      if (!response.data.error) {
        window.location = location.origin + "/home.html";
        return;
      }
      displayErrorPopup(response.data.error, $scope, $parse, $compile);
    }).catch(function(err){
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  };
});
