package clustering;

import java.util.HashSet;
import java.util.Set;

import output.Cluster;

import models.Contact;

import com.aliasi.cluster.CompleteLinkClusterer;
import com.aliasi.cluster.Dendrogram;
import com.aliasi.cluster.HierarchicalClusterer;
import com.aliasi.util.Distance;

public class BirthMonthCluster  extends Clustering {


	
	public Set<Cluster> cluster(Set<Contact> contacts){
		
		Distance<Contact> bm_distance = getDistance();
		
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
	
	public Dendrogram<Contact> cluster_for_d(Set<Contact> contacts){
		
		Distance<Contact> bm_distance = getDistance();
		
        HierarchicalClusterer<Contact> clusterer 
        = new CompleteLinkClusterer<Contact>(0,bm_distance);
		
       
        return clusterer.hierarchicalCluster(contacts);
        

        
	}	

	private static Distance<Contact> getDistance() {
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
		return bm_distance;
	}
	
}
