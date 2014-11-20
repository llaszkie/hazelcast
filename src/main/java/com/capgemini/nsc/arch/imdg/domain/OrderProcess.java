/**
 * 
 */
package com.capgemini.nsc.arch.imdg.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Consumer;

import com.capgemini.nsc.arch.imdg.details.benchmark.Metrics;
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
		Map<Long, Order> ordersToProcess = loadOrders(numberOfOrdersToProcess, timerName);
		Map<Long, Order> processedOrders = processOrders(ordersToProcess, timerName);
		saveOrders(processedOrders, timerName);
		return processedOrders.size();
	}

	private Map<Long, Order> loadOrders(int numberOfOrdersToProcess,
			String timerName) {
		Context time = measureLoadStart(numberOfOrdersToProcess, timerName);
		Map<Long, Order> ordersToProcess = orderRepository.loadOrders(numberOfOrdersToProcess);
		time.close();
		return ordersToProcess;
	}

	private Map<Long, Order> processOrders(Map<Long, Order> ordersToProcess,
			String timerName) {
		Context time = measureProcessStart(timerName);
		Map<Long, Order> processedOrders = orderProcessor.process(ordersToProcess, (Consumer<Order> & Serializable) o -> o.calculateTotal(), timerName);
		time.close();
		return processedOrders;
	}

	private void saveOrders(Map<Long, Order> processedOrders, String timerName) {
		Context time = measureSaveStart(timerName);
		orderRepository.save(processedOrders);
		time.close();
	}

	// ------------------
	// Measure stuff
	// ------------------
	
	private Context measureLoadStart(int numberOfOrdersToProcess, String timerName) {
		System.out.println("Loading orders: " + numberOfOrdersToProcess);
		Timer jpaLoadDefaultTimer = Metrics.registry.timer(timerName + "load");
		Context time = jpaLoadDefaultTimer.time();
		return time;
	}

	private Context measureProcessStart(String timerName) {
		System.out.println("Processing orders.");
		Timer jpaProcessDefaultTimer = Metrics.registry.timer(timerName + "process");
		Context time = jpaProcessDefaultTimer.time();
		return time;
	}

	private Context measureSaveStart(String timerName) {
		System.out.println("Saving orders.");
		Timer jpaSaveDefaultTimer = Metrics.registry.timer(timerName + "save");
		Context time = jpaSaveDefaultTimer.time();
		return time;
	}

}
