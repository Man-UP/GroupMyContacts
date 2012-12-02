package clustering;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import models.Contact;
import output.Cluster;

import com.aliasi.cluster.CompleteLinkClusterer;
import com.aliasi.cluster.Dendrogram;
import com.aliasi.cluster.HierarchicalClusterer;
import com.aliasi.util.Distance;

/*
 * This currently doesn't do anything useful - the idea was to use the bag of words
 * approach to build a multiset of words used in a Contact and then use a distance
 * metric on word-multisets to do clustering... this distance metric needs fixing
 */

public class bowCluster extends Clustering {

	@Override
	public Set<Cluster> cluster(Set<Contact> contacts) {
		
		HierarchicalClusterer<Contact> clusterer
	    = new CompleteLinkClusterer<Contact>(0.1,getdDistance());

        Set<Set<Contact>> raw_clusters = clusterer.cluster(contacts);
        
        Set<Cluster> clusters = new HashSet<Cluster>();
        
        for(Set<Contact> set : raw_clusters){
        	
        	String label = "";
        	
        	clusters.add(new Cluster(set,label));
        }
        
        return clusters;
		
		
	}


	@Override
	public Dendrogram<Contact> cluster_for_d(Set<Contact> contacts) {

		HierarchicalClusterer<Contact> clusterer
	    = new CompleteLinkClusterer<Contact>(getdDistance());

		return clusterer.hierarchicalCluster(contacts);
		
	}


	

	

private Distance<Contact> getdDistance(){
	return new Distance<Contact>(){
		@Override
		public double distance(Contact contact_1, Contact contact_2) {
			
			Map<String,Integer> c1 = contact_1.bag_of_words();
			Map<String,Integer> c2 = contact_2.bag_of_words();
			
		    double sum_c1 = 0.0;
		    for (Integer i : c1.values()) {
		        sum_c1 += i;  // tf =sqrt(count); sum += tf * tf
		    }
		    double sum_c2 = 0.0;
		    for (Integer i : c2.values()) {
		        sum_c2 += i;  // tf =sqrt(count); sum += tf * tf
		    }	    
		    
		    double product = 0.0;
		    
		    for (String token : c1.keySet()) {
		        Integer count = c2.get(token);
		        if (count == null || count==0) continue;
		        product += Math.sqrt(count * c1.get(token)); // tf = sqrt(count)
		    }
		    
		    double cosine = product / (sum_c1 * sum_c2);
		    
		    System.out.println(""+(1.0 - cosine));
		    
		    return 1.0 - cosine;
		}		
	};
}




}
