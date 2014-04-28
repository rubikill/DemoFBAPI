'use strict';

var GroupCtrl = (function($scope, Home, Auth, Feed, Photo, Album, $http, User,
    Page, Group, $location, ngProgress, RSS, tracking) {

    /**
     * Look at location
     */
    $scope.$on('$locationChangeSuccess', function() {
        $scope.path = $location.path();
    });

    /**
     * Current user
     */
    $scope.user = {};

    /**
     * Init
     */
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
        FB.api('/me', function(response) {
            $scope.user = response;
            console.log('Good to see you, ' + response.name + '.');
            $scope.getGroups();

            console.log(document.referrer);

            console.log(window.parent.document.referrer);

        });
        FB.getLoginStatus(function(response) {
            if (response.status === 'connected') {
                $scope.token = response.authResponse.accessToken;
                Auth.setToken({
                    token: response.authResponse.accessToken
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

    /**
     * Get all group
     */
    $scope.getGroups = function() {
        ngProgress.reset();
        ngProgress.start();
        Group.getGroups({

        }, function(data) {
            $scope.listGroups = [];
            for (var i = 0; i < data.length; i++) {
                $scope.listGroups.push(data[i]);
            }

            $scope.numberOfPages = Math.ceil($scope.listGroups.length / $scope.pageSize);

            ngProgress.complete();
        }, function(response) {
            console.log("error");
        });
    }

    // List of groups
    $scope.listGroups = [];

    // current page
    $scope.currentPage = 1;
    // number of items per page
    $scope.pageSize = 10;
    $scope.numberOfPages = 0;

    $scope.predicate = '-comments.length';

    $scope.sortByLikes = function() {
        $scope.predicate = '-likes.length';
    }

    $scope.sortByComments = function() {
        $scope.predicate = '-comments.length';
    }

    $scope.selectGroup = function(group) {
        $scope.selectedGroup = group;
    }

    /**
     * Get feeds before ...
     */
    $scope.getGroupFeedsBefore = function(date, group) {

        // ngProgress.start();
        if ($scope.isLoading && $scope.selectedGroup != null) {
            return;
        }

        if (group == null && group == undefined) {
            return;
        }

        console.log("-------load more-----------");
        if (date === 0) {
            ngProgress.reset();
            ngProgress.start();
            $scope.isLoading = true;
            $scope.selectedGroup = group;
            Group.getGroupFeedsBefore({
                id: group.id,
                par1: $scope.datepicker.from.toDateString(),
                after: $scope.datepicker.to.toDateString()
            }, function(data) {
                $scope.listFeed = data;
                $scope.lastestCreatedTime = data[data.length - 1].createdTime;
                ngProgress.complete();
                $scope.isLoading = false;
                console.log("success");
            }, function(response) {
                console.log("error");
            });
        } else {
            ngProgress.reset();
            ngProgress.start();
            $scope.isLoading = true;
            Group.getGroupFeedsBefore({
                id: group.id,
                par1: $scope.datepicker.from.toDateString(),
                after: $scope.lastestCreatedTime
            }, function(data) {
                for (var i = 0; i < data.length; i++) {
                    $scope.listFeed.push(data[i]);
                }
                $scope.lastestCreatedTime = data[data.length - 1].createdTime;
                ngProgress.complete();
                $scope.isLoading = false;
                console.log("success");
            }, function(response) {
                console.log("error");
            });
        }

    }

    $scope.getNewFeeds = function() {
        $scope.getGroupFeedsBefore(0, $scope.selectedGroup);
    }

    /**
     * Get feeds between ... ...
     */
    $scope.getGroupFeedsBetween = function(group) {
        if (group == null && group == undefined) {
            return;
        }

        Group.getGroupFeedsBetween({
            id: group.id,
            par1: $scope.datepicker.to,
            par2: $scope.datepicker.from
        }, function(data) {
            $scope.listFeed = data;
            // $scope.lastestCreatedTime = data[data.length - 1].createdTime;
            console.log("success");
        }, function(response) {
            console.log("error");
        });
    }

    /**
     * List statistic
     */
    $scope.listStatistic = [];
    $scope.selectedTop = "post";

    $scope.getTopPost = function() {
        $scope.selectedTop = "post";
    }

    $scope.getTopComment = function() {
        $scope.selectedTop = "comment";
    }

    $scope.getTopLiked = function() {
        $scope.selectedTop = "liked";
    }

    $scope.getTopSpam = function() {
        $scope.selectedTop = "spam";
    }

    /**
     * View comment
     */
    $scope.viewComment = function(feed) {
        if (feed.commentClick == undefined) {
            feed.commentClick = true;
        } else {
            feed.commentClick = !feed.commentClick;
        }

    }

    $scope.datepicker = {
        from: new Date("2010-01-02T00:00:00.000Z"),
        to: new Date()
    };

    /**
     * Statistic
     *
     * Get post from ... to ... and analyze
     */
    $scope.getBetween = function() {
        ngProgress.reset();
        ngProgress.start();
        switch ($scope.selectedTop) {
            case "post":
                console.log("post" + $scope.datepicker.from.toUTCString());
                Group.getTopPost({
                    id: $scope.selectedGroup.id,
                    par1: $scope.datepicker.from.toUTCString(),
                    after: $scope.datepicker.to.toUTCString()
                }, function(data) {
                    $scope.listStatistic = data;
                    ngProgress.complete();
                }, function(response) {
                    console.log("error");
                });
                break;
            case "comment":
                Group.getTopComment({
                    id: $scope.selectedGroup.id,
                    par1: $scope.datepicker.from.toUTCString(),
                    after: $scope.datepicker.to.toUTCString()
                }, function(data) {
                    $scope.listStatistic = data;
                    ngProgress.complete();
                }, function(response) {
                    console.log("error");
                });
                break;
            case "liked":
                Group.getTopLiked({
                    id: $scope.selectedGroup.id,
                    par1: $scope.datepicker.from.toUTCString(),
                    after: $scope.datepicker.to.toUTCString()
                }, function(data) {
                    $scope.listStatistic = data;
                    ngProgress.complete();
                }, function(response) {
                    console.log("error");
                });
                break;
            case "spam":
                Group.getTopSpam({
                    id: $scope.selectedGroup.id,
                    par1: $scope.datepicker.from.toUTCString(),
                    after: $scope.datepicker.to.toUTCString()
                }, function(data) {
                    $scope.listStatistic = data;
                    ngProgress.complete();
                }, function(response) {
                    console.log("error");
                });
                break;
            default:
                break;
        }
    }

    $scope.listFitFeed = [{
        name: "Thông tin chung",
        id: "57",
        tableId: "615"
    }, {
        name: "Thông báo hệ CQui",
        id: "58",
        tableId: "652"
    }, {
        name: "Thông báo hệ HCĐH",
        id: "261",
        tableId: "655"
    }, {
        name: "Thông báo hệ Tại chức",
        id: "260",
        tableId: "654"
    }, {
        name: "Thông báo hệ Cao đẳng",
        id: "263",
        tableId: "657"
    }, {
        name: "Thông báo Sau đại học",
        id: "264",
        tableId: "658"
    }, {
        name: "Thông tin tuyển dụng",
        id: "265",
        tableId: "659"
    }, {
        name: "Thông tin Hội thảo - Hội nghị",
        id: "493",
        tableId: "955"
    }, {
        name: "Tin tức thời sự CNTT",
        id: "266",
        tableId: "660"
    }];

    $scope.listRss = [];

    $scope.getRss = function(id, tableId) {
        ngProgress.reset();
        ngProgress.start();
        $scope.listRss = [];
        RSS.getRSSJson({
            id: id,
            tableid: tableId
        }, function(data) {
            $scope.listRss = data;
            ngProgress.complete();
        }, function(response) {
            console.log(response);
            ngProgress.complete();
        });
    }

    function getRef() {

        //setTimeout(getRef(), 3000);
    }

    $(window).load(function() {
        //setTimeout(getRef(), 3000);

        var s = document.referrer;

        if (s == '') {
            s = 'form addrerss';
        }

        tracking.doTracking({
            address: s
        }, {}, function() {
            // body...
        }, function() {
            // body...
        });
    });
});
