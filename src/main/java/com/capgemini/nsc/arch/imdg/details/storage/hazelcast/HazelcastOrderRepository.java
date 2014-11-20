/**
 * 
 */
package com.capgemini.nsc.arch.imdg.details.storage.hazelcast;

import java.util.Collection;
import java.util.Map;

import com.capgemini.nsc.arch.imdg.details.storage.jpa.JpaOrderRepository;
import com.capgemini.nsc.arch.imdg.domain.Order;
import com.capgemini.nsc.arch.imdg.domain.OrderRepository;
import com.hazelcast.core.IMap;

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
	public Map<Long, Order> loadOrders(int numberOfOrdersToLoad) {
		// source of data
		JpaOrderRepository jpaOrderRepository = new JpaOrderRepository();
		// distributed collection
		initilizeDistributedCollection();

		int offset = 0;
		Collection<Order> orders;
		// read the data in a scrollable way ...
		IMap<Long,Order> distributedOrderMap = getDistributedOrderMap();
		scrolling: while ((orders = jpaOrderRepository.loadOrdersIterable(offset, 100)).size() > 0) {
			System.out.println("Loading orders: " + offset + "/" + numberOfOrdersToLoad);
			for (Order order : orders) {
				// and feed the cluster :-)
				if (distributedOrderMap.size() < numberOfOrdersToLoad) {
					distributedOrderMap.put(order.getId(), order);
				} else {
					break scrolling;
				}
			}
			offset += orders.size();
		}

		return distributedOrderMap;
	}

	Order loadOrder(long orderId) {
		IMap<Long,Order> distributedOrderMap = getDistributedOrderMap();
		return distributedOrderMap.get(orderId);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.capgemini.nsc.arch.imdg.domain.OrderRepository#save(java.util.Collection
	 * )
	 */
	@Override
	public void save(Map<Long, Order> updatedOrders) {
		// Nothing to do here :-)
	}

	// ---------- 
	// private
	// ----------
	
	private IMap<Long, Order> getDistributedOrderMap() {
		return HazelcastClientProvider.hazelcast.getMap("orders");
	}

	private void initilizeDistributedCollection() {
		IMap<Long,Order> distributedOrderMap = getDistributedOrderMap();
		distributedOrderMap.clear();
	}

}
