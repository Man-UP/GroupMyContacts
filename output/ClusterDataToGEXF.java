package output;

import com.aliasi.cluster.Dendrogram;

public class ClusterDataToGEXF {

	public static String make(Dendrogram D){
		
		/*
		 * compute the parameters required
		 */
		int num_nodes = 0;
		
		/*
		 * make output string
		 */
		
		String output = "";
		
		output += "<gexf version=\"1.1\">\n";
		output +="<meta><creator>GroupMYContacts</creator></meta>";
		output += "<graph defaultedgetype=\"directed\" idtype=\"string\" type=\"static\"><nodes count=\""+num_nodes+"\">";
		
		
		return output;
		
	}
	
	
}
