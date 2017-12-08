angular.module('web_spotify').controller('PlaybarCtrl', function($scope, $http, $compile, $parse, $interval, $filter){
  $scope.loadQueue = function() {
    $http.get(location.origin + "/api/queue/").then(function(response) {
      handleJSONResponse(response, "main", "queue.html", "queue", $compile, $parse, $scope);
    }).catch(function (err) {
      displayErrorPopup(err, $scope, $parse, $compile);
    });
  }
  $scope.addSongToQueue = function(id) {
    data = JSON.stringify({"songId": id});
    $http.post("/api/queue/add/song/"+id, data, {headers: {"Content-Type":"application/json"}}).
      then(function(response) {
        if (!response.data.error) {
          displayErrorPopup("Song added to queue", $scope, $parse, $compile);
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function(err){
        displayErrorPopup(err, $scope, $parse, $compile);
      });
  }
  $scope.addCollectionToQueue = function(id, type) {
    $http.post("/api/queue/clear", {headers: {"Content-Type":"application/json"}});
    if(type === true) {
      data = JSON.stringify({"playlistId": id});
      $http.get("/api/queue/add/playlist/" + id, data, {headers: {"Content-Type":"application/json"}}).
        then(function(response) {
          if (!response.data.error) {
            displayErrorPopup("Playlist added to queue", $scope, $parse, $compile);
            return;
          }
          displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
        }).catch(function(err){
          displayErrorPopup(err, $scope, $parse, $compile);
        });
    } else {
      data = JSON.stringify({"albumId": id});
      $http.get("/api/queue/add/album/" + id, data, {headers: {"Content-Type":"application/json"}}).
        then(function(response) {
          if (!response.data.error) {
            displayErrorPopup("Album added to queue", $scope, $parse, $compile);
            return;
          }
          displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
        }).catch(function(err){
          displayErrorPopup(err, $scope, $parse, $compile);
        });
    }
  };
  $scope.clearQueue = function() {
    $http.post("/api/queue/clear", {headers: {"Content-Type":"application/json"}}).
            then(function(response) {
              displayErrorPopup("Queue cleared", $scope, $parse, $compile);
    });
  };
  $scope.removeSongFromQueue = function(id) {
    data = JSON.stringify({"songId": id});
    $http.post("/api/queue/rem/song/"+id, data, {headers: {"Content-Type":"application/json"}}).
      then(function(response) {
        if (!response.data.error) {
          for(var i = 0; i < $scope.queue.queue.length; i++){
            if($scope.queue.queue[i].id === id){
              $scope.queue.queue.splice(i,1);
            }
          }
          displayErrorPopup("Song removed from queue", $scope, $parse, $compile);
          return;
        }
        displayErrorPopup(response.data.errorMessage, $scope, $parse, $compile);
      }).catch(function(err){
        displayErrorPopup(err, $scope, $parse, $compile);
      });
  }
  var audio;
  var volumeBar = document.getElementById("songVolume");
  var progressBar = document.getElementById("songProgress");
	$scope.play = false;
	$scope.loadSong = function(hasAudio, id) {
	  if(hasAudio){

	  } else {
	    return;
	  }
	}
	$scope.playSong = function() {
		audio = document.getElementById("playAudio");
		volumeBar = document.getElementById("songVolume");
    audio.volume = volumeBar.value/100;
		if (!$scope.play) {
			$scope.doPlay();
		} else {
			$scope.doPause();
		}
		$interval(function() {$scope.progressAtInterval();}, 1000);
    $scope.progressAtInterval();
	}
  $scope.toggleDropdown = toggleDropdown;
  $scope.progressAtInterval = function() {
    progressBar.max = audio.duration;
    progressBar.value = audio.currentTime;
    document.getElementById("playSongTimeUp").innerHTML = $filter('secondsToMss')(progressBar.value);
    document.getElementById("playSongTimeDown").innerHTML = $filter('secondsToMss')(progressBar.max - progressBar.value);
      if (Math.floor(progressBar.value) >= Math.floor(progressBar.max)) {
       $scope.play = false;
       $scope.doPause();
      }
  }
	$scope.scrubSong = function() {
		progressBar.max = audio.duration;
		audio.currentTime = progressBar.value;
	}
	$scope.scrubVolume = function() {
    audio.volume = volumeBar.value/100;
	}
 $scope.mute = function() {
   if ($scope.lastVolume !== 0) {
    volumeBar.value = $scope.lastVolume;
    $scope.lastVolume = 0;
    audio.volume = volumeBar.value/100;
   } else {
    $scope.lastVolume = volumeBar.value;
    volumeBar.value = 0;
    audio.volume = 0;
  }
 };
	$scope.doPlay = function () {
		audio.play();
		document.getElementById("playButton").src = "../images/pause.png";
		$scope.play = true;
	}
	$scope.doPause = function () {
		audio.pause();
	  document.getElementById("playButton").src = "../images/play.png";
		$scope.play = false;
	}
});