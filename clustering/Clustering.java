package clustering;

import java.util.Set;

import models.Contact;
import output.Cluster;

import com.aliasi.cluster.Dendrogram;

public abstract class Clustering {

	abstract public Set<Cluster> cluster(Set<Contact> contacts);
	abstract public Dendrogram<Contact> cluster_for_d(Set<Contact> contacts);
	
	public void print_clusters(Set<Contact> friends){
		
		Set<Cluster> clusters = this.cluster(friends);
		
		System.out.println(clusters.size()+" clusters");
		
		for(Cluster cluster : clusters){
			System.out.println("=============================================");
			System.out.println(cluster.name);
			System.out.println("=============================================");
			for(Contact friend : cluster.nodes){
				System.out.println(friend);
			}
		}		
		
	}		
	
}
