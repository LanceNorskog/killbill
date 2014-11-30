package org.killbill.billing.jaxrs.resources.linksets;

import static org.killbill.billing.jaxrs.resources.JaxrsResource.QUERY_ACCOUNT_WITH_BALANCE;
import static org.killbill.billing.jaxrs.resources.JaxrsResource.QUERY_ACCOUNT_WITH_BALANCE_AND_CBA;
import static org.killbill.billing.jaxrs.resources.JaxrsResource.QUERY_AUDIT;
import static org.killbill.billing.jaxrs.resources.JaxrsResource.QUERY_SEARCH_LIMIT;
import static org.killbill.billing.jaxrs.resources.JaxrsResource.QUERY_SEARCH_OFFSET;

import java.util.Map;

import us.norskog.simplehal.Hyper;
import us.norskog.simplehal.Link;
import us.norskog.simplehal._Links;


public class AccountPagination extends Hyper {

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
	@Override
	public Map<String, ? extends Object> getLink(Object base) {
		// TODO Auto-generated method stub
		return null;
	}

}
