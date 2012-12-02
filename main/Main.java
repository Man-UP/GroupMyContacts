package main;

import java.util.Set;

import models.Contact;
import output.ClustersToGEXF;
import clustering.BirthMonthCluster;
import clustering.BirthdayCluster;
import clustering.Clustering;
import clustering.HomeTownCluster;
import clustering.LanguageCluster;
import clustering.bowCluster;
import datasource.FB;

public class Main {

	
	public static void main(String[] args){
		
		/*
		 * We're expecting an access taken as args[0]
		 * and the clustering method as args[1]
		 * - 1 is HomeTownCluster
		 * - 2 is BirthMonthCluster
		 * - 3 is BirthdayCluster
		 * 
		 * args[2] then tells us the output required 
		 * - 1 is print out
		 * - 2 is GEXF
		 */
		
		try{
			String access_token = args[0];
			FB.setAccessToken(access_token);
		}
		catch(IndexOutOfBoundsException e){
			System.err.println("Access Token not given");
			System.exit(1);
		}
		
		Set<Contact> friends = FB.getFriends("");
		
		Clustering clusterer = null;
		
		try{
			switch(Integer.parseInt(args[1])){
			case 1 : clusterer = new HomeTownCluster(); break;
			case 2 : clusterer = new BirthMonthCluster(); break;
			case 3 : clusterer = new BirthdayCluster(); break;
			case 4 : clusterer = new bowCluster(); break;
			case 5 : clusterer = new LanguageCluster(); break;
			default : throw new RuntimeException("You didn't give an existing clusterer option");
			}
		}
		catch(IndexOutOfBoundsException e){
			System.err.println("Clusterer not given");
			System.exit(1);
		}
		catch(NumberFormatException e){
			System.err.println("Clusterer needs to be given as a number");
			System.exit(1);			
		}

		//clusterer.print_clusters(friends);
		try{
			
			switch(Integer.parseInt(args[2])){
			case 1 : clusterer.print_clusters(friends); break;
			case 2 : System.out.println(ClustersToGEXF.make(clusterer.cluster(friends))); break;
			default : throw new RuntimeException("You didn't give an output option");
			}
			
		}
		catch(IndexOutOfBoundsException e){
			System.err.println("output not given");
			System.exit(1);
		}		

		
	}
	
}
