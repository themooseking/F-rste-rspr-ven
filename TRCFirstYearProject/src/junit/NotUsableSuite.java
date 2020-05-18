package junit;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(JUnitPlatform.class)

@SelectClasses({
	WrongCar.class,
	WrongCustomer.class,
	WrongProposal.class
})

public class NotUsableSuite {

}
