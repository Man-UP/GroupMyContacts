package main;

import java.util.Set;

import clustering.BirthMonthCluster;

import models.Contact;
import datasource.FB;

public class Main {

	
	public static void main(String[] args){
		
		// create FB object
		
		FB fb = new FB();
		Set<Contact> friends = fb.getFriends("");
		
		for(Contact friend : friends){
			if(friend.get("Hometown")!=null)
				System.out.println(friend.get("Hometown"));
		}
		
		
		// create clustering object
		
		// do clustering
		
		
	}
	
}
