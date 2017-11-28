angular.module('web_spotify').controller('ManageCtrl', function($scope, $http, $compile, $parse){
  $scope.showSongInfo(id){
    $http.get(location.origin + "/api/songs/manage/" + id).then(function (response) {
      handleJSONResponse(response, "modal_dialog", "manageSong.html", "manage", $compile, $parse, $scope);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
});