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
				"AAACEdEose0cBAF7vNaUQVegAhZAQZAGLPecpQ16h2N13MNNY9hk9c9aE57baBmBJ1RnWPZCUfmHBSbNvYTHpSLmoJoIkDtuwSCddiOczA4N36Kt0xWd"				 
				//accessToken.getAccessToken()
				);		
	}
	
	/*
	 * Get the friends of a person using a person id
	 */
	
	static public Set<Contact> getFriends(String person_id){
		
		String query = "me/friends";
		
		// These are the fields of the friends that we ask for
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

	/*
	 * Get longitude and latitude from a Facebook Place using a location id
	 * 
	 */
	
	public static String getLonLat(String location_id) {
		
		Place place = client.fetchObject(location_id,Place.class);
		
		Double lat = place.getLocation().getLatitude();
		Double lon = place.getLocation().getLongitude();
		try{
		String lat_s = (lat.toString()+"00").replace('.', 'n').substring(0,4);
		String lon_s = (lon.toString()+"00").replace('.', 'w').substring(0,4);
		
		
		return lon_s+"|"+lat_s;
		}
		catch(Exception e){
			System.out.println(lat+"   "+lon);
		}
		return "";
		
		
	}
	
	
}
