angular.module("web_spotify").controller("UserCtrl", function ($compile, $scope, $http, $parse) {
  $scope.loadUser = function (id) {
    $http.get(location.origin + "/api/users/get/" + id).then(function (response) {
      handleJSONResponse(response, "main", "user.html", "visitingUser", $compile, $parse, $scope);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  };

  $scope.getFollowedArtists = function () {
    $http.get(location.origin + "/api/users/get/followedArtists").then(function (response) {
      handleJSONResponse(response, "main", "artists.html", "artists", $compile, $parse, $scope);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }

  $scope.followUser = function(id) {
    data = JSON.stringify({"userId": id});
    $http.post("/api/users/follow/" + id, data, {headers: {"Content-Type": "application/json"}}).
      then(function (response) {
        if (!response.data.error) {
          $scope.visitingUser.followed = true;
          displayErrorPopup("Successfully followed user", $scope, $parse, $compile);
          return; 
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }

  $scope.unfollowUser = function(id) {
    data = JSON.stringify({"userId": id});
    $http.post("/api/users/unfollow/" + id, data, {headers: {"Content-Type": "application/json"}}).
      then(function (response) {
        if (!response.data.error) {
          $scope.visitingUser.followed = false;
          displayErrorPopup("Successfully unfollowed user", $scope, $parse, $compile);
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  
  $scope.toggleIsPublic = function() {
    $http.post("/api/users/info/set/public", null, {headers: {"Content-Type": "application/json"}}).
      then(function(response) {
        var elem = document.getElementById("togglePublicOn");
        if (elem.innerHTML=="Enable Private Session") {
          elem.innerHTML = "Disable Private Session";
        }
        else {
          elem.innerHTML = "Enable Private Session";
        }
        var elem = document.getElementById("togglePublicOff");
        if (elem.innerHTML=="Enable Private Session") {
          elem.innerHTML = "Disable Private Session";
        }
        else {
          elem.innerHTML = "Enable Private Session";
        }
        if(!response.data.error) {
          displayErrorPopup("Successfully toggled public/private status.", $scope, $parse, $compile);
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  
  $scope.setPremium = function(status) {
    $http.post("/api/users/info/set/premium", null, {
      params:{
        "premium": status}}).
      then(function(response) {
        if(!response.data.error) {
          if(status) {
            document.getElementById("downgrading").style.visibility = 'hidden';
            document.getElementById("upgrading").style.visibility = 'visible';
            displayErrorPopup("Successfully downgraded account.", $scope, $parse, $compile);
          }
          else {
            document.getElementById("upgrading").style.visibility = 'hidden';
            document.getElementById("downgrading").style.visibility = 'visible';
            displayErrorPopup("Successfully upgraded account.", $scope, $parse, $compile);
          }
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  
  $scope.deleteAccount = function() {
    $http.post("/api/users/delete", null, {headers: {"Content-Type": "application/json"}}).
    then(function(response) {
      if(!response.data.error) {
        displayErrorPopup("Successfully deleted account.", $scope, $parse, $compile);
        window.location = location.origin;
        return;
      }
      displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
    }).catch(function (err) {
    displayErrorPopup(err, $scope, $parse, $compile);
  });
  }

  $scope.toggleDropdown = toggleDropdown;
});