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
		
		for(Method m :User.class.getMethods()){
			String name = m.getName();
			if(name.contains("get") && !name.contains("getClass")){
				String field = name.substring(3);
				String value = "";
				try {
					Object ret_value = m.invoke(user);
					if(ret_value!=null && !ret_value.toString().equals("[]")){
						//System.out.println("adding "+field+" : "+ret_value);
						if(ret_value instanceof NamedFacebookType){
							value = ((NamedFacebookType) ret_value).getName();
							this_contact.put(field,value);
						}
						else{
							value = ret_value.toString();
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