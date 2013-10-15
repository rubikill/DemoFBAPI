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

appservice.factory('Auth', function($resource) {
	return $resource('/auth/:token', {
		token : "@token"
	}, {
		setToken : {
			method : 'POST'
		}
	});
});

appservice.factory('Feed', function($resource) {
	return $resource('/feeds', {}, {
		getFeeds : {
			method : 'GET',
			isArray : true
		}
	});
});

var HomeCtrl = (function($scope, Home, Auth, Feed) {
	// Load the SDK asynchronously
	(function(d) {
		var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
		if (d.getElementById(id)) {
			return;
		}
		js = d.createElement('script');
		js.id = id;
		js.async = true;
		js.src = "//connect.facebook.net/en_US/all.js";
		ref.parentNode.insertBefore(js, ref);
	}(document));

	// Here we run a very simple test of the Graph API after login is
	// successful.
	// This testAPI() function is only called in those cases.
	function testAPI() {
		console.log('Welcome!  Fetching your information.... ');
		FB.api('/me', function(response) {
			console.log('Good to see you, ' + response.name + '.');

		});
		FB.getLoginStatus(function(response) {
			console.log("-------------------------------");
			if (response.status === 'connected') {
				// the user is logged in and has authenticated your
				// app, and response.authResponse supplies
				// the user's ID, a valid access token, a signed
				// request, and the time the access token
				// and signed request each expire

				$scope.token = response.authResponse.accessToken;
				Auth.setToken({
					token : response.authResponse.accessToken
				}, function(data) {
					console
							.log("success: "
									+ response.authResponse.accessToken);
				}, function(response) {
					console.log("fail");
				});

			} else if (response.status === 'not_authorized') {
				// the user is logged in to Facebook,
				// but has not authenticated your app
				console.log('Fail');
			} else {
				// the user isn't logged in to Facebook.
				console.log('not login');
			}
		});
	}

	window.fbAsyncInit = function() {
		FB.init({
			appId : '380947045341754', // App ID
			channelUrl : 'https://localhost:9000/index.html', // Channel File
			status : true, // check login status
			xfbml : true,

		// parse XFBML
		});

		// Here we subscribe to the auth.authResponseChange JavaScript event.
		// This event is fired
		// for any authentication related change, such as login, logout or
		// session refresh. This means that
		// whenever someone who was previously logged out tries to log in again,
		// the correct case below
		// will be handled.
		var scope = "email,publish_stream,read_stream,user_status,user_likes,publish_actions";
		FB.Event.subscribe('auth.authResponseChange', function(response) {
			// Here we specify what we do with the response anytime this event
			// occurs.
			if (response.status === 'connected') {
				// The response object is returned with a status field that lets
				// the app know the current
				// login status of the person. In this case, we're handling the
				// situation where they
				// have logged in to the app.
				console.log("connected");
				testAPI();

			} else if (response.status === 'not_authorized') {
				// In this case, the person is logged into Facebook, but not
				// into the app, so we call
				// FB.login() to prompt them to do so.
				// In real-life usage, you wouldn't want to immediately prompt
				// someone to login
				// like this, for two reasons:
				// (1) JavaScript created popup windows are blocked by most
				// browsers unless they
				// result from direct interaction from people using the app
				// (such as a mouse click)
				// (2) it is a bad experience to be continually prompted to
				// login upon page load.
				console.log("Login 1");

				FB.login(function(response) {
					// handle the response
					if (!response || response.error) {
						alert('Error occured');
					} else {
						alert('OK');
					}
				}, {
					scope : 'email,user_likes,publish_actions'
				});

			} else {
				// In this case, the person is not logged into Facebook, so we
				// call the login()
				// function to prompt them to do so. Note that at this stage
				// there is no indication
				// of whether they are logged into the app. If they aren't then
				// they'll see the Login
				// dialog right after they log in to Facebook.
				// The same caveats as above apply to the FB.login() call here.
				console.log("Login 2");
				FB.login("publish_stream", scope, function(response) {
					if (!response || response.error) {
						alert('Error occured');
					} else {
						alert('OK');
					}
				});

				FB.login(function(response) {
					// handle the response
					if (!response || response.error) {
						alert('Error occured');
					} else {
						alert('OK');
					}
				}, {
					scope : 'email,user_likes,publish_actions'
				});
			}
		});
	};

	$scope.postStatus = function() {
		console.log("-----click------");
		console.log($scope.token);

		// https:://graph.facebook.com/oauth/authorize?client_id=' . FB_APP_ID .
		// '&redirect_uri=' . REDIRECT_URI . '&scope=publish_stream'
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

	$scope.listFeed = [];
	$scope.getfeeds = function() {
		console.log("------feed button click-------");

		Feed.getFeeds({

		}, function(data) {
			$scope.listFeed = [];
			for ( var i = 0; i < data.length; i++) {
				$scope.listFeed.push(data[i]);
			}

			console.log(angular.fromJson(data));
		}, function(response) {
			console.log("error");
		});
	}
	$scope.file = "";
	$scope.postPhoto = function() {
		console.log("------post photo button click-------");

		console.log($scope.file);
	}
});
