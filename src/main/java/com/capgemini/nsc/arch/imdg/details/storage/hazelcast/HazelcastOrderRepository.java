/**
 * 
 */
package com.capgemini.nsc.arch.imdg.details.storage.hazelcast;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import com.capgemini.nsc.arch.imdg.details.storage.jpa.EntityManagerProvider;
import com.capgemini.nsc.arch.imdg.details.storage.jpa.JpaOrderRepository;
import com.capgemini.nsc.arch.imdg.domain.Order;
import com.capgemini.nsc.arch.imdg.domain.OrderRepository;

/**
 * Hazelcast based IMDG repository
 * 
 * @author LLASZKIE
 *
 */
public class HazelcastOrderRepository implements OrderRepository {

	private EntityManager entityManager = EntityManagerProvider.getEntityManager();

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
		List<Order> distributedOrderList = HazelcastClientProvider.hazelcast.getList("orders");
		distributedOrderList.clear();

		int offset = 0;
		List<Order> orders;
		// read the data in a scrollable way ...
		scrolling: while ((orders = jpaOrderRepository.loadOrdersIterable(offset, 100)).size() > 0) {
			System.out.println("Loading orders: " + offset + "/" + numberOfOrdersToLoad);
			startTransactionEntityManager();
			for (Order order : orders) {
				// and feed the cluster :-)
				if (distributedOrderList.size() < numberOfOrdersToLoad) {
					distributedOrderList.add(order);
				} else {
					flushAndCommitEntityManager();
					break scrolling;
				}
			}
			flushAndCommitEntityManager();
			offset += orders.size();
		}

		return distributedOrderList;
	}

	private void startTransactionEntityManager() {
		entityManager.getTransaction().begin();
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

	private void flushAndCommitEntityManager() {
		entityManager.flush();
		entityManager.clear();
		entityManager.getTransaction().commit();
	}

}
