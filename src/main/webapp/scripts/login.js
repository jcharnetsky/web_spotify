var app = angular.module('login_module', []);

app.controller('logInCtrl', function ($http, $scope) {
    $scope.name = "";
    $scope.password = "";
    $scope.logIn = function () {

    $http.get(location.origin + "/login", {params:{"email":$scope.name, "password":$scope.password}}).then(function(response) {
        if (!response.data.error) {
            window.location = location.origin + "/home.html";
        } else {
			alert(response.data.errorMessage);
		}
    });

    };
});
