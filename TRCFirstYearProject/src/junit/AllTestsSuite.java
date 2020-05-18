package junit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)

@SuiteClasses({ 
	JCar.class, 
	JCustomer.class, 
	JProposal.class })

public class AllTestsSuite {

}
