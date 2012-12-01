package datasource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import models.Contact;
import clustering.WordDistance;

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
				"AAACEdEose0cBAGlENsuLLJmaTf9e8Ce5v4YbUlsYXsctyLQeAszydtccqQdvSyRJy7zYSC5fvRPQTNZBFueZAzZBDTgqBZBPs2pueUEdfwZDZD"				 
				//accessToken.getAccessToken()
				);		
	}
	
	public Set<Contact> getFriends(String person_id){
		
		String query = "me/friends";
		String fields = "birthday,name,hometown";//"birthday,name,about,bio,gender,quotes,address,hometown,interested_in,religion,sports,relationship_status";
		
		//fields+=",likes";
		
		Connection<User> myFriends = client.fetchConnection(query, User.class,Parameter.with("fields", fields));

		List<User> friends = myFriends.getData();
		Set<Contact> contacts = new HashSet<Contact>();
		
		for(User user: friends){
			//System.out.println(user.getName());
			
			Contact c = Contact.createContact(user);
			contacts.add(c);			
		}
		
		return contacts;
	}
	
	
}
