/**
 * 
 */
package com.capgemini.nsc.arch.imdg.details.benchmark;

import java.util.concurrent.TimeUnit;

import com.capgemini.nsc.arch.imdg.application.OrderProcessUC;
import com.capgemini.nsc.arch.imdg.domain.Metrics;
import com.codahale.metrics.ConsoleReporter;

/**
 * @author LLASZKIE
 *
 */
public final class OrderProcessBenchmark {

	static final int[] numberOfOrdersToProcess = new int[] { 1, 10, 1000, 100000 };
	private static ConsoleReporter reporter;

	public static void main(String args[]) {
		startReport();
		benchmarkJpaStoreDefaultProcessing();
		stopReport();
		System.err.println("Finished!");
	}


	private static void benchmarkJpaStoreDefaultProcessing() {
		OrderProcessUC uc = new OrderProcessUC();
		for (int i : numberOfOrdersToProcess) {
			uc.processWithDB(i, "DB_" + i + "_");
		}
	}

	private static void startReport() {
		reporter = ConsoleReporter.forRegistry(Metrics.registry)
				.convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS).build();
		reporter.start(1, TimeUnit.MINUTES);
	}
	
	private static void stopReport() {
		reporter.report();
		reporter.stop();
	}
}
