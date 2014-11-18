package com.capgemini.nsc.arch.imdg.domain;

import java.util.Collection;
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
	default Collection<Order> process(Collection<Order> ordersToProcess, Consumer<Order> useCase, String timerName) {
		ordersToProcess.forEach(useCase);
		return ordersToProcess;
	}

}
