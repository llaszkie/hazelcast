/**
 * 
 */
package com.capgemini.nsc.arch.imdg.details.storage.jpa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

	Order loadOrder(long orderId) {
		entityManager.getTransaction().begin();
		OrderEntity orderEntity = entityManager.find(OrderEntity.class, orderId);
		entityManager.getTransaction().commit();
		
		return orderEntity != null ? orderFromEntity(orderEntity) : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.capgemini.nsc.arch.imdg.domain.OrderRepository#loadOrders(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Order> loadOrders(int numberOfOrdersToLoad) {
		entityManager.getTransaction().begin();
		List<OrderEntity> orderEntities = entityManager
				.createQuery("from OrderEntity").setMaxResults(numberOfOrdersToLoad)
				.getResultList();
		entityManager.getTransaction().commit();

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
	public void save(Collection<Order> updatedOrders) {
		entityManager.getTransaction().begin();
		for (Order order : updatedOrders) {
			OrderEntity orderEntity = entityManager.find(OrderEntity.class,
					order.getId());
			orderEntity.setTotal(order.getTotal());
		}
		entityManager.getTransaction().commit();
	}

	
	/**
	 * Pattern applied for iterating over large result set
	 * 
	 * @param offset
	 * @param max
	 * @return
	 */
	public List<Order> loadOrdersIterable(int offset, int max)
	{
		entityManager.getTransaction().begin();
		List<OrderEntity> orderEntities = entityManager
				.createQuery("from OrderEntity", OrderEntity.class)
				.setFirstResult(offset).setMaxResults(max).getResultList();
		entityManager.getTransaction().commit();
		
		return transform(orderEntities);
	}
	
	private Order orderFromEntity(OrderEntity orderEntity) {
		return new Order(orderEntity.getId(), orderEntity.getPayload(), orderEntity.getTotal() == null ? 0 : orderEntity.getTotal());
	}

	private List<Order> transform(List<OrderEntity> orderEntities) {
		List<Order> orders = new ArrayList<Order>(orderEntities.size());
		for (OrderEntity orderEntity : orderEntities) {
			orders.add(orderFromEntity(orderEntity));
		}
		return orders;
	}
	
	
	
}

