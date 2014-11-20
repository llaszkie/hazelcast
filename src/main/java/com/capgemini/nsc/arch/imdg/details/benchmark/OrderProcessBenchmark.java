/**
 * 
 */
package com.capgemini.nsc.arch.imdg.details.benchmark;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

import com.capgemini.nsc.arch.imdg.application.OrderProcessUC;
import com.codahale.metrics.ConsoleReporter;

/**
 * @author LLASZKIE
 *
 */
public final class OrderProcessBenchmark {

	static final int[] numberOfOrdersToProcess = new int[] { 10000 };
	private static ConsoleReporter reporter;

	public static void main(String args[]) throws Exception {
		waitForAffinity();
		startReport();
		benchmarkJpaStoreProcessing();
		benchmarkHazelcastProcessing();
		stopReport();
	}


	private static void waitForAffinity() throws IOException {
		System.err.println("My ID: " + ManagementFactory.getRuntimeMXBean().getName());
		// System.in.read();
		System.err.println("Roger that!");
	}


	private static void benchmarkJpaStoreProcessing() {
		OrderProcessUC uc = new OrderProcessUC();
		for (int i : numberOfOrdersToProcess) {
			uc.processWithDB(i, "DB_" + i + "_");
			uc.processWithDBAndStream(i, "DB_Stream_" + i + "_");
		}	
	}

	private static void benchmarkHazelcastProcessing() {
		OrderProcessUC uc = new OrderProcessUC();
		for (int i : numberOfOrdersToProcess) {
			uc.processWithHazelcast(i, "Hazeclast_" + i + "_");
			uc.processWithHazelcastAndEntryProcessor(i, "Hazeclast_EntryProcessor_" + i + "_");
		}	
	}

	private static void startReport() {
		reporter = ConsoleReporter.forRegistry(Metrics.registry)
				.convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS).build();
		reporter.start(30, TimeUnit.SECONDS);
	}
	
	private static void stopReport() {
		reporter.report();
		reporter.stop();
		System.err.println("Finished!");
	}
}
