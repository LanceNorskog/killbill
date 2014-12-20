package org.killbill.billing.jaxrs.resources.linksets;

import java.util.Map;

import org.killbill.billing.jaxrs.resources.JaxrsResource;

import us.norskog.simplehal.Supplier;
import us.norskog.simplehal.Link;
import us.norskog.simplehal._Links;
import static org.killbill.billing.jaxrs.resources.JaxRsResourceBase.*;

/**
 * Serve as carrier for links because links syntax is inherently noisy
 * 
 * How to match up with main class? Subclass each class and add as needed?
 * But how to have macros?
 * 
 * @author lance
 *
 */

public class AccountLinks extends Supplier {

	@_Links(links = { 
			@Link(rel = "accountId", href = JaxrsResource.ACCOUNTS_PATH + "/${response.accountId",
					check = "${response.accountId}"),
					@Link(rel = "externalKey", href = JaxrsResource.ACCOUNTS_PATH + "?externalKey=${response.externalKey",
					check = "${response.externalKey}")}
			)
	@Override
	public Map<String, ? extends Object> getLink(Map<String,? extends Object> response) {
		return null;
	}

}
