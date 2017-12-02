angular.module('web_spotify').controller('ManageCtrl', function($scope, $http, $compile, $parse){
  $scope.showSongInfo = function (id){
    $http.get(location.origin + "/api/songs/manage/" + id).then(function (response) {
      handleJSONResponse(response, "modal_dialog", "manageSong.html", "manage", $compile, $parse, $scope);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.handleReport = function (reportType, reportId, type, id){
    data = JSON.stringify({
      "reportType" : reportType,
      "reportId" : reportId,
      "entityType" : type,
      "entityId" : id
    });
    $http.post("/api/reports/handle/", data, {headers: {"Content-Type": "application/json"}}).
      then(function (response) {
        if (!response.data.error) {
          for(var i = 0;i < $scope.api_reports_all.length;i++){
            if($scope.api_reports_all[i].id == reportId){
              $scope.api_reports_all.splice(i,1);
            }
          }
          if(response.data.content){
            $scope.api_reports_all.push(response.data.content);
          }
          displayErrorPopup("Report handled.", $scope, $parse, $compile);
          $("#manageReportModal").modal("hide");
          return;
        }
        $("#manageReportModal").modal("hide");
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.ignoreReport = function (reportId){
    $http.post("/api/reports/ignore/"+reportId, {headers: {"Content-Type": "application/json"}}).
      then(function (response) {
        if (!response.data.error) {
          for(var i = 0;i < $scope.api_reports_all.length;i++){
            if($scope.api_reports_all[i].id == reportId){
              $scope.api_reports_all.splice(i,1);
            }
          }
          displayErrorPopup("Report ignored", $scope, $parse, $compile);
          $("#manageReportModal").modal("hide");
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
        $("#manageReportModal").modal("hide");
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
  $scope.createReport = function(reportType) {
    data = JSON.stringify({
      "subject": $scope.report_subject,
      "description": $scope.report_description,
      "entityType": $scope.report.type.toUpperCase(),
      "reportType": reportType,
      "entityId": $scope.report.entityId,
      "completed": false
    })
    $http.post("/api/reports/create", data, {headers: {"Content-Type": "application/json"}}).
      then(function (response) {
        if (!response.data.error) {
          displayErrorPopup("Successfully made report", $scope, $parse, $compile);
          $("#createReportModal").modal("hide");
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
         "password":$scope.password,
         "type": $scope.type});
      $http.post('/api/users/register', data, {headers: {'Content-Type':'application/json'}}).
      then(function(response) {
        if(!response.data.error) {
          displayErrorPopup("Account created!", $scope, $parse, $compile);
          $("#createAccountModal").hide();
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function(err){
        displayErrorPopup(err, $scope, $parse, $compile);
      });
    };
});