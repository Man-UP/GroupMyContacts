package clustering;

import java.util.Set;

import models.Contact;

import com.aliasi.cluster.CompleteLinkClusterer;
import com.aliasi.cluster.HierarchicalClusterer;
import com.aliasi.util.Distance;

/*
 * Not Done Yet
 * 
 * The idea is to replace bm_distance below so that it works out the distance in the
 * calendar year (i.e. num of days into year modulo 365)
 * 
 */

public class BirthdayCluster {

	static public void print_clusters(Set<Contact> friends){
		
		Set<Set<Contact>> friend_months = BirthdayCluster.cluster(friends);
		
		System.out.println(friend_months.size()+" clusters");
		
		for(Set<Contact> set : friend_months){
			System.out.println("=============================================");
			for(Contact friend : set){
				System.out.println(friend);
			}
		}		
		
	}
	
	
	static public Set<Set<Contact>> cluster(Set<Contact> contacts){
		
		Distance<Contact> bm_distance = new Distance<Contact>(){
			
			
			private int days_in_month(int month){
				
				switch(month){
					case 1 : return 31;
					case 2 : return 28;
					case 3 : return 31;
					case 4 : return 30;
					case 5 : return 31;
					case 6 : return 30;
					case 7 : return 31;
					case 8 : return 31;
					case 9 : return 30;
					case 10 : return 31;
					case 11 : return 30;
					case 12 : return 31;
					default : return 0;
				}
			}
			
			@Override
			public double distance(Contact c0, Contact c1) {

				String bm0 = c0.get("Birthday");
				String bm1 = c1.get("Birthday");
				
				if(bm0==null) bm0 = "none";
				if(bm1==null) bm1 = "none";
				
				int c0bm = Integer.parseInt(bm0.substring(0,2));
				int c1bm = Integer.parseInt(bm1.substring(0,2));
				int c0bd = Integer.parseInt(bm0.substring(2,4));
				int c1bd = Integer.parseInt(bm1.substring(2,4));
				
				int c0dayinyear = days_in_month(c0bm) + c0bd;
				int c1dayinyear = days_in_month(c1bm) + c1bd;
				
				int dis = Math.abs(c0dayinyear - c1dayinyear);
				if(dis > 182.5) dis = 365-dis;
				
				//System.out.println(c0bm+" with "+c1bm+" is "+eq);

				
				return dis;
			}
			
		};
		
        HierarchicalClusterer<Contact> clusterer 
        = new CompleteLinkClusterer<Contact>(0,bm_distance);
		
       
        return clusterer.cluster(contacts);
	}
	
}
