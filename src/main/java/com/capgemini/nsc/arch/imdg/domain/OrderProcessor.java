package com.capgemini.nsc.arch.imdg.domain;

import java.util.Collection;

import com.google.common.base.Function;

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
	Collection<Order> process(Collection<Order> ordersToProcess, Function<Order, Order> useCase);

}
