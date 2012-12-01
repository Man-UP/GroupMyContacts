package models;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.restfb.types.NamedFacebookType;
import com.restfb.types.User;
/*
 * mycontact.get("name")
 */


public class Contact extends HashMap<String,String> {

/**
 * Factory method for creating Contacts
 **/
	public static Contact createContact(User user){
		
		Contact this_contact = new Contact();
		
		// Find all methods in User that are of the form getX
		for(Method m :User.class.getMethods()){
			String n = m.getName();
			if(n.contains("get") && !n.contains("getClass")){
				String field = n.substring(3);
				try {
					// try and call the found method
					Object ret_value = m.invoke(user);
					
					// if the return value is not null (or empty) then we will add it
					
					if(ret_value!=null && !ret_value.toString().equals("[]")){
						//System.out.println("adding "+field+" : "+ret_value);
						
						// the return value might be a composite type i.e
						// name=X, id=Y
						// in this case we add both the name and id (important for Places)
						
						if(ret_value instanceof NamedFacebookType){
							String name = ((NamedFacebookType) ret_value).getName();
							String id = ((NamedFacebookType) ret_value).getId();
							this_contact.put(field+".name",name);
							this_contact.put(field+".id",id);
						}
						else{
						
						// Otherwise we just add the field name and value	
							
							String value = ret_value.toString();
							this_contact.put(field,value);
						}
					}
					
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
		
		return this_contact;
	}

	/**
	 * This bag of words methods creates a multiset of words used in the values
	 * of the contact
	 * @return
	 */

	public Map<String,Integer> bag_of_words(){
		
		System.out.println("=======================");
		
		HashMap<String,Integer> bag = new HashMap<String,Integer>();
		
		for(String words : values()){
			
			//System.out.println(words);
			
			for(String word : words.split(" ")){
				System.out.println(word);
				
				if(bag.containsKey(word))
					bag.put(word, bag.get(word)+1);
				else
					bag.put(word,1);
			}
		}
		
		return bag;
	}
	
	@Override
	public String toString(){
		return get("Name");
	}
	
}
