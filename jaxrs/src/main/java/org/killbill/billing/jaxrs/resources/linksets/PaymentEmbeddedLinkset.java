package org.killbill.billing.jaxrs.resources.linksets;

import java.util.Map;

import org.killbill.billing.jaxrs.resources.JaxrsResource;

import us.norskog.simplehal.Supplier;
import us.norskog.simplehal.Items;
import us.norskog.simplehal._Embedded;
import us.norskog.simplehal._Links;
import us.norskog.simplehal.Link;

/**
 * InvoiceItem lists for InvoiceJson return
 * @author lance
 *
 */
public class PaymentEmbeddedLinkset extends Supplier {

	@Override
	// links start with JaxrsResource.INVOICE_PAYMENTS_PATH
	@_Embedded(links = { @Items(items = "", name = "") })
	public Map<String, ? extends Object> getLink(Map<String,? extends Object> response) {
		return null;
	}

}
