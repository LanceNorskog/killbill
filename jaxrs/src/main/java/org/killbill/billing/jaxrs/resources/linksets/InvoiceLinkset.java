package org.killbill.billing.jaxrs.resources.linksets;

import org.killbill.billing.jaxrs.resources.JaxrsResource;

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

@_Links(links = { 
		@Link(rel = "invoiceId", href = JaxrsResource.INVOICES_PATH + "/${response.invoiceId")}
		)
public class InvoiceLinkset {

}
