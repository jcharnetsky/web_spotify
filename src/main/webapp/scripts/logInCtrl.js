app.controller('logInCtrl', function ($scope) {
    $scope.name = "";
    $scope.password = "";
    $scope.logIn = function () {
        const tmpUser = "user";
        const tmpPass = "pass";
        
        if ($scope.name === tmpUser && $scope.password === tmpPass) {
            window.location = location.origin + "/home.html";
        };
    };
});
