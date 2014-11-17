/**
 * 
 */
package com.capgemini.nsc.arch.imdg.domain;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

/**
 * @author LLASZKIE
 *
 */
public class OrderTest {

	private static final Integer TOTAL_NOT_YET_CALCULATED = 0;

	/**
	 * Test method for {@link com.capgemini.nsc.arch.imdg.domain.Order#calculateTotal()}.
	 */
	@Test
	public void testCalculateTotal() {
		// given
		Order order = new Order(0, "ANY", TOTAL_NOT_YET_CALCULATED);
		
		// when
		order.calculateTotal();
		
		// then
		assertFalse(order.getTotal() == TOTAL_NOT_YET_CALCULATED);
	}

}
