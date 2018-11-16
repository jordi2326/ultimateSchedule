package domain.junits;

import static org.junit.Assert.*;
import org.junit.Test;

import domain.classes.Schedule;

public class TestGetSchedule {
	@Test
	public void testGetSchedule1() {
		Schedule schedule = new Schedule();
		Schedule SCFinal = new Schedule();

		schedule.putLecture("A6001", 1, 10, "FM-10-LABORATORY");
		schedule.putLecture("A5S102", 4, 2, "FM-10-THEORY");
		
		SCFinal.putLecture("A6001", 4, 2, "FM-10-LABORATORY");
		SCFinal.putLecture("A5S102", 1, 10, "FM-10-THEORY");

		assertNotEquals(schedule.getSchedule(), SCFinal.getSchedule());
	}
}
