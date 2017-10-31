var app = angular.module('web_spotify', ['mc.resizer']);

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
