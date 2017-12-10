angular.module("web_spotify").controller("UploadCtrl", function ($scope, $http, $parse, $compile) {
  $scope.editUserImage = function () {
    var imageFiles = document.getElementById("editUserImage").files[0];
    if(imageFiles.length == 0){
      return;
    }
    var imageData = new FormData();
    imageData.append('file', imageFiles);
    $http.post("/upload/image/user", imageData,
      {transformRequest: angular.identity, headers: {'Content-Type': undefined}})
      .then(function(response) {
        if(!response.data.error){
          $scope.editingUser.imageLink = response.data.content;
          if($scope.editingUser.id == $scope.user.id){
            $scope.user.imageLink = response.data.content;
          }
          displayErrorPopup("Successfully changed user image", $scope, $parse, $compile);
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
        displayErrorPopup(err, $scope, $parse, $compile);
      });
  }
  
  /*
  $scope.uploadAudio = function() {
    var audioFile = document.getElementById("uploadAudio").files[0];
    if(audioFile.length == 0) {
      return;
    }
    var audioData = new FormData();
    audioData.append('file', audioFile);
    audioData.append('id', !!!!!insert ID here!!!!!);
    $http.post("/upload/audio", audioData,
      {transformRequest: angular.identity, headers: {'Content-Type': undefined}})
      .then(function(response) {
        if(!response.data.error) {
          displayErrorPopup("Successfully uploaded audio", $scope, $parse, $compile);
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
        displayErrorPopup(err, $scope, $parse, $compile);
      });
  }
  */
  document.getElementById("editUserImage").addEventListener('change', $scope.editUserImage);
  //document.getElementById("editCollectionImage").addEventListener('change', $scope.editCollectionImage);
  //document.getElementById("uploadAudio").addEventListener('change', $scope.uploadAudio);
});