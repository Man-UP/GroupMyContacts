package clustering;

import java.util.Set;

import models.Contact;

import com.aliasi.cluster.CompleteLinkClusterer;
import com.aliasi.cluster.HierarchicalClusterer;
import com.aliasi.util.Distance;

public class BirthMonthCluster {

	static public void print_clusters(Set<Contact> friends){
		
		Set<Set<Contact>> friend_months = BirthMonthCluster.cluster(friends);
		
		System.out.println(friend_months.size()+" clusters");
		
		for(Set<Contact> set : friend_months){
			System.out.println("=============================================");
			String b = set.iterator().next().get("Birthday");
			if(b==null){
				System.out.println("not given");
			}
			else switch(Integer.parseInt(b.substring(0,2))){
				case 1 : System.out.println("Jan"); break;
				case 2 : System.out.println("Feb");break;
				case 3 : System.out.println("March");break;
				case 4 : System.out.println("April");break;
				case 5 : System.out.println("May");break;
				case 6 : System.out.println("June");break;
				case 7 : System.out.println("July");break;
				case 8 : System.out.println("Aug");break;
				case 9 : System.out.println("Sep");break;
				case 10 : System.out.println("Oct");break;
				case 11 : System.out.println("Nov");break;
				case 12 : System.out.println("Dec");break;
			}
			System.out.println("=============================================");
			for(Contact friend : set){
				System.out.println(friend);
			}
		}		
		
	}
	
	
	static public Set<Set<Contact>> cluster(Set<Contact> contacts){
		
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
		
       
        return clusterer.cluster(contacts);
	}
	
}
