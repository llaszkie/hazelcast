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
	 * @return
	 */
	default Collection<Order> process(Collection<Order> ordersToProcess, Consumer<Order> useCase) {
		ordersToProcess.forEach(useCase);
		return ordersToProcess;
	}

}
