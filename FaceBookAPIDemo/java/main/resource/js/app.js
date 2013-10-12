'use strict';

var app = angular.module('app', [ 'app.services' ]);

app.config([ '$routeProvider', function($routeProvider, $rootScope) {
	$routeProvider.otherwise({
		redirectTo : ('/home')
	});
} ]);

var appservice = angular.module('app.services', [ 'ngResource' ]);

appservice.factory('Home', function($resource) {
	return $resource('/status/:content', {
		content : "@content"
	}, {
		getStatus : {
			method : 'GET'
		},
		postStatus : {
			method : 'POST'
		}
	});
});

var HomeCtrl = (function($scope, Home) {
	$scope.post = function() {
		console.log("-----click------");
		Home.postStatus({
			content : $scope.content
		}, function(data) {
			console.log("success");
		}, function(response) {
			console.log("error");
			// alertify.error(localize.getLocalizedStringWithParams("_EditTaskFail_",
			// [response.data]));
		});
	}
});
