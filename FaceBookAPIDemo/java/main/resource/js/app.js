'use strict';

var app = angular.module('app', [ 'app.services' ]);

app.config([ '$routeProvider', function($routeProvider, $rootScope) {
	$routeProvider.otherwise({
		redirectTo : ('/home')
	});
} ]);