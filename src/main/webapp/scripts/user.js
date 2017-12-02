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
    $http.post("/api/users/follow/" + id).then(function (response) {
      if (!response.data.error) {
        $scope.visitingUser.followed = true;
        $scope.visitingUser.followerCount = $scope.visitingUser.followerCount + 1;
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
          $scope.visitingUser.followerCount = $scope.visitingUser.followerCount - 1;
          displayErrorPopup("Successfully unfollowed user", $scope, $parse, $compile);
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  
  $scope.openDialog = function (url) {
    $compile(loadToDiv("modal_dialog", url))($scope);
  }
  $scope.upgradePremiumStatus = function(status) {
    if($scope.cardholder_name == undefined || $scope.cardholder_name == "") {
      displayErrorPopup("Cardholder name field cannot be left blank.", $scope, $parse, $compile);
      return;
    }
    if($scope.card_number == undefined || $scope.card_number == "" || isNaN($scope.card_number) || String($scope.card_number).length >= 20) {
      displayErrorPopup("Invalid credit card number.", $scope, $parse, $compile);
      return;
    }
    if($scope.card_cvn == undefined || $scope.card_cvn == "" || isNaN($scope.card_cvn) || String($scope.card_cvn).length >= 5) {
      displayErrorPopup("Invalid CVN", $scope, $parse, $compile);
      return;
    }
    if($scope.card_zip == undefined || $scope.card_zip == "" || isNaN($scope.card_zip) || String($scope.card_zip.length) >= 6) {
      displayErrorPopup("Invalid zip code.", $scope, $parse, $compile);
      return;
    }
    var month = document.getElementById("expire_month");
    var monthValue = month.options[month.selectedIndex].value;
    if(monthValue == '') {
      displayErrorPopup("Card month field cannot be left as default.", $scope, $parse, $compile);
      return;
    }
    var year = document.getElementById("expire_year");
    var yearValue = year.options[year.selectedIndex].value;
    if(yearValue == '') {
      displayErrorPopup("Card year field cannot be left as default.", $scope, $parse, $compile);
      return;
    }
    var expiration = String(monthValue) + "/" + String(yearValue);
    data = JSON.stringify({
      "ccn": $scope.card_number,
      "cvn": $scope.card_cvn,
      "cardholderName": $scope.cardholder_name,
      "zipCode": $scope.card_zip,
      "expDate": expiration
    });
    $http.post("/api/creditcards/post", data, {headers: {"Content-Type": "application/json"}}).
      then(function(response) {
        if(response.data.error) {
          displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
        }
      }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
    $http.post("/api/users/info/set/premium", null, {
      params:{
        "premium": status}}).
      then(function(response) {
        if(!response.data.error) {
          $scope.user.premium = !scope.user.premium
          displayErrorPopup("Successfully upgraded premium status.", $scope, $parse, $compile)
          $("#upgradePremiumModal").modal("hide");
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  
  $scope.downgradePremiumStatus = function(status) {
    $http.post("/api/users/info/set/premium", null, {
      params:{
        "premium": status}}).
      then(function(response) {
        if(!response.data.error) {
          $scope.user.premium = !scope.user.premium
          displayErrorPopup("Successfully downgraded premium status.", $scope, $parse, $compile)
          $("#downgradePremiumModal").modal("hide");
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  
  $scope.editUsername = function (userId) {
    if($scope.edit_username == undefined || $scope.edit_username == "") {
      displayErrorPopup("Username field cannot be left blank.", $scope, $parse, $compile);
      return;
    }
    $http.post("/api/users/info/set/name", null, {
      params:{
        "name": $scope.edit_username,
        "userId": userId}}).
      then(function(response) {
        if (!response.data.error) {
          displayErrorPopup("Username changed successfully.", $scope, $parse, $compile);
          $scope.editingUser.name = $scope.edit_username;
          $("#editUsernameModal").modal("hide");
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
        displayErrorPopup(err, $scope, $parse, $compile);
      });
  }
  
  $scope.editEmail = function (userId) {
    if($scope.edit_email == undefined || $scope.edit_email == "") {
      displayErrorPopup("Email field cannot be left blank.", $scope, $parse, $compile);
      return;
    }
    $http.post("/api/users/info/set/email", null, {
      params:{
        "email": $scope.edit_email,
        "userId": $scope.userId}}).
      then(function(response) {
        if (!response.data.error) {
          displayErrorPopup("Email changed successfully.", $scope, $parse, $compile);
          $scope.editingUser.email = $scope.edit_email;
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
          $scope.user.public = !$scope.user.public;
          displayErrorPopup("Successfully toggled public/private status.", $scope, $parse, $compile);
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.loadBrowse = function () {
    $compile(loadToDiv("main", "navigateContent.html"))($scope);
  };
  $scope.deleteAccount = function(userId) {
    $http.post("/api/users/delete/"+userId).then(function(response) {
      if(!response.data.error) {
        displayErrorPopup("Successfully deleted account.", $scope, $parse, $compile);
        if(!response.data.content){
          window.location = location.origin + "/logIn.html";
          return;
        } else {
          $scope.visitingUser = {};
          $scope.loadBrowse();
          return;
        }
      }
      displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
    }).catch(function (err) {
    displayErrorPopup(err, $scope, $parse, $compile);
  });
  }

  $scope.toggleDropdown = toggleDropdown;
});