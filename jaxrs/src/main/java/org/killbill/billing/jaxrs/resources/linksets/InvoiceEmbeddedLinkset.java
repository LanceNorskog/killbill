package org.killbill.billing.jaxrs.resources.linksets;

import java.util.Map;

import org.killbill.billing.jaxrs.resources.JaxrsResource;

import us.norskog.simplehal.Hyper;
import us.norskog.simplehal.Items;
import us.norskog.simplehal._Embedded;
import us.norskog.simplehal._Links;
import us.norskog.simplehal.Link;

/**
 * InvoiceItem lists for InvoiceJson return
 * @author lance
 *
 */
public class InvoiceEmbeddedLinkset extends Hyper {

	@Override
	@_Embedded(value = { @Items(items = "", name = "") })
	public Map<String, ? extends Object> getLink(Object base) {
		return null;
	}

}
