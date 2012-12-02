package output;

import java.util.Set;

import models.Contact;

public class Cluster {

	public final Set<Contact> nodes;
	public final String name;
	
	public Cluster(Set<Contact> nodes, String name){
		this.nodes = nodes;
		this.name = name;
	}
	
}
