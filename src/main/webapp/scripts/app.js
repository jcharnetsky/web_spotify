var app = angular.module('web_spotify',[]);

app.controller('MainCtrl', function($scope) {

  $scope.content = '';

  $scope.playlists = ['On the Road', 'Spotify and Chill', 'Spotify top 50 Sweden', 'Spotify Sessions',
  'Rock Classics', 'Pop Rising', 'Liked from Radio'];

});

app.controller('NavCtrl', function($compile, $scope, $http, $parse){
    $scope.selectPane = function (div, URL, controllerPath) {
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

       var node = document.getElementById(div);

       //Clear out whatever was in the content container
       if(node == null) return;
       while(node.firstChild){
          node.removeChild(node.firstChild);
       }

       //Add the new include to whatever was clic   ked
       var tag = document.createElement('div');
       tag.setAttribute("ng-include", "'templates/" + URL + "'");
       node.appendChild(tag);

       //Compile the include
       $compile(node)($scope);
    }

    $scope.selectPane('content', 'overview.html', 'overview');

});

