package com.capgemini.nsc.arch.imdg;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.capgemini.nsc.arch.imdg.details.storage.jpa.JpaOrderRepositoryTest;
import com.capgemini.nsc.arch.imdg.domain.OrderProcessTest;
import com.capgemini.nsc.arch.imdg.domain.OrderTest;

@RunWith(Suite.class)
@SuiteClasses({ JpaOrderRepositoryTest.class, OrderTest.class, OrderProcessTest.class })
public class AllTests {

}
