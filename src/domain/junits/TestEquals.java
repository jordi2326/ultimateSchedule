package domain.junits;

import static org.junit.Assert.*;
import org.junit.Test;

import domain.classes.Schedule;

/**
 * @author Xavier Martín Ballesteros
*/

public class TestEquals {
	@Test
	public void testEquals1() {
		Schedule schedule = new Schedule();
		schedule.putLecture("A6001", 1, 10, "FM-10-LABORATORY");
		schedule.putLecture("A5S102", 4, 2, "FM-10-THEORY");
		
		Schedule SCFinal = new Schedule();
		SCFinal.putLecture("A6001", 1, 10, "FM-10-LABORATORY");
		SCFinal.putLecture("A5S102", 4, 2, "FM-10-THEORY");
		
		assertTrue(SCFinal.equals(schedule));
	}
}