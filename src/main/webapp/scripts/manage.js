angular.module('web_spotify').controller('ManageCtrl', function($scope, $http, $compile, $parse){
  $scope.showSongInfo = function (id){
    $http.get(location.origin + "/api/songs/manage/" + id).then(function (response) {
      handleJSONResponse(response, "modal_dialog", "manageSong.html", "manage", $compile, $parse, $scope);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.handleReport = function (reportType, reportId, type, id){
    $http.post("/api/ban/banContent/"+reportId+"/"+type+"/"+id, {headers: {"Content-Type": "application/json"}}).
      then(function (response) {
        if (!response.data.error) {
          displayErrorPopup("Successfully banned content", $scope, $parse, $compile);
          return;
        }
        $("#manageReportModal").modal("hide");
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.ignoreReport = function (reportId){
    $http.post("/api/reports/ban/"+type+"/"+id, {headers: {"Content-Type": "application/json"}}).
      then(function (response) {
        if (!response.data.error) {
          displayErrorPopup("Report ignored", $scope, $parse, $compile);
          return;
        }
        $("#manageReportModal").modal("hide");
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
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
  $scope.createAccount = function () {
  		if($scope.password !== $scope.confirm_password) {
  			displayErrorPopup("Passwords do not match.", $scope, $parse, $compile);
  			return;
  		}
      data = JSON.stringify(
        {"name":$scope.name,
         "email":$scope.email,
         "address":$scope.address,
         "birthdate":$scope.birthdate,
         "password":$scope.password},
         "type": $scope.type);
      $http.post('/api/users/register', data, {headers: {'Content-Type':'application/json'}}).
      then(function(response) {
        if(!response.data.error) {
          window.location = location.origin;
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function(err){
        displayErrorPopup(err, $scope, $parse, $compile);
      });
    };
});