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
  
  $scope.openEditUsernameDialog = function () {
    $http.get(location.origin + "/api/songs/genres").then(function (response) {
      handleJSONResponse(response, "modal_dialog", "editPlaylist.html", "genres", $compile, $parse, $scope);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
 
  $scope.editUsername
  
  $scope.toggleIsPublic = function() {
    $http.post("/api/users/info/set/public", null, {headers: {"Content-Type": "application/json"}}).
      then(function(response) {
        if(!response.data.error) {
          $scope.user.public = !scope.user.public
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
          $scope.user.premium = !scope.user.premium
          displayErrorPopup("Successfully upgraded/downgraded premium status.")
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