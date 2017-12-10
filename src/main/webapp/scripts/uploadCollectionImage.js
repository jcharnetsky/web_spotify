angular.module("web_spotify").controller("UploadCollectionImageCtrl", function ($scope, $http, $parse, $compile) {
  $scope.editCollectionImage = function() {
    var imageFiles = document.getElementById("editCollectionImage").files[0];
    if(imageFiles.length == 0) {
      console.log("Image was empty.")
      return;
    }
    var imageData = new FormData();
    imageData.append('file', imageFiles);
    if($scope.collection.playlist) {
      imageData.append('playlistId', $scope.collection.id);
      $http.post("/upload/image/playlist", imageData,
          {transformRequest: angular.identity, headers: {'Content-Type': undefined}})
          .then(function(response) {
            if(!response.data.error){
              $scope.collection.imageLink = response.data.content;
              displayErrorPopup("Successfully changed playlist image", $scope, $parse, $compile);
              return;
            }
            displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
          }).catch(function (err) {
            displayErrorPopup(err, $scope, $parse, $compile);
          });
      }
    if($scope.collection.album) {
      imageData.append('albumId', $scope.collection.id);
      $http.post("/upload/image/album", imageData,
          {transformRequest: angular.identity, headers: {'Content-Type': undefined}})
          .then(function(response) {
            if(!response.data.error){
              $scope.collection.imageLink = response.data.content;
              displayErrorPopup("Successfully changed album image", $scope, $parse, $compile);
              return;
            }
            displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
          }).catch(function (err) {
            displayErrorPopup(err, $scope, $parse, $compile);
          });
      }
    }
    
  document.getElementById("editCollectionImage").addEventListener('change', $scope.editCollectionImage);
});