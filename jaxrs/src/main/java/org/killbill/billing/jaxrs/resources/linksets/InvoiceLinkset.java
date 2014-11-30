package org.killbill.billing.jaxrs.resources.linksets;

import java.util.Map;

import org.killbill.billing.jaxrs.resources.JaxrsResource;

import us.norskog.simplehal.Hyper;
import us.norskog.simplehal.Link;
import us.norskog.simplehal._Links;

/**
 * Items for embedded set of invoice
 * Each bundle has a subscriptions. don't know if these are always the same subscription?
 * 
 * 
 * @author lance
 *
 */

public class InvoiceLinkset extends Hyper {

	@Override
	@_Links(links = { 
			@Link(rel = "invoiceId", href = JaxrsResource.INVOICES_PATH + "/${response.invoiceId")}
			)
	public Map<String, ? extends Object> getLink(Object base) {
		return null;
	}

}
