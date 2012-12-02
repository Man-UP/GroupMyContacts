function loggedIn() {
	FB.api('/me', function(r) {
		$("#hello").html("Hello, " + r.name);
	});
	$("#throbber").hide();
	$("#hello").show();
}

function notLoggedIn() {
	$("#throbber").hide();
	$("#hello").show();
}

window.fbAsyncInit = function() {
	FB.init({
		appId      : '212113275590062',
		status     : true,
		cookie     : true,
		xfbml      : true
	});

	FB.getLoginStatus(function(r) {
		console.log(r);
		if (r.status === 'connected') {
			loggedIn();
		} else {
			notLoggedIn();
		}
	});

	FB.Event.subscribe('auth.login', function(r) {
		if (r.status === 'connected') {
			loggedIn();
		}
	});
};


$(document).ready(function () {
	var d = document;
	var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
	if (d.getElementById(id)) {return;}
	js = d.createElement('script'); js.id = id; js.async = true;
	js.src = "http://connect.facebook.net/en_US/all.js";
	ref.parentNode.insertBefore(js, ref);
});
