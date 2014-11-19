package com.capgemini.nsc.arch.imdg.details.storage.hazelcast;

import java.util.List;

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
		List<Object> list = hazelcast.getList("orders");
		System.out.println("Total: " + list.size());
		list.forEach(e -> System.out.println(e));
		System.out.println("Done!");
	}
}
