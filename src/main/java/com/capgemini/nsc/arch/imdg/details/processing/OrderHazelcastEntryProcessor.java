/**
 * 
 */
package com.capgemini.nsc.arch.imdg.details.processing;

import java.io.Serializable;
import java.util.Map;
import java.util.function.Consumer;

import com.capgemini.nsc.arch.imdg.domain.Order;
import com.capgemini.nsc.arch.imdg.domain.OrderProcessor;
import com.hazelcast.core.IMap;
import com.hazelcast.map.EntryBackupProcessor;
import com.hazelcast.map.EntryProcessor;

/**
 * @author LLASZKIE
 *
 */
public class OrderHazelcastEntryProcessor implements OrderProcessor {

	private static class OrderEntryProcessor implements EntryProcessor<Long, Order>, EntryBackupProcessor<Long, Order>, Serializable {
		private static final long serialVersionUID = -7195595859957977381L;

		private Consumer<Order> useCase;
		
		public OrderEntryProcessor(Consumer<Order> useCase) {
			super();
			this.useCase = useCase;
		}

		@Override
		public Object process(Map.Entry<Long, Order> entry ) {
			processOrder(entry);
			return null;
	    }

		@Override
	    public void processBackup(Map.Entry<Long, Order> entry ) {
			processOrder(entry);
	    }

		@Override
		public EntryBackupProcessor<Long, Order> getBackupProcessor() {
			return OrderEntryProcessor.this;
		}

		private void processOrder(Map.Entry<Long, Order> entry) {
			Order orderToProcess = entry.getValue();
			useCase.accept(orderToProcess);
			entry.setValue(orderToProcess);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.capgemini.nsc.arch.imdg.domain.OrderProcessor#process(java.util.Map, java.util.function.Consumer, java.lang.String)
	 */
	@Override
	public Map<Long, Order> process(Map<Long, Order> ordersToProcess, Consumer<Order> useCase, String timerName) {
		if (ordersToProcess instanceof IMap) {
			IMap<Long, Order> distributedOrdersToProcess = (IMap<Long, Order>) ordersToProcess;
			distributedOrdersToProcess.executeOnEntries(new OrderEntryProcessor(useCase));
			return distributedOrdersToProcess;
		}
		return OrderProcessor.super.process(ordersToProcess, useCase, timerName);
	}
}
