package com.capgemini.nsc.arch.imdg.details.storage.hazelcast;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import com.hazelcast.config.Config;
import com.hazelcast.config.InMemoryFormat;
import com.hazelcast.core.Hazelcast;

public class HazelcastServerProvider {
	
	public static void main(String[] args) throws Exception {
		waitForAffinity();
		Config config = new Config();
		config.getMapConfig("default").setInMemoryFormat(InMemoryFormat.OBJECT);
		Hazelcast.newHazelcastInstance(config);
	}

	private static void waitForAffinity() throws IOException {
		System.err.println("My ID: " + ManagementFactory.getRuntimeMXBean().getName());
		// System.in.read();
		System.err.println("Roger that!");
	}

}
