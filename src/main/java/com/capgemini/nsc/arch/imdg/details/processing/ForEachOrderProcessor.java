/**
 * 
 */
package com.capgemini.nsc.arch.imdg.details.processing;

import java.util.Collection;

import com.capgemini.nsc.arch.imdg.domain.Order;
import com.capgemini.nsc.arch.imdg.domain.OrderProcessor;
import com.google.common.base.Function;

/**
 * @author LLASZKIE
 *
 */
public class ForEachOrderProcessor implements OrderProcessor {

	@Override
	public Collection<Order> process(Collection<Order> ordersToProcess,
			Function<Order, Order> useCase) {
		for (Order order : ordersToProcess) {
			useCase.apply(order);
		}
		return ordersToProcess;
	}

}
