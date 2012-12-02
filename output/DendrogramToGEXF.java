package output;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.aliasi.cluster.Dendrogram;
import com.aliasi.util.Pair;

public class DendrogramToGEXF {

	public static String make(Dendrogram D){
		
		/*
		 * compute the parameters required
		 */
		int num_nodes = 0;
		
		Map<String,Integer> node_id_map = new HashMap<String,Integer>();
		Set<Pair<Integer,Integer>> edge_set = new HashSet<Pair<Integer,Integer>>();
		
		
		/*
		 * make output string
		 */
		
		String output = "";
		
		output += "<gexf version=\"1.1\">\n";
		output +="<meta><creator>GroupMYContacts</creator></meta>";
		output += "<graph defaultedgetype=\"directed\" idtype=\"string\" type=\"static\"><nodes count=\""+num_nodes+"\">";
		
		
		return output;
		
	}
	
	
	private static void trace(Dendrogram D, Map<String,Integer> node_id_map, Set<Pair<Integer,Integer>> edge_set){
		
		
		
	}
	
}
