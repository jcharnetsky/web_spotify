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
    data = JSON.stringify({"userId": id});-
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
    $compile(loadToDiv("modal_dialog", "editUsername.html"))($scope);
  }
  
  $scope.openEditEmailDialog = function () {
    $compile(loadToDiv("modal_dialog", "editEmail.html"))($scope);
  }
  
  $scope.openEditPasswordDialog = function () {
    $compile(loadToDiv("modal_dialog", "editPassword.html"))($scope);
  }
 
  $scope.editUsername = function () {
    if($scope.edit_username == undefined || $scope.edit_username == "") {
      displayErrorPopup("Username field cannot be left blank.", $scope, $parse, $compile);
      return;
    }
    $http.post("/api/users/info/set/name", null, {
      params:{
        "name": $scope.edit_username}}).
      then(function(response) {
        if (!response.data.error) {
          displayErrorPopup("Username changed successfully.", $scope, $parse, $compile);
          $scope.user.name = $scope.edit_username;
          $("#editUsernameModal").modal("hide");
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
        displayErrorPopup(err, $scope, $parse, $compile);
      });
  }
  
  $scope.editEmail = function () {
    if($scope.edit_email == undefined || $scope.edit_email == "") {
      displayErrorPopup("Email field cannot be left blank.", $scope, $parse, $compile);
      return;
    }
    $http.post("/api/users/info/set/email", null, {
      params:{
        "email": $scope.edit_email}}).
      then(function(response) {
        if (!response.data.error) {
          displayErrorPopup("Email changed successfully.", $scope, $parse, $compile);
          $scope.user.email = $scope.edit_email;
          $("#editEmailModal").modal("hide");
          return;
        }
      displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
        displayErrorPopup(err, $scope, $parse, $compile);
      });
  }
  
  $scope.editPassword = function () {
    var oldPass = $scope.old_password;
    var newPass = $scope.new_password;
    var confPass = $scope.confirm_password;
    if(oldPass == undefined || oldPass == "") {
      displayErrorPopup("Old password field cannot be left blank.", $scope, $parse, $compile);
      return;
    }
    if(newPass == undefined || newPass == "") {
      displayErrorPopup("New password field cannot be left blank.", $scope, $parse, $compile);
      return;
    }
    if(confPass == undefined || confPass == "") {
      displayErrorPopup("Confirm password field cannot be left blank.", $scope, $parse, $compile);
      return;
    }
    $http.post("/api/users/info/set/password", null, {
      params:{
        "oldPassword": oldPass,
        "newPassword": newPass,
        "confirmPassword": $scope.confirm_password}}).
      then(function(response) {
        if (!response.data.error) {
          displayErrorPopup("Password changed successfully.", $scope, $parse, $compile);
          $("#editPasswordModal").modal("hide");
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