angular.module('web_spotify').controller('playbarCtrl', function($scope, $interval){
  var audio;
  var volumeBar = document.getElementById("songVolume");
  var progressBar = document.getElementById("songProgress");
	$scope.play = false;

	$scope.playSong = function () {
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

  $scope.progressAtInterval = function() {
		progressBar.max = audio.duration;
		progressBar.value = audio.currentTime;

		document.getElementById("playSongTimeUp").innerHTML = secondsToMinSec(progressBar.value);
		document.getElementById("playSongTimeDown").innerHTML = secondsToMinSec(progressBar.max - progressBar.value);
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