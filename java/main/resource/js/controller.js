'use strict';
/*====================================================
                     GroupCtrl
=====================================================*/
app.controller('GroupCtrl', ['$scope', 'Group', 'Auth', '$location', '$rootScope',
    function(scope, Group, Auth, location, rootScope) {
        /* facebook login use facebook.js in vendor*/
        /* Current user */
        rootScope.show = null;
        scope.user = {};
        /* Init */
        window.fbAsyncInit = function() {
            FB.init({
                appId: '380947045341754', // App ID
                channelUrl: 'http://localhost:9000/index.html', // Channel File
                status: true, // check login status
                xfbml: true,

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
                                        if (!response || response.error) {
                                            alert('Error occured');
                                        } else {
                                            alert('OK');
                                        }
                                    }, {
                                        scope: 'email,user_notes,user_status,publish_actions,user_groups,user_likes,user_photos,user_about_me,user_birthday,user_friends,user_hometown,user_location,user_videos,create_note,manage_friendlists,photo_upload,read_requests,share_item,export_stream,manage_notifications,publish_stream,read_mailbox,read_stream,video_upload,manage_pages,read_friendlists,read_page_mailboxes,status_update,user_events,page'
                                    });
                        } else {
                            FB
                                .login(
                                    function(response) {
                                        // handle the response
                                        if (!response || response.error) {
                                            alert('Error occured');
                                        } else {
                                            alert('OK');
                                        }
                                    }, {
                                        scope: 'email,user_notes,user_status,publish_actions,user_groups,user_likes,user_photos,user_about_me,user_birthday,user_friends,user_hometown,user_location,user_videos,create_note,manage_friendlists,photo_upload,read_requests,share_item,export_stream,manage_notifications,publish_stream,read_mailbox,read_stream,video_upload,manage_pages,read_friendlists,read_page_mailboxes,status_update,user_events,page'
                                    });
                        }
                    });
        };

        function testAPI() {
            console.log('Welcome!  Fetching your information.... ');

            FB.getLoginStatus(function(response) {
                if (response.status === 'connected') {
                    scope.token = response.authResponse.accessToken;
                    Auth.setToken({
                        token: response.authResponse.accessToken
                    }, function(data) {
                        console.log("success");

                        FB.api('/me', function(response) {
                            console.log(response);
                            scope.user = response;
                            console.log('Good to see you, ' + response.name + '.');
                            scope.getGroups();
                        });
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
        scope.getGroups = function() {
            rootScope.show= 1;
            Group.getGroups({}, function(data) {
                scope.listGroups = [];
                scope.listGroups = data;
                scope.numberOfPages = Math.ceil(scope.listGroups.length / scope.pageSize);
                console.log(scope.listGroups);
                rootScope.show = null;
            }, function(response) {
                console.log('error');
                rootScope.show = null;
            });
        }

        scope.processFeed = function(group) {
            scope.groupName = localStorage.setItem('gName', group.name);
            location.path('/feed/' + group.id);
        }
        scope.processStatistic = function(group) {
            scope.groupName = localStorage.setItem('gName', group.name);
            location.path('/statistic/' + group.id);
        }
    }
]);
/*====================================================
                     FeedCtrl
=====================================================*/
app.controller('FeedCtrl', ['$scope', '$routeParams', 'Group', '$rootScope',
    function(scope, routeParams, Group, rootScope) {
        rootScope.show = 1;
        scope.groupName = localStorage.getItem('gName');
        scope.groupId = routeParams.groupId;
        /**
         * Get feeds
         */
/*        Group.getGroupFeeds({
            id: routeParams.groupId
        }, function(data) {
            console.log(data);
            scope.listFeed = data;
            rootScope.show = null;
        }, function(response) {
            console.log("error");
            rootScope.show = null;
        });*/
        var today = new Date();
        today.setYear(2005);
        scope.isLoading = true;
        Group.getGroupFeedsBefore({
            id: routeParams.groupId,
            par1: today.toUTCString(),
            after: (new Date()).toUTCString()
        }, function(data) {
            console.log(data);
            scope.listFeed = data;
            scope.lastestCreatedTime = data[data.length - 1].createdTime;
            scope.isLoading = false;
            rootScope.show = null;
            console.log("success");
        }, function(response) {
            console.log("error");
        });

       scope.getGroupFeedsBefore = function(date, idgroup) {
            console.log("-------load more-----------");
            if (date === 0) {
                scope.isLoading = true;
                Group.getGroupFeedsBefore({
                    id: idgroup,
                    par1: today.toUTCString(),
                    after: (new Date()).toUTCString()
                }, function(data) {
                    console.log(data);
                    scope.listFeed = data;
                    scope.lastestCreatedTime = data[data.length - 1].createdTime;
                    scope.isLoading = false;
                    console.log("success");
                }, function(response) {
                    console.log("error");
                });
            } else {
                scope.isLoading = true;
                Group.getGroupFeedsBefore({
                    id: idgroup,
                    par1: today.toUTCString(),
                    after: (new Date()).toUTCString()
                }, function(data) {
                    for (var i = 0; i < data.length; i++) {
                        scope.listFeed.push(data[i]);
                    }
                    scope.lastestCreatedTime = data[data.length - 1].createdTime;
                    scope.isLoading = false;
                    console.log("success");
                }, function(response) {
                    console.log("error");
                });
            }
        }      

        /**
         * Get comment by feed
         */
        scope.viewComment = function(feed) {
            if (feed.commentClick == undefined) {
                feed.commentClick = true;
            } else {
                feed.commentClick = !feed.commentClick;
            }
        }
    }
]);
/*====================================================
                     StatisticCtrl
=====================================================*/
app.controller('StatisticCtrl', ['$scope', '$routeParams',
    function(scope, routeParams) {
        scope.groupName = localStorage.getItem('gName');
        scope.groupId = routeParams.groupId;
    }
]);
/*====================================================
                     DetailCtrl
=====================================================*/
app.controller('DetailCtrl', ['$scope', '$routeParams', 'Group', '$rootScope',
    function(scope, routeParams, Group, rootScope) {
        rootScope.show = 1;
        scope.groupName = localStorage.getItem('gName');
        var today = new Date();
        today.setYear(2005);
        scope.groupId = routeParams.groupId;
        scope.groupBy = routeParams.groupBy;
        console.log(routeParams);
        switch (routeParams.groupBy) {
            case "like":
                Group.getTopLiked({
                    id: scope.groupId,
                    par1: today.toUTCString(),
                    after: (new Date()).toUTCString()
                }, function(data) {
                    scope.listStatistic = data;
                    console.log(data);
                    rootScope.show = null;
                }, function(response) {
                    console.log("error");
                    rootScope.show = null;
                });
                break;
            case "post":
                Group.getTopPost({
                    id: scope.groupId,
                    par1: today.toUTCString(),
                    after: (new Date()).toUTCString()
                }, function(data) {
                    scope.listStatistic = data;
                    console.log(data);
                    rootScope.show = null;
                }, function(response) {
                    console.log("error");
                    rootScope.show = null;
                });
                break;
            case "comment":
                Group.getTopComment({
                    id: scope.groupId,
                    par1: today.toUTCString(),
                    after: (new Date()).toUTCString()
                }, function(data) {
                    scope.listStatistic = data;
                    console.log(data);
                    rootScope.show = null;
                }, function(response) {
                    console.log("error");
                    rootScope.show = null;
                });
                break;
            case "spam":
                Group.getTopSpam({
                    id: scope.groupId,
                    par1: today.toUTCString(),
                    after: (new Date()).toUTCString()
                }, function(data) {
                    scope.listStatistic = data;
                    console.log(data);
                    rootScope.show = null;
                }, function(response) {
                    console.log("error");
                    rootScope.show = null;
                });
                break;
            default:
                break;
        }
    }
]);
