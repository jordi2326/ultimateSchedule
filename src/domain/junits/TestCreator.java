package domain.junits;

import static org.junit.Assert.*;
import org.junit.Test;

import domain.classes.Schedule;

import java.util.HashMap;

public class TestCreator {
	@Test
	public void testCreator1() {
		Schedule schedule = new Schedule();

		assertEquals(schedule.getSchedule(), new HashMap<String, String[][]>());
	}
	
	@Test
	public void testCreator2() {
		Schedule schedule = new Schedule();
		schedule.putLecture("A6001", 1, 10, "FM-10-LABORATORY");
		schedule.putLecture("A5S102", 4, 2, "FM-10-THEORY");
		
		Schedule SCFinal = new Schedule(schedule.toJsonString());
		assertTrue(SCFinal.equals(schedule)); // Vaig a un lloc del mapa que no existeix (0, 0)
	}
}
