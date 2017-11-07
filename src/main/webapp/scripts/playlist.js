app = angular.module('web_spotify');

app.controller('LoadPlaylistCtrl', function($compile, $scope, $http, $parse){
       console.log("LOAD PLAYLIST CALLED");
    $scope.loadPlaylist = function (div, URL, id) {
       controllerPath = "getPlaylistData";

       // Send the XMLHttpRequest
       $http.get(location.origin + "/" + controllerPath, {params:{"playlistId": id}}).then(function(response) {

          // If there was no error, proceed as usual
         if(!response.data.error){

            // Grab the JSON response and parse it straight to scope variables
            $parse(controllerPath).assign($scope, response.data);
            node = loadToDiv(div, URL);
            $compile(node)($scope);

         } else {

            console.log(response.data);
            // If there was an error, display an error pop-up
            displayErrorPopup(response.data.error, $scope, $parse, $compile);
         }

       }).catch(function (err) {
             // Catch and print any server-side error to console
             displayErrorPopup(err, $scope, $parse, $compile);
       });

    }
});