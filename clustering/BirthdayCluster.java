package clustering;

import java.util.HashSet;
import java.util.Set;

import output.Cluster;

import models.Contact;

import com.aliasi.cluster.CompleteLinkClusterer;
import com.aliasi.cluster.Dendrogram;
import com.aliasi.cluster.HierarchicalClusterer;
import com.aliasi.util.Distance;

/*
 * 
 *
 * bd_distance below works out the distance in the
 * calendar year (i.e. num of days into year modulo 365)
 * 
 */

public class BirthdayCluster  extends Clustering {

	
	public Set<Cluster> cluster(Set<Contact> contacts){
		
		Distance<Contact> bm_distance = getDistance();
		
        HierarchicalClusterer<Contact> clusterer 
        = new CompleteLinkClusterer<Contact>(10,bm_distance);
		
        Set<Contact> rem = new HashSet<Contact>();
        for(Contact c : contacts){
        	if(c.get("Birthday")==null) rem.add(c);
        }
        contacts.removeAll(rem);
        
        Set<Set<Contact>> raw_clusters = clusterer.cluster(contacts);
        
        Set<Cluster> clusters = new HashSet<Cluster>();
        
        for(Set<Contact> set : raw_clusters){
        	
        	int total = 0;
        	for(Contact c : set){
        		total += day_of_year(c.get("Birthday"));
        	}
        	total /= set.size();
        	
        	String label = "average of "+reverse_day_of_year(total);
        	
        	clusters.add(new Cluster(set,label));
        }
        
        return clusters;
	}
	
	public Dendrogram<Contact> cluster_for_d(Set<Contact> contacts){
		
		Distance<Contact> bd_distance = getDistance();
		
        HierarchicalClusterer<Contact> clusterer 
        = new CompleteLinkClusterer<Contact>(0,bd_distance);
		
        Set<Contact> rem = new HashSet<Contact>();
        for(Contact c : contacts){
        	if(c.get("Birthday")==null) rem.add(c);
        }
        contacts.removeAll(rem);
        
        return clusterer.hierarchicalCluster(contacts);
   
	}
	

	private static Distance<Contact> getDistance() {
		Distance<Contact> bm_distance = new Distance<Contact>(){
			
			@Override
			public double distance(Contact c0, Contact c1) {

				String bm0 = c0.get("Birthday");
				String bm1 = c1.get("Birthday");
				
				int d0 = day_of_year(bm0);
				int d1 = day_of_year(bm1);
				
				
				int dis = (d0>d1) ? (d0-d1) : (d1-d0);
				if(dis > 182.5) dis = 365-dis;
				
				//System.out.println(c0bm+" with "+c1bm+" is "+eq);

				
				return dis;
			}
			
		};
		return bm_distance;
	}
	
	private static String reverse_day_of_year(int total) {
		
		for(int i=1;i<13;i++){
			int now = days_in_month(i);
			if(total < now){
				switch(i){
				case 1 : return "January "+total; 
				case 2 : return "February "+total; 
				case 3 : return "March "+total; 
				case 4 : return "April "+total; 
				case 5 : return "May "+total; 
				case 6 : return "June "+total; 
				case 7 : return "July "+total; 
				case 8 : return "August "+total; 
				case 9 : return "September "+total; 
				case 10 : return "October "+total; 
				case 11 : return "November "+total; 
				case 12 : return "December "+total; 
				}
			}
			else{
				total = total - now;
			}
		}
		
		return null;
	}


	private static int days_in_month(int month){
		
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
	
	private static int day_of_year(String date){
		
		int m = Integer.parseInt(date.substring(0,2));
		int d = Integer.parseInt(date.substring(3,5));
		
		for(int i=1;i<m;i++)
			d += days_in_month(i);
		
		return d;				
	}
	
}
