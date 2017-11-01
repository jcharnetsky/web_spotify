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

  var focuses = [['images/rhiannon-giddens-tomorrow-is-my-turn.jpg','Tomorrow Is My Turn','Tomorrow Is My Turn by Rhiannon Giddens'],
                 ['images/tile2.png', 'dancePOP', 'The guilty pleasures of dance and electronic. Cover: Eden Price and Cassie'],
                 ['images/tile3.png', 'Deep Focus', 'Keep calm and focus. This playlist has some great, atmospheric rock to help you relax and concentrate.'],
                 ['images/tile4.png', 'Lush Vibes', 'Lo-fi and chill instrumental hip hop. Related playlist: Jazz Vibes'],
                 ['images/tile5.png', 'The Pulse of Americana', 'Keep your finger on the pulse of Americana. We\'re featuring artists performing at AMERICANAFEST.'],
                 ['images/tile6.png', 'Young & Free', 'Live your life... this is Young & Free']];

  var charts = [['images/charts.png','Global and Regional Top Charts'],
                ['images/new_releases.png', 'Macklemore, The Killers, Illenium'],
                ['images/discover.png', 'Recommended For You']]

  $scope.focus = [];
  $scope.charts = [];
  $scope.playlists = ['On the Road', 'Spotify and Chill', 'Spotify top 50 Sweden', 'Spotify Sessions',
  'Rock Classics', 'Pop Rising', 'Liked from Radio'];

  for (i = 0; i < focuses.length; i++){
    var item = {
      img  : focuses[i][0],
      head : focuses[i][1],
      desc : focuses[i][2]
    };
    $scope.focus.push(item);
  }

  for (i = 0; i < charts.length; i++){
    var chart = {
      img : charts[i][0],
      desc : charts[i][1]
    };
    $scope.charts.push(chart);
  }
});

app.controller('NavCtrl', function($compile, $scope){
$scope.selectPane = function (param) {
   var node = document.getElementById("content");

   //Clear out whatever was in the content container
   while(node.firstChild){
      node.removeChild(node.firstChild);
   }

   //Add the new include to whatever was clicked
   var tag = document.createElement('div');
   tag.setAttribute("ng-include", "'templates/" + param + "'");
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

app.controller('logInCtrl', function ($scope) {
    $scope.name = "";
    $scope.password = "";
    $scope.logIn = function () {
        const tmpUser = "user";
        const tmpPass = "pass";
        
        if ($scope.name === tmpUser && $scope.password === tmpPass) {
            window.location = location.origin + "/home.html";
        };
    };
});
