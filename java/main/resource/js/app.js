'use strict';

var app = angular.module('app', ['ngRoute', 'ngResource', 'ngProgress', 'infinite-scroll']);

app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/group', {
		templateUrl : 'view/group.html'
	}).when('/feed/:groupId', {
		templateUrl : 'view/feed.html',
		controller : 'FeedCtrl'
	}).when('/statistic/:groupId', {
		templateUrl : 'view/statistic.html',
		controller : 'StatisticCtrl'
	}).when('/statistic/:groupId/:groupBy', {
		templateUrl : 'view/detail.html',
		controller : 'DetailCtrl'
	});
}]);