package main;

import java.util.Set;

import models.Contact;
import clustering.HomeTownCluster;
import datasource.FB;

public class Main {

	
	public static void main(String[] args){
		
		// create FB object
		
		Set<Contact> friends = FB.getFriends("");
		
		HomeTownCluster htc = new HomeTownCluster();
		
		htc.cluster(friends);
		
		
		// create clustering object
		
		// do clustering
		
		
	}
	
}
