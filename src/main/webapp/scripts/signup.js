angular.module('signup_module', []).controller('SignupCtrl', function ($http, $scope, $parse, $compile) {
  $scope.createAccount = function () {
		console.log($scope.password);
		console.log($scope.confirm_password);
		if($scope.password !== $scope.confirm_password) {
			displayErrorPopup("Passwords do not match.", $scope, $parse, $compile);
			return;
		}
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