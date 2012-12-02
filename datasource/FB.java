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
import com.restfb.exception.FacebookException;
import com.restfb.types.Place;
import com.restfb.types.User;

/**
 * 
 * @author Man-UP Giles, Dragos
 * FB creates the link to facebook api.
 * Link: RESTfb.com
 *
 */

public class FB {

	private static final String app_id = "212113275590062";
	private static final String app_secret = "c46e82a7fd92912a713963dc8c71d310";
	private static String query;
	private static String queryFields;
	
	private static FacebookClient client;

	public static void setAccessToken(String access_token) {
		client = new DefaultFacebookClient(access_token);
		query = "me/friends";
		queryFields = "birthday,name,about,bio,gender,quotes,address,hometown,"+
				 "interested_in,religion,sports,relationship_status,education,"+
				 "inspirational_people, political, languages, books";
		
	}	
	
	/*
	 * Gets the main user's friend's by returning a Set<contacts> .
	 */
	
	static public Set<Contact> getMyFriends(){
		
		
		//fields+=",likes";
		
		Connection<User> myFriends = client.fetchConnection(query, User.class,Parameter.with("fields", queryFields));

		List<User> userList = myFriends.getData();
		Set<Contact> contacts = new HashSet<Contact>();
		
		for(User user: userList){
			//System.out.println(user.getName());
			
			Contact c = Contact.createContact(user);
			contacts.add(c);			
		}
		
		return contacts;
	}
	
	/*
	 * Expand the clusterer to crawl on friend's friend list aswell.
	 * query is modified to friendId/friends
	 * 
	 * Issue: some don't have their friend list public. Solution: Catch unauthorised
	 * exception and go to next contact. New issue: java.util.ConcurrentModificationException
	 */
	
	static public Set<Contact> crawlMyFriends(Set<Contact> contacts) throws FacebookException
	{
		for(Contact someContact: contacts){
			queryLinkToContact(someContact);

			Connection<User> someContactFriends;
			
			try{someContactFriends = client.fetchConnection(query, User.class,Parameter.with("fields", queryFields));}
			catch(FacebookException e) {continue;}
			System.out.println(query + " continue");
			List<User> userList = someContactFriends.getData();
			for(User user: userList){
				contacts.add(Contact.createContact(user));
			}
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
		
		return lon+"|"+lat;
		
	}
	
	/*
	 * Returns a query link to the contact's friends.
	 * Format example 36920689/friends
	 */
	public static void queryLinkToContact(Contact contact){
		String queryLink="";
		String contactId = contact.get("Id");
		queryLink = contactId + "/friends";
		
		query = queryLink;
	}
	


	
	
}
