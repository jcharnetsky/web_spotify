angular.module("web_spotify").controller("UploadCtrl", function ($scope, $http, $parse, $compile) {
  $scope.editUserImage = function (element) {
    var imageFiles = element.files;
    if(imageFiles.length == 0){
      return;
    }
    var imageData = new FormData();
    imageData.append('file', imageFiles[0]);
    $http.post("/upload/image/user", imageData,
      {transformRequest: angular.identity, headers: {'Content-Type': undefined}})
      .then(function(response) {
        if(!response.data.error){
          $scope.editingUser.imageLink = response.data.content;
          if($scope.editingUser.id == $scope.user.id){
            $scope.user.imageLink = response.data.content;
          }
          displayErrorPopup("Successfully changed image", $scope, $parse, $compile);
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
        displayErrorPopup(err, $scope, $parse, $compile);
      });
  }

  document.getElementById("editImage").addEventListener('change', $scope.editImage);
});