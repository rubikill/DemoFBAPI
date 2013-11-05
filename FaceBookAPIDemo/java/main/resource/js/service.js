'use strict';

angular.module('scroll', []).directive('whenScrolled', function() {
    return function(scope, elm, attr) {
        var raw = elm[0];
        
        elm.bind('scroll', function() {
            if (raw.scrollTop + raw.offsetHeight >= raw.scrollHeight) {
                scope.$apply(attr.whenScrolled);
            }
        });
    };
});

var appservice = angular.module('app.services', [ 'ngResource',
		'angularFileUpload' , 'ui.bootstrap']);

appservice.filter('startFrom', function() {
	return function(input, start) {
		if (input) {
			start = +start; // parse to int
			return input.slice(start);
		}
		return [];
	}
});	
	
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
	return $resource('/group/:id/:action/:by/:par1/:after/:par2', {
		id : "@id",
		action : "@action",
		by : "@by",
		par1 : "@par1",
		after : "@after",
		par2 : "@par2"
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
		getGroupFeedsBefore : {
			method : 'GET',
			isArray : true,
			params : {
				action : "feeds",
				by : "before"
			}
		},
		getGroupFeedsBetween : {
			method : 'GET',
			isArray : true,
			params : {
				action : "feeds",
				by : "before",
				after : "after"
			}
		},
		getGroupCover : {
			method : 'GET',
			params : {
				action : "cover"
			}
		},
		getTopPost : {
			method : 'GET',
			isArray : true,
			params : {
				action : "statistic",
				by : "post"
			}
		},
		getTopComment : {
			method : 'GET',
			isArray : true,
			params : {
				action : "statistic",
				by : "comment"
			}
		},
		getTopLiked : {
			method : 'GET',
			isArray : true,
			params : {
				action : "statistic",
				by : "liked"
			}
		},
		getTopSpam : {
			method : 'GET',
			isArray : true,
			params : {
				action : "statistic",
				by : "spam"
			}
		}
	});
});