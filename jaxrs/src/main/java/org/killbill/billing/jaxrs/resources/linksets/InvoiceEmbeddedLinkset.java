package org.killbill.billing.jaxrs.resources.linksets;

import org.killbill.billing.jaxrs.resources.JaxrsResource;

import us.norskog.simplehal._Links;
import us.norskog.simplehal.Link;

@_Links(links = @Link(rel = "invoiceId", href = JaxrsResource.INVOICES_PATH + "/${item.invoiceId"))
public class InvoiceEmbeddedLinkset {

}
