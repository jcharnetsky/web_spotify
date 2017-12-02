angular.module('signup_module', []).controller('SignupCtrl', function ($http, $scope, $parse, $compile) {
  $scope.createAccount = function () {
		if($scope.password !== $scope.confirm_password) {
			displayErrorPopup("Passwords do not match.", $scope, $parse, $compile);
			return;
		}
    data = JSON.stringify(
      {"name":$scope.name,
       "email":$scope.email,
       "address":$scope.address,
       "birthdate":$scope.birthdate,
       "password":$scope.password,
       "type": "BASIC"});
    $http.post('/api/users/register', data, {headers: {'Content-Type':'application/json'}}).
    then(function(response) {
      if(!response.data.error) {
        displayErrorPopup("Account created!", $scope, $parse, $compile);
        window.location = location.origin;
        return;
      }
      displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
    }).catch(function(err){
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  };
});