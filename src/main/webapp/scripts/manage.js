angular.module('web_spotify').controller('ManageCtrl', function($scope, $http, $compile, $parse){
  $scope.showSongInfo = function (id){
    $http.get(location.origin + "/api/songs/manage/" + id).then(function (response) {
      handleJSONResponse(response, "modal_dialog", "manageSong.html", "manage", $compile, $parse, $scope);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.requestRemoval = function (type, id){
    return;
  }
  $scope.openReport = function (id) {
    $http.get(location.origin + "/api/reports/reportNo/" + id).then(function (response) {
      handleJSONResponse(response, "modal_dialog", "manageReport.html", "manage", $compile, $parse, $scope);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.openCreateReport = function(type, name, id) {
    let response = {"data": {"content": {}}};
    response.data.content.type = type;
    response.data.content.entityName = name;
    response.data.content.entityId = id;
    handleJSONResponse(response, "modal_dialog", "createReport.html", "report", $compile, $parse, $scope);
  }
  $scope.createReport = function() {
    data = JSON.stringify({
      "subject": $scope.report_subject,
      "description": $scope.report_description,
      "entityType": $scope.report.type.toUpperCase(),
      "entityId": $scope.report.entityId
    })
    $http.post("/api/reports/create", data, {headers: {"Content-Type": "application/json"}}).
      then(function (response) {
        if (!response.data.error) {
          displayErrorPopup("Successfully made report", $scope, $parse, $compile);
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
}).filter('toLowercase', function($filter) {
    return function(word) {
      return word.charAt(0) + word.slice(1).toLowerCase();
    };
});;