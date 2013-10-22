'use strict';

var app = angular.module('app', [ 'app.services' ]);

app.config([ '$routeProvider', function($routeProvider, $rootScope) {
	$routeProvider.otherwise({
		redirectTo : ('/home')
	});
} ]);

var appservice = angular.module('app.services', [ 'ngResource',
		'angularFileUpload' ]);

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

appservice.factory('Photo', function($resource) {
	return $resource('/photo/:photo', {
		photo : "@photo"
	}, {
		postPhoto : {
			method : 'POST'
		},
		getPhoto : {
			method : 'GET'
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
	return $resource('/feeds/:id/:action', {
		id : "@id",
		action : "@action"
	}, {
		getFeeds : {
			method : 'GET',
			isArray : true
		},
		like : {
			method : 'POST',
			params : {
				action : "like"
			}
		},
		unlike : {
			method : 'POST',
			params : {
				action : "unlike"
			}
		}
	});
});

appservice.factory('Album', function($resource) {
	return $resource('/album/:id', {
		id : "@id"
	}, {
		getAlbums : {
			method : 'GET',
			isArray : true
		},
		getAlbum : {
			method : 'GET',
			isArray : true
		}
	});
});

appservice.factory('User', function($resource) {
	return $resource('/user/:id', {
		id : "@id"
	}, {
		getUser : {
			method : 'GET'
		}
	});
});

appservice.factory('Page', function($resource) {
	return $resource('/page', {}, {
		getPagesAdmin : {
			method : 'GET',
			isArray : true
		}
	});
});

appservice.factory('Group', function($resource) {
	return $resource('/group/:id/:action', {
		id : "@id",
		action : "@action"
	}, {
		getGroups : {
			method : 'GET',
			isArray : true
		},
		getGroup : {
			method : 'GET'
		},
		getGroupMembers : {
			method : 'GET',
			isArray : true,
			params : {
				action : "members"
			}
		},
		getGroupFeeds : {
			method : 'GET',
			isArray : true,
			params : {
				action : "feeds"
			}
		},
		getGroupCover : {
			method : 'GET',
			params : {
				action : "cover"
			}
		},
		getStatistic : {
			method : 'GET',
			isArray : true,
			params : {
				action : "statistic"
			}
		}
	});
});

var HomeCtrl = (function($scope, Home, Auth, Feed, Photo, Album, $http, User,
		Page, Group) {

	// Here we run a very simple test of the Graph API after login is
	// successful.
	// This testAPI() function is only called in those cases.

	$scope.user = {};

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
		// var scope =
		// "email,publish_stream,read_stream,user_status,user_likes,publish_actions";
		FB.Event
				.subscribe(
						'auth.authResponseChange',
						function(response) {
							// Here we specify what we do with the response
							// anytime this event
							// occurs.
							if (response.status === 'connected') {
								// The response object is returned with a status
								// field that lets
								// the app know the current
								// login status of the person. In this case,
								// we're handling the
								// situation where they
								// have logged in to the app.
								console.log("connected");
								testAPI();

							} else if (response.status === 'not_authorized') {
								// In this case, the person is logged into
								// Facebook, but not
								// into the app, so we call
								// FB.login() to prompt them to do so.
								// In real-life usage, you wouldn't want to
								// immediately prompt
								// someone to login
								// like this, for two reasons:
								// (1) JavaScript created popup windows are
								// blocked by most
								// browsers unless they
								// result from direct interaction from people
								// using the app
								// (such as a mouse click)
								// (2) it is a bad experience to be continually
								// prompted to
								// login upon page load.
								console.log("Login 1");

								FB
										.login(
												function(response) {
													// handle the response
													if (!response
															|| response.error) {
														alert('Error occured');
													} else {
														alert('OK');
													}
												},
												{
													scope : 'email,user_notes,user_status,publish_actions,user_groups,user_likes,user_photos,user_about_me,user_birthday,user_friends,user_hometown,user_location,user_videos,create_note,manage_friendlists,photo_upload,read_requests,share_item,export_stream,manage_notifications,publish_stream,read_mailbox,read_stream,video_upload,manage_pages,read_friendlists,read_page_mailboxes,status_update,user_events,page'
												});

							} else {
								// In this case, the person is not logged into
								// Facebook, so we
								// call the login()
								// function to prompt them to do so. Note that
								// at this stage
								// there is no indication
								// of whether they are logged into the app. If
								// they aren't then
								// they'll see the Login
								// dialog right after they log in to Facebook.
								// The same caveats as above apply to the
								// FB.login() call here.

								FB
										.login(
												function(response) {
													// handle the response
													if (!response
															|| response.error) {
														alert('Error occured');
													} else {
														alert('OK');
													}
												},
												{
													scope : 'email,user_notes,user_status,publish_actions,user_groups,user_likes,user_photos,user_about_me,user_birthday,user_friends,user_hometown,user_location,user_videos,create_note,manage_friendlists,photo_upload,read_requests,share_item,export_stream,manage_notifications,publish_stream,read_mailbox,read_stream,video_upload,manage_pages,read_friendlists,read_page_mailboxes,status_update,user_events,page'
												});
							}
						});
	};

	function testAPI() {
		console.log('Welcome!  Fetching your information.... ');
		FB.api('/me', function(response) {

			$scope.user = response;
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
					console.log("success");
					// Feed.getFeeds({
					//
					// }, function(data) {
					// $scope.listFeed = [];
					// for ( var i = 0; i < data.length; i++) {
					// $scope.listFeed.push(data[i]);
					// }
					//
					// console.log(angular.fromJson(data));
					// }, function(response) {
					// console.log("error");
					// });
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

	$scope.postPhoto = function() {
		console.log("-----post photo btn click------");
		console.log($scope.token);

		for ( var i = 0; i < $scope.files.length; i++) {
			var $file = $scope.files[i];
			$http.uploadFile({
				url : '/photo/:photo', // upload.php script, node.js route,
				// or servlet upload url)
				data : {
					myObj : "Upload file"
				},
				file : $file,
				photo : $scope.content
			}).then(function(data, status, headers, config) {
				// file is uploaded successfully
				console.log(data);
			}, function(dada) {
				console.log(data);
			}, function(response) {
				console.log(response);
			});
		}
	}

	$scope.files = [];
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

	$scope.onFileSelect = function($files) {
		$scope.files = $files;
	}

	$scope.listAlbum = [];
	$scope.getAlbums = function() {
		console.log("------albums button click-------");

		Album.getAlbums({

		}, function(data) {
			$scope.listAlbum = [];
			for ( var i = 0; i < data.length; i++) {
				$scope.listAlbum.push(data[i]);
			}

			console.log(angular.fromJson(data));
		}, function(response) {
			console.log("error");
		});
	}

	$scope.listPhotos = [];
	$scope.getAlbum = function(id) {
		console.log("------view photos button click-------");

		Album.getAlbum({
			id : id
		}, function(data) {
			$scope.listPhotos = [];
			for ( var i = 0; i < data.length; i++) {
				$scope.listPhotos.push(data[i]);
			}
			console.log(angular.fromJson(data));
		}, function(response) {
			console.log("error");
		});
	}

	$scope.checkLike = function(listLike) {
		for ( var i = 0; i < listLike.length; i++) {
			if (listLike[i].id == $scope.user.id) {
				$scope.isLike = true;
				return;
			}
		}
		$scope.isLike = false;
	}

	$scope.like = function(id, listLike) {
		console.log(id);

		Feed.like({
			id : id
		}, function(data) {
			console.log("success");
			$scope.isLike = true;
		}, function(response) {
			console.log("error");
		});

	}

	$scope.unlike = function(id, listLike) {

		console.log(id);

		Feed.unlike({
			id : id
		}, function(data) {
			console.log("success");
			$scope.isLike = false;
		}, function(response) {
			console.log("error");
		});

	}

	$scope.getPages = function() {

		Page.getPagesAdmin({

		}, function(data) {
			console.log("success");
			console.log(data);
		}, function(response) {
			console.log("error");
		});
	}

	$scope.listGroups = [];
	$scope.getGroups = function() {

		Group.getGroups({

		}, function(data) {
			$scope.listGroups = [];
			for ( var i = 0; i < data.length; i++) {
				$scope.listGroups.push(data[i]);
			}
			console.log(data);
			console.log("success");
		}, function(response) {
			console.log("error");
		});
	}

	$scope.getGroup = function(id) {

		Group.getGroups({
			id : id
		}, function(data) {
			console.log(angular.fromJson(data));
			console.log("success");
		}, function(response) {
			console.log("error");
		});
	}

	$scope.getGroupMembers = function(id) {
		Group.getGroupMembers({
			id : id
		}, function(data) {
			console.log(angular.fromJson(data));
			console.log("success");
		}, function(response) {
			console.log("error");
		});
	}

	
	$scope.getGroupFeeds = function(id) {
		Group.getGroupFeeds({
			id : id
		}, function(data) {
			console.log(angular.fromJson(data));
			console.log("success");
		}, function(response) {
			console.log("error");
		});
	}

	$scope.listStatistic = [];
	$scope.getGroupStatistic = function(id) {
		Group.getStatistic({
			id : id
		}, function(data) {
			//console.log(angular.fromJson(data));
			$scope.listStatistic = data;			
			console.log("success");
		}, function(response) {
			console.log("error");
		});
	}

	$scope.getGroupCover = function(id) {
		Group.getGroupCover({
			id : id
		}, function(data) {
			console.log(angular.fromJson(data));
			console.log("success");
		}, function(response) {
			console.log("error");
		});
	}

	// $scope.getPhoto = function(id) {
	// Photo.getPhoto({
	// id : id
	// }, function(data) {
	// console.log(data);
	// }, function(response) {
	// console.log("error");
	// });
	// }
});