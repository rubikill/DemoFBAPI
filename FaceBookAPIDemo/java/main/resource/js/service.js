'use strict';

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
	return $resource('/group/:id/:action/:by', {
		id : "@id",
		action : "@action",
		by : "@by"
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