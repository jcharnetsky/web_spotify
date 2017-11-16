angular.module('signup_module', []).controller('signUpCtrl', function ($http, $scope, $parse, $compile) {
  $scope.createAccount = function () {
    var data = {"name":$scope.name,"email":$scope.email, "address":"", "birthdate":$scope.birthdate,"password":$scope.password};
    $http({
        url: '/api/users/register',
        method:"POST",
        data:JSON.stringify(data),
        headers: {'Content-Type':'application/json'}
    }).
    then(function(response) {
      if(!response.data.error) {
        window.location = location.origin;
        return;
      }
      displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
    }).catch(function(err){
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  };
});