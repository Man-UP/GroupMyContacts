package clustering;

import java.util.HashSet;
import java.util.Set;

import output.Cluster;

import models.Contact;

import com.aliasi.cluster.CompleteLinkClusterer;
import com.aliasi.cluster.HierarchicalClusterer;
import com.aliasi.util.Distance;

public class BirthMonthCluster {

	static public void print_clusters(Set<Contact> friends){
		
		Set<Cluster> friend_months = BirthMonthCluster.cluster(friends);
		
		System.out.println(friend_months.size()+" clusters");
		
		for(Cluster cluster : friend_months){
			System.out.println("=============================================");
			System.out.println(cluster.name);
			System.out.println("=============================================");
			for(Contact friend : cluster.nodes){
				System.out.println(friend);
			}
		}		
		
	}	
	
	
	static public Set<Cluster> cluster(Set<Contact> contacts){
		
		Distance<Contact> bm_distance = new Distance<Contact>(){
			@Override
			public double distance(Contact c0, Contact c1) {

				String bm0 = c0.get("Birthday");
				String bm1 = c1.get("Birthday");
				
				if(bm0==null) bm0 = "none";
				if(bm1==null) bm1 = "none";
				
				String c0bm = bm0.substring(0,2);
				String c1bm = bm1.substring(0,2);
				
				int eq = c0bm.equals(c1bm) ? 0 : 10;
				
				//System.out.println(c0bm+" with "+c1bm+" is "+eq);

				
				return eq;
			}
			
		};
		
        HierarchicalClusterer<Contact> clusterer 
        = new CompleteLinkClusterer<Contact>(0,bm_distance);
		
       
        Set<Set<Contact>> raw_clusters = clusterer.cluster(contacts);
        
        Set<Cluster> clusters = new HashSet<Cluster>();
        
        for(Set<Contact> set : raw_clusters){
        	String label = "";
        	String b = set.iterator().next().get("Birthday");
    		if(b==null){
    			label="not given";
    		}
    		else switch(Integer.parseInt(b.substring(0,2))){
    			case 1 : label="Jan"; break;
    			case 2 : label="Feb";break;
    			case 3 : label="March";break;
    			case 4 : label="April";break;
    			case 5 : label="May";break;
    			case 6 : label="June";break;
    			case 7 : label="July";break;
    			case 8 : label="Aug";break;
    			case 9 : label="Sep";break;
    			case 10 : label="Oct";break;
    			case 11 : label="Nov";break;
    			case 12 : label="Dec";break;
    		}        	
        	clusters.add(new Cluster(set,label));
        }
        
        return clusters;
        

        
	}
	
}
