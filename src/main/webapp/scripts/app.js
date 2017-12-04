angular.module('web_spotify', ['mc.resizer']).controller('MainCtrl', function ($compile, $scope, $location, $http, $parse) {
  $scope.loadTemplateAndJSON = function (div, URL, controllerPath) {
    if (!(controllerPath === 'null')) {
      $http.get(location.origin + "/" + controllerPath).then(function (response) {
        handleJSONResponse(response, div, URL, controllerPath, $compile, $parse, $scope);
      }).catch(function (err) {
        displayErrorPopup(err, $scope, $parse, $compile);
      });
    } else {
      $compile(loadToDiv(div, URL))($scope);
    }
  }
  $scope.logout = function () {
    $http.get(location.origin + "/api/users/logout").then(function (response) {
      if (response.data.error) {
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }
      window.location = location.origin + "/logIn.html";
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.compile = function (domNode) {
    $compile(domNode)($scope);
  }
  $scope.loadUserInfo = function () {
    $http.get(location.origin + "/api/users/info/get/userInfo/self").then(function (response) {
      if (!response.data.error) {
        $parse("user").assign($scope, response.data.content);
      } else {
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.loadUserInfoPage = function (id) {
    $http.get(location.origin + "/api/users/info/get/userInfo/"+id).then(function (response) {
      if (!response.data.error) {
        $parse("editingUser").assign($scope, response.data.content);
        $compile(loadToDiv("main", "user_info.html"))($scope);
      } else {
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.loadGenreAlbums = function (genre) {
    data = JSON.stringify({"genre": genre});
    $http.get("/api/albums/genre/"+genre, data, {headers: {"Content-Type": "application/json"}})
    .then(function (response) {
      handleJSONResponse(response, "main", "albums.html", "albums",
       $compile, $parse, $scope);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.order='songId';
  $scope.setOrder = function (i) {
    if (i == 0) {
        if($scope.order == 'trackName') {
            $scope.order = '-trackName';
        } else{
            $scope.order = 'trackName';
        }
    } else if (i == 1) {
      if($scope.order == 'artist.stageName') {
        $scope.order = '-artist.stageName';
      } else{
        $scope.order = 'artist.stageName';
      }
    } else if (i == 2) {
      if($scope.order == 'trackLength') {
        $scope.order = '-trackLength';
      } else{
        $scope.order = 'trackLength';
      }
    } else if (i == 3) {
      if($scope.order == 'albumName') {
        $scope.order = '-albumName';
      } else{
        $scope.order = 'albumName';
      }
    }
  };
}).filter('secondsToMss', function ($filter) {
  return function (seconds) {
    if (seconds < 600) {
      return $filter('date')(new Date(0, 0, 0).setSeconds(seconds), 'm:ss');
    } else if (seconds < 3600) {
      return $filter('date')(new Date(0, 0, 0).setSeconds(seconds), 'mm:ss');
    } else {
      return $filter('date')(new Date(0, 0, 0).setSeconds(seconds), 'h:mm:ss');
    }
  };
}).filter('toLowercase', function ($filter) {
  return function (word) {
    return word.charAt(0) + word.slice(1).toLowerCase();
  };
});