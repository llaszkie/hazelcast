/**
 * 
 */
package com.capgemini.nsc.arch.imdg.details.processing;

import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Consumer;

import org.junit.Test;

import com.capgemini.nsc.arch.imdg.details.storage.hazelcast.HazelcastOrderRepository;
import com.capgemini.nsc.arch.imdg.domain.Order;

/**
 * @author LLASZKIE
 *
 */
public class OrderHazelcastEntryProcessorTest {

	private HazelcastOrderRepository hazelcastOrderRepository = new HazelcastOrderRepository();
	private OrderHazelcastEntryProcessor sut = new OrderHazelcastEntryProcessor();
	
	/**
	 * Test method for {@link com.capgemini.nsc.arch.imdg.details.processing.OrderHazelcastEntryProcessor#process(java.util.Map, java.util.function.Consumer, java.lang.String)}.
	 */
	@Test
	public void testProcess() {
		// given
		Map<Long, Order> ordersToProcess = hazelcastOrderRepository.loadOrders(10);
		
		// when
		Map<Long, Order> processedOrders = sut.process(ordersToProcess, (Consumer<Order> & Serializable) o -> o.calculateTotal(), "FAKE");
		
		// then
		assertTrue(processedOrders.values().stream().allMatch(OrderHazelcastEntryProcessorTest::orderWasProcessed));
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
