/**
 * 
 */
package com.capgemini.nsc.arch.imdg.application;

import com.capgemini.nsc.arch.imdg.details.processing.OrderStreamProcessor;
import com.capgemini.nsc.arch.imdg.details.storage.hazelcast.HazelcastOrderRepository;
import com.capgemini.nsc.arch.imdg.details.storage.jpa.JpaOrderRepository;
import com.capgemini.nsc.arch.imdg.domain.Order;
import com.capgemini.nsc.arch.imdg.domain.OrderProcess;
import com.capgemini.nsc.arch.imdg.domain.OrderProcessor;

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
	 * @param timerName 
	 * @return count of processed {@link Order}s
	 */
	public int processWithDB(int numberOfOrdersToProcess, String timerName) {
		return new OrderProcess(
				new JpaOrderRepository(), 
				new OrderProcessor() {})
			.process(numberOfOrdersToProcess, timerName);
	}
	
	/**
	 * Use Case for processing orders using JPA and parallel stream algorithm
	 * 
	 * @param numberOfOrdersToProcess
	 * @param timerName 
	 * @return count of processed {@link Order}s
	 */
	public int processWithDBAndStream(int numberOfOrdersToProcess, String timerName) {
		return new OrderProcess(
				new JpaOrderRepository(), 
				new OrderStreamProcessor() {})
			.process(numberOfOrdersToProcess, timerName);
	}

	/**
	 * Use Case for processing orders using Hazelcast and default algorithm
	 * 
	 * @param numberOfOrdersToProcess
	 * @param timerName 
	 * @return count of processed {@link Order}s
	 */
	public int processWithHazelcast(int numberOfOrdersToProcess, String timerName) {
		return new OrderProcess(
				new HazelcastOrderRepository(), 
				new OrderProcessor() {})
			.process(numberOfOrdersToProcess, timerName);
	}

}
