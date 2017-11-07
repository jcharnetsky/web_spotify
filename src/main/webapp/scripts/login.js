var app = angular.module('login_module', []);

function loadToDiv(div, URL) {
    var node = document.getElementById(div);

    //Clear out whatever was in the content container
    if(node == null) return;
    while(node.firstChild){
      node.removeChild(node.firstChild);
    }

    if(!(URL === 'null')){

       //Add the new include to whatever was clicked
       var tag = document.createElement('div');
       tag.setAttribute("ng-include", "'templates/" + URL + "'");
       node.appendChild(tag);
    }
    return node;
}

app.controller('logInCtrl', function ($http, $scope, $parse, $compile) {
    $scope.name = "";
    $scope.password = "";
    $scope.logIn = function () {

        $http.get(location.origin + "/login", {params:{"email":$scope.name, "password":$scope.password}}).
        then(function(response) {
            if (!response.data.error) {
                window.location = location.origin + "/home.html";
            } else {
                $parse('error').assign($scope, response.data.error);
                node = loadToDiv('error_dialog', 'error.html');
                $compile(node)($scope);
            }
        }).catch(function(err){
            $parse('error').assign($scope, err);
            node = loadToDiv('error_dialog', 'error.html');
            $compile(node)($scope);
        });

    };
});
