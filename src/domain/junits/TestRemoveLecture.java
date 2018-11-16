package domain.junits;

import static org.junit.Assert.*;
import org.junit.Test;

import domain.classes.Room;
import domain.classes.Schedule;

/**
 * @author Xavier Martín Ballesteros
*/

public class TestRemoveLecture {
	@Test
	public void testRemoveLecture1() {
		Schedule schedule = new Schedule();
		
		Room room = new Room("A6001", 90, false);
		schedule.putLecture(room.toString(), 1, 10, "FM-12-LABORATORY");
		
		boolean result = schedule.removeLecture("A6002", 1, 10);
		assertTrue(!result);
	}
	
	@Test
	public void testRemoveLecture2() {
		Schedule schedule = new Schedule();
		
		Room room = new Room("A6001", 90, false);
		schedule.putLecture(room.toString(), 1, 10, "FM-12-LABORATORY");
		
		boolean result = schedule.removeLecture(room.toString(), 1, 10);
		assertTrue(result);
	}
}
