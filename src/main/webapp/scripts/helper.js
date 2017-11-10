function loadToDiv(div, URL) {
    var node = document.getElementById(div);
    if(node == null) return;
    while(node.firstChild){
      node.removeChild(node.firstChild);
    }
    if(!(URL === 'null')){
       var tag = document.createElement('div');
       tag.setAttribute("ng-include", "'templates/" + URL + "'");
       node.appendChild(tag);
    }
    return node;
}

function displayErrorPopup(error, scope, parse, compile) {
    parse('error').assign(scope, error);
    node = loadToDiv('error_dialog', 'error.html');
    compile(node)(scope);
}

function padZero(time){
  if(time < 10){
    return "0" + time;
  }
  return time;
}

function secondsToMinSec(time){
  return Math.floor(time/60) + ":" + padZero((Math.floor(time%60)));
}
