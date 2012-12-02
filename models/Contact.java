package models;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.restfb.types.NamedFacebookType;
import com.restfb.types.User;
import com.restfb.types.User.Education;
import com.restfb.types.User.EducationClass;
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
						
						deal_with_ret(this_contact, field, ret_value);
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
		
	/*	System.out.println("=============================");
		for(java.util.Map.Entry<String, String> e : this_contact.entrySet()){
			System.out.println(e.getKey()+"\t\t"+e.getValue());
		}
		*/
		
		
		return this_contact;
	}

private final static Class unmodlistclass = Collections.unmodifiableList(new LinkedList<Object>()).getClass();	
	
private static void deal_with_ret(Contact this_contact, String field, Object ret_value) {
	// the return value might be a composite type i.e
	// name=X, id=Y
	// in this case we add both the name and id (important for Places)
	
	//System.out.println(ret_value.getClass()+"");
	
	if(unmodlistclass.isInstance(ret_value)){
		//System.out.println("list detected");
		int i=0;
		for(Object o : ((List) ret_value)){
			deal_with_ret(this_contact,field+":"+i,o);
			i++;
		}
	}	
	if(ret_value instanceof Education){
		String school = ((Education) ret_value).getSchool().getName();
		this_contact.put(field+".school", school);
		NamedFacebookType year = ((Education) ret_value).getYear();	
		if(year!=null) this_contact.put(field+".school.year", year.getName());
		int j=0;
		for(EducationClass cl : ((Education) ret_value).getClasses()){
			this_contact.put(field+".school.class:"+j, school);
			j++;
		}
	}
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

	/**
	 * This bag of words methods creates a multiset of words used in the values
	 * of the contact
	 * @return
	 */

	public Map<String,Integer> bag_of_words(){
		
		//System.out.println("=======================");
		
		HashMap<String,Integer> bag = new HashMap<String,Integer>();
		
		for(java.util.Map.Entry<String, String> entry : this.entrySet()){
			
			if(entry.getKey()=="Name" ||
			   entry.getKey()=="Birthday" 	
					)
				continue;

			
			for(String word : entry.getValue().split(" ")){
				//System.out.println(word);
				
				int step = 10;
				
				if(bag.containsKey(word))
					bag.put(word, bag.get(word)+step);
				else
					bag.put(word,step);
			}
		}
		
		return bag;
	}
	
	@Override
	public String toString(){
		return get("Name");
	}
	
}
