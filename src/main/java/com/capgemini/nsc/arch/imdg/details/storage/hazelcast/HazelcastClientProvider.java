package com.capgemini.nsc.arch.imdg.details.storage.hazelcast;

import java.util.Map;

import com.capgemini.nsc.arch.imdg.domain.Order;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;

/**
 * Static {@link HazelcastClient} provider
 * 
 * @author LLASZKIE
 */
public class HazelcastClientProvider {
	public static final HazelcastInstance hazelcast = HazelcastClient.newHazelcastClient();
	
	/**
	 * some reading out of the cluster 
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Map<Long, Order> map = hazelcast.getMap("orders");
		System.out.println("Total: " + map.size());
		map.values().forEach(e -> System.out.println(e));
		System.out.println("Done!");
	}
}
