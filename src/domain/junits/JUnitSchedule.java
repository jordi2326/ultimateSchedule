package domain.junits;

import domain.classes.Room;
import domain.classes.Schedule;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

class ScheduleJUnitTest {

	@Test
	void test() {
		Schedule schedule = new Schedule();
		Schedule SCFinal = new Schedule();
		
		Room room = new Room("A6001", 90, false);
		String[][] horariAula = new String[5][12];
		horariAula[1][10] = "PRO1-10-THEORY";
		SCFinal.getSchedule().put("A6001", horariAula);
		
		boolean result = schedule.putLecture(room.toString(), 1, 10, "PRO1-10-THEORY");
		if (result) {
			assertEquals(SCFinal.getSchedule().get(room.toString())[1][10], schedule.getSchedule().get(room.toString())[1][10], 0);
		}
	}
}