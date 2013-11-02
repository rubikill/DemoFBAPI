'use strict';

var app = angular.module('app', [ 'app.services' , 'scroll' , 'infinite-scroll']);

app.config([ '$routeProvider', function($routeProvider, $rootScope) {
	$routeProvider.when('/group', {
		templateUrl : 'partials/group.html'
	}).when('/statistic', {
		templateUrl : 'partials/statistic.html'
	}).otherwise({
		redirectTo : ('/group')
	});
} ]);