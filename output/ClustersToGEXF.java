package output;

import java.util.HashSet;
import java.util.Set;

import models.Contact;

import com.aliasi.util.Pair;

public class ClustersToGEXF {

	public static String make(Set<Cluster> clusters){
		
		/*
		 * compute the parameters required
		 */
		int num_nodes = 0;
		
		Set<Integer> cluster_nodes = new HashSet<Integer>();
		Set<Pair<Integer,String>> nodes = new HashSet<Pair<Integer,String>>();
		Set<Pair<Integer,Integer>> edges = new HashSet<Pair<Integer,Integer>>();
		
		for(Cluster cluster : clusters){
			int c_node = num_nodes;
			num_nodes++;
			nodes.add(new Pair(c_node,cluster.name));
			cluster_nodes.add(c_node);
			for(Contact c : cluster.nodes){
				int i_node = num_nodes;
				num_nodes++;
				nodes.add(new Pair(i_node,c.toString()));
				edges.add(new Pair(c_node,i_node));
			}
		}
		
		
		/*
		 * make output string
		 */
		
		String cluster_color = "r=\"250\" g=\"0\" b=\"0\"";
		String other_color   = "r=\"0\" g=\"250\" b=\"0\"";
		
		String output = "";
		
		output += "<gexf xmlns=\"http://www.gexf.net/1.2draft\" xmlns:viz=\"http://www.gexf.net/1.1draft/viz\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.gexf.net/1.2draft http://www.gexf.net/1.2draft/gexf.xsd\" version=\"1.2\">\n";
		output +="<meta><creator>GroupMYContacts</creator></meta>\n";
		output += "<graph defaultedgetype=\'directed\' idtype=\'string\' type=\'static\'>\n";
		output += "<nodes count=\'"+nodes.size()+"\'>\n";
		for(Pair<Integer,String> p : nodes){
			output += "<node id=\""+p.a()+"\" label=\""+p.b()+"\">\n";
			output += "<viz:color "+(cluster_nodes.contains(p.a())?cluster_color:other_color)+"/>\n";
			output += "</node>\n";
		}
		output += "</nodes>\n";
		output += "<edges count=\'"+edges.size()+"\'>\n";
		int i=0;
		for(Pair<Integer,Integer> p : edges){
			output += "<edge id=\""+i+"\" source=\""+p.a()+"\" target=\""+p.b()+"\"/>\n";
			i++;
		}
		output += "</edges>\n";		
		output += "</graph>\n";
		output += "</gexf>\n";
		
		return output;
		
	}

	
}
