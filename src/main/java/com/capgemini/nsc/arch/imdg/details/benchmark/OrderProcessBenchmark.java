/**
 * 
 */
package com.capgemini.nsc.arch.imdg.details.benchmark;

import com.capgemini.nsc.arch.imdg.application.OrderProcessUC;
import com.capgemini.nsc.arch.imdg.details.storage.jpa.JpaOrderRepository;
import com.capgemini.nsc.arch.imdg.domain.OrderProcess;
import com.google.caliper.Benchmark;
import com.google.caliper.Param;

/**
 * @author LLASZKIE
 *
 */
public final class OrderProcessBenchmark {

	@Param({
		"1",
		"10",
		"100"
	}) 
	int numberOfOrdersToProcess;  // -DnumberOfOrdersToProcess=1,2,3
	
	/**
	 * Benchmark for {@link OrderProcess} based on:
	 * 	1) JPA storage: {@link JpaOrderRepository}
	 *  2) Default processing 
	 * 
	 * @param reps
	 * @return
	 */
	@Benchmark int jpaStoreDefaultProcessing(int reps) {
		int numberOfOrdersToProcess = this.numberOfOrdersToProcess;
		OrderProcessUC uc = new OrderProcessUC();
		int result = 0;
		for (int i = 0; i < reps; i++) {
			result |= uc.processWithDB(numberOfOrdersToProcess);
		}
		return result;
	}
}
