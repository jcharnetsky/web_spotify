var app = angular.module('web_spotify',['mc.resizer']);

function padZero(time){
  if(time < 10){
    return "0" + time;
  }
  return time;
}

function secondsToMinSec(time){
  return Math.floor(time/60) + ":" + padZero((time%60));
}

app.controller('MainCtrl', function($scope) {
  $scope.content = '';

  $scope.playlists = ['On the Road', 'Spotify and Chill', 'Spotify top 50 Sweden', 'Spotify Sessions',
  'Rock Classics', 'Pop Rising', 'Liked from Radio'];

});

app.controller('NavCtrl', function($compile, $scope){
$scope.selectPane = function (div, URL) {
   console.log(div, URL);
   var node = document.getElementById(div);

   //Clear out whatever was in the content container
   while(node.firstChild){
      node.removeChild(node.firstChild);
   }

   //Add the new include to whatever was clic   ked
   var tag = document.createElement('div');
   tag.setAttribute("ng-include", "'templates/" + URL + "'");
   node.appendChild(tag);

   //Compile the include
   $compile(node)($scope);
}

});


app.controller('playbarCtrl', function($scope, $interval){
	$scope.play = false;

	$scope.playSong = function () {
		const aud = document.getElementById("playAudio");
		const plb = document.getElementById("playButton");
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
	
	$scope.scrub = function() {
		const aud = document.getElementById("playAudio");
		const pro = document.getElementById("songProgress");
		
		pro.max = aud.duration;
		aud.currentTime = pro.value;
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


app.controller('logInCtrl', function ($http, $scope) {
    $scope.name = "";
    $scope.password = "";
    $scope.logIn = function () {

    $http.get(location.origin + "/login", {params:{"email":$scope.name, "password":$scope.password}}).then(function(response) {
        if (!response.error) {
            window.location = location.origin + "/home.html";
        }
    });

    };
});

