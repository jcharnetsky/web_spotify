var app = angular.module('web_spotify',[]);

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

app.controller('MainCtrl', function($compile, $scope, $http, $parse){
    $scope.loadTemplateAndJSON = function (div, URL, controllerPath) {

        if(!(controllerPath === 'null')){

           // Send the XMLHttpRequest
           $http.get(location.origin + "/" + controllerPath).then(function(response) {

              // If there was no error, proceed as usual
             if(!response.data.error){

                // Grab the JSON response and parse it straight to scope variables
                $parse(controllerPath).assign($scope, response.data);
                node = loadToDiv(div, URL);
                $compile(node)($scope);

             } else {

                console.log(response.data);
                // If there was an error, display an error pop-up
                $parse('error').assign($scope, response.data.error);
                node = loadToDiv('error_dialog', 'error.html');
                $compile(node)($scope);
             }

           }).catch(function (err) {
                 // Catch and print any server-side error to console
                 console.log(err);
                 $parse('error').assign($scope, err);
                 node = loadToDiv('error_dialog', 'error.html');
                 $compile(node)(scope);
           });
        } else {
           node = loadToDiv(div, URL);
           $compile(node)($scope);
        }

    }
});

