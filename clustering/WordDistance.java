package clustering;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import models.Contact;

import com.aliasi.spell.EditDistance;
import com.aliasi.util.Distance;

public class WordDistance implements Distance<Contact> {

	@Override
	public double distance(Contact contact_1, Contact contact_2) {
		
		Distance<CharSequence> EDIT_DISTANCE
        = new EditDistance(false);
		
		Map<String,Integer> c1 = contact_1.bag_of_words();
		Map<String,Integer> c2 = contact_2.bag_of_words();
		
		int total = 0;
		
		Set<String> keys = new HashSet<String>();
		keys.addAll(c1.keySet());
		keys.addAll(c2.keySet());
		
		for(String s : keys){
			Integer v1 = c1.get(s);
			Integer v2 = c2.get(s);
			total += ((v1 == null) ? 0 : 1) * ((v2 == null) ? 0 : 1);
		}
		double result = (double) total / keys.size();
		
		// TODO Auto-generated method stub
		return result;
	}

}
