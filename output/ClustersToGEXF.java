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
		
		Set<Pair<Integer,String>> nodes = new HashSet<Pair<Integer,String>>();
		Set<Pair<Integer,Integer>> edges = new HashSet<Pair<Integer,Integer>>();
		
		for(Cluster cluster : clusters){
			int c_node = num_nodes;
			num_nodes++;
			nodes.add(new Pair(c_node,cluster.name));
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
		
		String output = "";
		
		output += "<gexf version=\"1.1\">\n";
		output +="<meta><creator>GroupMYContacts</creator></meta>\n";
		output += "<graph defaultedgetype=\"directed\" idtype=\"string\" type=\"static\">\n";
		output += "<nodes count=\""+nodes.size()+"\">\n";
		for(Pair<Integer,String> p : nodes){
			output += "<node id=\""+p.a()+".0\" label=\""+p.b()+"\"/>\n";
		}
		output += "</nodes>\n";
		output += "<edges count=\""+edges.size()+"\">\n";
		int i=0;
		for(Pair<Integer,Integer> p : edges){
			output += "<edge id=\""+i+".0\" source=\""+p.a()+"\" target\""+p.b()+"\"/>\n";
			i++;
		}
		output += "</edges>\n";		
		output += "</graph>\n";
		output += "</gexf>\n";
		
		return output;
		
	}

	
}
