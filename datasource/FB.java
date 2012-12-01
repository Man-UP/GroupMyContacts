package datasource;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import models.Contact;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.types.Place;
import com.restfb.types.User;

public class FB {

	private static final String app_id = "212113275590062";
	private static final String app_secret = "c46e82a7fd92912a713963dc8c71d310";
	
	private static final FacebookClient client;
	
	static{
		
		AccessToken accessToken = new DefaultFacebookClient().obtainAppAccessToken(app_id,app_secret);
		
		client = new DefaultFacebookClient(
				"AAACEdEose0cBAGOtoKWlC6qyrogcRS6Qs6dUQlnZAfZAky6HlPG3Jl75GNljMUXgJMX9X2snPo9F68EI5MZB0JMyecV4VZA0Yacred7CgwZDZD"				 
				//accessToken.getAccessToken()
				);		
	}
	
	static public Set<Contact> getFriends(String person_id){
		
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

	public static String getLatLong(String location_id) {
		
		Place place = client.fetchObject(location_id,Place.class);
		
		Double lat = place.getLocation().getLatitude();
		Double lon = place.getLocation().getLongitude();
		
		String lat_s = lat.toString().replace('.', 'n').substring(0,4);
		String lon_s = lon.toString().replace('.', 'w').substring(0,4);
		
		return null;
		
	}
	
	
}
