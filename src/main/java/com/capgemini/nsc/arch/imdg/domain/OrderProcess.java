/**
 * 
 */
package com.capgemini.nsc.arch.imdg.domain;

import java.util.Collection;

import com.google.common.base.Function;

/**
 * @author LLASZKIE
 *
 */
public class OrderProcess {
	
	private OrderRepository orderRepository;
	private OrderProcessor orderProcessor;
	
	
	/**
	 * C'tor
	 * 
	 * @param orderRepository
	 * @param orderProcessor
	 */
	public OrderProcess(OrderRepository orderRepository,
			OrderProcessor orderProcessor) {
		super();
		this.orderRepository = orderRepository;
		this.orderProcessor = orderProcessor;
	}


	/**
	 * business process implementation 
	 * 
	 * @param numberOfOrdersToProcess
	 * @return count of processed {@link Order}s
	 */
	public int process(int numberOfOrdersToProcess) {
		Collection<Order> ordersToProcess = orderRepository.loadOrders(numberOfOrdersToProcess);
		Collection<Order> processedOrders = orderProcessor.process(ordersToProcess, 
				new Function<Order, Order>() {
					@Override
					public Order apply(Order input) {
						input.calculateTotal();
						return input;
					}
				});
		orderRepository.save(processedOrders);
		return processedOrders.size();
	}
	
}
