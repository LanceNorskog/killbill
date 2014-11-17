package org.killbill.billing.jaxrs.resources.linksets;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import us.norskog.simplehal.Items;
import us.norskog.simplehal._Embedded;
import us.norskog.simplehal._Links;
import us.norskog.simplehal.Link;

import static org.killbill.billing.jaxrs.resources.JaxRsResourceBase.*;

/**
 * Database of endpoints and associated hyperlinks.
 * Easier to have them all in one place.
 * Only the endpoints that cross-link resources.
 * 
 * An endpoint can have both these and the annotations on the endpoint method.
 * 
 * @author lance
 *
 */
public class Endpoints {
	
	@_Links(links = {@Link(href = { "/blah" }, rel = "")})
	public static final String account_resource = "/accounts";

	@_Links(linkset = Object.class)
	@_Embedded(value = { @Items(items = "", name = "") })
	public static final String account_fufu = "/accounts/fufu";
	
    @_Links(links = { 
       		@Link(rel = "self", href = {"${this}?", QUERY_SEARCH_OFFSET, "=0&",
    				QUERY_SEARCH_LIMIT, "=${params.", QUERY_SEARCH_LIMIT, "}",
    	    		QUERY_SEARCH_OFFSET, "=${params.", QUERY_SEARCH_OFFSET, "}",
    	    		QUERY_ACCOUNT_WITH_BALANCE, "=${params.", QUERY_ACCOUNT_WITH_BALANCE, "}",
    	    		QUERY_ACCOUNT_WITH_BALANCE_AND_CBA, "=${params.", QUERY_ACCOUNT_WITH_BALANCE_AND_CBA, "}",
    	    		QUERY_AUDIT, "=${params.", QUERY_AUDIT, "}",
    	    		} ),
    		@Link(rel = "first", href = {"${this}?", QUERY_SEARCH_OFFSET, "=0&",
    				QUERY_SEARCH_LIMIT, "=${params.", QUERY_SEARCH_LIMIT, "}",
       	    		QUERY_ACCOUNT_WITH_BALANCE, "=${params.", QUERY_ACCOUNT_WITH_BALANCE, "}",
    	    		QUERY_ACCOUNT_WITH_BALANCE_AND_CBA, "=${params.", QUERY_ACCOUNT_WITH_BALANCE_AND_CBA, "}",
    	    		QUERY_AUDIT, "=${params.", QUERY_AUDIT, "}",
    				} ),
    	    @Link(rel = "next", href = {"${this}?", 
    	    		QUERY_SEARCH_LIMIT, "=${params.", QUERY_SEARCH_LIMIT, "}", 
    	    		QUERY_SEARCH_OFFSET, "=${params.", QUERY_SEARCH_OFFSET, "+",
    	    		QUERY_SEARCH_LIMIT, "=${params.", QUERY_SEARCH_LIMIT, "}",
       	    		QUERY_ACCOUNT_WITH_BALANCE, "=${params.", QUERY_ACCOUNT_WITH_BALANCE, "}",
    	    		QUERY_ACCOUNT_WITH_BALANCE_AND_CBA, "=${params.", QUERY_ACCOUNT_WITH_BALANCE_AND_CBA, "}",
    	    		QUERY_AUDIT, "=${params.", QUERY_AUDIT, "}",
    	    		})
    		})    
	public static final String[] accounts_paginated = {};

	static final Map<String, _Links> linksDB = new ConcurrentHashMap<String, _Links>();
	static final Map<String, _Embedded> embeddedDB = new ConcurrentHashMap<String, _Embedded>();
	
	public static void addDB(Object db) {
		if (linksDB.size() > 0)
			return;
		Class cl = db.getClass();
		Field[] fields = cl.getDeclaredFields();
		for(Field f: fields) {
			if (java.lang.reflect.Modifier.isStatic(f.getModifiers()) && f.getType().equals(String.class)) {
				Annotation[] links = f.getAnnotationsByType(_Links.class);
				Annotation[] embedded = f.getAnnotationsByType(_Embedded.class);
				f.setAccessible(true);
				if (links.length > 0)
					try {
						linksDB.put((String) f.get(null), (_Links) links[0]);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if (embedded.length > 0)
					try {
						embeddedDB.put((String) f.get(null), (_Embedded) embedded[0]);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
	}
	
	public static _Links getLinks(String endpoint) {
		return linksDB.get(endpoint);
	}
	
	public static _Embedded getEmbedded(String endpoint) {
		return embeddedDB.get(endpoint);
	}
	
	
	public static void main(String[] args) {
		Endpoints ep = new Endpoints();
		Endpoints.addDB(ep);
		System.out.println("Link1: " + getLinks(account_resource));
		System.out.println("Link2: " + getLinks(account_fufu));
		System.out.println("Embedded1: " + getEmbedded(account_resource));
		System.out.println("Embedded2: " + getEmbedded(account_fufu));
	}

}
