package domain.junits;

import static org.junit.Assert.*;
import org.junit.Test;

import domain.classes.Room;
import domain.classes.Schedule;

public class TestPutLecture {
	@Test
	public void testPutLecture1() {
		Schedule schedule = new Schedule();
		Schedule SCFinal = new Schedule();
		
		Room room = new Room("A6001", 90, false);
		String[][] horariAula = new String[5][12];
		horariAula[1][10] = "PRO1-10-THEORY";
		SCFinal.getSchedule().put("A6001", horariAula);
		
		boolean result = schedule.putLecture(room.toString(), 1, 10, "PRO1-10-THEORY");
		if (result) {
			assertNotNull(schedule.getSchedule().get(room.toString())[1][10]);
			assertEquals(SCFinal.getSchedule().get(room.toString())[1][10], schedule.getSchedule().get(room.toString())[1][10]);
		}
	}

	@Test //putLecture
	public void testPutLecture2() {
		Schedule schedule = new Schedule();
		
		Room room = new Room("A6001", 90, false);
		
		boolean result = schedule.putLecture(room.toString(), 1, 10, "FM-12-LABORATORY");
		if (result) {
			assertEquals("FM-12-LABORATORY", schedule.getSchedule().get(room.toString())[1][10]);
		}
	}
}