package models;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
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
			if(name.contains("get")){
				String field = name.substring(3);
				String value = "";
				try {
					Object ret_value = m.invoke(user);
					if(ret_value!=null){
						//System.out.println("adding "+field+" : "+ret_value);
						value = ret_value.toString();
						this_contact.put(field,value);
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
	
	@Override
	public String toString(){
		return get("Name");
	}
	
}
