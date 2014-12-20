package org.killbill.billing.jaxrs.resources.linksets;

import java.util.Map;

import us.norskog.simplehal.Supplier;
import us.norskog.simplehal.Items;
import us.norskog.simplehal._Embedded;
import us.norskog.simplehal._Links;


public class AccountEmbedded implements Supplier {

	@_Links(links = { 
			})  
	@_Embedded(links = { @Items(items = "", name = "") 
	})

	public Map<String, ? extends Object> getLink(Map<String,? extends Object> response) {
		// TODO Auto-generated method stub
		return null;
	}

}
