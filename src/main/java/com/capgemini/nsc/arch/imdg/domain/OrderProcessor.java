package com.capgemini.nsc.arch.imdg.domain;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Processor for orders
 * 
 * @author LLASZKIE
 *
 */
public interface OrderProcessor {

	/**
	 * Port for processing delivery
	 * 
	 * @param ordersToProcess
	 * @param timerName 
	 * @return
	 */
	default Map<Long, Order> process(Map<Long, Order> ordersToProcess, Consumer<Order> useCase, String timerName) {
		ordersToProcess.values().forEach(useCase);
		return ordersToProcess;
	}

}
