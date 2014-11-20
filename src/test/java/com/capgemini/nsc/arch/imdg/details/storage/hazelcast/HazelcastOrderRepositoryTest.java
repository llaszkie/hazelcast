/**
 * 
 */
package com.capgemini.nsc.arch.imdg.details.storage.hazelcast;

import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.capgemini.nsc.arch.imdg.details.storage.jpa.JpaOrderRepository;
import com.capgemini.nsc.arch.imdg.domain.Order;

/**
 * Test for {@link JpaOrderRepository}
 * 
 * @author LLASZKIE
 *
 */
public class HazelcastOrderRepositoryTest {

	private HazelcastOrderRepository sut = new HazelcastOrderRepository();
	
	/**
	 * Test method for {@link com.capgemini.nsc.arch.imdg.details.storage.hazelcast.HazelcastOrderRepository#loadOrders(int)}.
	 */
	@Test
	public void testLoadOrders() {
		// given
		int expectedNumberOfOrders = 10;
		// when 
		Map<Long, Order> orders = sut.loadOrders(expectedNumberOfOrders);
		// then
		Assert.assertFalse(orders.isEmpty());
		Assert.assertEquals(expectedNumberOfOrders, orders.size());
	}
	
	/**
	 * Test method for {@link com.capgemini.nsc.arch.imdg.details.storage.hazelcast.HazelcastOrderRepository#loadOrder(long)}.
	 */
	@Test
	public void testLoadOrder() {
		// given
		Order expectedOrder = sut.loadOrders(1).values().toArray(new Order[0])[0];
		// when
		Order loadedOrder = sut.loadOrder(expectedOrder.getId());
		// then
		Assert.assertNotNull(loadedOrder);
		Assert.assertEquals(expectedOrder, loadedOrder);
	}
	
}
