var app = angular.module('web_spotify',[]);

app.controller('MainCtrl', function($compile, $scope, $http, $parse){
    $scope.loadTemplateAndJSON = function (div, URL, controllerPath) {
        if(controllerPath != "null"){

           // Send the XMLHttpRequest
           $http.get(location.origin + "/" + controllerPath).then(function(response) {

             // Grab the JSON response and parse it straight to scope variables
             $parse(controllerPath).assign($scope, response.data);

           }).catch(function (err) {
             // Catch and print any server-side error to console
             console.log(err)
           });
        }

        if(div == "null" || URL == "null"){
           var node = document.getElementById(div);

           //Clear out whatever was in the content container
           if(node.firstChild == null) return;
           while(node.firstChild){
             node.removeChild(node.firstChild);
           }

           //Add the new include to whatever was clicked
           var tag = document.createElement('div');
           tag.setAttribute("ng-include", "'templates/" + URL + "'");
           node.appendChild(tag);
        }

       //Compile the include
       $compile(node)($scope);

       //If you the navigateContent page was loaded, load the overview inside it
       if(div == "main" && URL == "navigateContent.html"){

       }
    }

    $scope.playlists = ['On the Road', 'Spotify and Chill', 'Spotify top 50 Sweden', 'Spotify Sessions',
              'Rock Classics', 'Pop Rising', 'Liked from Radio'];
});

