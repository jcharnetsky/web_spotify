var app = angular.module('login_module', []);

app.controller('logInCtrl', function ($http, $scope, $parse, $compile) {

    $scope.logIn = function () {

        $http.get(location.origin + "/login", {params:{"email":$scope.name, "password":$scope.password}}).
        then(function(response) {
            if (!response.data.error) {
                window.location = location.origin + "/home.html";
            } else {
                displayErrorPopup(response.data.error, $scope, $parse, $compile);
            }
        }).catch(function(err){
            displayErrorPopup(err, $scope, $parse, $compile);
        });

    };
});
