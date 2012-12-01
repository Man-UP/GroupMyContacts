package clustering;

import java.util.List;
import java.util.Set;

import models.Contact;

import com.aliasi.cluster.CompleteLinkClusterer;
import com.aliasi.cluster.HierarchicalClusterer;
import com.aliasi.matrix.Matrix;
import com.aliasi.matrix.ProximityMatrix;
import com.aliasi.util.Distance;

/*
 * Cluster using HomeTown
 */

import datasource.FB;

public class HomeTownCluster {
 
	static double EARTH_RADIUS_MILES = 3963.1;
	
	
	static public Set<Set<Contact>> cluster(Set<Contact> contacts){
		
		
		String[][] LON_LAT = new String[contacts.size()][];
		
		int i = 0;
		for(Contact c : contacts){

			String name = c.get("Hometown.name");
			String id = c.get("Hometown.id");
			
			if(name!=null){
				String lonlat = FB.getLonLat(id);			
				String[] lonlat_a = lonlat.split("|");
				LON_LAT[i] = new String[]{name, lonlat_a[0], lonlat_a[1]};
			}
		}
		
		System.out.println(getDistanceMatrix(LON_LAT));
		
		return null;
	}	
	/*
	 * The below has been copied from 
	 * http://ir.exp.sis.pitt.edu/ne/lingpipe-2.4.0/demos/tutorial/cluster/src/CityDistances.java
	 * 
	 */
	
	 public static Matrix getDistanceMatrix(String[][] LON_LAT) {
	        ProximityMatrix matrix = new ProximityMatrix(LON_LAT.length);
	        for (int i = 0; i < LON_LAT.length; ++i) {
	            String city1 = LON_LAT[i][0];
	            //matrix.setLabel(i,city1);
	            double lonA = toRadians(LON_LAT[i][1],"n");
	            double latA = toRadians(LON_LAT[i][2],"w");
	            for (int j = i+1; j < LON_LAT.length; ++j) {
	                String city2 = LON_LAT[j][0];
	                double lonB = toRadians(LON_LAT[j][1],"n");
	                double latB = toRadians(LON_LAT[j][2],"w");
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

	    static public double toRadians(String frac, String divider) {
	        String[] nums = frac.split(divider);
	        double degs = Double.parseDouble(nums[0])
	            + Double.parseDouble(nums[1])/60.0;
	        return Math.toRadians(degs);
	    }
}
