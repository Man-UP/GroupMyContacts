package clustering;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import models.Contact;
import output.Cluster;

import com.aliasi.cluster.CompleteLinkClusterer;
import com.aliasi.cluster.Dendrogram;
import com.aliasi.cluster.HierarchicalClusterer;
import com.aliasi.util.Distance;

public class LanguageCluster extends Clustering {

	public Set<Cluster> cluster(Set<Contact> contacts){
		
		Distance<Contact> bm_distance = getDistance();
		
        HierarchicalClusterer<Contact> clusterer 
        = new CompleteLinkClusterer<Contact>(3,bm_distance);
		
       Set<Contact> rem = new HashSet<Contact>();
       for(Contact c : contacts){
    	   if(getLanguages(c).isEmpty()) rem.add(c);
       }
       contacts.removeAll(rem);
        
        Set<Set<Contact>> raw_clusters = clusterer.cluster(contacts);
        
        Set<Cluster> clusters = new HashSet<Cluster>();
        
        for(Set<Contact> set : raw_clusters){
        	String label="";
        	clusters.add(new Cluster(set,label);
        	
        }
        
        return clusters;
        

        
	}
	
	public Dendrogram<Contact> cluster_for_d(Set<Contact> contacts){
		
		Distance<Contact> bm_distance = getDistance();
		
        HierarchicalClusterer<Contact> clusterer 
        = new CompleteLinkClusterer<Contact>(5,bm_distance);
		
       
        return clusterer.hierarchicalCluster(contacts);
        

        
	}	

	private static Set<String> getLanguages(Contact contact){
		Set<String> langs = new HashSet<String>();
		int i=0;
		for(Entry<String,String> entry : contact.entrySet()){
			if(entry.getKey().contains("Language")){
				langs.add(entry.getValue());
			}
		}
		return langs;
	}
	
	private static Distance<Contact> getDistance() {
		Distance<Contact> bm_distance = new Distance<Contact>(){
			@Override
			public double distance(Contact c0, Contact c1) {

				Set<String> langs0 = getLanguages(c0);
				Set<String> langs1 = getLanguages(c1);				
				
				if(langs0.isEmpty() || langs1.isEmpty()) return 20.0;
				
				Set<String> intersection = intersection(c0, c1);
				
				if(intersection.isEmpty()) return 20.0;
				
				//System.out.println(""+(langs0.size()+langs1.size())/intersection.size());
				
				return (langs0.size()+langs1.size())/intersection.size();

			}

			private Set<String> intersection(Contact c0, Contact c1) {
				Set<String> langs0 = getLanguages(c0);
				Set<String> langs1 = getLanguages(c1);
				
				Set<String> intersection = new HashSet<String>();
				
				for(String s : langs0) 
					if(langs1.contains(s)) 
						intersection.add(s);
				
				return intersection;
			}
			
		};
		return bm_distance;
	}	
	
}
