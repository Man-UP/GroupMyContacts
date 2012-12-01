package clustering;

import java.util.List;
import java.util.Set;

import models.Contact;

import com.aliasi.cluster.CompleteLinkClusterer;
import com.aliasi.cluster.HierarchicalClusterer;
import com.aliasi.util.Distance;

import datasource.FB;

public class HomeTownCluster {
 
	
	static public Set<Set<Contact>> cluster(Set<Contact> contacts){
		
		
		System.exit(0);
		
		Distance<Contact> hm_distance = new Distance<Contact>(){
			@Override
			public double distance(Contact c0, Contact c1) {

				String hm0 = c0.get("Hometown");
				String hm1 = c1.get("Hometown");
				
				if(hm0==null) hm0 = "none";
				else{
					FB.getLatLong(hm0);
				}
				if(hm1==null) hm1 = "none";
				
				
				
				
				return 0;
			}
			
		};
		
        HierarchicalClusterer<Contact> clusterer 
        = new CompleteLinkClusterer<Contact>(0,hm_distance);
		
       
        return clusterer.cluster(contacts);
	}	
	
}
