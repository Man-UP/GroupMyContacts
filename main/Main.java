package main;

import java.util.Set;

import models.Contact;
import clustering.BirthMonthCluster;
import clustering.BirthdayCluster;
import clustering.HomeTownCluster;
import datasource.FB;

public class Main {

	
	public static void main(String[] args){
		
		/*
		 * We're expecting an access taken as args[0]
		 */
		
		try{
			String access_token = args[0];
			FB.setAccessToken(access_token);
		}
		catch(IndexOutOfBoundsException e){
			System.err.println("Access Token not given");
			System.exit(1);
		}
		
		/*
		 * FB contains all the logic for talking to Facebook
		 */
		
		Set<Contact> friends = FB.getFriends("");
		
		//BirthMonthCluster.print_clusters(friends);
		
		//HomeTownCluster htc = new HomeTownCluster();		
		//htc.print_clusters(friends);
		
		BirthdayCluster.print_clusters(friends);

		
		
	}
	
}
