var app = angular.module('web_spotify');

function padZero(time){
  if(time < 10){
    return "0" + time;
  }
  return time;
}

function secondsToMinSec(time){
  return Math.floor(time/60) + ":" + padZero((time%60));
}

app.controller('playbarCtrl', function($scope, $interval){
	$scope.play = false;

	$scope.playSong = function () {
		const aud = document.getElementById("playAudio");
		const plb = document.getElementById("playButton");
		const vol = document.getElementById("songVolume");
        aud.volume = vol.value / 100;

		if(!$scope.play) {
			$scope.doPlay();
		} else {
			$scope.doPause();
		}
	}

    $scope.progressAtInterval = function() {
		const aud = document.getElementById("playAudio");
		const pro = document.getElementById("songProgress");

		pro.max = aud.duration;
		pro.value = aud.currentTime;

		const tup = document.getElementById("playSongTimeUp");
		const tdo = document.getElementById("playSongTimeDown");

		tup.innerHTML = secondsToMinSec(Math.floor(pro.value));
		tdo.innerHTML = secondsToMinSec(Math.floor(pro.max - pro.value));

		if(Math.floor(pro.value)>=Math.floor(pro.max)) {
		// Play the next song here
			$scope.play = false;
			$scope.doPause();
		}
    }

	$scope.scrubSong = function() {
		const aud = document.getElementById("playAudio");
		const pro = document.getElementById("songProgress");

		pro.max = aud.duration;
		aud.currentTime = pro.value;
	}

	$scope.scrubVolume = function() {
	    const aud = document.getElementById("playAudio");
        const vol = document.getElementById("songVolume");
        aud.volume = vol.value / 100;
	}

	$scope.doPlay = function () {
		const aud = document.getElementById("playAudio");
		const plb = document.getElementById("playButton");
		aud.play();
		plb.src = "../images/pause.png";
		$scope.play = true;
	}

	$scope.doPause = function () {
		const aud = document.getElementById("playAudio");
		const plb = document.getElementById("playButton");
		aud.pause();
		plb.src = "../images/play.png";
		$scope.play = false;
	}

	$interval(function() {$scope.progressAtInterval();}, 1000);

	$scope.progressAtInterval();

});