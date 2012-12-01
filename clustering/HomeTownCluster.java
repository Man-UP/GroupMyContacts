package clustering;

import java.util.Set;

import models.Contact;

import com.aliasi.cluster.CompleteLinkClusterer;
import com.aliasi.cluster.HierarchicalClusterer;
import com.aliasi.util.Distance;

public class HomeTownCluster {


	
	static public Set<Set<Contact>> cluster(Set<Contact> contacts){
		
		Distance<Contact> hm_distance = new Distance<Contact>(){
			@Override
			public double distance(Contact c0, Contact c1) {

				String bm0 = c0.get("Hometown");
				String bm1 = c1.get("Hometown");
				
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
        = new CompleteLinkClusterer<Contact>(0,hm_distance);
		
       
        return clusterer.cluster(contacts);
	}	
	
}
