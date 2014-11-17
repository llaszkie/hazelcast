/**
 * 
 */
package com.capgemini.nsc.arch.imdg.application;

import com.capgemini.nsc.arch.imdg.details.processing.ForEachOrderProcessor;
import com.capgemini.nsc.arch.imdg.details.storage.jpa.JpaOrderRepository;
import com.capgemini.nsc.arch.imdg.domain.Order;
import com.capgemini.nsc.arch.imdg.domain.OrderProcess;

/**
 * Orchestration for domain logic.
 * 
 * @author LLASZKIE
 *
 */
public class OrderProcessUC {
	
	/**
	 * Use Case for processing orders using JPA and default algorithm
	 * 
	 * @param numberOfOrdersToProcess
	 * @return count of processed {@link Order}s
	 */
	public int processWithDB(int numberOfOrdersToProcess) {
		return new OrderProcess(
				new JpaOrderRepository(), 
				new ForEachOrderProcessor())
			.process(numberOfOrdersToProcess);
	}

}
