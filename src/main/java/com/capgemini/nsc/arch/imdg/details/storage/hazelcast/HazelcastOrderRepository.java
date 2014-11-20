/**
 * 
 */
package com.capgemini.nsc.arch.imdg.details.storage.hazelcast;

import java.util.Collection;
import java.util.List;

import com.capgemini.nsc.arch.imdg.details.storage.jpa.JpaOrderRepository;
import com.capgemini.nsc.arch.imdg.domain.Order;
import com.capgemini.nsc.arch.imdg.domain.OrderRepository;
import com.hazelcast.core.IList;

/**
 * Hazelcast based IMDG repository
 * 
 * @author LLASZKIE
 *
 */
public class HazelcastOrderRepository implements OrderRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.capgemini.nsc.arch.imdg.domain.OrderRepository#loadOrders(int)
	 */
	@Override
	public Collection<Order> loadOrders(int numberOfOrdersToLoad) {
		// source of data
		JpaOrderRepository jpaOrderRepository = new JpaOrderRepository();
		// distributed collection
		initilizeDistributedCollection();

		int offset = 0;
		List<Order> orders;
		// read the data in a scrollable way ...
		List<Order> distributedOrderList = getDistributedOrderList();
		scrolling: while ((orders = jpaOrderRepository.loadOrdersIterable(offset, 100)).size() > 0) {
			System.out.println("Loading orders: " + offset + "/" + numberOfOrdersToLoad);
			for (Order order : orders) {
				// and feed the cluster :-)
				if (distributedOrderList.size() < numberOfOrdersToLoad) {
					distributedOrderList.add(order);
				} else {
					break scrolling;
				}
			}
			offset += orders.size();
		}

		return distributedOrderList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.capgemini.nsc.arch.imdg.domain.OrderRepository#save(java.util.Collection
	 * )
	 */
	@Override
	public void save(Collection<Order> updatedOrders) {
		// Nothing to do here :-)
	}

	// ---------- 
	// private
	// ----------
	
	private IList<Order> getDistributedOrderList() {
		return HazelcastClientProvider.hazelcast.getList("orders");
	}

	private void initilizeDistributedCollection() {
		List<Order> distributedOrderList = getDistributedOrderList();
		distributedOrderList.clear();
	}

}
