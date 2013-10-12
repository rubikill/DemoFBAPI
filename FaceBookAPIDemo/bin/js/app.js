'use strict';

var app = angular.module('app', ['app.services']);

app.config([ '$routeProvider', function($routeProvider, $rootScope) {
	$routeProvider.otherwise({
		redirectTo : ('/home')
	});
} ]);

var appservice = angular.module('app.services', [ 'ngResource' ]);

appservice.factory('Home', function($resource) {
	return $resource('/status', {
	}, {
		postStatus : {
			method : 'GET'
		}
	});
});

var HomeCtrl = (function($scope, Home) {
	$scope.post = function() {
		console.log("-----click------");
		
		Home.postStatus($scope.content, function(data) { // success
			// update the model!
			console.log($scope.content);
		}, function(response) { // error
			console.log("Error")
		});
	}
});

