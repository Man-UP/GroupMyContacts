package main;

import java.util.Set;

import models.Contact;
import clustering.BirthMonthCluster;
import clustering.HomeTownCluster;
import datasource.FB;

public class Main {

	
	public static void main(String[] args){
		
		/*
		 * FB contains all the logic for talking to Facebook
		 */
		
		Set<Contact> friends = FB.getFriends("");
		
		//BirthMonthCluster.print_clusters(friends);
		
		HomeTownCluster htc = new HomeTownCluster();		
		htc.print_clusters(friends);

		
		
	}
	
}
