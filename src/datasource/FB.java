package datasource;

import java.util.List;

import models.Contact;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.types.User;

public class FB {

	private final String app_id = "212113275590062";
	private final String app_secret = "c46e82a7fd92912a713963dc8c71d310";
	
	private final FacebookClient client;
	
	public FB(){
		
		AccessToken accessToken = new DefaultFacebookClient().obtainAppAccessToken(app_id,app_secret);
		
		client = new DefaultFacebookClient(
				"AAACEdEose0cBAD8TPVKbN0nPl1ZBpXfmpL1uWjkyWoqDvjfENHZBngQTdtJD8pP8gwG4lz1TOgzvaLX1ZCNcFvTJ65U9j6BxsnMpZAVrrQZDZD"
				//accessToken.getAccessToken()
				);

		
	}
	
	public List<Contact> getFriends(String person_id){
		
		Connection<User> myFriends = client.fetchConnection("me/friends", User.class);

		List<User> friends = myFriends.getData();
		
		System.out.println(friends.size()+" friends");
		
		for(User user: friends)
			System.out.println(user.getName());
		
		return null;
	}
	
}
