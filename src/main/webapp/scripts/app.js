angular.module('web_spotify', ['mc.resizer']).controller('MainCtrl', function($compile, $scope, $location, $http, $parse){
  $scope.loadTemplateAndJSON = function (div, URL, controllerPath) {
    if(!(controllerPath === 'null')){
      $http.get(location.origin + "/" + controllerPath).then(function(response) {
        handleJSONResponse(response, div, URL, controllerPath, $compile, $parse, $scope);
      }).catch(function (err) {
        displayErrorPopup(err, $scope, $parse, $compile);
      });
    } else {
      $compile(loadToDiv(div, URL))($scope);
    }
  }

  $scope.logout = function(){
    $http.get(location.origin + "/api/users/logout").then(function(response){
      if (response.data.error) {
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }
      window.location = location.origin + "/logIn.html";
    }).catch(function(err){
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }

  $scope.compile = function(domNode){
    $compile(domNode)($scope);
  }

  $scope.loadUserInfo = function() {
    $http.get(location.origin + "/api/users/info/get/userInfo").then(function(response) {
      if(!response.data.error){
        $parse("user").assign($scope, response.data.content);
      } else {
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
}).filter('secondsToMss', function($filter) {
    return function(seconds) {
      if(seconds < 600){
        return $filter('date')(new Date(0, 0, 0).setSeconds(seconds), 'm:ss');
      } else if (seconds < 3600) {
        return $filter('date')(new Date(0, 0, 0).setSeconds(seconds), 'mm:ss');
      } else {
        return $filter('date')(new Date(0, 0, 0).setSeconds(seconds), 'h:mm:ss');
      }
    };
}).filter('toLowercase', function($filter) {
    return function(word) {
      return word.charAt(0) + word.slice(1).toLowerCase();
    };
});
