/**
 * 
 */
package com.capgemini.nsc.arch.imdg.domain;

import java.util.Collection;

/**
 * @author LLASZKIE
 *
 */
public interface OrderRepository {

	/**
	 * Port for repository delivery: load
	 * 
	 * @param numberOfOrdersToLoad Loads given number of {@link Order}
	 * @return
	 */
	Collection<Order> loadOrders(int numberOfOrdersToLoad);

	/**
	 * Port for repository delivery: save
	 * 
	 * @param updatedOrders to be saved
	 */
	void save(Collection<Order> updatedOrders);

}
