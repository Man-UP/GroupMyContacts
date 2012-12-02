package clustering;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import models.Contact;
import output.Cluster;

import com.aliasi.cluster.CompleteLinkClusterer;
import com.aliasi.cluster.Dendrogram;
import com.aliasi.cluster.HierarchicalClusterer;
import com.aliasi.matrix.Matrix;
import com.aliasi.matrix.ProximityMatrix;
import com.aliasi.util.Distance;

import datasource.FB;
/*
 * Cluster using HomeTown
 */

public class HomeTownCluster extends Clustering {
 
	static double EARTH_RADIUS_MILES = 3963.1;
	


	
	public Set<Cluster> cluster(Set<Contact> contacts){
		
		
		Distance<Contact> ht_distance = getDistance(contacts);		

        HierarchicalClusterer<Contact> clusterer 
        = new CompleteLinkClusterer<Contact>(1,ht_distance);
		
       
        Set<Set<Contact>> raw_clusters = clusterer.cluster(contacts);
        
        Set<Cluster> clusters = new HashSet<Cluster>();
        
        for(Set<Contact> set : raw_clusters){
        	String label = "near "+(set.iterator().next().get("Hometown.name"));
        	clusters.add(new Cluster(set,label));
        }
        
        return clusters;

	}	
	
	@Override
	public Dendrogram<Contact> cluster_for_d(Set<Contact> contacts) {
		
		Distance<Contact> ht_distance = getDistance(contacts);		
		
		HierarchicalClusterer<Contact> clusterer 
        = new CompleteLinkClusterer<Contact>(1,ht_distance);		
		
		return clusterer.hierarchicalCluster(contacts);
	}	


	private Distance<Contact> getDistance(Set<Contact> contacts) {
		String[][] LON_LAT = new String[contacts.size()][];
		
		int i = 0;
		Set<Contact> rem = new HashSet<Contact>();
		for(Contact c : contacts){

			String name = c.get("Hometown.name");
			String id = c.get("Hometown.id");
			if(name!=null){
				String lonlat = FB.getLonLat(id);
				if(!lonlat.isEmpty())
				{
					String[] splitLonLat = lonlat.split("[|]");
					LON_LAT[i] = new String[]{name, splitLonLat[0], splitLonLat[1]};
					i++;
				}
			}
			else rem.add(c);
		}
		contacts.removeAll(rem);
		
		String[][] temp = new String[contacts.size()][];
		System.arraycopy(LON_LAT, 0, temp, 0,contacts.size());
		LON_LAT = temp;
		
		final HashMap<String,Integer> nameMap = new HashMap<String,Integer>();
		
		final Matrix distances = getDistanceMatrix(LON_LAT, nameMap);
		
		Distance<Contact> ht_distance = new Distance<Contact>(){
			@Override
			public double distance(Contact c0, Contact c1) {

				
				// first get there ids in distances
				// we know they must be there
				int c0_i = nameMap.get(c0.get("Hometown.name"));
				int c1_i = nameMap.get(c1.get("Hometown.name"));
				
				// get the distance between them
				return distances.value(c0_i, c1_i);
			}
			
		};
		return ht_distance;
	}
	
	/*
	 * This has been copied from 
	 * http://ir.exp.sis.pitt.edu/ne/lingpipe-2.4.0/demos/tutorial/cluster/src/CityDistances.java
	 * 
	 */
	 public static Matrix getDistanceMatrix(String[][] LON_LAT,HashMap<String,Integer> nameMap) {
	        ProximityMatrix matrix = new ProximityMatrix(LON_LAT.length);
	        for (int i = 0; i < LON_LAT.length; ++i) {
	            //String city1 = LON_LAT[i][0];
	            //matrix.setLabel(i,city1);
	        	nameMap.put(LON_LAT[i][0], i);
	        	//System.out.println(LON_LAT[i][1] + LON_LAT[i][2]);
	            double lonA = Math.toRadians(Double.parseDouble(LON_LAT[i][1]));
	            //System.out.println("lonA = " + lonA + " NotSplit = "+ LON_LAT[i][1]);
	            double latA = Math.toRadians(Double.parseDouble(LON_LAT[i][2]));
	            //System.out.println("latA = " + latA);
	            
	            for (int j = i+1; j < LON_LAT.length; ++j) {
	                //String city2 = LON_LAT[j][0];
	            	//System.out.println("j " + LON_LAT[j][1] + LON_LAT[j][2]);
	                double lonB = Math.toRadians(Double.parseDouble(LON_LAT[j][1]));
	                //System.out.println("lonB = "+ lonB);
	                double latB = Math.toRadians(Double.parseDouble(LON_LAT[j][2]));
	               // System.out.println("latB = "+ latB);
	                double dist = d(lonA,latA,lonB,latB);
			// System.out.println(city1 + "<==>" + city2 + "=" + dist);
	                matrix.setValue(i,j,dist);
	            }
	        }
	        return matrix;
	    }

	    static public double d(double lonA, double latA,
	                           double lonB, double latB) {
	        return (int) ( EARTH_RADIUS_MILES
	            * Math.acos( ( Math.cos(latA) 
	                           * Math.cos(latB) 
	                           * Math.cos(lonB-lonA) )
	                         + 
	                         ( Math.sin(latA) 
	                           * Math.sin(latB) ) ) );
	    }


}
