/**
 * 
 */
package com.capgemini.nsc.arch.imdg.details.storage.jpa;

import java.util.Arrays;
import java.util.Collection;

import junit.framework.Assert;

import org.junit.Test;

import com.capgemini.nsc.arch.imdg.domain.Order;

/**
 * Test for {@link JpaOrderRepository}
 * 
 * @author LLASZKIE
 *
 */
public class JpaOrderRepositoryTest {

	private JpaOrderRepository sut = new JpaOrderRepository();
	
	/**
	 * Test method for {@link com.capgemini.nsc.arch.imdg.details.storage.jpa.JpaOrderRepository#loadOrders(int)}.
	 */
	@Test
	public void testLoadOrders() {
		// given
		int expectedNumberOfOrders = 10;
		// when 
		Collection<Order> orders = sut.loadOrders(expectedNumberOfOrders);
		// then
		Assert.assertFalse(orders.isEmpty());
		Assert.assertEquals(expectedNumberOfOrders, orders.size());
	}


	/**
	 * Test method for {@link com.capgemini.nsc.arch.imdg.details.storage.jpa.JpaOrderRepository#loadOrder(int)}.
	 */
	@Test
	public void testLoadOrder() {
		// given
		Collection<Order> orders = sut.loadOrders(1);
		Order expectedOrder = orders.iterator().next();
		// when 
		
		Order order = sut.loadOrder(expectedOrder.getId());
		
		// then
		Assert.assertEquals(expectedOrder, order);
	}

	/**
	 * Test method for {@link com.capgemini.nsc.arch.imdg.details.storage.jpa.JpaOrderRepository#save(java.util.Collection)}.
	 */
	@Test
	public void testSave() {
		// given
		Collection<Order> orders = sut.loadOrders(1);
		Order expectedOrder = orders.iterator().next();
		expectedOrder.calculateTotal();
		
		// when 
		sut.save(Arrays.asList(expectedOrder));
		Order savedOrder =sut.loadOrder(expectedOrder.getId());
		
		// then
		Assert.assertEquals(expectedOrder, savedOrder);
	}
	


	/**
	 * Test method for {@link com.capgemini.nsc.arch.imdg.details.storage.jpa.JpaOrderRepository#loadOrdersIterable(int, int)}.
	 */
	@Test
	public void testloadOrdersIterable() {
		// given - when
		Collection<Order> orders = sut.loadOrdersIterable(0, 1);
		Order expectedFirstOrder = orders.iterator().next();

		orders = sut.loadOrdersIterable(1, 1);
		Order expectedSecondOrder = orders.iterator().next();
		
		// then
		Assert.assertNotNull(expectedFirstOrder);
		Assert.assertNotNull(expectedSecondOrder);
		Assert.assertFalse(expectedFirstOrder.equals(expectedSecondOrder));
	}

}
