'use strict';

app.factory('Auth', function($resource) {
	return $resource('/auth/:token', {
		token : "@token"
	}, {
		setToken : {
			method : 'POST'
		}
	});
});
app.factory('Group', function($resource) {
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