package org.killbill.billing.jaxrs.resources.linksets;

import org.killbill.billing.jaxrs.resources.JaxrsResource;

import us.norskog.simplehal.Link;
import us.norskog.simplehal._Links;

/**
 * Serve as carrier for links because links syntax is inherently noisy
 * 
 * 
 * @author lance
 *
 */

@_Links(links = { 
		@Link(rel = "accountId", href = JaxrsResource.ACCOUNTS_PATH + "/${response.accountId",
			    check = "${response.accountId}"),
		@Link(rel = "externalKey", href = JaxrsResource.ACCOUNTS_PATH + "?externalKey=${response.externalKey",
		    check = "${response.externalKey}")}
		)
public class AccountLinkset {

}
