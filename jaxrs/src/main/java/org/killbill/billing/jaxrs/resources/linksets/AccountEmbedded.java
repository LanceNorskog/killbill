package org.killbill.billing.jaxrs.resources.linksets;

import java.util.Map;

import us.norskog.simplehal.Hyper;
import us.norskog.simplehal.Items;
import us.norskog.simplehal._Embedded;
import us.norskog.simplehal._Links;


public class AccountEmbedded extends Hyper {

	@_Links(links = { 
			})  
	@_Embedded(value = { @Items(items = "", name = "") 
	})
	@Override
	public Map<String, ? extends Object> getLink(Object base) {
		// TODO Auto-generated method stub
		return null;
	}

}
