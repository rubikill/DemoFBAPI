'use strict';

var app = angular.module('app', [ 'app.services', 'scroll', 'infinite-scroll',
		'ngProgress' ]);

app.config([ '$routeProvider', function($routeProvider, $rootScope) {
	$routeProvider.when('/group', {
		templateUrl : 'partials/group.html'
	}).when('/statistic', {
		templateUrl : 'partials/statistic.html'
	}).when('/fitfeed', {
		templateUrl : 'partials/fitfeed.html'
	}).otherwise({
		redirectTo : ('/group')
	});
} ]);

// date picker
app.directive('bsDatepicker', function() {
	return {
		require : 'ngModel',
		scope : true,
		restrict : 'A',
		link : function($scope, element, attrs, controller) {
			var options = {};
			var updateModel = function(ev) {
				element.datepicker('hide');
				element.blur();

				if (angular.equals(attrs.name, "dueDate")) {
					$("#startDate").datepicker('setEndDate', ev.date);

				} else if (angular.equals(attrs.name, "startDate")) {
					$("#endDate").datepicker('setStartDate', ev.date);
				}
				return $scope.$apply(function() {
					return controller.$setViewValue(ev.date);
				});
			};

			if (controller != null) {
				controller.$render = function() {
					return controller.$viewValue;
				};
			}

			return attrs.$observe('bsDatepicker', function(value) {

				if (angular.isObject(value)) {
					options = value;
				}

				if (typeof (value) === "string" && value.length > 0) {
					options = angular.fromJson(value);
				}

				if (angular.equals(attrs.name, "startDate")) {
					var current = new Date();
					var startStr = current.getFullYear() + "-"
							+ (current.getMonth() <= 8 ? "0" : "")
							+ (current.getMonth() + 1) + "-"
							+ (current.getDate() <= 9 ? "0" : "")
							+ current.getDate();

					$('#startDate').datepicker('setStartDate', startStr);
					$('#endDate').datepicker('setStartDate', startStr);
				}

				// element.datepicker('setLanguage',
				// $scope.currentLanguage.localize.split('-')[0]);
				// datepicker at the bottom search
				if (attrs.placement === "top") {
					element.on('show', function() {
						var picker = $('body').find('.datepicker');

						picker.addClass('datepicker-dropup');

						var offset = element.offset();
						picker.css({
							top : offset.top - picker.outerHeight(true) - 3,
							left : offset.left

						});
					});

				}

				return element.datepicker(options)
						.on('changeDate', updateModel);
			});
		}
	};
});