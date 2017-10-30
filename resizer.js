angular.module('mc.resizer', []).directive('resizer', function($document) {

	return function($scope, $element, $attrs) {

		$element.on('mousedown', function(event) {
			event.preventDefault();

			$document.on('mousemove', mousemove);
			$document.on('mouseup', mouseup);
		});

		function mousemove(event) {

			if ($attrs.resizer == 'vertical') {
				// Handle vertical resizer
				var x = event.pageX;
				x = x * (100 / document.documentElement.clientWidth);

				if ($attrs.resizerMax && x > $attrs.resizerMax) {
					x = parseInt($attrs.resizerMax);
				}
				if ($attrs.resizerMin && x < $attrs.resizerMin) {
					x = parseInt($attrs.resizerMin);
				}

				$element.css({
					left: x + 'vw'
				});

				$($attrs.resizerLeft).css({
					width: x + 'vw'
				});
				$($attrs.resizerRight).css({
					left: (x + parseInt($attrs.resizerWidth)) + 'vw'
				});

			} else {
				// Handle horizontal resizer
				var y = window.innerHeight - event.pageY;
				y = y * (100 / document.documentElement.clientHeight);

				$element.css({
					bottom: y + 'vh'
				});

				$($attrs.resizerTop).css({
					bottom: (y + parseInt($attrs.resizerHeight)) + 'vh'
				});
				$($attrs.resizerBottom).css({
					height: y + 'vh'
				});
			}
		}

		function mouseup() {
			$document.unbind('mousemove', mousemove);
			$document.unbind('mouseup', mouseup);
		}
	};
});
