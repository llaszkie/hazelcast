/**
 * 
 */
package com.capgemini.nsc.arch.imdg.details.processing;

import java.util.Collection;
import java.util.function.Consumer;

import com.capgemini.nsc.arch.imdg.domain.Order;
import com.capgemini.nsc.arch.imdg.domain.OrderProcessor;

/**
 * @author LLASZKIE
 *
 */
public class OrderStreamProcessor implements OrderProcessor {

	/* (non-Javadoc)
	 * @see com.capgemini.nsc.arch.imdg.domain.OrderProcessor#process(java.util.Collection, java.util.function.Consumer, java.lang.String)
	 */
	@Override
	public Collection<Order> process(Collection<Order> ordersToProcess,
			Consumer<Order> useCase, String timerName) {
		ordersToProcess.stream().parallel().forEach(useCase);
		return ordersToProcess;
	}
	
	

}
