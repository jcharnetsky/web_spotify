function loadToDiv(div, URL) {
  node = document.getElementById(div);
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

function handleJSONResponse(response, div, URL, controllerPath, compile, parse, scope){
  if(!response.data.error){
    parse(controllerPath).assign(scope, response.data);
    node = loadToDiv(div, URL);
    compile(node)(scope);
    return;
  }
  displayErrorPopup(response.data.error, scope, parse, compile);
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
