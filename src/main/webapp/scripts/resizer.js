angular.module('mc.resizer', []).directive('resizer', function($document) {
	return function($scope, $element, $attrs) {
		$element.on('mousedown', function(event) {
      event.preventDefault();
      $document.on('mousemove', mousemove);
      $document.on('mouseup', mouseup);
    });

    function mousemove(event) {
      if ($attrs.resizer == 'left') {
        var x = event.pageX;
        x = x * (100 / document.documentElement.clientWidth);
        if (x > $attrs.resizermax) {
          x = parseInt($attrs.resizermax);
        }
        if (x < $attrs.resizermin) {
          x = parseInt($attrs.resizermin);
        }
        $element.css({
          left: x + 'vw'
        });
        $($attrs.resizerleft).css({
          width: x + 'vw'
        });
        $($attrs.resizerright).css({
          left: (x + parseInt($attrs.resizerwidth)) + 'vw !important'
        });
      } else {
        var x = event.pageX;
        x = x * (100 / document.documentElement.clientWidth);
        if (x > $attrs.resizermax) {
            x = parseInt($attrs.resizerMax);
        }
        if (x < $attrs.resizermin) {
            x = parseInt($attrs.resizerMin);
        }
        x = 100 - x;
        $element.css({
            right: x + 'vw'
        });
        $($attrs.resizerright).css({
            width: x + 'vw'
        });
        $($attrs.resizerleft).css({
            right: (x + parseInt($attrs.resizerwidth)) + 'vw'
        });
      }
    }

    function mouseup() {
      $document.unbind('mousemove', mousemove);
      $document.unbind('mouseup', mouseup);
    }
  };
});
