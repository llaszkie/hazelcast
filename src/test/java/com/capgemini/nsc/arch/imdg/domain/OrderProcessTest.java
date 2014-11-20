/**
 * 
 */
package com.capgemini.nsc.arch.imdg.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Test;

/**
 * Tests for {@link OrderProcess}
 * 
 * @author LLASZKIE
 *
 */
public class OrderProcessTest {
	
	/**
	 * Test method for {@link com.capgemini.nsc.arch.imdg.domain.OrderProcess#process(int)}.
	 */
	@Test
	public void testProcess() {
		// given
		final List<Order> ordersToProcess = Arrays.asList(
				new Order(1, "1", 0), new Order(2, "2", 0)
				);
		OrderProcessor mockedOrderProcessor = new OrderProcessor() {};
		OrderRepository mockedOrderRepository = new OrderRepository() {
			@Override
			public Map<Long, Order> loadOrders(int numberOfOrdersToLoad) {
				return ordersToProcess.stream().limit(numberOfOrdersToLoad).collect(Collectors.toMap(o -> o.getId(), o -> o));
			}

			@Override
			public void save(Map<Long, Order> updatedOrders) {
			}
		};
		OrderProcess sut = new OrderProcess(mockedOrderRepository, mockedOrderProcessor);
				
		// when
		int processed = sut.process(1, "FAKE");
		
		// then
		assertEquals(1, processed);
		assertTrue(orderWasProcessed(ordersToProcess.get(0)));   
		assertFalse(orderWasProcessed(ordersToProcess.get(1)));
	}

	/**
	 * {@link Order} was processed if total was calculated
	 *  
	 * @param o to check
	 * @return <code>true</code> if processed
	 */
	private boolean orderWasProcessed(Order o) {
		return o.getTotal() > 0;
	}
}
