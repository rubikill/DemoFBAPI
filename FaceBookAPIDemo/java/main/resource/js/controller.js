'use strict';

var GroupCtrl = (function($scope, Home, Auth, Feed, Photo, Album, $http, User,
		Page, Group, $location) {
	
	$scope.$on('$locationChangeSuccess', function() {
		$scope.path = $location.path();
	});

	$scope.user = {};

	window.fbAsyncInit = function() {
		FB.init({
			appId : '607212762654727', // App ID
			channelUrl : 'http://localhost:9000/index.html', // Channel File
			status : true, // check login status
			xfbml : true,

		// parse XFBML
		});

		FB.Event
				.subscribe(
						'auth.authResponseChange',
						function(response) {
							if (response.status === 'connected') {
								console.log("connected");
								testAPI();

							} else if (response.status === 'not_authorized') {
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
				$scope.token = response.authResponse.accessToken;
				Auth.setToken({
					token : response.authResponse.accessToken
				}, function(data) {
					
					console.log("success");
				}, function(response) {
					console.log("fail");
				});

			} else if (response.status === 'not_authorized') {
				console.log('Fail');
			} else {
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

	Group.getGroups({

	}, function(data) {
		$scope.listGroups = [];
		for ( var i = 0; i < data.length; i++) {
			$scope.listGroups.push(data[i]);
		}

		$scope.numberOfPages = Math
				.ceil($scope.listGroups.length
						/ $scope.pageSize);

		console.log("success");
	}, function(response) {
		console.log("error");
	});
	
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
	// current page
	$scope.currentPage = 1;

	// number of items per page
	$scope.pageSize = 5;
	$scope.numberOfPages = 0;

	$scope.getGroup = function(id) {

		Group.getGroups({
			id : id
		}, function(data) {
			console.log("success");
		}, function(response) {
			console.log("error");
		});
	}

	$scope.getGroupMembers = function(id) {
		Group.getGroupMembers({
			id : id
		}, function(data) {
			console.log("success");
		}, function(response) {
			console.log("error");
		});
	}

	$scope.getGroupFeeds = function(id) {
		Group.getGroupFeeds({
			id : id
		}, function(data) {
			$scope.listFeed = data;
			console.log("success");
		}, function(response) {
			console.log("error");
		});
	}

	$scope.selectGroup = function(group) {
		$scope.selectedGroup = group;
	}

	$scope.getGroupFeedsBefore = function(date, group) {
		if (date === 0) {
			$scope.selectedGroup = group;
			Group.getGroupFeedsBefore({
				id : group.id,
				par : (new Date()).toDateString()
			}, function(data) {
				$scope.listFeed = data;
				$scope.lastestCreatedTime = data[data.length - 1].createdTime;
				console.log("success");
			}, function(response) {
				console.log("error");
			});
		} else {
			Group.getGroupFeedsBefore({
				id : group.id,
				par : (new Date(date)).toDateString()
			}, function(data) {
				for ( var i = 0; i < data.length; i++) {
					$scope.listFeed.push(data[i]);
				}
				$scope.lastestCreatedTime = data[data.length - 1].createdTime;
				console.log("success");
			}, function(response) {
				console.log("error");
			});
		}

	}

	$scope.listStatistic = [];
	$scope.getTopPost = function(id) {
		Group.getTopPost({
			id : id
		}, function(data) {
			// console.log(angular.fromJson(data));
			$scope.listStatistic = data;
			console.log("success");
		}, function(response) {
			console.log("error");
		});
	}

	$scope.getTopComment = function(id) {
		Group.getTopComment({
			id : id
		}, function(data) {
			// console.log(angular.fromJson(data));
			$scope.listStatistic = data;
			console.log(data);
		}, function(response) {
			console.log("error");
		});
	}

	$scope.getTopLiked = function(id) {
		Group.getTopLiked({
			id : id
		}, function(data) {
			// console.log(angular.fromJson(data));
			$scope.listStatistic = data;
			console.log(data);
		}, function(response) {
			console.log("error");
		});
	}

	$scope.getTopSpam = function(id) {
		Group.getTopSpam({
			id : id
		}, function(data) {
			// console.log(angular.fromJson(data));
			$scope.listStatistic = data;
			console.log(data);
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
});