package com.capgemini.nsc.arch.imdg.domain;

import java.util.Collection;
import java.util.function.Consumer;

import com.codahale.metrics.Timer;
import com.codahale.metrics.Timer.Context;

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
		Timer jpaProcessDefaultTimer = Metrics.registry.timer(timerName + "process");
		for (Order order : ordersToProcess) {
			Context time = jpaProcessDefaultTimer.time();
			useCase.accept(order);
			time.close();
		}

		return ordersToProcess;
	}

}
