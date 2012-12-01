package datasource;

import java.util.List;

import models.Contact;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.types.User;

public class FB {

	private final String app_id = "212113275590062";
	private final String app_secret = "c46e82a7fd92912a713963dc8c71d310";
	
	private final FacebookClient client;
	
	public FB(){
		
		AccessToken accessToken = new DefaultFacebookClient().obtainAppAccessToken(app_id,app_secret);
		
		client = new DefaultFacebookClient(
				"AAACEdEose0cBAEooXM2HxKOMaulyPyYEc7qQ8pbOt9aAQqiBTZBRfZBpY8leOodfKRUgGH6XUtkBXNDfEaYQy8KQyzPe9ZBrMRdRgstKAZDZD"				 
				//accessToken.getAccessToken()
				);		
	}
	
	public List<Contact> getFriends(String person_id){
		
		String query = "me/friends";
		
		Connection<User> myFriends = client.fetchConnection(query, User.class,Parameter.with("fields", "birthday,bio,name,about"));

		List<User> friends = myFriends.getData();
		
		System.out.println(friends.size()+" friends");
		
		int about = 0;
		int bio = 0;
		int birthday = 0;
		
		for(User user: friends){
			System.out.println(user.getName());
			
			Contact c = Contact.createContact(user);
			
			System.out.println(c);
			
			if(user.getAbout()!=null) about++;
			if(user.getBio()!=null) bio++;
			if(user.getBirthday()!=null) birthday++;
			
		}
		
		System.out.println(about);
		System.out.println(bio);
		System.out.println(birthday);
		
		return null;
	}
	
}
