package domain.junits;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Xavier Mart�n Ballesteros
*/

@RunWith(value = Suite.class)
@SuiteClasses(value = { TestCreator.class, 
                        TestGetSchedule.class, 
                        TestEquals.class,
                        TestPutLecture.class,
                        TestRemoveLecture.class,
                        TestToJSONString.class})
public class TestingAll {}
