/**
 * 
 */
package com.capgemini.nsc.arch.imdg.domain;

import java.util.Collection;

import com.codahale.metrics.Timer;
import com.codahale.metrics.Timer.Context;

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
	 * @param timerName
	 * @return count of processed {@link Order}s
	 */
	public int process(int numberOfOrdersToProcess, String timerName) {
		Collection<Order> ordersToProcess = loadOrders(numberOfOrdersToProcess, timerName);
		Collection<Order> processedOrders = processOrders(ordersToProcess, timerName);
		saveOrders(processedOrders, timerName);
		return processedOrders.size();
	}

	private Collection<Order> loadOrders(int numberOfOrdersToProcess,
			String timerName) {
		Timer jpaLoadDefaultTimer = Metrics.registry.timer(timerName + "load");
		Context time = jpaLoadDefaultTimer.time();
		Collection<Order> ordersToProcess = orderRepository.loadOrders(numberOfOrdersToProcess);
		time.close();
		return ordersToProcess;
	}

	private Collection<Order> processOrders(Collection<Order> ordersToProcess,
			String timerName) {
		Timer jpaProcessDefaultTimer = Metrics.registry.timer(timerName + "process");
		Context time = jpaProcessDefaultTimer.time();
		Collection<Order> processedOrders = orderProcessor.process(ordersToProcess, o -> o.calculateTotal(), timerName);
		time.close();
		return processedOrders;
	}

	private void saveOrders(Collection<Order> processedOrders, String timerName) {
		Timer jpaSaveDefaultTimer = Metrics.registry.timer(timerName + "save");
		Context time = jpaSaveDefaultTimer.time();
		orderRepository.save(processedOrders);
		time.close();
	}

}
