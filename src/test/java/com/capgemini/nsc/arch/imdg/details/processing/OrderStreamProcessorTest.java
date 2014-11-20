/**
 * 
 */
package com.capgemini.nsc.arch.imdg.details.processing;

import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.capgemini.nsc.arch.imdg.domain.Order;

/**
 * Tests for {@link OrderStreamProcessor}
 * 
 * @author LLASZKIE
 *
 */
public class OrderStreamProcessorTest {
	
	/**
	 * Test method for {@link OrderStreamProcessor#process(Collection, java.util.function.Consumer, String)}.
	 */
	@Test
	public void testProcess() {
		// given
		final Map<Long, Order> ordersToProcess = new HashMap<Long, Order>();
		ordersToProcess.put(1L, new Order(1, "1", 0));
		ordersToProcess.put(2L, new Order(2, "2", 0));
		OrderStreamProcessor sut = new OrderStreamProcessor();
				
		// when
		Map<Long, Order> processedOrders = sut.process(ordersToProcess, o -> o.calculateTotal(), "FAKE");
		
		// then
		assertTrue(processedOrders.values().stream().allMatch(OrderStreamProcessorTest::orderWasProcessed));
	}

	/**
	 * {@link Order} was processed if total was calculated
	 *  
	 * @param o to check
	 * @return <code>true</code> if processed
	 */
	private static boolean orderWasProcessed(Order o) {
		return o.getTotal() > 0;
	}
}
