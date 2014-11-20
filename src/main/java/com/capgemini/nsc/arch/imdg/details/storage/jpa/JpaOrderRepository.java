/**
 * 
 */
package com.capgemini.nsc.arch.imdg.details.storage.jpa;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.capgemini.nsc.arch.imdg.domain.Order;
import com.capgemini.nsc.arch.imdg.domain.OrderRepository;

/**
 * @author LLASZKIE
 *
 */
public class JpaOrderRepository implements OrderRepository {

	private EntityManager entityManager = EntityManagerProvider
			.getEntityManager();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.capgemini.nsc.arch.imdg.domain.OrderRepository#loadOrders(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Long, Order> loadOrders(int numberOfOrdersToLoad) {
		startTransactionEntityManager();
		List<OrderEntity> orderEntities = entityManager
				.createQuery("from OrderEntity").setMaxResults(numberOfOrdersToLoad)
				.getResultList();
		flushAndCommitEntityManager();

		return transform(orderEntities);
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
		startTransactionEntityManager();
		for (Order order : updatedOrders.values()) {
			OrderEntity orderEntity = entityManager.find(OrderEntity.class,
					order.getId());
			orderEntity.setTotal(order.getTotal());
		}
		flushAndCommitEntityManager();
	}

	
	/**
	 * Pattern applied for iterating over large result set
	 * 
	 * @param offset
	 * @param max
	 * @return
	 */
	public Collection<Order> loadOrdersIterable(int offset, int max)
	{
		startTransactionEntityManager();
		List<OrderEntity> orderEntities = entityManager
				.createQuery("from OrderEntity", OrderEntity.class)
				.setFirstResult(offset).setMaxResults(max).getResultList();
		flushAndCommitEntityManager();
		
		return transform(orderEntities).values();
	}

	// ---------- 
	// protected
	// ----------

	Order loadOrder(long orderId) {
		startTransactionEntityManager();		
		OrderEntity orderEntity = entityManager.find(OrderEntity.class, orderId);
		flushAndCommitEntityManager();
		
		return orderEntity != null ? orderFromEntity(orderEntity) : null;
	}

	
	// ---------- 
	// private
	// ----------

	private void flushAndCommitEntityManager() {
		entityManager.flush();
		entityManager.clear();
		entityManager.getTransaction().commit();
	}

	private Order orderFromEntity(OrderEntity orderEntity) {
		return new Order(orderEntity.getId(), orderEntity.getPayload(), orderEntity.getTotal() == null ? 0 : orderEntity.getTotal());
	}

	private void startTransactionEntityManager() {
		entityManager.getTransaction().begin();
	}

	private Map<Long, Order> transform(List<OrderEntity> orderEntities) {
		Map<Long, Order> orders = new HashMap<Long, Order>(orderEntities.size());
		for (OrderEntity orderEntity : orderEntities) {
			orders.put(orderEntity.getId(), orderFromEntity(orderEntity));
		}
		return orders;
	}

	
}

