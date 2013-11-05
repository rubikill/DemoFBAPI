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
			$scope.getGroups();
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

	$scope.getGroups = function() {
		Group.getGroups({

		}, function(data) {
			$scope.listGroups = [];
			for ( var i = 0; i < data.length; i++) {
				$scope.listGroups.push(data[i]);
			}

			$scope.numberOfPages = Math.ceil($scope.listGroups.length
					/ $scope.pageSize);

			console.log("success");
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

	$scope.predicate = '-comments.length';

	$scope.sortByLikes = function() {
		$scope.predicate = '-likes.length';
	}

	$scope.sortByComments = function() {
		$scope.predicate = '-comments.length';
	}

	$scope.getGroupFeeds = function(id) {
		Group.getGroupFeeds({
			id : id
		}, function(data) {
			$scope.listFeed = data;
			console.log(data);
			console.log("success");
		}, function(response) {
			console.log("error");
		});
	}

	$scope.selectGroup = function(group) {
		$scope.selectedGroup = group;
	}

	$scope.getGroupFeedsBefore = function(date, group) {
		if (group == null && group == undefined) {
			return;
		}

		if (date === 0) {
			$scope.selectedGroup = group;
			Group.getGroupFeedsBefore({
				id : group.id,
				par1 : (new Date()).toDateString()
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
				par1 : (new Date(date)).toDateString()
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

	$scope.getGroupFeedsBetween = function(group, from, to) {
		if (group == null && group == undefined) {
			return;
		}

		Group.getGroupFeedsBetween({
			id : group.id,
			par1 : to,
			par2 : from
		}, function(data) {
			$scope.listFeed = data;
			// $scope.lastestCreatedTime = data[data.length - 1].createdTime;
			console.log("success");
		}, function(response) {
			console.log("error");
		});
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

	$scope.viewComment = function(feed) {
		if (feed.commentClick == undefined) {
			feed.commentClick = true;
		} else {
			feed.commentClick = !feed.commentClick;
		}

	}

	$scope.like = function(id) {
		Feed.like({
			id : id
		}, function(data) {

		}, function(response) {

		});
	}
	
	$scope.datepicker = { from : new Date("2000-01-02T00:00:00.000Z"),
			to : new Date()};
	  
	$scope.cal = function() {		
		console.log($scope.datepicker);
	}
});